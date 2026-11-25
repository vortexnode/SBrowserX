package dev.vortexnode.sbrowserx.extension;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

import java.lang.reflect.Method;

public final class SBrowserXHooks {
    private static final String TAG = "SBrowserXHooks";

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
        if (!stockSupported) return false;
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            if (ctx == null) return true;
            return SBrowserXPrefs.isBottomBarBlurEnabled(ctx);
        } catch (Throwable t) {
            return stockSupported;
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

    /**
     * Preserves stock #D9 blur translucency (0xD9000000) when bottom bar blur is ON,
     * and returns solid #FF000000 only when blur is OFF.
     */
    public static int getAmoledBottomBarColor() {
        try {
            Context ctx = SBrowserXPrefs.getAppContext();
            boolean blurOn = ctx == null || SBrowserXPrefs.isBottomBarBlurEnabled(ctx);
            return blurOn ? 0xD9000000 : 0xFF000000;
        } catch (Throwable t) {
            return 0xD9000000;
        }
    }
}
