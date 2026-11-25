package dev.vortexnode.sbrowserx.extension;

import android.content.Context;
import android.content.SharedPreferences;

public final class SBrowserXPrefs {
    public static final String PREFS_NAME = "sbrowserx_prefs";

    public static final String KEY_WEBRTC_POLICY = "webrtc_ip_policy";
    public static final String KEY_FINGERPRINT_PROTECTION = "fingerprint_protection_enabled";
    public static final String KEY_BOTTOM_BAR_BLUR = "bottom_bar_blur_enabled";
    public static final String KEY_AMOLED_BLACK = "amoled_black_enabled";
    public static final String KEY_EXTERNAL_DOWNLOADER = "external_downloader_enabled";
    public static final String KEY_AUTO_CLOSE_1DAY = "auto_close_tabs_1day_enabled";
    public static final String KEY_GOOGLE_TRANSLATE = "google_translate_backend_enabled";
    public static final String KEY_HIGHLIGHT_DAYS = "new_feature_highlight_days";

    public static final String WEBRTC_DEFAULT = "default";
    public static final String WEBRTC_PUBLIC_AND_PRIVATE = "default_public_and_private_interfaces";
    public static final String WEBRTC_PUBLIC_ONLY = "default_public_interface_only";
    public static final String WEBRTC_DISABLE_NON_PROXIED_UDP = "disable_non_proxied_udp";

    public static final String FEATURE_WEBRTC = "feat_webrtc_020";
    public static final String FEATURE_AMOLED = "feat_amoled_020";
    public static final String FEATURE_BOTTOM_BLUR = "feat_bottom_blur_020";
    public static final String FEATURE_EXT_INSTALLER = "feat_ext_installer_030";
    public static final String FEATURE_EXT_PACK = "feat_ext_pack_030";
    public static final String FEATURE_FINGERPRINT = "feat_fingerprint_030";
    public static final String FEATURE_EXT_DOWNLOADER = "feat_ext_downloader_040";
    public static final String FEATURE_AUTO_CLOSE_1DAY = "feat_autoclose_1day_040";
    public static final String FEATURE_GOOGLE_TRANSLATE = "feat_google_translate_040";

    private static final String[] ALL_FEATURES = new String[] {
            FEATURE_WEBRTC,
            FEATURE_AMOLED,
            FEATURE_BOTTOM_BLUR,
            FEATURE_EXT_INSTALLER,
            FEATURE_EXT_PACK,
            FEATURE_FINGERPRINT,
            FEATURE_EXT_DOWNLOADER,
            FEATURE_AUTO_CLOSE_1DAY,
            FEATURE_GOOGLE_TRANSLATE
    };

    private static volatile Context sAppContext;

    private SBrowserXPrefs() {}

    public static void init(Context context) {
        if (context != null) {
            sAppContext = context.getApplicationContext();
            ensureFeatureTimestamps(sAppContext);
        }
    }

    public static Context getAppContext() {
        return sAppContext;
    }

    public static SharedPreferences prefs(Context context) {
        Context ctx = context != null ? context.getApplicationContext() : sAppContext;
        return ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static String getWebRtcPolicy(Context context) {
        return prefs(context).getString(KEY_WEBRTC_POLICY, WEBRTC_DISABLE_NON_PROXIED_UDP);
    }

    public static void setWebRtcPolicy(Context context, String policy) {
        prefs(context).edit().putString(KEY_WEBRTC_POLICY, policy).apply();
        markFeatureViewed(context, FEATURE_WEBRTC);
    }

    public static boolean isFingerprintProtectionEnabled(Context context) {
        return prefs(context).getBoolean(KEY_FINGERPRINT_PROTECTION, true);
    }

    public static void setFingerprintProtectionEnabled(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_FINGERPRINT_PROTECTION, enabled).apply();
        markFeatureViewed(context, FEATURE_FINGERPRINT);
    }

    public static boolean isBottomBarBlurEnabled(Context context) {
        return prefs(context).getBoolean(KEY_BOTTOM_BAR_BLUR, true);
    }

    public static void setBottomBarBlurEnabled(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_BOTTOM_BAR_BLUR, enabled).apply();
        markFeatureViewed(context, FEATURE_BOTTOM_BLUR);
    }

    public static boolean isAmoledBlackEnabled(Context context) {
        return prefs(context).getBoolean(KEY_AMOLED_BLACK, false);
    }

    public static void setAmoledBlackEnabled(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_AMOLED_BLACK, enabled).apply();
        markFeatureViewed(context, FEATURE_AMOLED);
    }

    public static boolean isExternalDownloaderEnabled(Context context) {
        return prefs(context).getBoolean(KEY_EXTERNAL_DOWNLOADER, false);
    }

    public static void setExternalDownloaderEnabled(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_EXTERNAL_DOWNLOADER, enabled).apply();
        markFeatureViewed(context, FEATURE_EXT_DOWNLOADER);
    }

    public static boolean isAutoClose1DayEnabled(Context context) {
        return prefs(context).getBoolean(KEY_AUTO_CLOSE_1DAY, false);
    }

    public static void setAutoClose1DayEnabled(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_AUTO_CLOSE_1DAY, enabled).apply();
        markFeatureViewed(context, FEATURE_AUTO_CLOSE_1DAY);
    }

    public static boolean isGoogleTranslateEnabled(Context context) {
        return prefs(context).getBoolean(KEY_GOOGLE_TRANSLATE, true);
    }

    public static void setGoogleTranslateEnabled(Context context, boolean enabled) {
        prefs(context).edit().putBoolean(KEY_GOOGLE_TRANSLATE, enabled).apply();
        markFeatureViewed(context, FEATURE_GOOGLE_TRANSLATE);
    }

    public static int getHighlightDays(Context context) {
        return prefs(context).getInt(KEY_HIGHLIGHT_DAYS, 7);
    }

    public static void setHighlightDays(Context context, int days) {
        int clamped = Math.max(0, Math.min(10, days));
        prefs(context).edit().putInt(KEY_HIGHLIGHT_DAYS, clamped).apply();
    }

    private static void ensureFeatureTimestamps(Context context) {
        SharedPreferences sp = prefs(context);
        long now = System.currentTimeMillis();
        SharedPreferences.Editor editor = null;
        for (String feat : ALL_FEATURES) {
            String key = "first_seen_" + feat;
            if (!sp.contains(key)) {
                if (editor == null) editor = sp.edit();
                editor.putLong(key, now);
            }
        }
        if (editor != null) editor.apply();
    }

    public static boolean isFeatureNew(Context context, String featureId) {
        int days = getHighlightDays(context);
        if (days <= 0) return false;
        SharedPreferences sp = prefs(context);
        if (sp.getBoolean("viewed_" + featureId, false)) return false;
        long firstSeen = sp.getLong("first_seen_" + featureId, System.currentTimeMillis());
        long windowMs = days * 24L * 60L * 60L * 1000L;
        return (System.currentTimeMillis() - firstSeen) < windowMs;
    }

    public static void markFeatureViewed(Context context, String featureId) {
        prefs(context).edit().putBoolean("viewed_" + featureId, true).apply();
    }

    public static void markAllViewed(Context context) {
        SharedPreferences.Editor editor = prefs(context).edit();
        for (String feat : ALL_FEATURES) {
            editor.putBoolean("viewed_" + feat, true);
        }
        editor.apply();
    }
}
