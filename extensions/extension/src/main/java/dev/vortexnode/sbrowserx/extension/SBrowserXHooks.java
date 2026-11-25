package dev.vortexnode.sbrowserx.extension;

import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;

public final class SBrowserXHooks {
    private static final String TAG = "SBrowserXHooks";

    private SBrowserXHooks() {}

    public static void onApplicationCreate(Application app) {
        if (app == null) return;
        try {
            // CRITICAL: Never touch SharedPreferences or filesystem inside Chromium's
            // isolated renderer processes (:sandboxed_process*, :privileged_process*).
            if (Process.isIsolated()) return;
            String processName = Application.getProcessName();
            if (processName != null && processName.contains(":")) return;

            SBrowserXPrefs.init(app);
            SBrowserXWebRtcManager.applyToProfile(app);
        } catch (Throwable t) {
            Log.e(TAG, "onApplicationCreate failed", t);
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
}
