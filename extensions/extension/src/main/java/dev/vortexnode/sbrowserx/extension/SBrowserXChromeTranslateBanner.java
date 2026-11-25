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
import android.view.MotionEvent;
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
 * Unified translucent One UI Glass + Chrome-style translation pill.
 * - In main browser (SBrowserMainActivity): anchored cleanly at BOTTOM right above the address bar for one-handed reach.
 * - In Custom Tabs (Telegram links, etc.): anchored at TOP right below the Custom Tab toolbar.
 * - Supports tap on language pair to quickly switch target language, swipe-to-dismiss, gear menu, and instant Undo.
 */
public final class SBrowserXChromeTranslateBanner {
    private static final String BANNER_TAG = "sbrowserx_chrome_translate_banner";
    private static final int ONEUI_BLUE = 0xFF6BA6FF;
    private static final Handler MAIN = new Handler(Looper.getMainLooper());

    private SBrowserXChromeTranslateBanner() {}

    public static void show(Activity activity, String sourceLang, String targetLang, String host) {
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) return;
        final boolean isCustomTab = activity.getClass().getSimpleName().contains("CustomTab");

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

                // One UI translucent glass look
                int bgColor = night ? 0xD6181A20 : 0xE6FFFFFF;
                int borderColor = night ? 0x55FFFFFF : 0x28000000;
                int textPrimary = night ? 0xFFF5F5F7 : 0xFF151518;
                int textSecondary = night ? 0xFFB0B5C4 : 0xFF5F6368;
                int gearBg = night ? 0x44FFFFFF : 0x18000000;

                LinearLayout pill = new LinearLayout(activity);
                pill.setTag(BANNER_TAG);
                pill.setOrientation(LinearLayout.HORIZONTAL);
                pill.setGravity(Gravity.CENTER_VERTICAL);
                pill.setPadding(dp(activity, 14), dp(activity, 10), dp(activity, 14), dp(activity, 10));
                pill.setElevation(dp(activity, 12));

                GradientDrawable bg = new GradientDrawable();
                bg.setColor(bgColor);
                bg.setCornerRadius(dp(activity, 24));
                bg.setStroke(dp(activity, 1), borderColor);
                pill.setBackground(bg);

                // Compact engine badge
                String eng = SBrowserXPrefs.getTranslateEngine(activity);
                TextView badge = new TextView(activity);
                badge.setText(engineShortBadge(eng));
                badge.setTypeface(Typeface.DEFAULT_BOLD);
                badge.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                badge.setTextColor(0xFFFFFFFF);
                badge.setGravity(Gravity.CENTER);
                GradientDrawable badgeBg = new GradientDrawable();
                badgeBg.setColor(engineBadgeColor(eng));
                badgeBg.setCornerRadius(dp(activity, 7));
                badge.setBackground(badgeBg);
                LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(dp(activity, 28), dp(activity, 28));
                blp.rightMargin = dp(activity, 10);
                badge.setLayoutParams(blp);
                pill.addView(badge);

                // Title + language pair (tapping opens quick target language picker)
                LinearLayout textCol = new LinearLayout(activity);
                textCol.setOrientation(LinearLayout.VERTICAL);
                textCol.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                textCol.setOnClickListener(v -> showLanguagePicker(activity, sourceLang, host, night, ru));

                TextView title = new TextView(activity);
                title.setText(ru ? "Страница переведена" : "Page translated");
                title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                title.setTypeface(Typeface.DEFAULT_BOLD);
                title.setTextColor(textPrimary);
                textCol.addView(title);

                String srcName = displayLang(sourceLang, ru);
                String dstName = displayLang(targetLang, ru);
                TextView sub = new TextView(activity);
                sub.setText(srcName + " \u2192 " + dstName + " \u25BE");
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

                // Undo / Original button
                TextView cancel = new TextView(activity);
                cancel.setText(ru ? "Оригинал" : "Original");
                cancel.setTextSize(TypedValue.COMPLEX_UNIT_SP, 13);
                cancel.setTypeface(Typeface.DEFAULT_BOLD);
                cancel.setTextColor(ONEUI_BLUE);
                cancel.setPadding(dp(activity, 6), dp(activity, 6), dp(activity, 4), dp(activity, 6));
                cancel.setOnClickListener(v -> {
                    SBrowserXDomTranslator.revertTab(activity);
                    dismiss(activity);
                });
                pill.addView(cancel);

