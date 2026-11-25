package dev.vortexnode.sbrowserx.extension.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.os.Bundle;

import dev.vortexnode.sbrowserx.extension.SBrowserXPrefs;
import dev.vortexnode.sbrowserx.extension.SBrowserXStrings;

public final class SBrowserXExternalDownloaderDialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SBrowserXPrefs.init(this);

        boolean night = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
        int theme = night
                ? android.R.style.Theme_DeviceDefault_Dialog_Alert
                : android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;

        boolean enabled = SBrowserXPrefs.isExternalDownloaderEnabled(this);
        String[] options = new String[] {
                SBrowserXStrings.get(SBrowserXStrings.BTN_DISABLE),
                SBrowserXStrings.get(SBrowserXStrings.BTN_ENABLE)
        };

        new AlertDialog.Builder(this, theme)
                .setTitle(SBrowserXStrings.get(SBrowserXStrings.EXT_DL_TITLE))
                .setSingleChoiceItems(options, enabled ? 1 : 0, (dialog, which) -> {
                    SBrowserXPrefs.setExternalDownloaderEnabled(this, which == 1);
                    dialog.dismiss();
                    finish();
                })
                .setNegativeButton(android.R.string.cancel, (d, w) -> finish())
                .setOnCancelListener(d -> finish())
                .show();
    }
}
