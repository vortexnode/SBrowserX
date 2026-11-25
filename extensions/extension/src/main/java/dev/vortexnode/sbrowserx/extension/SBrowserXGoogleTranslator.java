package dev.vortexnode.sbrowserx.extension;

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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class SBrowserXGoogleTranslator {
    private static final String TAG = "SBrowserXTranslate";
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);
    private static final String SEP = "\n<<<SBX_SEP>>>\n";

    private SBrowserXGoogleTranslator() {}

    public static boolean handleTranslate(
            Object sixTransService,
            String sourceLang,
            String targetLang,
            String[] textList,
            long nativeCallback
    ) {
        if (sixTransService == null || textList == null || textList.length == 0) return false;
        if (SBrowserXPrefs.getAppContext() != null
                && !SBrowserXPrefs.isGoogleTranslateEnabled(SBrowserXPrefs.getAppContext())) {
            return false;
        }

        final String sl = (sourceLang == null || sourceLang.isEmpty() || "auto".equalsIgnoreCase(sourceLang))
                ? "auto" : normalizeLang(sourceLang);
        final String tl = (targetLang == null || targetLang.isEmpty())
                ? "en" : normalizeLang(targetLang);

        EXECUTOR.execute(() -> {
            String[] results = new String[textList.length];
            try {
                // Batch chunks up to ~1800 chars per Google Translate HTTP call for speed
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
                        // Fallback: translate item-by-item if separator was altered
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

            deliverTranslateResult(sixTransService, nativeCallback, results);
        });
        return true;
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

    private static String translateSingle(String sl, String tl, String text) throws Exception {
        if (text == null || text.trim().isEmpty()) return text != null ? text : "";
        String urlStr = "https://translate.googleapis.com/translate_a/single?client=gtx"
                + "&sl=" + URLEncoder.encode(sl, "UTF-8")
                + "&tl=" + URLEncoder.encode(tl, "UTF-8")
                + "&dt=t&q=" + URLEncoder.encode(text, "UTF-8");
        HttpURLConnection conn = (HttpURLConnection) new URL(urlStr).openConnection();
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(15000);
        conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 15) AppleWebKit/537.36 Chrome/131.0.0.0 Mobile Safari/537.36");
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

    private static void deliverTranslateResult(Object service, long callbackId, String[] results) {
        new Handler(Looper.getMainLooper()).post(() -> {
            try {
                Method m = service.getClass().getMethod("translateResult", long.class, String[].class);
                m.invoke(service, callbackId, results);
            } catch (Throwable t) {
                Log.e(TAG, "deliverTranslateResult failed", t);
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
