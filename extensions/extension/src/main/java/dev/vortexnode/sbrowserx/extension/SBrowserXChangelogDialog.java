package dev.vortexnode.sbrowserx.extension;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Refined One UI frosted-glass "What's New" dialog displayed once on first launch after updating SBrowserX.
 */
public final class SBrowserXChangelogDialog {
    public static final String CURRENT_VERSION = "1.3.7";
    private static final String ACK_KEY_VERSION = "1.3.7-b17";
    private static volatile boolean sShowing = false;

    private SBrowserXChangelogDialog() {}

    public static void showIfNeeded(Activity act) {
        if (act == null || act.isFinishing() || act.isDestroyed() || sShowing) return;
        String seen = SBrowserXPrefs.getLastSeenChangelogVersion(act);
        if (ACK_KEY_VERSION.equals(seen)) return;
        sShowing = true;
        try {
            final boolean night = SBrowserXHooks.isBrowserNightMode(act);
            final float d = act.getResources().getDisplayMetrics().density;
            final int oneUiBlue = night ? 0xFF3E82F7 : 0xFF0381FE;
            final int uncheckedCol = night ? 0xFF7B8190 : 0xFF9CA3AF;
            final int dialogGlassBg = night ? 0xDD151821 : 0xEBFAFBFD;
            final int dialogBorder = night ? 0x2AFFFFFF : 0x1A000000;
            final int cardBg = night ? 0x5A262B38 : 0xCCFFFFFF;
            final int cardBorder = night ? 0x1CFFFFFF : 0x14000000;
            final int promoBg = night ? 0x382563EB : 0x160381FE;
            final int promoBorder = night ? 0x883E82F7 : 0x660381FE;
            final int textPrimary = night ? 0xFFF8FAFC : 0xFF111827;
            final int textSecondary = night ? 0xFFD8DEE9 : 0xFF374151;

            String lang = SBrowserXStrings.getAppLang();
            boolean ru = "ru".equals(lang);
            boolean uk = "uk".equals(lang);

            String title = ru ? "Что нового в SBrowserX"
                    : (uk ? "Що нового в SBrowserX"
                    : "What's New in SBrowserX");
            String hPromo = ru ? "★ Рекомендация: подключите Gemini API"
                    : (uk ? "★ Рекомендація: підключіть Gemini API"
                    : "★ Tip: Connect Free Gemini API");
            String promoBody = ru
                    ? "Настоятельно рекомендуем открыть «Настройки → Ассистент по просмотру», создать бесплатный ключ Gemini API в Google AI Studio, вставить его в поле ключа и выбрать Gemini 3.5 Flash-Lite основным движком переводчика. Это даст самый быстрый и точный перевод с пониманием контекста и сразу включит ИИ-сводку страниц."
                    : (uk
                    ? "Наполегливо рекомендуємо відкрити «Налаштування → Асистент перегляду», створити безкоштовний ключ Gemini API в Google AI Studio, вставити його в поле ключа та вибрати Gemini 3.5 Flash-Lite основним перекладачем. Це дасть найшвидший і найточніший переклад із розумінням контексту та одразу ввімкне ШІ-зведення сторінок."
                    : "We strongly recommend opening Settings → Browsing Assist, creating a free Gemini API key in Google AI Studio, pasting it into the API key field, and selecting Gemini 3.5 Flash-Lite as your primary translator. You'll get fast, context-aware translation plus AI Page Summary.");

            String hAdded = ru ? "Что добавлено" : (uk ? "Що додано" : "What's new");
            String hImproved = ru ? "Что улучшено и исправлено" : (uk ? "Що покращено та виправлено" : "Improved & fixed");
            String hRemoved = ru ? "Что удалено и защищено" : (uk ? "Що видалено та захищено" : "Removed & protected");
            String cbText = ru ? "Спасибо, всё понятно" : (uk ? "Дякую, все зрозуміло" : "Thanks, got it");
            String okText = "OK";

            String addedBody = ru
                    ? "• Новые движки перевода: Bing (по умолчанию), Яндекс, DeepL API и Gemini 3.5 Flash-Lite (AI).\n"
                    + "• Полная поддержка языка приложения (Per-App Language) и переводы на 20 мировых языков.\n"
                    + "• Сетка ярлыков стартовой страницы (4 или 5 колонок) и режим заполнения плитки иконкой.\n"
                    + "• Mate Translate добавлен в набор рекомендуемых расширений (теперь их 7)."
                    : (uk
                    ? "• Нові рушії перекладу: Bing (за замовчуванням), Яндекс, DeepL API та Gemini 3.5 Flash-Lite (AI).\n"
                    + "• Підтримка мови додатка (Per-App Language) та локалізація на 20 світових мов.\n"
                    + "• Сітка ярликів стартової сторінки (4 або 5 колонок) і режим заповнення плитки іконкою.\n"
                    + "• Mate Translate додано до рекомендованих розширень (тепер їх 7)."
                    : "• New translation engines: Bing (default), Yandex, DeepL API, and Gemini 3.5 Flash-Lite (AI).\n"
                    + "• Full Per-App Language support and natural translations across 20 major world languages.\n"
                    + "• Start Page shortcut grid (4 or 5 columns) and edge-to-edge tile icon fill mode.\n"
                    + "• Added Mate Translate to the Recommended Extensions Pack (now 7 extensions).");

            String improvedBody = ru
                    ? "• Обновлённый экран «Настройки SBrowserX» с единой верхней карточкой и синими акцентами One UI.\n"
                    + "• Бирюзовые точки (как в Morphe): пропадают автоматически после просмотра видимых пунктов.\n"
                    + "• Стабильная стартовая страница (исправлен «Адрес null недоступен», живое обновление обоев и погоды).\n"
                    + "• Белая иконка «Перевести» в тёмной теме везде, ровный баннер перевода и работа на Google News."
                    : (uk
                    ? "• Оновлений екран «Налаштування SBrowserX» із єдиною верхньою карткою та синіми акцентами One UI.\n"
                    + "• Бірюзові крапки (як у Morphe): зникають автоматично після перегляду видимих пунктів.\n"
                    + "• Стабільна стартова сторінка (виправлено «Адреса null недоступна», живі шпалери та погода).\n"
                    + "• Біла іконка «Перекласти» в темній темі всюди, охайний банер перекладу та робота на Google News."
                    : "• Redesigned SBrowserX settings with a unified top card and One UI accent summaries.\n"
                    + "• Visibility-based teal dots (Morphe-style): clear automatically once items appear on screen.\n"
                    + "• Rock-solid Start Page (fixed null address error + instant live wallpaper & weather refresh).\n"
                    + "• Crisp white Translate icon in Dark Mode everywhere, refined banner spacing, and Google News fix.");

            String removedBody = ru
                    ? "• Убран навязчивый промо-баннер «Сделать домашней страницей Samsung» в настройках.\n"
                    + "• Скрыты пустые/лишние штатные пункты (блок конфиденциальности, «О Samsung Browser», Debug).\n"
                    + "• Заблокирована установка устаревших Manifest V2 (.crx/.zip) до вылета с понятным диалогом One UI.\n"
                    + "• Удалён устаревший движок Microsoft Edge (HTTP 404)."
                    : (uk
                    ? "• Прибрано нав'язливий промо-банер «Зробити домашньою сторінкою Samsung» у налаштуваннях.\n"
                    + "• Приховано зайві штатні пункти (конфіденційність, «Про Samsung Browser», Debug).\n"
                    + "• Заблоковано встановлення застарілих Manifest V2 (.crx/.zip) до збою з діалогом One UI.\n"
                    + "• Видалено неробочий рушій Microsoft Edge (HTTP 404)."
                    : "• Removed the Samsung homepage promotion banner from Settings.\n"
                    + "• Hid unused stock Settings entries (Privacy block, About Samsung Browser, Debug settings).\n"
                    + "• Blocked incompatible Manifest V2 (.crx/.zip) installs with a clear One UI warning dialog.\n"
                    + "• Removed the deprecated Microsoft Edge translator endpoint (HTTP 404).");

            // Root frosted-glass One UI sheet
            LinearLayout root = new LinearLayout(act);
            root.setOrientation(LinearLayout.VERTICAL);
            GradientDrawable rootBg = new GradientDrawable();
            rootBg.setColor(dialogGlassBg);
            rootBg.setCornerRadius(dp(d, 28));
            rootBg.setStroke(Math.max(1, dp(d, 1)), dialogBorder);
            root.setBackground(rootBg);
            root.setPadding(dp(d, 20), dp(d, 20), dp(d, 20), dp(d, 16));

            // Header row with title + version pill
            LinearLayout headerRow = new LinearLayout(act);
            headerRow.setOrientation(LinearLayout.HORIZONTAL);
            headerRow.setGravity(Gravity.CENTER_VERTICAL);
            headerRow.setPadding(dp(d, 4), 0, dp(d, 4), dp(d, 14));

            TextView tvTitle = new TextView(act);
            tvTitle.setText(title);
            tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 19.5f);
            tvTitle.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
            tvTitle.setTextColor(textPrimary);
            headerRow.addView(tvTitle, new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

            TextView badge = new TextView(act);
            badge.setText("v" + CURRENT_VERSION);
            badge.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.5f);
            badge.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
            badge.setTextColor(0xFFFFFFFF);
            GradientDrawable badgeBg = new GradientDrawable();
            badgeBg.setColor(oneUiBlue);
            badgeBg.setCornerRadius(dp(d, 12));
            badge.setBackground(badgeBg);
            badge.setPadding(dp(d, 10), dp(d, 4), dp(d, 10), dp(d, 4));
            headerRow.addView(badge);
            root.addView(headerRow);

            // Scrollable cards area
            ScrollView sv = new ScrollView(act);
            sv.setVerticalScrollBarEnabled(false);
            sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
            LinearLayout cards = new LinearLayout(act);
            cards.setOrientation(LinearLayout.VERTICAL);

            // Separate highlighted Gemini API recommendation card at the top
            cards.addView(buildSectionCard(act, d, promoBg, promoBorder,
                    night ? 0xFF60A5FA : 0xFF0381FE, textPrimary, hPromo, promoBody, Math.max(2, dp(d, 1))));
            cards.addView(buildSectionCard(act, d, cardBg, cardBorder,
                    night ? 0xFF34D399 : 0xFF059669, textSecondary, hAdded, addedBody, 1));
            cards.addView(buildSectionCard(act, d, cardBg, cardBorder,
                    oneUiBlue, textSecondary, hImproved, improvedBody, 1));
            cards.addView(buildSectionCard(act, d, cardBg, cardBorder,
                    night ? 0xFFF87171 : 0xFFDC2626, textSecondary, hRemoved, removedBody, 1));

            sv.addView(cards);
            int maxScrollH = Math.round(act.getResources().getDisplayMetrics().heightPixels * 0.56f);
            LinearLayout.LayoutParams svLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, maxScrollH);
            root.addView(sv, svLp);

