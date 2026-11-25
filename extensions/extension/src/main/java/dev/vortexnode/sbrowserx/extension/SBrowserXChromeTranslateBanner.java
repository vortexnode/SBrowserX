package dev.vortexnode.sbrowserx.extension;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.Locale;
import java.util.Set;

import dev.vortexnode.sbrowserx.extension.settings.SBrowserXTranslateSettingsActivity;

/**
 * Compact Chrome-style floating translation pill ("Страница переведена | английский — русский | ⚙ | Отмена")
 * with dropdown menu (Other languages, Always translate, Never translate language, Never translate this site, Settings).
 */
public final class SBrowserXChromeTranslateBanner {
    private static final String BANNER_TAG = "sbrowserx_chrome_translate_banner";
    private static final int ONEUI_BLUE = 0xFF6BA6FF;
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    private SBrowserXChromeTranslateBanner() {}

    public static void show(Activity activity, String sourceLang, String targetLang, String host) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) return;
        MAIN.post(() -> {
            try {
                ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
                View existing = decor.findViewWithTag(BANNER_TAG);
                if (existing != null && existing.getParent() instanceof ViewGroup) {
                    ((ViewGroup) existing.getParent()).removeView(existing);
                }

                boolean night = (activity.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                        == Configuration.UI_MODE_NIGHT_YES;
                boolean ru = isRu();

                int bgColor = night ? 0xF21B1D22 : 0xF7FFFFFF;
                int borderColor = night ? 0xFF343844 : 0xFFDCDCE2;
                int textPrimary = night ? 0xFFF5F5F7 : 0xFF151518;
                int textSecondary = night ? 0xFFA2A7B5 : 0xFF5F6368;
                int gearBg = night ? 0xFF2B2F3A : 0xFFECEEF2;

                LinearLayout pill = new LinearLayout(activity);
                pill.setTag(BANNER_TAG);
                pill.setOrientation(LinearLayout.HORIZONTAL);
                pill.setGravity(Gravity.CENTER_VERTICAL);
                pill.setPadding(dp(activity, 14), dp(activity, 10), dp(activity, 14), dp(activity, 10));
                pill.setElevation(dp(activity, 10));

                GradientDrawable bg = new GradientDrawable();
                bg.setColor(bgColor);
                bg.setCornerRadius(dp(activity, 24));
                bg.setStroke(dp(activity, 1), borderColor);
                pill.setBackground(bg);

                // Compact G-Translate badge
                TextView badge = new TextView(activity);
                badge.setText("G");
                badge.setTypeface(Typeface.DEFAULT_BOLD);
                badge.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                badge.setTextColor(0xFFFFFFFF);
                badge.setGravity(Gravity.CENTER);
                GradientDrawable badgeBg = new GradientDrawable();
                badgeBg.setColor(0xFF4285F4);
                badgeBg.setCornerRadius(dp(activity, 6));
                badge.setBackground(badgeBg);
                LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(dp(activity, 26), dp(activity, 26));
                blp.rightMargin = dp(activity, 10);
                badge.setLayoutParams(blp);
                pill.addView(badge);

                // Title + language pair
                LinearLayout textCol = new LinearLayout(activity);
                textCol.setOrientation(LinearLayout.VERTICAL);
                textCol.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                TextView title = new TextView(activity);
                title.setText(ru ? "Страница переведена" : "Page translated");
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                title.setTypeface(Typeface.DEFAULT_BOLD);
                title.setTextColor(textPrimary);
                textCol.addView(title);

                String srcName = displayLang(sourceLang, ru);
                String dstName = displayLang(targetLang, ru);
                TextView sub = new TextView(activity);
                sub.setText(srcName + " \u2014 " + dstName);
                sub.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                sub.setTextColor(textSecondary);
                textCol.addView(sub);
                pill.addView(textCol);

                // Gear button (Chrome-style dropdown menu)
                TextView gear = new TextView(activity);
                gear.setText("\u2699");
                gear.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                gear.setTextColor(textPrimary);
                gear.setGravity(Gravity.CENTER);
                GradientDrawable gbg = new GradientDrawable();
                gbg.setShape(GradientDrawable.OVAL);
                gbg.setColor(gearBg);
                gear.setBackground(gbg);
                LinearLayout.LayoutParams glp = new LinearLayout.LayoutParams(dp(activity, 34), dp(activity, 34));
                glp.leftMargin = dp(activity, 8);
                glp.rightMargin = dp(activity, 10);
                gear.setLayoutParams(glp);
                gear.setOnClickListener(v -> showOptionsMenu(activity, v, sourceLang, targetLang, host, night, ru));
                pill.addView(gear);

                // Undo / Cancel button
                TextView cancel = new TextView(activity);
                cancel.setText(ru ? "Отмена" : "Undo");
                cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                cancel.setTypeface(Typeface.DEFAULT_BOLD);
                cancel.setTextColor(ONEUI_BLUE);
                cancel.setPadding(dp(activity, 6), dp(activity, 6), dp(activity, 4), dp(activity, 6));
                cancel.setOnClickListener(v -> {
                    SBrowserXDomTranslator.revertTab(activity);
                    dismiss(activity);
                });
                pill.addView(cancel);

                FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        Gravity.TOP
                );
                flp.leftMargin = dp(activity, 12);
                flp.rightMargin = dp(activity, 12);
                flp.topMargin = dp(activity, 94); // sits neatly right below Custom Tab top toolbar
                decor.addView(pill, flp);

                // Auto-hide smoothly after 12 seconds (tap gear or Cancel anytime while visible)
                MAIN.postDelayed(() -> {
                    if (pill.isAttachedToWindow()) {
                        dismiss(activity);
                    }
                }, 12000L);
            } catch (Throwable ignored) {}
        });
    }

    public static void dismiss(Activity activity) {
        if (activity == null) return;
        MAIN.post(() -> {
            try {
                ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
                View existing = decor.findViewWithTag(BANNER_TAG);
                if (existing != null && existing.getParent() instanceof ViewGroup) {
                    ((ViewGroup) existing.getParent()).removeView(existing);
                }
            } catch (Throwable ignored) {}
        });
    }

    private static void showOptionsMenu(
            Activity activity, View anchor, String sourceLang, String targetLang, String host, boolean night, boolean ru
    ) {
        String srcCode = (sourceLang == null || sourceLang.isEmpty() || "auto".equals(sourceLang)) ? "en" : sourceLang;
        String srcName = displayLang(srcCode, ru);

        PopupMenu pm = new PopupMenu(activity, anchor);
        pm.getMenu().add(0, 1, 0, ru ? "Другие языки" : "More languages");
        pm.getMenu().add(0, 2, 1, (ru ? "Всегда переводить " : "Always translate ") + srcName);
        pm.getMenu().add(0, 3, 2, (ru ? "Не переводить " : "Never translate ") + srcName);
        if (host != null && !host.isEmpty()) {
            pm.getMenu().add(0, 4, 3, ru ? "Никогда не переводить этот сайт" : "Never translate this site");
        }
        pm.getMenu().add(0, 5, 4, SBrowserXStrings.get(SBrowserXStrings.GTRANS_SETTINGS_TITLE));

        pm.setOnMenuItemClickListener(item -> {
            int id = item.getItemId();
            if (id == 1) {
                showLanguagePicker(activity, sourceLang, host, night, ru);
                return true;
            } else if (id == 2) {
                Set<String> always = SBrowserXPrefs.getTranslateAlwaysLangs(activity);
                always.add(srcCode);
                SBrowserXPrefs.setTranslateAlwaysLangs(activity, always);
                Set<String> never = SBrowserXPrefs.getTranslateNeverLangs(activity);
                never.remove(srcCode);
                SBrowserXPrefs.setTranslateNeverLangs(activity, never);
                SBrowserXPrefs.setTranslateAutoEnabled(activity, true);
                return true;
            } else if (id == 3) {
                Set<String> never = SBrowserXPrefs.getTranslateNeverLangs(activity);
                never.add(srcCode);
                SBrowserXPrefs.setTranslateNeverLangs(activity, never);
                Set<String> always = SBrowserXPrefs.getTranslateAlwaysLangs(activity);
                always.remove(srcCode);
                SBrowserXPrefs.setTranslateAlwaysLangs(activity, always);
                SBrowserXDomTranslator.revertTab(activity);
                dismiss(activity);
                return true;
            } else if (id == 4 && host != null && !host.isEmpty()) {
                Set<String> sites = SBrowserXPrefs.getTranslateNeverSites(activity);
                sites.add(host.toLowerCase(Locale.US));
                SBrowserXPrefs.setTranslateNeverSites(activity, sites);
                SBrowserXDomTranslator.revertTab(activity);
                dismiss(activity);
                return true;
            } else if (id == 5) {
                activity.startActivity(new Intent(activity, SBrowserXTranslateSettingsActivity.class));
                return true;
            }
            return false;
        });
        pm.show();
    }

    private static void showLanguagePicker(Activity activity, String sourceLang, String host, boolean night, boolean ru) {
        int theme = night
                ? android.R.style.Theme_DeviceDefault_Dialog_Alert
                : android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
        String[][] langs = SBrowserXGoogleTranslator.SUPPORTED_LANGUAGES;
        String[] labels = new String[langs.length];
        String current = SBrowserXPrefs.getTranslateTargetLang(activity);
        int selected = 0;
        for (int i = 0; i < langs.length; i++) {
            labels[i] = langs[i][1];
            if (langs[i][0].equalsIgnoreCase(current)) selected = i;
        }
        new AlertDialog.Builder(activity, theme)
                .setTitle(ru ? "Перевести на язык" : "Translate to")
                .setSingleChoiceItems(labels, selected, (dialog, which) -> {
                    String newLang = langs[which][0];
                    SBrowserXPrefs.setTranslateTargetLang(activity, newLang);
                    SBrowserXDomTranslator.translateTab(activity, sourceLang, newLang, true);
                    dialog.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
    }

    private static String displayLang(String code, boolean ru) {
        if (code == null || code.isEmpty() || "auto".equalsIgnoreCase(code)) {
            return ru ? "авто" : "auto";
        }
        try {
            String name = Locale.forLanguageTag(code.replace('_', '-')).getDisplayLanguage(Locale.getDefault());
            if (name != null && !name.isEmpty()) return name.toLowerCase(Locale.getDefault());
        } catch (Throwable ignored) {}
        return code;
    }

    private static boolean isRu() {
        String l = Locale.getDefault().getLanguage();
        return "ru".equals(l) || "uk".equals(l);
    }

    private static int dp(Activity act, int value) {
        return Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, value, act.getResources().getDisplayMetrics()));
    }
}
