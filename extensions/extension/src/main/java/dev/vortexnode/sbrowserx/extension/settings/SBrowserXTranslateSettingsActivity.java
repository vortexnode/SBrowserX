package dev.vortexnode.sbrowserx.extension.settings;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import dev.vortexnode.sbrowserx.extension.SBrowserXGoogleTranslator;
import dev.vortexnode.sbrowserx.extension.SBrowserXPrefs;
import dev.vortexnode.sbrowserx.extension.SBrowserXStrings;

public final class SBrowserXTranslateSettingsActivity extends Activity {
    private static final int ONEUI_BLUE = 0xFF387AFF;

    private TextView targetLangSummary;
    private TextView neverLangsSummary;
    private TextView alwaysLangsSummary;
    private Switch masterSwitch;
    private Switch autoSwitch;
    private int dialogTheme;

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
        dialogTheme = night
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
        title.setText(SBrowserXStrings.get(SBrowserXStrings.GTRANS_SETTINGS_TITLE));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 21);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(textPrimary);
        header.addView(title);
        root.addView(header);

        LinearLayout mainCard = card(cardColor);
        masterSwitch = addSwitchRow(mainCard,
                SBrowserXStrings.get(SBrowserXStrings.GTRANS_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.GTRANS_SUMMARY),
                SBrowserXPrefs.isGoogleTranslateEnabled(this), night, textPrimary, textSecondary,
                (btn, checked) -> SBrowserXPrefs.setGoogleTranslateEnabled(this, checked));
        mainCard.addView(divider(night));
        autoSwitch = addSwitchRow(mainCard,
                SBrowserXStrings.get(SBrowserXStrings.GTRANS_AUTO_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.GTRANS_AUTO_SUMMARY),
                SBrowserXPrefs.isTranslateAutoEnabled(this), night, textPrimary, textSecondary,
                (btn, checked) -> SBrowserXPrefs.setTranslateAutoEnabled(this, checked));
        root.addView(mainCard);

        LinearLayout langCard = card(cardColor);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) langCard.getLayoutParams();
        lp.topMargin = dp(16);

        targetLangSummary = addActionRow(langCard,
                SBrowserXStrings.get(SBrowserXStrings.GTRANS_TARGET_LANG),
                "", textPrimary, textSecondary, v -> showTargetLangPicker());
        langCard.addView(divider(night));
        neverLangsSummary = addActionRow(langCard,
                SBrowserXStrings.get(SBrowserXStrings.GTRANS_NEVER_LANGS),
                "", textPrimary, textSecondary, v -> showMultiLangPicker(true));
        langCard.addView(divider(night));
        alwaysLangsSummary = addActionRow(langCard,
                SBrowserXStrings.get(SBrowserXStrings.GTRANS_ALWAYS_LANGS),
                "", textPrimary, textSecondary, v -> showMultiLangPicker(false));
        langCard.addView(divider(night));
        addActionRow(langCard,
                SBrowserXStrings.get(SBrowserXStrings.GTRANS_RESET),
                null, ONEUI_BLUE, textSecondary, v -> {
                    SBrowserXPrefs.resetTranslateSettings(this);
                    refresh();
                    Toast.makeText(this, SBrowserXStrings.get(SBrowserXStrings.GTRANS_RESET_DONE), Toast.LENGTH_SHORT).show();
                });
        root.addView(langCard);

        scroll.addView(root);
        setContentView(scroll);
        refresh();
    }

    private void refresh() {
        masterSwitch.setChecked(SBrowserXPrefs.isGoogleTranslateEnabled(this));
        autoSwitch.setChecked(SBrowserXPrefs.isTranslateAutoEnabled(this));
        targetLangSummary.setText(SBrowserXGoogleTranslator.getLanguageDisplayName(
                SBrowserXPrefs.getTranslateTargetLang(this)));
        neverLangsSummary.setText(formatLangSet(SBrowserXPrefs.getTranslateNeverLangs(this)));
        alwaysLangsSummary.setText(formatLangSet(SBrowserXPrefs.getTranslateAlwaysLangs(this)));
    }

    private String formatLangSet(Set<String> codes) {
        if (codes == null || codes.isEmpty()) {
            return SBrowserXStrings.get(SBrowserXStrings.GTRANS_NONE);
        }
        List<String> names = new ArrayList<>();
        for (String[] pair : SBrowserXGoogleTranslator.SUPPORTED_LANGUAGES) {
            if (codes.contains(pair[0])) {
                names.add(pair[1]);
            }
        }
        if (names.isEmpty()) return SBrowserXStrings.get(SBrowserXStrings.GTRANS_NONE);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < names.size(); i++) {
            if (i > 0) sb.append(", ");
            sb.append(names.get(i));
        }
        return sb.toString();
    }

    private void showTargetLangPicker() {
        String[][] langs = SBrowserXGoogleTranslator.SUPPORTED_LANGUAGES;
        String[] labels = new String[langs.length];
        String current = SBrowserXPrefs.getTranslateTargetLang(this);
        int selected = 0;
        for (int i = 0; i < langs.length; i++) {
            labels[i] = langs[i][1];
            if (langs[i][0].equalsIgnoreCase(current)) selected = i;
        }
        new AlertDialog.Builder(this, dialogTheme)
                .setTitle(SBrowserXStrings.get(SBrowserXStrings.GTRANS_TARGET_LANG))
                .setSingleChoiceItems(labels, selected, (dialog, which) -> {
                    SBrowserXPrefs.setTranslateTargetLang(this, langs[which][0]);
                    refresh();
                    dialog.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private void showMultiLangPicker(boolean isNever) {
        String[][] langs = SBrowserXGoogleTranslator.SUPPORTED_LANGUAGES;
        String[] labels = new String[langs.length];
        boolean[] checked = new boolean[langs.length];
        Set<String> current = isNever
                ? SBrowserXPrefs.getTranslateNeverLangs(this)
                : SBrowserXPrefs.getTranslateAlwaysLangs(this);
        for (int i = 0; i < langs.length; i++) {
            labels[i] = langs[i][1];
            checked[i] = current.contains(langs[i][0]);
        }
        new AlertDialog.Builder(this, dialogTheme)
                .setTitle(SBrowserXStrings.get(isNever ? SBrowserXStrings.GTRANS_NEVER_LANGS : SBrowserXStrings.GTRANS_ALWAYS_LANGS))
                .setMultiChoiceItems(labels, checked, (dialog, which, isChecked) -> checked[which] = isChecked)
                .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    java.util.HashSet<String> out = new java.util.HashSet<>();
                    for (int i = 0; i < langs.length; i++) {
                        if (checked[i]) out.add(langs[i][0]);
                    }
                    if (isNever) {
                        SBrowserXPrefs.setTranslateNeverLangs(this, out);
                    } else {
                        SBrowserXPrefs.setTranslateAlwaysLangs(this, out);
                    }
                    refresh();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private LinearLayout card(int bgColor) {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bgColor);
        gd.setCornerRadius(dp(22));
        ll.setBackground(gd);
        ll.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
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
            View.OnClickListener onClick
    ) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(dp(18), dp(15), dp(18), dp(15));
        row.setOnClickListener(onClick);

        TextView tvTitle = new TextView(this);
        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvTitle.setTextColor(titleColor);
        row.addView(tvTitle);

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
            int titleColor, int summaryColor,
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

        TextView tvTitle = new TextView(this);
        tvTitle.setText(title);
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        tvTitle.setTextColor(titleColor);
        textCol.addView(tvTitle);

        TextView tvSummary = new TextView(this);
        tvSummary.setText(summary);
        tvSummary.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tvSummary.setTextColor(summaryColor);
        tvSummary.setPadding(0, dp(3), 0, 0);
        textCol.addView(tvSummary);

        Switch sw = new Switch(this);
        SBrowserXSettingsActivity.styleOneUiBlueSwitch(sw, night);
        sw.setChecked(initialValue);
        sw.setOnCheckedChangeListener(listener);

        row.addView(textCol);
        row.addView(sw);
        row.setOnClickListener(v -> sw.toggle());
        parent.addView(row);
        return sw;
    }

    private int dp(int value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics()));
    }
}
