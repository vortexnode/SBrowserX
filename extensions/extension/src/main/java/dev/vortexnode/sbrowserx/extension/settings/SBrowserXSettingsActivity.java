package dev.vortexnode.sbrowserx.extension.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
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

import dev.vortexnode.sbrowserx.extension.SBrowserXPrefs;
import dev.vortexnode.sbrowserx.extension.SBrowserXStrings;

public final class SBrowserXSettingsActivity extends Activity {
    private static final int TEAL_DOT_COLOR = 0xFF00BFA5;

    private TextView webrtcSummaryView;
    private View webrtcDot;
    private View amoledDot;
    private View blurDot;
    private TextView daysValueView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SBrowserXPrefs.init(this);
        buildUi();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDynamicViews();
    }

    private void buildUi() {
        boolean night = (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES;
        boolean amoled = night && SBrowserXPrefs.isAmoledBlackEnabled(this);

        int bgColor = amoled ? 0xFF000000 : (night ? 0xFF010101 : 0xFFF6F6F8);
        int cardColor = amoled ? 0xFF121212 : (night ? 0xFF1C1C1E : 0xFFFFFFFF);
        int textPrimary = night ? 0xFFF5F5F7 : 0xFF111113;
        int textSecondary = night ? 0xFFA0A0A8 : 0xFF636368;
        int accent = 0xFF6250FF;

        getWindow().setStatusBarColor(bgColor);
        getWindow().setNavigationBarColor(bgColor);

        ScrollView scroll = new ScrollView(this);
        scroll.setBackgroundColor(bgColor);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(dp(16), dp(20), dp(16), dp(32));
        scroll.addView(root);

        // Header bar
        LinearLayout header = new LinearLayout(this);
        header.setOrientation(LinearLayout.HORIZONTAL);
        header.setGravity(Gravity.CENTER_VERTICAL);
        header.setPadding(dp(4), dp(8), dp(4), dp(16));

        TextView back = new TextView(this);
        back.setText("\u2190");
        back.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        back.setTextColor(textPrimary);
        back.setPadding(dp(4), dp(4), dp(16), dp(4));
        back.setOnClickListener(v -> finish());
        header.addView(back);

        TextView title = new TextView(this);
        title.setText(SBrowserXStrings.get(SBrowserXStrings.TITLE_HUB));
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 22);
        title.setTypeface(Typeface.DEFAULT_BOLD);
        title.setTextColor(textPrimary);
        header.addView(title);
        root.addView(header);

        // --- Privacy & Security ---
        root.addView(sectionHeader(SBrowserXStrings.get(SBrowserXStrings.CAT_PRIVACY), textSecondary));
        LinearLayout privacyCard = card(cardColor);
        webrtcDot = createTealDot();
        webrtcSummaryView = addActionRow(
                privacyCard,
                SBrowserXStrings.get(SBrowserXStrings.WEBRTC_TITLE),
                SBrowserXStrings.webrtcLabel(SBrowserXPrefs.getWebRtcPolicy(this)),
                textPrimary,
                textSecondary,
                webrtcDot,
                v -> startActivity(new Intent(this, SBrowserXWebRtcDialogActivity.class))
        );
        root.addView(privacyCard);

        // --- Appearance & UI ---
        root.addView(sectionHeader(SBrowserXStrings.get(SBrowserXStrings.CAT_APPEARANCE), textSecondary));
        LinearLayout appearanceCard = card(cardColor);
        amoledDot = createTealDot();
        addSwitchRow(
                appearanceCard,
                SBrowserXStrings.get(SBrowserXStrings.AMOLED_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.AMOLED_SUMMARY),
                SBrowserXPrefs.isAmoledBlackEnabled(this),
                textPrimary,
                textSecondary,
                amoledDot,
                (btn, checked) -> {
                    SBrowserXPrefs.setAmoledBlackEnabled(this, checked);
                    refreshDynamicViews();
                }
        );
        appearanceCard.addView(divider(night));
        blurDot = createTealDot();
        addSwitchRow(
                appearanceCard,
                SBrowserXStrings.get(SBrowserXStrings.BOTTOM_BLUR_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.BOTTOM_BLUR_SUMMARY),
                SBrowserXPrefs.isBottomBarBlurEnabled(this),
                textPrimary,
                textSecondary,
                blurDot,
                (btn, checked) -> {
                    SBrowserXPrefs.setBottomBarBlurEnabled(this, checked);
                    refreshDynamicViews();
                }
        );
        root.addView(appearanceCard);

        // --- New Features Highlight ---
        root.addView(sectionHeader(SBrowserXStrings.get(SBrowserXStrings.CAT_HIGHLIGHT), textSecondary));
        LinearLayout highlightCard = card(cardColor);
        addDaysSliderRow(highlightCard, textPrimary, textSecondary);
        highlightCard.addView(divider(night));
        addActionRow(
                highlightCard,
                SBrowserXStrings.get(SBrowserXStrings.MARK_ALL_VIEWED),
                null,
                accent,
                textSecondary,
                null,
                v -> {
                    SBrowserXPrefs.markAllViewed(this);
                    refreshDynamicViews();
                    Toast.makeText(this, SBrowserXStrings.get(SBrowserXStrings.MARK_ALL_VIEWED_DONE), Toast.LENGTH_SHORT).show();
                }
        );
        root.addView(highlightCard);

        // --- Restart Browser ---
        LinearLayout restartCard = card(cardColor);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) restartCard.getLayoutParams();
        lp.topMargin = dp(16);
        addActionRow(
                restartCard,
                SBrowserXStrings.get(SBrowserXStrings.RESTART_TITLE),
                SBrowserXStrings.get(SBrowserXStrings.RESTART_SUMMARY),
                accent,
                textSecondary,
                null,
                v -> SBrowserXWebRtcDialogActivity.restartBrowser(this)
        );
        root.addView(restartCard);

        setContentView(scroll);
        refreshDynamicViews();
    }

    private void refreshDynamicViews() {
        if (webrtcSummaryView != null) {
            webrtcSummaryView.setText(SBrowserXStrings.webrtcLabel(SBrowserXPrefs.getWebRtcPolicy(this)));
        }
        if (webrtcDot != null) {
            webrtcDot.setVisibility(SBrowserXPrefs.isFeatureNew(this, SBrowserXPrefs.FEATURE_WEBRTC) ? View.VISIBLE : View.GONE);
        }
        if (amoledDot != null) {
            amoledDot.setVisibility(SBrowserXPrefs.isFeatureNew(this, SBrowserXPrefs.FEATURE_AMOLED) ? View.VISIBLE : View.GONE);
        }
        if (blurDot != null) {
            blurDot.setVisibility(SBrowserXPrefs.isFeatureNew(this, SBrowserXPrefs.FEATURE_BOTTOM_BLUR) ? View.VISIBLE : View.GONE);
        }
        if (daysValueView != null) {
            int days = SBrowserXPrefs.getHighlightDays(this);
            daysValueView.setText(days == 0
                    ? SBrowserXStrings.get(SBrowserXStrings.HIGHLIGHT_DAYS_OFF)
                    : String.format(SBrowserXStrings.get(SBrowserXStrings.HIGHLIGHT_DAYS_FMT), days));
        }
    }

    private TextView sectionHeader(String text, int color) {
        TextView tv = new TextView(this);
        tv.setText(text);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
        tv.setTypeface(Typeface.DEFAULT_BOLD);
        tv.setTextColor(color);
        tv.setPadding(dp(12), dp(14), dp(12), dp(6));
        return tv;
    }

    private LinearLayout card(int bgColor) {
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable bg = new GradientDrawable();
        bg.setColor(bgColor);
        bg.setCornerRadius(dp(20));
        ll.setBackground(bg);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ll.setLayoutParams(lp);
        return ll;
    }

    private View divider(boolean night) {
        View v = new View(this);
        v.setBackgroundColor(night ? 0x22FFFFFF : 0x16000000);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 1);
        lp.leftMargin = dp(18);
        lp.rightMargin = dp(18);
        v.setLayoutParams(lp);
        return v;
    }

    private View createTealDot() {
        View dot = new View(this);
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setColor(TEAL_DOT_COLOR);
        dot.setBackground(shape);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(dp(8), dp(8));
        lp.leftMargin = dp(8);
        dot.setLayoutParams(lp);
        return dot;
    }

    private TextView addActionRow(
            LinearLayout parent,
            String title,
            String summary,
            int titleColor,
            int summaryColor,
            View badgeDot,
            View.OnClickListener listener
    ) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(dp(18), dp(14), dp(18), dp(14));
        row.setClickable(true);
        row.setOnClickListener(listener);

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

    private void addSwitchRow(
            LinearLayout parent,
            String title,
            String summary,
            boolean initialValue,
            int titleColor,
            int summaryColor,
            View badgeDot,
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
        sw.setChecked(initialValue);
        sw.setOnCheckedChangeListener(listener);

        row.addView(textCol);
        row.addView(sw);
        row.setOnClickListener(v -> sw.toggle());
        parent.addView(row);
    }

    private void addDaysSliderRow(LinearLayout parent, int titleColor, int summaryColor) {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.VERTICAL);
        row.setPadding(dp(18), dp(14), dp(18), dp(14));

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
        daysValueView.setTextColor(summaryColor);
        top.addView(daysValueView);
        row.addView(top);

        SeekBar seek = new SeekBar(this);
        seek.setMax(10);
        seek.setProgress(SBrowserXPrefs.getHighlightDays(this));
        seek.setPadding(0, dp(10), 0, dp(4));
        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                SBrowserXPrefs.setHighlightDays(SBrowserXSettingsActivity.this, progress);
                refreshDynamicViews();
            }
            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
        row.addView(seek);
        parent.addView(row);
    }

    private int dp(int value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, getResources().getDisplayMetrics()));
    }
}
