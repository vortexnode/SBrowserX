package dev.vortexnode.sbrowserx.extension;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SBrowserXGoogleTranslator {
    private static final String TAG = "SBrowserXTranslate";
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);
    private static final String SEP = "\n<<<SBX_SEP>>>\n";

    public static final String[][] SUPPORTED_LANGUAGES = new String[][] {
            {"en", "English"},
            {"ru", "Русский (Russian)"},
            {"uk", "Українська (Ukrainian)"},
            {"es", "Español (Spanish)"},
            {"pt", "Português (Portuguese)"},
            {"de", "Deutsch (German)"},
            {"fr", "Français (French)"},
            {"it", "Italiano (Italian)"},
            {"pl", "Polski (Polish)"},
            {"tr", "Türkçe (Turkish)"},
            {"ar", "العربية (Arabic)"},
            {"hi", "हिन्दी (Hindi)"},
            {"zh-CN", "中文 简体 (Chinese Simplified)"},
            {"zh-TW", "中文 繁體 (Chinese Traditional)"},
            {"ja", "日本語 (Japanese)"},
            {"ko", "한국어 (Korean)"},
            {"nl", "Nederlands (Dutch)"},
            {"sv", "Svenska (Swedish)"},
            {"cs", "Čeština (Czech)"},
            {"ro", "Română (Romanian)"},
            {"el", "Ελληνικά (Greek)"},
            {"he", "עברית (Hebrew)"},
            {"th", "ไทย (Thai)"},
            {"vi", "Tiếng Việt (Vietnamese)"},
            {"id", "Bahasa Indonesia (Indonesian)"},
            {"ms", "Bahasa Melayu (Malay)"},
            {"fa", "فارسی (Persian)"},
            {"bg", "Български (Bulgarian)"},
            {"sr", "Српски (Serbian)"},
            {"hr", "Hrvatski (Croatian)"},
            {"sk", "Slovenčina (Slovak)"},
            {"hu", "Magyar (Hungarian)"},
            {"fi", "Suomi (Finnish)"},
            {"da", "Dansk (Danish)"},
            {"no", "Norsk (Norwegian)"},
            {"ca", "Català (Catalan)"},
            {"lt", "Lietuvių (Lithuanian)"},
            {"lv", "Latviešu (Latvian)"},
            {"et", "Eesti (Estonian)"},
            {"sl", "Slovenščina (Slovenian)"}
    };

    private SBrowserXGoogleTranslator() {}

    public static String getLanguageDisplayName(String code) {
        if (code == null) return "English";
        String norm = normalizeLang(code);
        for (String[] pair : SUPPORTED_LANGUAGES) {
            if (pair[0].equalsIgnoreCase(norm) || pair[0].equalsIgnoreCase(code)) {
                return pair[1];
            }
        }
        return code;
    }

    private static boolean isEnabled() {
        Context ctx = SBrowserXPrefs.getAppContext();
        return ctx == null || SBrowserXPrefs.isGoogleTranslateEnabled(ctx);
    }

    private static boolean isTranslateV2() {
        try {
            Class<?> cls = Class.forName("com.sec.android.app.sbrowser.common.extensions.SixStatus");
            Object inst = cls.getMethod("getInstance").invoke(null);
            Boolean v2 = (Boolean) cls.getMethod("isTranslateV2").invoke(inst);
            return v2 != null && v2;
        } catch (Throwable t) {
            return true;
        }
    }

    public static boolean handleGetSupport(Object service, long cb) {
        if (service == null || !isEnabled()) return false;
        String support = isTranslateV2() ? "ON_DEVICE_V2" : "ON_DEVICE";
        deliverArray(service, "getSupportResult", cb, new String[] {"Success", support});
        return true;
    }

    public static boolean handleGetMode(Object service, long cb) {
        if (service == null || !isEnabled()) return false;
        String mode = isTranslateV2() ? "ON_DEVICE_V2" : "CLOUD";
        deliverArray(service, "getModeResult", cb, new String[] {"Success", mode});
        return true;
    }

    public static boolean handleCreateTrans(Object service, long cb) {
        if (service == null || !isEnabled()) return false;
        deliverArray(service, "createTransResult", cb, new String[] {"Success"});
        return true;
    }

    public static boolean handleDestroyTrans(Object service, long cb) {
        if (service == null || !isEnabled()) return false;
        deliverArray(service, "destroyTransResult", cb, new String[] {"Success"});
        return true;
    }

    public static boolean handleClear(Object service, long cb) {
        if (service == null || !isEnabled()) return false;
        deliverString(service, "clearResult", cb, "Success");
        return true;
    }

    public static boolean handleRefresh(Object service, long cb) {
        if (service == null || !isEnabled()) return false;
        deliverString(service, "refreshResult", cb, "Success");
        return true;
    }

    public static boolean handleGetLangList(Object service, String direction, long cb) {
        if (service == null || !isEnabled()) return false;
        boolean v2 = isTranslateV2();
        int extra = v2 ? 0 : 1;
        String[] langs = new String[SUPPORTED_LANGUAGES.length + extra];
        if (!v2) langs[0] = "Success";
        for (int i = 0; i < SUPPORTED_LANGUAGES.length; i++) {
            langs[i + extra] = SUPPORTED_LANGUAGES[i][0];
        }
        String dir = direction != null ? direction : "TARGET";
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Method m = service.getClass().getMethod("getLangListResult", long.class, String.class, String[].class);
                m.invoke(service, cb, dir, langs);
            } catch (Throwable t) {
                Log.e(TAG, "deliver getLangListResult failed", t);
            }
        });
        return true;
    }

    public static boolean handleDetectionLang(Object service, String text, long cb) {
        if (service == null || !isEnabled()) return false;
        EXECUTOR.execute(() -> {
            String detected = "en";
            try {
                String sample = text != null && text.length() > 300 ? text.substring(0, 300) : (text != null ? text : "");
                if (!sample.trim().isEmpty()) {
                    String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx"
                            + "&sl=auto&tl=en&dt=t&q=" + URLEncoder.encode(sample, "UTF-8");
                    HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
                    conn.setConnectTimeout(8000);
                    conn.setReadTimeout(10000);
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 16) AppleWebKit/537.36 Chrome/145.0.0.0 Mobile Safari/537.36");
                    try {
                        if (conn.getResponseCode() == 200) {
                            JSONArray root = new JSONArray(readUtf8(conn.getInputStream()));
                            String lang = root.optString(2, "en");
                            if (lang != null && !lang.isEmpty()) detected = normalizeLang(lang);
                        }
                    } finally {
                        conn.disconnect();
                    }
                }
            } catch (Throwable t) {
                Log.e(TAG, "detectLang failed", t);
            }
            boolean v2 = isTranslateV2();
            String[] res = v2 ? new String[] {detected} : new String[] {"Success", detected};
            deliverArray(service, "detectionLangResult", cb, res);
        });
        return true;
    }

    public static boolean handleTranslate(
            Object sixTransService,
            String sourceLang,
            String targetLang,
            String[] textList,
            long nativeCallback
    ) {
        if (sixTransService == null || textList == null || textList.length == 0) return false;
        if (!isEnabled()) return false;

        Context ctx = SBrowserXPrefs.getAppContext();
        final String sl = (sourceLang == null || sourceLang.isEmpty() || "auto".equalsIgnoreCase(sourceLang))
                ? "auto" : normalizeLang(sourceLang);
        String resolvedTarget = targetLang;
        if ((resolvedTarget == null || resolvedTarget.isEmpty()) && ctx != null) {
            resolvedTarget = SBrowserXPrefs.getTranslateTargetLang(ctx);
        }
        final String tl = (resolvedTarget == null || resolvedTarget.isEmpty())
                ? "en" : normalizeLang(resolvedTarget);

        // Respect "Never translate languages" when source language is explicitly known and equals a never-translate entry
        if (ctx != null && !"auto".equals(sl) && !sl.equalsIgnoreCase(tl)) {
            Set<String> never = SBrowserXPrefs.getTranslateNeverLangs(ctx);
            Set<String> always = SBrowserXPrefs.getTranslateAlwaysLangs(ctx);
            if (never.contains(sl) && !always.contains(sl)) {
                deliverTranslatePayload(sixTransService, nativeCallback, textList);
                return true;
            }
        }

        EXECUTOR.execute(() -> {
            String[] results = new String[textList.length];
            try {
                int i = 0;
                while (i < textList.length) {
                    StringBuilder batch = new StringBuilder();
                    int start = i;
                    int count = 0;
                    while (i < textList.length && (count == 0 || batch.length() + safeLen(textList[i]) < 1600)) {
                        if (count > 0) batch.append(SEP);
                        batch.append(textList[i] != null ? textList[i] : "");
                        i++;
                        count++;
                    }
                    String translatedBatch = translateSingle(sl, tl, batch.toString());
                    String[] parts = translatedBatch.split("\\s*<<<SBX_SEP>>>\\s*", -1);
                    if (parts.length == count) {
                        for (int k = 0; k < count; k++) {
                            results[start + k] = parts[k];
                        }
                    } else {
                        for (int k = 0; k < count; k++) {
                            String item = textList[start + k];
                            results[start + k] = (item == null || item.trim().isEmpty())
                                    ? (item != null ? item : "")
                                    : translateSingle(sl, tl, item);
                        }
                    }
                }
            } catch (Throwable t) {
                Log.e(TAG, "Google Translate failed", t);
                for (int k = 0; k < textList.length; k++) {
                    if (results[k] == null) results[k] = textList[k] != null ? textList[k] : "";
                }
            }

            deliverTranslatePayload(sixTransService, nativeCallback, results);
        });
        return true;
    }

    private static void deliverTranslatePayload(Object service, long cb, String[] results) {
        boolean v2 = isTranslateV2();
        if (v2) {
            deliverArray(service, "translateResult", cb, results);
        } else {
            String[] wrapped = new String[results.length + 1];
            wrapped[0] = "Success";
            System.arraycopy(results, 0, wrapped, 1, results.length);
            deliverArray(service, "translateResult", cb, wrapped);
        }
    }

    private static int safeLen(String s) {
        return s == null ? 0 : s.length();
    }

    private static String normalizeLang(String code) {
        String c = code.trim().replace('_', '-');
        if (c.equalsIgnoreCase("zh-cn") || c.equalsIgnoreCase("zh-hans")) return "zh-CN";
        if (c.equalsIgnoreCase("zh-tw") || c.equalsIgnoreCase("zh-hant")) return "zh-TW";
        int dash = c.indexOf('-');
        return dash > 0 ? c.substring(0, dash).toLowerCase() : c.toLowerCase();
    }

    public static String translateTextSync(String sl, String tl, String text) throws Exception {
        return translateSingle(sl, tl, text);
    }

    private static String translateSingle(String sl, String tl, String text) throws Exception {
        if (text == null || text.trim().isEmpty()) return text != null ? text : "";
        String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx"
                + "&sl=" + URLEncoder.encode(sl, "UTF-8")
                + "&tl=" + URLEncoder.encode(tl, "UTF-8")
                + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(15000);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 16) AppleWebKit/537.36 Chrome/145.0.0.0 Mobile Safari/537.36");
        try {
            if (conn.getResponseCode() != 200) {
                return text;
            }
            String body = readUtf8(conn.getInputStream());
            JSONArray root = new JSONArray(body);
            JSONArray sentences = root.optJSONArray(0);
            if (sentences == null) return text;
            StringBuilder out = new StringBuilder();
            for (int j = 0; j < sentences.length(); j++) {
                JSONArray seg = sentences.optJSONArray(j);
                if (seg != null && !seg.isNull(0)) {
                    out.append(seg.optString(0, ""));
                }
            }
            return out.length() > 0 ? out.toString() : text;
        } finally {
            conn.disconnect();
        }
    }

    private static void deliverArray(Object service, String methodName, long cb, String[] payload) {
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Method m = service.getClass().getMethod(methodName, long.class, String[].class);
                m.invoke(service, cb, payload);
            } catch (Throwable t) {
                Log.e(TAG, "deliver " + methodName + " failed", t);
            }
        });
    }

    private static void deliverString(Object service, String methodName, long cb, String payload) {
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Method m = service.getClass().getMethod(methodName, long.class, String.class);
                m.invoke(service, cb, payload);
            } catch (Throwable t) {
                Log.e(TAG, "deliver " + methodName + " failed", t);
            }
        });
    }

    private static String readUtf8(InputStream is) throws Exception {
        try (InputStream in = is; ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buf = new byte[4096];
            int n;
            while ((n = in.read(buf)) != -1) {
                bos.write(buf, 0, n);
            }
            return new String(bos.toByteArray(), StandardCharsets.UTF_8);
        }
    }
}
