package dev.vortexnode.sbrowserx.extension;

import android.app.Application;
import android.content.Context;

public final class SBrowserXHooks {
    private SBrowserXHooks() {}

    public static void onApplicationCreate(Application app) {
        if (app == null) return;
        SBrowserXPrefs.init(app);
        SBrowserXWebRtcManager.applyToProfile(app);
    }

    public static boolean isToolbarBlurAllowed(boolean stockSupported) {
        if (!stockSupported) return false;
        Context ctx = SBrowserXPrefs.getAppContext();
        if (ctx == null) return true;
        return SBrowserXPrefs.isBottomBarBlurEnabled(ctx);
    }

    public static boolean isAmoledBlackEnabled() {
        Context ctx = SBrowserXPrefs.getAppContext();
        if (ctx == null) return false;
        return SBrowserXPrefs.isAmoledBlackEnabled(ctx);
    }
}
