package dev.vortexnode.sbrowserx.extension;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.graphics.drawable.GradientDrawable;
import android.graphics.Shader;
import android.graphics.Paint;
import android.graphics.LinearGradient;
import android.graphics.Color;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.os.Process;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dev.vortexnode.sbrowserx.extension.settings.SBrowserXTranslateSettingsActivity;
import dev.vortexnode.sbrowserx.extension.settings.SBrowserXExtensionsActivity;

public final class SBrowserXHooks {
    private static final String TAG = "SBrowserXHooks";
    private static volatile Object sActiveTransService;
    private static volatile WeakReference<Activity> sMainActivityRef = new WeakReference<>(null);

    private SBrowserXHooks() {}

    private static boolean isMainBrowserProcess() {
        try {
            if (Process.isIsolated()) return false;
            String processName = Application.getProcessName();
            return processName == null || !processName.contains(":");
        } catch (Throwable t) {
            return false;
        }
    }

    public static void onApplicationCreate(Application app) {
        if (app == null || !isMainBrowserProcess()) return;
        try {
            SBrowserXPrefs.init(app);
            SBrowserXWebRtcManager.applyToProfile(app);
        } catch (Throwable t) {
            Log.e(TAG, "onApplicationCreate failed", t);
        }
    }

    public static void onMainActivityResume(Activity activity) {
        if (activity != null) {
            sMainActivityRef = new WeakReference<>(activity);
        }
    }

    public static boolean openExtensionsManager(Context context) {
        return openInternalUrl(context, "internet://extensions/");
    }

    public static boolean openInternalUrl(Context context, String url) {
        if (context == null || url == null) return false;
        try {
            Activity main = sMainActivityRef.get();
            if (main != null && !main.isFinishing() && !main.isDestroyed()) {
                try {
                    Method loadNew = main.getClass().getMethod("loadUrlInNewTab", String.class);
                    loadNew.invoke(main, url);
                    Intent bringFront = new Intent(context, main.getClass());
                    bringFront.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    if (!(context instanceof Activity)) {
                        bringFront.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    }
                    context.startActivity(bringFront);
                    return true;
                } catch (Throwable t) {
                    Log.w(TAG, "Direct loadUrlInNewTab failed, using explicit MainActivity intent", t);
                }
            }
            Class<?> mainCls = Class.forName("com.sec.android.app.sbrowser.SBrowserMainActivity");
            Intent intent = new Intent(context, mainCls);
            intent.putExtra("create_new_tab", true);
            intent.putExtra("com.android.browser.application_id", context.getPackageName());
            intent.setData(Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);
            return true;
        } catch (Throwable t) {
            Log.e(TAG, "openInternalUrl failed for " + url, t);
            return false;
        }
    }