            // One UI Blue Checkbox
            ColorStateList cbTint = new ColorStateList(
                    new int[][] { new int[] { android.R.attr.state_checked }, new int[] {} },
                    new int[] { oneUiBlue, uncheckedCol });
            CheckBox cb = new CheckBox(act);
            cb.setText(cbText);
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.5f);
            cb.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            cb.setTextColor(textPrimary);
            cb.setButtonTintList(cbTint);
            cb.setPadding(dp(d, 6), dp(d, 12), 0, dp(d, 10));
            root.addView(cb);

            // Pill-shaped One UI OK button
            TextView okBtn = new TextView(act);
            okBtn.setText(okText);
            okBtn.setGravity(Gravity.CENTER);
            okBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            okBtn.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
            okBtn.setPadding(0, dp(d, 12), 0, dp(d, 12));
            final GradientDrawable okBg = new GradientDrawable();
            okBg.setCornerRadius(dp(d, 22));
            okBg.setColor(night ? 0x336B7280 : 0x269CA3AF);
            okBtn.setBackground(okBg);
            okBtn.setTextColor(uncheckedCol);
            okBtn.setEnabled(false);
            LinearLayout.LayoutParams btnLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            btnLp.topMargin = dp(d, 2);
            root.addView(okBtn, btnLp);

            final Dialog dlg = new Dialog(act);
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dlg.setContentView(root);
            dlg.setCancelable(false);
            dlg.setCanceledOnTouchOutside(false);

            final View activityDecor = act.getWindow() != null ? act.getWindow().getDecorView() : null;
            if (Build.VERSION.SDK_INT >= 31 && activityDecor != null) {
                try {
                    activityDecor.setRenderEffect(RenderEffect.createBlurEffect(20f, 20f, Shader.TileMode.CLAMP));
                } catch (Throwable ignored) {}
            }

            Window win = dlg.getWindow();
            if (win != null) {
                win.setBackgroundDrawableResource(android.R.color.transparent);
                if (Build.VERSION.SDK_INT >= 31) {
                    win.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                }
                WindowManager.LayoutParams lp = win.getAttributes();
                lp.width = Math.round(act.getResources().getDisplayMetrics().widthPixels * 0.92f);
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                lp.dimAmount = night ? 0.42f : 0.28f;
                if (Build.VERSION.SDK_INT >= 31) {
                    try { lp.setBlurBehindRadius(36); } catch (Throwable ignored) {}
                }
                win.setAttributes(lp);
            }

            cb.setOnCheckedChangeListener((btn, checked) -> {
                okBtn.setEnabled(checked);
                okBg.setColor(checked ? oneUiBlue : (night ? 0x336B7280 : 0x269CA3AF));
                okBtn.setTextColor(checked ? 0xFFFFFFFF : uncheckedCol);
            });

            okBtn.setOnClickListener(v -> {
                SBrowserXPrefs.setLastSeenChangelogVersion(act, ACK_KEY_VERSION);
                dlg.dismiss();
            });

            dlg.setOnDismissListener(di -> {
                sShowing = false;
                if (Build.VERSION.SDK_INT >= 31 && activityDecor != null) {
                    try { activityDecor.setRenderEffect(null); } catch (Throwable ignored) {}
                }
            });

            dlg.show();
        } catch (Throwable t) {
            sShowing = false;
        }
    }

    private static View buildSectionCard(
            Activity act, float d, int bgColor, int strokeColor, int headerColor,
            int bodyColor, String header, String body, int strokePx
    ) {
        LinearLayout card = new LinearLayout(act);
        card.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bgColor);
        gd.setCornerRadius(dp(d, 20));
        gd.setStroke(strokePx, strokeColor);
        card.setBackground(gd);
        card.setPadding(dp(d, 16), dp(d, 14), dp(d, 16), dp(d, 14));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = dp(d, 10);
        card.setLayoutParams(lp);

        TextView tvH = new TextView(act);
        tvH.setText(header);
        tvH.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.2f);
        tvH.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
        tvH.setTextColor(headerColor);
        tvH.setPadding(0, 0, 0, dp(d, 6));
        card.addView(tvH);

        TextView tvB = new TextView(act);
        tvB.setText(body);
        tvB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.5f);
        tvB.setLineSpacing(dp(d, 3), 1.04f);
        tvB.setTextColor(bodyColor);
        card.addView(tvB);
        return card;
    }

    private static int dp(float d, int v) {
        return Math.round(v * d);
    }
}