                // Swipe horizontally to dismiss pill cleanly
                attachSwipeToDismiss(activity, pill);

                FrameLayout.LayoutParams flp = new FrameLayout.LayoutParams(
                        FrameLayout.LayoutParams.MATCH_PARENT,
                        FrameLayout.LayoutParams.WRAP_CONTENT,
                        isCustomTab ? Gravity.TOP : Gravity.BOTTOM
                );
                flp.leftMargin = dp(activity, 12);
                flp.rightMargin = dp(activity, 12);
                if (isCustomTab) {
                    flp.topMargin = dp(activity, 94);
                } else {
                    // Positioned cleanly ABOVE bottom address bar & navigation bar without covering URL bar
                    flp.bottomMargin = dp(activity, 152);
                }

                pill.setAlpha(0f);
                pill.setTranslationY(isCustomTab ? -dp(activity, 14) : dp(activity, 14));
                decor.addView(pill, flp);
                pill.animate().alpha(1f).translationY(0f).setDuration(200).start();

                MAIN.postDelayed(() -> {
                    if (pill.isAttachedToWindow()) {
                        dismiss(activity);
                    }
                }, 14000L);
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

    private static void attachSwipeToDismiss(Activity activity, View pill) {
        final float[] downX = new float[1];
        pill.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                downX[0] = event.getRawX();
            } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
                float dx = event.getRawX() - downX[0];
                if (Math.abs(dx) > 16) {
                    v.setTranslationX(dx);
                    v.setAlpha(Math.max(0.2f, 1f - Math.abs(dx) / 450f));
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_CANCEL) {
                float dx = event.getRawX() - downX[0];
                if (Math.abs(dx) > 150) {
                    dismiss(activity);
                    return true;
                } else {
                    v.animate().translationX(0f).alpha(1f).setDuration(150).start();
                }
            }
            return false;
        });
    }

    private static String engineShortBadge(String eng) {
        if ("microsoft".equalsIgnoreCase(eng)) return "M";
        if ("deepl".equalsIgnoreCase(eng)) return "DL";
        if ("gemini".equalsIgnoreCase(eng)) return "AI";
        return "G";
    }

    private static int engineBadgeColor(String eng) {
        if ("microsoft".equalsIgnoreCase(eng)) return 0xFF0078D4;
        if ("deepl".equalsIgnoreCase(eng)) return 0xFF0F2B46;
        if ("gemini".equalsIgnoreCase(eng)) return 0xFF7C4DFF;
        return 0xFF4285F4;
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
        pm.getMenu().add(0, 6, 4, ru ? "Сменить движок перевода" : "Switch translation engine");
        pm.getMenu().add(0, 5, 5, SBrowserXStrings.get(SBrowserXStrings.GTRANS_SETTINGS_TITLE));

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
            } else if (id == 6) {
                showQuickEnginePicker(activity, sourceLang, targetLang, night, ru);
                return true;
            } else if (id == 5) {
                activity.startActivity(new Intent(activity, SBrowserXTranslateSettingsActivity.class));
                return true;
            }
            return false;
        });
        pm.show();
    }

    private static void showQuickEnginePicker(Activity activity, String sourceLang, String targetLang, boolean night, boolean ru) {
        int theme = night
                ? android.R.style.Theme_DeviceDefault_Dialog_Alert
                : android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
        String[] labels = new String[] {
                "Google Translate (Chrome NMT)",
                "Microsoft Translator (Edge NMT)",
                "DeepL API (Free / Pro)",
                "Gemini 2.5 Flash (AI)"
        };
        String[] values = new String[] {"google", "microsoft", "deepl", "gemini"};
        String cur = SBrowserXPrefs.getTranslateEngine(activity);
        int sel = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i].equalsIgnoreCase(cur)) sel = i;
        }
        new AlertDialog.Builder(activity, theme)
                .setTitle(ru ? "Движок перевода" : "Translation engine")
                .setSingleChoiceItems(labels, sel, (dialog, which) -> {
                    SBrowserXPrefs.setTranslateEngine(activity, values[which]);
                    SBrowserXDomTranslator.translateTab(activity, sourceLang, targetLang, true);
                    dialog.dismiss();
                })
                .setNegativeButton(android.R.string.cancel, null)
                .show();
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
