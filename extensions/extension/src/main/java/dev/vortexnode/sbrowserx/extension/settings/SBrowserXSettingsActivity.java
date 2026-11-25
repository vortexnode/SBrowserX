package dev.vortexnode.sbrowserx.extension.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dev.vortexnode.sbrowserx.extension.SBrowserXCrxInstaller;
import dev.vortexnode.sbrowserx.extension.SBrowserXGoogleTranslator;
import dev.vortexnode.sbrowserx.extension.SBrowserXHooks;
import dev.vortexnode.sbrowserx.extension.SBrowserXPrefs;
import dev.vortexnode.sbrowserx.extension.SBrowserXStrings;
import dev.vortexnode.sbrowserx.extension.SBrowserXWebRtcManager;

public final class SBrowserXSettingsActivity extends Activity {
    private static final int TEAL_DOT_COLOR = 0xFF00BFA5;
    private static final int ONEUI_BLUE = 0xFF387AFF;
    private static final int REQ_PICK_EXTENSION = 4101;

    private TextView webrtcSummaryView;
    private TextView gtransSummaryView;
    private Switch extDlSwitch;
    private TextView daysValueView;
    private View extInstallDot, extPackDot, webrtcDot, fingerprintDot, hideAdsDot, gtransDot, extDlDot, amoledDot, blurDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SBrowserXPrefs.init(this);

        boolean night = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
        boolean amoled = night && SBrowserXPrefs.isAmoledBlackEnabled(this);

        int bgColor = amoled ? 0xFF000000 : (night ? 0xFF0D0D10 : 0xFFF2F2F7);
        int cardColor = amoled ? 0xFF121216 : (night ? 0xFF1C1C22 : 0xFFFFFFFF);
        int textPrimary = night ? 0xFFF5F5F7 : 0xFF111111;
        int textSecondary = night ? 0xFFA2A2AD : 0xFF63636B;
        int accent = ONEUI_BLUE;
        int dialogTheme = night
                ? android.R.style.Theme_DeviceDefault_Dialog_Alert
                : android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;

