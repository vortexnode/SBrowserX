package dev.vortexnode.sbrowserx.extension.settings;

import android.app.Activity;
import android.os.Bundle;

import dev.vortexnode.sbrowserx.extension.SBrowserXHooks;

public final class SBrowserXExtensionsBridgeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SBrowserXHooks.openExtensionsManager(this);
        finish();
    }
}
