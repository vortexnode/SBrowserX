package dev.vortexnode.sbrowserx.extension;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Samsung One UI-style "What's New" dialog displayed once on first launch after updating SBrowserX.
 */
public final class SBrowserXChangelogDialog {
    public static final String CURRENT_VERSION = "1.3.7";
    private static volatile boolean sShowing = false;

    private SBrowserXChangelogDialog() {}

    public static void showIfNeeded(Activity act) {
        if (act == null || act.isFinishing() || act.isDestroyed() || sShowing) return;
        String seen = SBrowserXPrefs.getLastSeenChangelogVersion(act);
        if (CURRENT_VERSION.equals(seen)) return;
        sShowing = true;
        try {
            boolean night = SBrowserXHooks.isBrowserNightMode(act);
            int theme = night ? android.R.style.Theme_DeviceDefault_Dialog_Alert
                    : android.R.style.Theme_DeviceDefault_Light_Dialog_Alert;
            float d = act.getResources().getDisplayMetrics().density;
            int oneUiBlue = night ? 0xFF3E82F7 : 0xFF0381FE;
            int uncheckedCol = night ? 0xFF6B7280 : 0xFF9CA3AF;
            int cardBg = night ? 0xFF1C1C21 : 0xFFF3F4F6;
            int textPrimary = night ? 0xFFF5F5F7 : 0xFF141416;
            int textSecondary = night ? 0xFFD1D5DB : 0xFF374151;

            String lang = SBrowserXStrings.getAppLang();
            boolean ru = "ru".equals(lang);
            boolean uk = "uk".equals(lang);

            String title = ru ? "Что нового в SBrowserX " + CURRENT_VERSION
                    : (uk ? "Що нового в SBrowserX " + CURRENT_VERSION
                    : "What's New in SBrowserX " + CURRENT_VERSION);
            String hAdded = ru ? "Что добавлено" : (uk ? "Що додано" : "What's new");
            String hImproved = ru ? "Что улучшено и исправлено" : (uk ? "Що покращено та виправлено" : "Improved & fixed");
            String hRemoved = ru ? "Что удалено и защищено" : (uk ? "Що видалено та захищено" : "Removed & protected");
            String cbText = ru ? "Спасибо, всё понятно" : (uk ? "Дякую, все зрозуміло" : "Thanks, got it");

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

            LinearLayout container = new LinearLayout(act);
            container.setOrientation(LinearLayout.VERTICAL);
            container.setPadding(dp(d, 20), dp(d, 10), dp(d, 20), dp(d, 6));

            ScrollView sv = new ScrollView(act);
            sv.setVerticalScrollBarEnabled(false);
            sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
            LinearLayout cards = new LinearLayout(act);
            cards.setOrientation(LinearLayout.VERTICAL);

            cards.addView(buildSectionCard(act, d, cardBg, night ? 0xFF34D399 : 0xFF059669, textSecondary, hAdded, addedBody));
            cards.addView(buildSectionCard(act, d, cardBg, oneUiBlue, textSecondary, hImproved, improvedBody));
            cards.addView(buildSectionCard(act, d, cardBg, night ? 0xFFF87171 : 0xFFDC2626, textSecondary, hRemoved, removedBody));

            sv.addView(cards);
            LinearLayout.LayoutParams svLp = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, 0, 1f);
            container.addView(sv, svLp);

            ColorStateList cbTint = new ColorStateList(
                    new int[][] { new int[] { android.R.attr.state_checked }, new int[] {} },
                    new int[] { oneUiBlue, uncheckedCol });
            CheckBox cb = new CheckBox(act);
            cb.setText(cbText);
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
            cb.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            cb.setTextColor(textPrimary);
            cb.setButtonTintList(cbTint);
            cb.setPadding(dp(d, 8), dp(d, 14), 0, dp(d, 6));
            container.addView(cb);

            AlertDialog dlg = new AlertDialog.Builder(act, theme)
                    .setTitle(title)
                    .setView(container)
                    .setCancelable(false)
                    .setPositiveButton(android.R.string.ok, (di, w) -> {
                        SBrowserXPrefs.setLastSeenChangelogVersion(act, CURRENT_VERSION);
                        sShowing = false;
                    })
                    .create();
            dlg.setCanceledOnTouchOutside(false);
            dlg.setOnDismissListener(di -> sShowing = false);
            dlg.setOnShowListener(di -> {
                Button okBtn = dlg.getButton(AlertDialog.BUTTON_POSITIVE);
                if (okBtn != null) {
                    okBtn.setEnabled(false);
                    okBtn.setAlpha(0.4f);
                    okBtn.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f);
                    cb.setOnCheckedChangeListener((btn, checked) -> {
                        okBtn.setEnabled(checked);
                        okBtn.setAlpha(checked ? 1.0f : 0.4f);
                        okBtn.setTextColor(checked ? oneUiBlue : uncheckedCol);
                    });
                }
                Window win = dlg.getWindow();
                if (win != null) {
                    WindowManager.LayoutParams lp = win.getAttributes();
                    lp.width = Math.round(act.getResources().getDisplayMetrics().widthPixels * 0.92f);
                    lp.dimAmount = night ? 0.58f : 0.38f;
                    if (android.os.Build.VERSION.SDK_INT >= 31) {
                        win.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                        lp.setBlurBehindRadius(48);
                    }
                    win.setAttributes(lp);
                }
            });
            dlg.show();
        } catch (Throwable t) {
            sShowing = false;
        }
    }

    private static View buildSectionCard(Activity act, float d, int bgColor, int headerColor, int bodyColor, String header, String body) {
        LinearLayout card = new LinearLayout(act);
        card.setOrientation(LinearLayout.VERTICAL);
        GradientDrawable gd = new GradientDrawable();
        gd.setColor(bgColor);
        gd.setCornerRadius(dp(d, 22));
        gd.setStroke(1, bgColor == 0xFF1C1C21 ? 0x1AFFFFFF : 0x14000000);
        card.setBackground(gd);
        card.setPadding(dp(d, 18), dp(d, 15), dp(d, 18), dp(d, 15));
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.bottomMargin = dp(d, 10);
        card.setLayoutParams(lp);

        TextView tvH = new TextView(act);
        tvH.setText(header);
        tvH.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.5f);
        tvH.setTypeface(Typeface.create("sans-serif-medium", Typeface.BOLD));
        tvH.setTextColor(headerColor);
        tvH.setPadding(0, 0, 0, dp(d, 6));
        card.addView(tvH);

        TextView tvB = new TextView(act);
        tvB.setText(body);
        tvB.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.8f);
        tvB.setLineSpacing(dp(d, 3), 1.04f);
        tvB.setTextColor(bodyColor);
        card.addView(tvB);
        return card;
    }

    private static int dp(float d, int v) {
        return Math.round(v * d);
    }
}