    public static void onCommandLineCustomFeatures() {
        if (!isMainBrowserProcess()) return;
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            if (ctx == null || !SBrowserXPrefs.isFingerprintProtectionEnabled(ctx)) return;

            Class<?> cmdClass = Class.forName("com.sec.android.app.sbrowser.init.SBrowserCommandLine");
            Method appendFeature = cmdClass.getMethod("appendEnableFeature", String.class);
            appendFeature.invoke(null, "WebAudioAntiFingerprint");
            appendFeature.invoke(null, "ScreenAntiFingerprint");
            appendFeature.invoke(null, "EnableScreenAntiFingerprintingV2");
            Log.i(TAG, "Enabled native anti-fingerprint features");
        } catch (Throwable t) {
            Log.e(TAG, "onCommandLineCustomFeatures failed", t);
        }
    }

    public static boolean isToolbarBlurAllowed(boolean stockSupported) {
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            if (ctx == null) return stockSupported;
            return SBrowserXPrefs.isBottomBarBlurEnabled(ctx);
        } catch (Throwable t) {
            return stockSupported;
        }
    }

    public static boolean isBottomBarBlurEnabled() {
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            return ctx == null || SBrowserXPrefs.isBottomBarBlurEnabled(ctx);
        } catch (Throwable t) {
            return true;
        }
    }

    public static boolean isAmoledBlackEnabled() {
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            if (ctx == null) return false;
            return SBrowserXPrefs.isAmoledBlackEnabled(ctx);
        } catch (Throwable t) {
            return false;
        }
    }

    private static int getGlassBaseAlpha() {
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            int transparency = ctx != null ? SBrowserXPrefs.getBottomBarTransparency(ctx) : 50;
            int opacity = 100 - transparency; // 15..85
            return clampByte(Math.round(255f * opacity / 100f));
        } catch (Throwable t) {
            return 0x80;
        }
    }

    private static int clampByte(int v) {
        return Math.max(0, Math.min(255, v));
    }

    private static int withAlpha(int color, int alpha) {
        return (color & 0x00FFFFFF) | (clampByte(alpha) << 24);
    }

    private static boolean isDarkColor(int color) {
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        // Perceived luminance < 96
        return (r * 299 + g * 587 + b * 114) < 96000;
    }

    private static int normalizeGlassBaseRgb(int baseColor) {
        if (isAmoledBlackEnabled() && isDarkColor(baseColor)) {
            return 0xFF000000;
        }
        return baseColor | 0xFF000000;
    }

    private static boolean isFocusLayout(Context ctx) {
        if (ctx == null) return false;
        try {
            Class<?> cls = Class.forName("com.sec.android.app.sbrowser.common.device.DeviceLayoutUtil");
            Boolean focus = (Boolean) cls.getMethod("isFocusLayoutType", Context.class).invoke(null, ctx);
            if (!Boolean.TRUE.equals(focus)) return false;
            Boolean landscapeOrTab = (Boolean) cls.getMethod("isLandscapeOrTabletStyle", Context.class).invoke(null, ctx);
            return !Boolean.TRUE.equals(landscapeOrTab);
        } catch (Throwable t) {
            return false;
        }
    }

    public static int getAmoledBottomBarColor() {
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            boolean blurOn = ctx == null || SBrowserXPrefs.isBottomBarBlurEnabled(ctx);
            return blurOn ? withAlpha(0x000000, getGlassBaseAlpha()) : 0xFF000000;
        } catch (Throwable t) {
            return 0x80000000;
        }
    }

    public static GradientDrawable createToolbarGlassDrawable(Context ctx, int baseColor) {
        if (!isBottomBarBlurEnabled()) return null;
        try {
            int rgb = normalizeGlassBaseRgb(baseColor);
            int baseAlpha = getGlassBaseAlpha();
            int edgeAlpha = clampByte(Math.round(baseAlpha * 0.62f));
            int midAlpha = clampByte(Math.round(baseAlpha * 0.88f));
            int solidAlpha = clampByte(Math.round(baseAlpha * 1.06f));

            GradientDrawable gd = new GradientDrawable();
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            if (isFocusLayout(ctx)) {
                // Address bar is at bottom directly above Bottombar: soft fade from page into bar
                gd.setColors(new int[] { withAlpha(rgb, edgeAlpha), withAlpha(rgb, midAlpha) });
            } else {
                // Address bar is at top: denser near status bar, softer toward page
                gd.setColors(new int[] { withAlpha(rgb, solidAlpha), withAlpha(rgb, edgeAlpha) });
            }
            return gd;
        } catch (Throwable t) {
            return null;
        }
    }

    public static GradientDrawable createBottombarGlassDrawable(Context ctx, int baseColor) {
        try {
            int rgb = normalizeGlassBaseRgb(baseColor);
            GradientDrawable gd = new GradientDrawable();
            gd.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
            if (!isBottomBarBlurEnabled()) {
                gd.setColors(new int[] { withAlpha(rgb, 0xFF), withAlpha(rgb, 0xFF) });
                return gd;
            }
            int baseAlpha = getGlassBaseAlpha();
            int edgeAlpha = clampByte(Math.round(baseAlpha * 0.62f));
            int midAlpha = clampByte(Math.round(baseAlpha * 0.88f));
            int botAlpha = clampByte(Math.round(baseAlpha * 1.08f));
            if (isFocusLayout(ctx)) {
                // Seamless continuation from Focus Toolbar above it down to system navbar
                gd.setColors(new int[] { withAlpha(rgb, midAlpha), withAlpha(rgb, botAlpha) });
            } else {
                // Standalone bottom navigation bar: soft edge at top, slightly denser at bottom
                gd.setColors(new int[] { withAlpha(rgb, edgeAlpha), withAlpha(rgb, botAlpha) });
            }
            return gd;
        } catch (Throwable t) {
            GradientDrawable fallback = new GradientDrawable();
            fallback.setColor(baseColor);
            return fallback;
        }
    }

    public static Bitmap createGlassGradientBitmapOrNull(int baseColor, int width, int height, boolean fadeToBottom) {
        if (!isBottomBarBlurEnabled() || width <= 0 || height <= 0) return null;
        try {
            int rgb = normalizeGlassBaseRgb(baseColor);
            int baseAlpha = getGlassBaseAlpha();
            int softAlpha = clampByte(Math.round(baseAlpha * 0.65f));
            int denseAlpha = clampByte(Math.round(baseAlpha * 1.04f));
            int cTop = fadeToBottom ? withAlpha(rgb, denseAlpha) : withAlpha(rgb, softAlpha);
            int cBot = fadeToBottom ? withAlpha(rgb, softAlpha) : withAlpha(rgb, denseAlpha);

            Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bmp);
            Paint paint = new Paint();
            paint.setShader(new LinearGradient(0f, 0f, 0f, height, cTop, cBot, Shader.TileMode.CLAMP));
            canvas.drawRect(0f, 0f, width, height, paint);
            return bmp;
        } catch (Throwable t) {
            return null;
        }
    }

    public static float adjustToolbarBlurRadius(float stockRadius) {
        if (!isBottomBarBlurEnabled()) return stockRadius;
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            int transparency = ctx != null ? SBrowserXPrefs.getBottomBarTransparency(ctx) : 50;
            // Scale blur radius smoothly from 44f (low transparency) to 68f (high transparency)
            return 40f + (transparency * 0.34f);
        } catch (Throwable t) {
            return 56f;
        }
    }

    public static int adjustToolbarBlurTintColor(int stockColor) {
        if (!isBottomBarBlurEnabled()) return stockColor;
        try {
            int rgb = normalizeGlassBaseRgb(stockColor);
            // Keep Chromium compositor tint light so it doesn't double-stack with the View overlay
            int tintAlpha = clampByte(Math.round(getGlassBaseAlpha() * 0.42f));
            return withAlpha(rgb, tintAlpha);
        } catch (Throwable t) {
            return stockColor;
        }
    }

    public static boolean isHideSamsungAdBlockersEnabled() {
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            return ctx != null && SBrowserXPrefs.isHideSamsungAdBlockersEnabled(ctx);
        } catch (Throwable t) {
            return false;
        }
    }

    // --- Native Auto-Close Unused Tabs (adds "After 1 day" at index 1) ---

    public static List<String> getAutoCloseKeys() {
        return Arrays.asList(
                "close_never",
                "close_after_1day",
                "close_after_7days",
                "close_after_30days",
                "close_automatically"
        );
    }

    public static List<String> insertAutoClose1DayTitle(List<String> stockTitles) {
        List<String> out = new ArrayList<>();
        if (stockTitles != null && !stockTitles.isEmpty()) {
            out.add(stockTitles.get(0)); // Never
            out.add(SBrowserXStrings.get(SBrowserXStrings.AUTOCLOSE_1D_OPTION)); // After 1 day
            for (int i = 1; i < stockTitles.size(); i++) {
                out.add(stockTitles.get(i));
            }
        } else {
            out.add("Never");
            out.add(SBrowserXStrings.get(SBrowserXStrings.AUTOCLOSE_1D_OPTION));
            out.add("After 7 days");
            out.add("After 30 days");
            out.add("With smart tab management");
        }
        return out;
    }

    public static int getAutoCloseTabStatus(long lastUsedTime, int settingValue) {
        if (settingValue == 0) return 1; // Never -> keep
        if (settingValue == 4) return 2; // Smart tab management
        long days = TimeUnit.MILLISECONDS.toDays(System.currentTimeMillis() - lastUsedTime);
        if (settingValue == 1) return days < 1L ? 1 : 0; // 1 day
        if (settingValue == 2) return days < 7L ? 1 : 0; // 7 days
        if (settingValue == 3) return days < 30L ? 1 : 0; // 30 days
        return 1;
    }

    // --- External Download Manager ---

    public static boolean tryHandOffDownload(Object downloadHandler, Activity activity) {
        if (downloadHandler == null || activity == null) return false;
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            if (ctx == null || !SBrowserXPrefs.isExternalDownloaderEnabled(ctx)) return false;

            Field f = downloadHandler.getClass().getDeclaredField("mPreDownloadInfo");
            f.setAccessible(true);
            Object info = f.get(downloadHandler);
            if (info == null) return false;

            String url = (String) info.getClass().getMethod("getUrl").invoke(info);
            if (url == null || !(url.startsWith("http://") || url.startsWith("https://"))) {
                return false;
            }
            String mime = (String) info.getClass().getMethod("getMimetype").invoke(info);
            String filename = (String) info.getClass().getMethod("getFilename").invoke(info);

            Intent viewIntent = new Intent(Intent.ACTION_VIEW);
            if (mime != null && !mime.isEmpty() && !mime.equals("application/octet-stream")) {
                viewIntent.setDataAndType(Uri.parse(url), mime);
            } else {
                viewIntent.setData(Uri.parse(url));
            }
            if (filename != null && !filename.isEmpty()) {
                viewIntent.putExtra(Intent.EXTRA_TITLE, filename);
            }
            viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            String targetPkg = SBrowserXPrefs.getExternalDownloaderPackage(ctx);
            if (targetPkg != null && !targetPkg.isEmpty()) {
                viewIntent.setPackage(targetPkg);
                try {
                    activity.startActivity(viewIntent);
                    return true;
                } catch (Throwable viewErr) {
                    // Fallback to ACTION_SEND (used by ABDM, Seal, etc.)
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, url);
                    if (filename != null && !filename.isEmpty()) {
                        sendIntent.putExtra(Intent.EXTRA_TITLE, filename);
                    }
                    sendIntent.setPackage(targetPkg);
                    sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(sendIntent);
                    return true;
                }
            }

            Intent chooser = Intent.createChooser(viewIntent, SBrowserXStrings.get(SBrowserXStrings.EXT_DL_CHOOSER));
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            activity.startActivity(chooser);
            return true;
        } catch (Throwable t) {
            Log.e(TAG, "tryHandOffDownload failed, falling back to stock downloader", t);
            return false;
        }
    }

    // --- Google Translate Hooks ---

    public static boolean isGoogleTranslateEnabled() {
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            return ctx == null || SBrowserXPrefs.isGoogleTranslateEnabled(ctx);
        } catch (Throwable t) {
            return true;
        }
    }

    public static void setActiveTransService(Object sixTransService) {
        sActiveTransService = sixTransService;
    }

    public static boolean tryTransGetSupport(Object service, long cb) {
        return SBrowserXGoogleTranslator.handleGetSupport(service, cb);
    }

    public static boolean tryTransGetMode(Object service, long cb) {
        return SBrowserXGoogleTranslator.handleGetMode(service, cb);
    }

    public static boolean tryTransCreate(Object service, long cb) {
        return SBrowserXGoogleTranslator.handleCreateTrans(service, cb);
    }

    public static boolean tryTransDestroy(Object service, long cb) {
        return SBrowserXGoogleTranslator.handleDestroyTrans(service, cb);
    }

    public static boolean tryTransClear(Object service, long cb) {
        return SBrowserXGoogleTranslator.handleClear(service, cb);
    }

    public static boolean tryTransRefresh(Object service, long cb) {
        return SBrowserXGoogleTranslator.handleRefresh(service, cb);
    }

    public static boolean tryTransGetLangList(Object service, String direction, long cb) {
        return SBrowserXGoogleTranslator.handleGetLangList(service, direction, cb);
    }

    public static boolean tryTransDetectLang(Object service, String text, long cb) {
        return SBrowserXGoogleTranslator.handleDetectionLang(service, text, cb);
    }

    public static boolean tryGoogleTranslate(
            String sourceLang,
            String targetLang,
            String[] textList,
            long nativeCallback
    ) {
        return SBrowserXGoogleTranslator.handleTranslate(
                sActiveTransService, sourceLang, targetLang, textList, nativeCallback);
    }

    public static boolean tryOpenTranslateSettings(Object service) {
        if (!isGoogleTranslateEnabled()) return false;
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            Activity main = sMainActivityRef.get();
            Context launchCtx = main != null ? main : ctx;
            if (launchCtx == null) return false;
            Intent intent = new Intent(launchCtx, SBrowserXTranslateSettingsActivity.class);
            if (!(launchCtx instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            launchCtx.startActivity(intent);
            return true;
        } catch (Throwable t) {
            Log.e(TAG, "tryOpenTranslateSettings failed", t);
            return false;
        }
    }

    public static void localizeCustomPreferences(Object fragment) {
        if (fragment == null) return;
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            if (ctx == null) return;
            Method findPref = fragment.getClass().getMethod("findPreference", CharSequence.class);

            Object extDl = findPref.invoke(fragment, "sbrowserx_external_downloader");
            if (extDl != null) {
                extDl.getClass().getMethod("setTitle", CharSequence.class)
                        .invoke(extDl, SBrowserXStrings.get(SBrowserXStrings.EXT_DL_TITLE));
                extDl.getClass().getMethod("setSummary", CharSequence.class)
                        .invoke(extDl, getExternalDownloaderSummary(ctx));
            }

            Object webrtc = findPref.invoke(fragment, "sbrowserx_privacy_webrtc");
            if (webrtc != null) {
                webrtc.getClass().getMethod("setTitle", CharSequence.class)
                        .invoke(webrtc, SBrowserXStrings.get(SBrowserXStrings.WEBRTC_TITLE));
                webrtc.getClass().getMethod("setSummary", CharSequence.class)
                        .invoke(webrtc, SBrowserXStrings.webrtcLabel(SBrowserXPrefs.getWebRtcPolicy(ctx)));
            }

            Object extPref = findPref.invoke(fragment, "sbrowserx_settings_extensions");
            if (extPref != null) {
                String lang = java.util.Locale.getDefault().getLanguage();
                String extSum = "ru".equals(lang) ? "Управление расширениями и Chrome Web Store"
                        : ("uk".equals(lang) ? "Керування розширеннями та Chrome Web Store"
                        : "Manage extensions & Chrome Web Store");
                extPref.getClass().getMethod("setSummary", CharSequence.class).invoke(extPref, extSum);
            }

            Object hub = findPref.invoke(fragment, "sbrowserx_settings_hub");
            if (hub != null) {
                String summary = SBrowserXStrings.get(SBrowserXStrings.CAT_EXTENSIONS) + ", WebRTC, "
                        + SBrowserXStrings.get(SBrowserXStrings.CAT_APPEARANCE);
                hub.getClass().getMethod("setSummary", CharSequence.class).invoke(hub, summary);
            }
        } catch (Throwable ignored) {}
    }

    public static String getExternalDownloaderSummary(Context ctx) {
        if (ctx == null || !SBrowserXPrefs.isExternalDownloaderEnabled(ctx)) {
            return SBrowserXStrings.get(SBrowserXStrings.BTN_DISABLE);
        }
        String pkg = SBrowserXPrefs.getExternalDownloaderPackage(ctx);
        if (pkg == null || pkg.isEmpty()) {
            return SBrowserXStrings.get(SBrowserXStrings.EXT_DL_CHOOSER);
        }
        try {
            android.content.pm.PackageManager pm = ctx.getPackageManager();
            CharSequence label = pm.getApplicationLabel(pm.getApplicationInfo(pkg, 0));
            if (label != null && label.length() > 0) return label.toString();
        } catch (Throwable ignored) {}
        return pkg;
    }

    public static String[] getGoogleTranslateLangCodes() {
        if (!isGoogleTranslateEnabled()) return null;
        String[][] langs = SBrowserXGoogleTranslator.SUPPORTED_LANGUAGES;
        String[] out = new String[langs.length];
        for (int i = 0; i < langs.length; i++) {
            out[i] = langs[i][0];
        }
        return out;
    }

    public static String getGoogleTranslateTargetLangOrNull() {
        if (!isGoogleTranslateEnabled()) return null;
        Context ctx = SBrowserXPrefs.getAppContext();
        return ctx != null ? SBrowserXPrefs.getTranslateTargetLang(ctx) : "en";
    }

    public static void onTransBarTargetLangSelected(String code) {
        if (code == null || code.isEmpty() || "add_lang".equals(code)) return;
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            if (ctx != null && SBrowserXPrefs.isGoogleTranslateEnabled(ctx)) {
                SBrowserXPrefs.setTranslateTargetLang(ctx, code);
            }
        } catch (Throwable ignored) {}
    }

    public static boolean openNativeExtensionsActivity(Context context) {
        if (context == null) return false;
        try {
            Intent intent = new Intent(context, SBrowserXExtensionsActivity.class);
            Activity main = sMainActivityRef.get();
            if (main != null) {
                try {
                    Class<?> mim = Class.forName("com.sec.android.app.sbrowser.multi_instance.MultiInstanceManager");
                    Object inst = mim.getMethod("getInstance").invoke(null);
                    String id = (String) mim.getMethod("getMainActivityId", Activity.class).invoke(inst, main);
                    if (id != null) intent.putExtra("SBrowserMainActivityContextId", id);
                } catch (Throwable ignored) {}
            }
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
            return true;
        } catch (Throwable t) {
            Log.e(TAG, "openNativeExtensionsActivity failed", t);
            return false;
        }
    }

    public static String getGoogleTranslateLangDisplayName(String code) {
        if (!isGoogleTranslateEnabled() || code == null || code.isEmpty()) return null;
        try {
            java.util.Locale loc = java.util.Locale.forLanguageTag(code.replace('_', '-'));
            String name = loc.getDisplayLanguage(java.util.Locale.getDefault());
            if (name != null && !name.isEmpty() && !name.equalsIgnoreCase(code)) {
                return name;
            }
        } catch (Throwable ignored) {}
        return SBrowserXGoogleTranslator.getLanguageDisplayName(code);
    }

    public static String resolveTransLangDisplayName(Object service, String code) {
        if (code == null || code.isEmpty()) return "";
        if (isGoogleTranslateEnabled()) {
            // TransBarLangListHandler.getDisplayName() expects a BCP-47 locale tag (e.g. "ru", "en", "zh-CN"),
            // NOT a pre-translated display name!
            return code.replace('_', '-');
        }
        if (service != null) {
            try {
                Field f = service.getClass().getDeclaredField("mSupportLangMap");
                f.setAccessible(true);
                Object map = f.get(service);
                if (map instanceof java.util.Map) {
                    Object val = ((java.util.Map<?, ?>) map).get(code);
                    if (val instanceof String) return (String) val;
                }
            } catch (Throwable ignored) {}
        }
        return code;
    }

    public static Activity getMainActivity() {
        return sMainActivityRef.get();
    }

    public static void onTransBarTranslatePage(boolean translate) {
        if (!isGoogleTranslateEnabled()) return;
        if (translate) {
            Context ctx = SBrowserXPrefs.getAppContext();
            String tl = ctx != null ? SBrowserXPrefs.getTranslateTargetLang(ctx) : "ru";
            SBrowserXDomTranslator.translateCurrentTab("auto", tl);
        } else {
            SBrowserXDomTranslator.revertCurrentTab();
        }
    }

    public static void onTransBarTargetItemSelected(Object transBarInfo) {
        if (transBarInfo == null || !isGoogleTranslateEnabled()) return;
        try {
            String code = (String) transBarInfo.getClass().getMethod("getCode").invoke(transBarInfo);
            if (code == null || code.isEmpty() || "add_lang".equals(code)) return;
            Context ctx = SBrowserXPrefs.getAppContext();
            if (ctx != null) {
                SBrowserXPrefs.setTranslateTargetLang(ctx, code);
            }
            SBrowserXDomTranslator.translateCurrentTab("auto", code);
        } catch (Throwable t) {
            Log.e(TAG, "onTransBarTargetItemSelected failed", t);
        }
    }

    public static void onTransBarSourceItemSelected(Object transBarInfo) {
        if (transBarInfo == null || !isGoogleTranslateEnabled()) return;
        try {
            String code = (String) transBarInfo.getClass().getMethod("getCode").invoke(transBarInfo);
            Context ctx = SBrowserXPrefs.getAppContext();
            String tl = ctx != null ? SBrowserXPrefs.getTranslateTargetLang(ctx) : "ru";
            SBrowserXDomTranslator.translateCurrentTab(code != null ? code : "auto", tl);
        } catch (Throwable t) {
            Log.e(TAG, "onTransBarSourceItemSelected failed", t);
        }
    }

    public static void onTransBarClose() {
        if (!isGoogleTranslateEnabled()) return;
        SBrowserXDomTranslator.revertCurrentTab();
    }

    public static void onTransBarLoadFinished() {
        if (!isGoogleTranslateEnabled()) return;
        SBrowserXDomTranslator.maybeAutoTranslateOnLoad();
    }

    private static final int CUSTOM_TAB_MENU_TRANSLATE_ID = 0x5B580001;
    private static volatile WeakReference<Activity> sCustomTabActivityRef = new WeakReference<>(null);

    public static void onCustomTabResume(Activity activity) {
        if (activity != null) {
            sCustomTabActivityRef = new WeakReference<>(activity);
        }
    }

    public static void onCustomTabLoadFinished(Activity activity, String url) {
        if (activity == null || !isGoogleTranslateEnabled()) return;
        sCustomTabActivityRef = new WeakReference<>(activity);
        SBrowserXDomTranslator.maybeAutoTranslateActivity(activity, true);
    }

    public static void onCustomTabMenuInitialized(Object toolbar) {
        if (toolbar == null || !isGoogleTranslateEnabled()) return;
        try {
            Field f = toolbar.getClass().getDeclaredField("mMenuPopup");
            f.setAccessible(true);
            Object popup = f.get(toolbar);
            if (popup == null) return;
            android.view.Menu menu = (android.view.Menu) popup.getClass().getMethod("getMenu").invoke(popup);
            if (menu == null || menu.findItem(CUSTOM_TAB_MENU_TRANSLATE_ID) != null) return;
            String lang = java.util.Locale.getDefault().getLanguage();
            String label = "ru".equals(lang) ? "Перевести..."
                    : ("uk".equals(lang) ? "Перекласти..." : "Translate...");
            menu.add(0, CUSTOM_TAB_MENU_TRANSLATE_ID, 2, label);
        } catch (Throwable t) {
            Log.e(TAG, "onCustomTabMenuInitialized failed", t);
        }
    }

    public static boolean tryHandleCustomTabMenuItem(Object coordinator, android.view.MenuItem item) {
        if (item == null || item.getItemId() != CUSTOM_TAB_MENU_TRANSLATE_ID) return false;
        try {
            Activity ct = null;
            if (coordinator != null) {
                try {
                    Field f = coordinator.getClass().getDeclaredField("mActivity");
                    f.setAccessible(true);
                    Object a = f.get(coordinator);
                    if (a instanceof Activity) ct = (Activity) a;
                } catch (Throwable ignored) {}
            }
            if (ct == null) ct = sCustomTabActivityRef.get();
            if (ct != null) {
                String tl = SBrowserXPrefs.getTranslateTargetLang(ct);
                SBrowserXDomTranslator.translateTab(ct, "auto", tl, true);
            }
            return true;
        } catch (Throwable t) {
            Log.e(TAG, "tryHandleCustomTabMenuItem failed", t);
            return false;
        }
    }

    public static boolean tryHandleMainTranslateClick(Object menuHelper, Object delegate) {
        if (!isGoogleTranslateEnabled()) return false;
        try {
            if (delegate != null) {
                try {
                    delegate.getClass().getMethod("closeOptionsMenu").invoke(delegate);
                } catch (Throwable ignored) {}
            }
            Activity main = sMainActivityRef.get();
            if (main == null && menuHelper != null) {
                try {
                    Field f = menuHelper.getClass().getDeclaredField("mActivity");
                    f.setAccessible(true);
                    Object a = f.get(menuHelper);
                    if (a instanceof Activity) main = (Activity) a;
                } catch (Throwable ignored) {}
            }
            if (main != null) {
                String tl = SBrowserXPrefs.getTranslateTargetLang(main);
                SBrowserXDomTranslator.translateTab(main, "auto", tl, true);
                return true;
            }
        } catch (Throwable t) {
            Log.e(TAG, "tryHandleMainTranslateClick failed", t);
        }
        return false;
    }
}