        if (getWindow() != null) {
            getWindow().setStatusBarColor(bgColor);
            getWindow().setNavigationBarColor(bgColor);
        }

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bgColor);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(16), dp(20), dp(16), dp(36));

        // Top Bar
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(4), dp(8), dp(4), dp(18));

        TextView backBtn = new TextView(this);
        backBtn.setText("\u2190");
        backBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        backBtn.setTextColor(textPrimary);
        backBtn.setPadding(dp(4), dp(4), dp(16), dp(4));
        backBtn.setOnClickListener(v -> finish());
        header.addView(backBtn);

        TextView title = new TextView(this);
        title.setText(SBrowserXStrings.get(SBrowserXStrings.TITLE_HUB));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(textPrimary);
        header.addView(title);
        root.addView(header);

        // --- Extensions ---
        root.addView(sectionHeader(SBrowserXStrings.get(SBrowserXStrings.CAT_EXTENSIONS), textSecondary));
        LinearLayout extCard = card(cardColor);
        extInstallDot = createTealDot();
        addActionRow(extCard, SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALL_FILE),
                SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALL_FILE_SUMMARY),
                textPrimary, textSecondary, extInstallDot, v -> launchFilePicker());
        extCard.addView(divider(night));
        extPackDot = createTealDot();
        addActionRow(extCard, SBrowserXStrings.get(SBrowserXStrings.EXT_PACK_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.EXT_PACK_SUMMARY),
                textPrimary, textSecondary, extPackDot, v -> showRecommendedPackDialog(dialogTheme));
        extCard.addView(divider(night));
        addActionRow(extCard, SBrowserXStrings.get(SBrowserXStrings.EXT_OPEN_MANAGER),
                null, accent, textSecondary, null, v -> {
                    if (SBrowserXHooks.openExtensionsManager(this)) {
                        finish();
                    }
                });
        extCard.addView(divider(night));
        addActionRow(extCard, SBrowserXStrings.get(SBrowserXStrings.EXT_OPEN_CWS),
                null, accent, textSecondary, null, v -> openWebUrlInSelf("https://chromewebstore.google.com/category/extensions"));
        root.addView(extCard);

        // --- Privacy & Security ---
        root.addView(sectionHeader(SBrowserXStrings.get(SBrowserXStrings.CAT_PRIVACY), textSecondary));
        LinearLayout privacyCard = card(cardColor);
        webrtcDot = createTealDot();
        webrtcSummaryView = addActionRow(privacyCard, SBrowserXStrings.get(SBrowserXStrings.WEBRTC_TITLE),
                SBrowserXStrings.webrtcLabel(SBrowserXPrefs.getWebRtcPolicy(this)),
                textPrimary, textSecondary, webrtcDot,
                v -> startActivity(new Intent(this, SBrowserXWebRtcDialogActivity.class)));
        privacyCard.addView(divider(night));
        fingerprintDot = createTealDot();
        addSwitchRow(privacyCard, SBrowserXStrings.get(SBrowserXStrings.FINGERPRINT_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.FINGERPRINT_SUMMARY),
                SBrowserXPrefs.isFingerprintProtectionEnabled(this), night, textPrimary, textSecondary, fingerprintDot,
                (btn, checked) -> {
                    SBrowserXPrefs.setFingerprintProtectionEnabled(this, checked);
                    SBrowserXWebRtcManager.applyToProfile(this);
                    refreshDynamicViews();
                });
        privacyCard.addView(divider(night));
        hideAdsDot = createTealDot();
        addSwitchRow(privacyCard, SBrowserXStrings.get(SBrowserXStrings.HIDE_ADBLOCKERS_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.HIDE_ADBLOCKERS_SUMMARY),
                SBrowserXPrefs.isHideSamsungAdBlockersEnabled(this), night, textPrimary, textSecondary, hideAdsDot,
                (btn, checked) -> {
                    SBrowserXPrefs.setHideSamsungAdBlockersEnabled(this, checked);
                    refreshDynamicViews();
                });
        root.addView(privacyCard);

        // --- Features & Downloads ---
        root.addView(sectionHeader(SBrowserXStrings.get(SBrowserXStrings.CAT_FEATURES), textSecondary));
        LinearLayout featCard = card(cardColor);
        gtransDot = createTealDot();
        gtransSummaryView = addActionRow(featCard, SBrowserXStrings.get(SBrowserXStrings.GTRANS_SETTINGS_TITLE),
                buildTranslateSummary(),
                textPrimary, textSecondary, gtransDot,
                v -> startActivity(new Intent(this, SBrowserXTranslateSettingsActivity.class)));
        featCard.addView(divider(night));
        extDlDot = createTealDot();
        extDlSwitch = addSwitchRow(featCard, SBrowserXStrings.get(SBrowserXStrings.EXT_DL_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.EXT_DL_SUMMARY),
                SBrowserXPrefs.isExternalDownloaderEnabled(this), night, textPrimary, textSecondary, extDlDot,
                (btn, checked) -> {
                    SBrowserXPrefs.setExternalDownloaderEnabled(this, checked);
                    refreshDynamicViews();
                });
        root.addView(featCard);

        // --- Appearance & UI ---
        root.addView(sectionHeader(SBrowserXStrings.get(SBrowserXStrings.CAT_APPEARANCE), textSecondary));
        LinearLayout appearanceCard = card(cardColor);
        amoledDot = createTealDot();
        addSwitchRow(appearanceCard, SBrowserXStrings.get(SBrowserXStrings.AMOLED_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.AMOLED_SUMMARY),
                SBrowserXPrefs.isAmoledBlackEnabled(this), night, textPrimary, textSecondary, amoledDot,
                (btn, checked) -> {
                    SBrowserXPrefs.setAmoledBlackEnabled(this, checked);
                    refreshDynamicViews();
                });
        appearanceCard.addView(divider(night));
        blurDot = createTealDot();
        addSwitchRow(appearanceCard, SBrowserXStrings.get(SBrowserXStrings.BOTTOM_BLUR_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.BOTTOM_BLUR_SUMMARY),
                SBrowserXPrefs.isBottomBarBlurEnabled(this), night, textPrimary, textSecondary, blurDot,
                (btn, checked) -> {
                    SBrowserXPrefs.setBottomBarBlurEnabled(this, checked);
                    refreshDynamicViews();
                });
        root.addView(appearanceCard);

        // --- New Features Highlight ---
        root.addView(sectionHeader(SBrowserXStrings.get(SBrowserXStrings.CAT_HIGHLIGHT), textSecondary));
        LinearLayout highlightCard = card(cardColor);
        addDaysSliderRow(highlightCard, textPrimary, textSecondary);
        highlightCard.addView(divider(night));
        addActionRow(highlightCard, SBrowserXStrings.get(SBrowserXStrings.MARK_ALL_VIEWED),
                null, accent, textSecondary, null, v -> {
                    SBrowserXPrefs.markAllViewed(this);
                    refreshDynamicViews();
                    Toast.makeText(this, SBrowserXStrings.get(SBrowserXStrings.MARK_ALL_VIEWED_DONE), Toast.LENGTH_SHORT).show();
                });
        root.addView(highlightCard);

        // --- Restart ---
        LinearLayout restartCard = card(cardColor);
        LinearLayout.LayoutParams rlp = (LinearLayout.LayoutParams) restartCard.getLayoutParams();
        rlp.topMargin = dp(16);
        addActionRow(restartCard, SBrowserXStrings.get(SBrowserXStrings.RESTART_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.RESTART_SUMMARY),
                accent, textSecondary, null, v -> SBrowserXWebRtcManager.restartBrowser(this));
        root.addView(restartCard);

        scroll.addView(root);
        setContentView(scroll);
        refreshDynamicViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDynamicViews();
    }

    private String buildTranslateSummary() {
        boolean on = SBrowserXPrefs.isGoogleTranslateEnabled(this);
        String lang = SBrowserXGoogleTranslator.getLanguageDisplayName(SBrowserXPrefs.getTranslateTargetLang(this));
        return (on ? "ON • " : "OFF • ") + lang;
    }

    private void launchFilePicker() {
        SBrowserXPrefs.markFeatureViewed(this, SBrowserXPrefs.FEATURE_EXT_INSTALLER);
        refreshDynamicViews();
        try {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("*/*");
            startActivityForResult(intent, REQ_PICK_EXTENSION);
        } catch (Throwable t) {
            Toast.makeText(this, SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALL_FAIL) + t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_PICK_EXTENSION && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Toast.makeText(this, SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALLING), Toast.LENGTH_SHORT).show();
            SBrowserXCrxInstaller.installFromUri(this, data.getData(), (ok, msg) -> {
                String toast = ok
                        ? SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALL_OK) + " (" + msg + ")"
                        : SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALL_FAIL) + msg;
                Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
            });
        }
    }

    private void showRecommendedPackDialog(int theme) {
        SBrowserXPrefs.markFeatureViewed(this, SBrowserXPrefs.FEATURE_EXT_PACK);
        refreshDynamicViews();
        String[][] items = SBrowserXCrxInstaller.RECOMMENDED_EXTENSIONS;
        String[] labels = new String[items.length];
        boolean[] checked = new boolean[items.length];
        for (int i = 0; i < items.length; i++) {
            labels[i] = items[i][1];
            checked[i] = true;
        }

        new AlertDialog.Builder(this, theme)
                .setTitle(SBrowserXStrings.get(SBrowserXStrings.EXT_PACK_TITLE))
                .setMultiChoiceItems(labels, checked, (dialog, which, isChecked) -> checked[which] = isChecked)
                .setPositiveButton(SBrowserXStrings.get(SBrowserXStrings.BTN_INSTALL), (dialog, which) -> {
                    List<String> selectedIds = new ArrayList<>();
                    for (int i = 0; i < items.length; i++) {
                        if (checked[i]) selectedIds.add(items[i][0]);
                    }
                    if (selectedIds.isEmpty()) return;
                    Toast.makeText(this, SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALLING), Toast.LENGTH_SHORT).show();
                    SBrowserXCrxInstaller.installFromChromeWebStore(this, selectedIds, (ok, msg) -> {
                        String toast = ok
                                ? SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALL_OK) + " (" + msg + ")"
                                : SBrowserXStrings.get(SBrowserXStrings.EXT_INSTALL_FAIL) + msg;
                        Toast.makeText(this, toast, Toast.LENGTH_LONG).show();
                    });
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void openWebUrlInSelf(String url) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setPackage(getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        } catch (Throwable t) {
            Toast.makeText(this, t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void refreshDynamicViews() {
        if (webrtcSummaryView != null) {
            webrtcSummaryView.setText(SBrowserXStrings.webrtcLabel(SBrowserXPrefs.getWebRtcPolicy(this)));
        }
        if (gtransSummaryView != null) {
            gtransSummaryView.setText(buildTranslateSummary());
        }
        if (extDlSwitch != null) {
            boolean cur = SBrowserXPrefs.isExternalDownloaderEnabled(this);
            if (extDlSwitch.isChecked() != cur) {
                extDlSwitch.setChecked(cur);
            }
        }
        setDot(extInstallDot, SBrowserXPrefs.FEATURE_EXT_INSTALLER);
        setDot(extPackDot, SBrowserXPrefs.FEATURE_EXT_PACK);
        setDot(webrtcDot, SBrowserXPrefs.FEATURE_WEBRTC);
        setDot(fingerprintDot, SBrowserXPrefs.FEATURE_FINGERPRINT);
        setDot(hideAdsDot, SBrowserXPrefs.FEATURE_HIDE_ADBLOCKERS);
        setDot(gtransDot, SBrowserXPrefs.FEATURE_GOOGLE_TRANSLATE);
        setDot(extDlDot, SBrowserXPrefs.FEATURE_EXT_DOWNLOADER);
        setDot(amoledDot, SBrowserXPrefs.FEATURE_AMOLED);
        setDot(blurDot, SBrowserXPrefs.FEATURE_BOTTOM_BLUR);
        if (daysValueView != null) {
            int days = SBrowserXPrefs.getHighlightDays(this);
            daysValueView.setText(days == 0
                    ? SBrowserXStrings.get(SBrowserXStrings.HIGHLIGHT_DAYS_OFF)
                    : String.format(SBrowserXStrings.get(SBrowserXStrings.HIGHLIGHT_DAYS_FMT), days));
        }
    }

    private void setDot(View dot, String feat) {
        if (dot != null) {
            dot.setVisibility(SBrowserXPrefs.isFeatureNew(this, feat) ? View.VISIBLE : View.GONE);
        }
    }

    private View createTealDot() {
        View dot = new View(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dp(8), dp(8));
        lp.leftMargin = dp(8);
        dot.setLayoutParams(lp);
        GradientDrawable gd = new GradientDrawable();
        gd.setShape(GradientDrawable.OVAL);
        gd.setColor(TEAL_DOT_COLOR);
        dot.setBackground(gd);
        return dot;
    }

    private TextView sectionHeader(String text, int color) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(color);
        tv.setPadding(dp(14), dp(14), dp(14), dp(6));
        return tv;
    }

    private LinearLayout card(int bgColor) {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bgColor);
        gd.setCornerRadius(dp(22));
        ll.setBackground(gd);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(lp);
        return ll;
    }

    private View divider(boolean night) {
        View v = new View(this);
        v.setBackgroundColor(night ? 0xFF2A2A32 : 0xFFE5E5EA);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1);
        lp.leftMargin = dp(18);
        lp.rightMargin = dp(18);
        v.setLayoutParams(lp);
        return v;
    }

    private TextView addActionRow(
            LinearLayout parent, String title, String summary, int titleColor, int summaryColor,
            View badgeDot, View.OnClickListener onClick
    ) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(dp(18), dp(15), dp(18), dp(15));
        row.setOnClickListener(onClick);

        LinearLayout titleRow = new LinearLayout(this);
        titleRow.setOrientation(LinearLayout.HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);
        TextView tvTitle = new TextView(this);
        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvTitle.setTextColor(titleColor);
        titleRow.addView(tvTitle);
        if (badgeDot != null) titleRow.addView(badgeDot);
        row.addView(titleRow);

        TextView tvSummary = null;
        if (summary != null) {
            tvSummary = new TextView(this);
            tvSummary.setText(summary);
            tvSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
            tvSummary.setTextColor(summaryColor);
            tvSummary.setPadding(0, dp(3), 0, 0);
            row.addView(tvSummary);
        }

        parent.addView(row);
        return tvSummary;
    }

    private Switch addSwitchRow(
            LinearLayout parent, String title, String summary, boolean initialValue, boolean night,
            int titleColor, int summaryColor, View badgeDot,
            android.widget.CompoundButton.OnCheckedChangeListener listener
    ) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(18), dp(14), dp(14), dp(14));

        LinearLayout textCol = new LinearLayout(this);
        textCol.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams colLp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        colLp.rightMargin = dp(12);
        textCol.setLayoutParams(colLp);

        LinearLayout titleRow = new LinearLayout(this);
        titleRow.setOrientation(LinearLayout.HORIZONTAL);
        titleRow.setGravity(Gravity.CENTER_VERTICAL);
        TextView tvTitle = new TextView(this);
        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvTitle.setTextColor(titleColor);
        titleRow.addView(tvTitle);
        if (badgeDot != null) titleRow.addView(badgeDot);
        textCol.addView(titleRow);

        TextView tvSummary = new TextView(this);
        tvSummary.setText(summary);
        tvSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tvSummary.setTextColor(summaryColor);
        tvSummary.setPadding(0, dp(3), 0, 0);
        textCol.addView(tvSummary);

        Switch sw = new Switch(this);
        styleOneUiBlueSwitch(sw, night);
        sw.setChecked(initialValue);
        sw.setOnCheckedChangeListener(listener);

        row.addView(textCol);
        row.addView(sw);
        row.setOnClickListener(v -> sw.toggle());
        parent.addView(row);
        return sw;
    }

    static void styleOneUiBlueSwitch(Switch sw, boolean night) {
        float d = sw.getResources().getDisplayMetrics().density;
        int trackW = Math.round(46 * d);
        int trackH = Math.round(26 * d);
        int trackR = Math.round(13 * d);
        int thumbSize = Math.round(24 * d);
        int strokeW = Math.round(2 * d);

        GradientDrawable trackOn = new GradientDrawable();
        trackOn.setShape(GradientDrawable.RECTANGLE);
        trackOn.setSize(trackW, trackH);
        trackOn.setCornerRadius(trackR);
        trackOn.setColor(ONEUI_BLUE);

        GradientDrawable trackOff = new GradientDrawable();
        trackOff.setShape(GradientDrawable.RECTANGLE);
        trackOff.setSize(trackW, trackH);
        trackOff.setCornerRadius(trackR);
        trackOff.setColor(night ? 0xFF555562 : 0xFFB8B8C4);

        StateListDrawable trackStates = new StateListDrawable();
        trackStates.addState(new int[] { android.R.attr.state_checked }, trackOn);
        trackStates.addState(new int[] {}, trackOff);

        GradientDrawable thumb = new GradientDrawable();
        thumb.setShape(GradientDrawable.OVAL);
        thumb.setSize(thumbSize, thumbSize);
        thumb.setColor(0xFFFFFFFF);
        thumb.setStroke(strokeW, 0x00000000);

        sw.setTrackTintList(null);
        sw.setThumbTintList(null);
        sw.setTrackDrawable(trackStates);
        sw.setThumbDrawable(thumb);
    }

    private void addDaysSliderRow(LinearLayout parent, int titleColor, int summaryColor) {
        LinearLayout box = new LinearLayout(this);
        box.setOrientation(LinearLayout.VERTICAL);
        box.setPadding(dp(18), dp(14), dp(18), dp(14));

        LinearLayout top = new LinearLayout(this);
        top.setOrientation(LinearLayout.HORIZONTAL);
        top.setGravity(Gravity.CENTER_VERTICAL);

        TextView tvTitle = new TextView(this);
        tvTitle.setText(SBrowserXStrings.get(SBrowserXStrings.HIGHLIGHT_DAYS_TITLE));
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvTitle.setTextColor(titleColor);
        tvTitle.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
        top.addView(tvTitle);

        daysValueView = new TextView(this);
        daysValueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        daysValueView.setTextColor(ONEUI_BLUE);
        top.addView(daysValueView);
        box.addView(top);

        SeekBar sb = new SeekBar(this);
        sb.setMax(10);
        sb.setProgress(SBrowserXPrefs.getHighlightDays(this));
        sb.setPadding(0, dp(10), 0, dp(4));
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    SBrowserXPrefs.setHighlightDays(SBrowserXSettingsActivity.this, progress);
                    refreshDynamicViews();
                }
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        box.addView(sb);
        parent.addView(box);
    }

    private int dp(int value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics()));
    }
}
