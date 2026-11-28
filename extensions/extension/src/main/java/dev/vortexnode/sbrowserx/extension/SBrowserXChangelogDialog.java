package dev.vortexnode.sbrowserx.extension;

import android.app.Activity;
import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.lang.reflect.Method;

/**
 * Full-screen One UI Quick-Panel-style live blurred backdrop + sleek frosted-glass "What's New" sheet.
 * Shown once on first launch after updating SBrowserX.
 */
public final class SBrowserXChangelogDialog {
    public static final String CURRENT_VERSION = "1.3.7";
    private static final String ACK_KEY_VERSION = "1.3.7-b20";
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
            final int backdropTint = night ? 0x8C0D0F14 : 0x7AEEF0F5;
            final int sheetBg = night ? 0xE2171A23 : 0xEEFFFFFF;
            final int sheetBorder = night ? 0x26FFFFFF : 0x18000000;
            final int cardBg = night ? 0x5C262B38 : 0xCCF2F4F8;
            final int cardBorder = night ? 0x1AFFFFFF : 0x14000000;
            final int promoBg = night ? 0x3A2563EB : 0x180381FE;
            final int promoBorder = night ? 0x8C3E82F7 : 0x660381FE;
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
            String scrollHint = ru ? "↓ Пролистайте список до конца"
                    : (uk ? "↓ Прогорніть список до кінця"
                    : "↓ Scroll to the bottom to continue");

            String addedBody = ru
                    ? "• Новые движки перевода: Bing (по умолчанию, с фоновым прогревом), Яндекс, DeepL API и Gemini 3.5 Flash-Lite (AI).\n"
                    + "• Полная поддержка языка приложения (Per-App Language) и переводы на 20 мировых языков (включая персидский, индонезийский, вьетнамский, нидерландский и суахили).\n"
                    + "• Настройки сетки ярлыков стартовой страницы (4 или 5 колонок) и режим заполнения плитки иконкой в стиле Quetta.\n"
                    + "• Mate Translate добавлен в набор рекомендуемых расширений (теперь их 7).\n"
                    + "• Одноразовая памятка переводчика в «Ассистенте по просмотру»."
                    : (uk
                    ? "• Нові рушії перекладу: Bing (за замовчуванням, із фоновим прогрівом), Яндекс, DeepL API та Gemini 3.5 Flash-Lite (AI).\n"
                    + "• Підтримка мови додатка (Per-App Language) та локалізація на 20 світових мов.\n"
                    + "• Сітка ярликів стартової сторінки (4 або 5 колонок) і режим заповнення плитки іконкою у стилі Quetta.\n"
                    + "• Mate Translate додано до рекомендованих розширень (тепер їх 7).\n"
                    + "• Одноразова пам'ятка перекладача в «Асистенті перегляду»."
                    : "• New translation engines: Bing (default, with background warm-up), Yandex, DeepL API, and Gemini 3.5 Flash-Lite (AI).\n"
                    + "• Full Per-App Language support and natural translations across 20 major world languages.\n"
                    + "• Start Page shortcut grid (4 or 5 columns) and edge-to-edge Quetta-style icon tiles.\n"
                    + "• Added Mate Translate to the Recommended Extensions Pack (now 7 extensions).\n"
                    + "• One-time translator guide in Browsing Assist.");

            String improvedBody = ru
                    ? "• Перекомпонован экран «Настройки SBrowserX»: единая верхняя карточка с синими подписями One UI, заголовок «Настройки SBrowserX», нижняя карточка «О SBrowserX» и «Источник патчей для Morphe».\n"
                    + "• Бирюзовые точки (логика Morphe): отображаются в главном меню и хабе, синхронизированы и автоматически пропадают после просмотра видимых пунктов.\n"
                    + "• Стартовая страница: исправлен баг «Адрес null недоступен» (синхронный старт + закрытие сокета при перезапуске), починено живое обновление обоев и погоды без перезапуска.\n"
                    + "• Переводчик: обход Trusted Types CSP (теперь стабильно переводятся Google News, Reuters, Le Monde и SPA-сайты), перехват кнопки с нижней панели, белая иконка в тёмной теме везде, ровный баннер на 138dp и заголовок «Настройки переводчика»."
                    : (uk
                    ? "• Оновлено екран «Налаштування SBrowserX»: єдина верхня картка з синіми підписами One UI, заголовок «Налаштування SBrowserX», нижній блок «Про SBrowserX».\n"
                    + "• Бірюзові крапки (логіка Morphe): відображаються в головному меню і хабі та зникають автоматично після перегляду.\n"
                    + "• Стартова сторінка: виправлено «Адреса null недоступна» та живе оновлення шпалер і погоди без перезапуску.\n"
                    + "• Перекладач: обхід Trusted Types CSP (Google News, Reuters, Le Monde), перехоплення кнопки з нижньої панелі, біла іконка в темній темі всюди та рівний банер 138dp."
                    : "• Redesigned SBrowserX settings with a unified top card, One UI accent summaries, and SBrowserX Settings header.\n"
                    + "• Visibility-based teal dots (Morphe-style): synced across Main Settings and Hub, clearing automatically once seen.\n"
                    + "• Rock-solid Start Page: fixed null address error + instant live wallpaper & weather refresh without restart.\n"
                    + "• Translator: Trusted Types CSP bypass (Google News, Reuters, Le Monde), bottom-bar interception, white icon in Dark Mode everywhere, and refined 138dp floating banner.");

            String removedBody = ru
                    ? "• Полностью отключён навязчивый промо-баннер «Сделать домашней страницей Samsung» в настройках.\n"
                    + "• Скрыты пустые/лишние штатные пункты (блок конфиденциальности, «О Samsung Browser», Debug settings).\n"
                    + "• Заблокирована установка несовместимых Manifest V2 (.crx/.zip) до вылета с понятным диалогом One UI и рекомендацией uBlock Origin Lite (MV3).\n"
                    + "• Удалён нерабочий движок Microsoft Edge (HTTP 404)."
                    : (uk
                    ? "• Повністю вимкнено промо-банер «Зробити домашньою сторінкою Samsung» у налаштуваннях.\n"
                    + "• Приховано зайві штатні пункти (конфіденційність, «Про Samsung Browser», Debug settings).\n"
                    + "• Заблоковано встановлення застарілих Manifest V2 (.crx/.zip) до збою з діалогом One UI.\n"
                    + "• Видалено неробочий рушій Microsoft Edge (HTTP 404)."
                    : "• Removed the Samsung homepage promotion banner from Settings.\n"
                    + "• Hid unused stock Settings entries (Privacy block, About Samsung Browser, Debug settings).\n"
                    + "• Blocked incompatible Manifest V2 (.crx/.zip) installs with a clear One UI dialog recommending uBlock Origin Lite (MV3).\n"
                    + "• Removed the deprecated Microsoft Edge translator endpoint (HTTP 404).");

            // Full-screen blurred backdrop (One UI Quick Settings shade look)
            FrameLayout backdrop = new FrameLayout(act);
            backdrop.setLayoutParams(new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            backdrop.setBackgroundColor(backdropTint);
            applyOneUiWindowBlur(backdrop, 220, backdropTint);

            // Sleek frosted-glass sheet positioned slightly below vertical center
            LinearLayout root = new LinearLayout(act);
            root.setOrientation(LinearLayout.VERTICAL);
            GradientDrawable rootBg = new GradientDrawable();
            rootBg.setColor(sheetBg);
            rootBg.setCornerRadius(dp(d, 28));
            rootBg.setStroke(Math.max(1, dp(d, 1)), sheetBorder);
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
            final ScrollView sv = new ScrollView(act);
            sv.setVerticalScrollBarEnabled(false);
            sv.setOverScrollMode(View.OVER_SCROLL_NEVER);
            final LinearLayout cards = new LinearLayout(act);
            cards.setOrientation(LinearLayout.VERTICAL);

            cards.addView(buildSectionCard(act, d, promoBg, promoBorder,
                    night ? 0xFF60A5FA : 0xFF0381FE, textPrimary, hPromo, promoBody, Math.max(2, dp(d, 1))));
            cards.addView(buildSectionCard(act, d, cardBg, cardBorder,
                    night ? 0xFF34D399 : 0xFF059669, textSecondary, hAdded, addedBody, 1));
            cards.addView(buildSectionCard(act, d, cardBg, cardBorder,
                    oneUiBlue, textSecondary, hImproved, improvedBody, 1));
            cards.addView(buildSectionCard(act, d, cardBg, cardBorder,
                    night ? 0xFFF87171 : 0xFFDC2626, textSecondary, hRemoved, removedBody, 1));

            sv.addView(cards);
            int maxScrollH = Math.round(act.getResources().getDisplayMetrics().heightPixels * 0.54f);
            root.addView(sv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, maxScrollH));

            // Scroll hint (shown only while content needs scrolling)
            final TextView tvHint = new TextView(act);
            tvHint.setText(scrollHint);
            tvHint.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12.5f);
            tvHint.setTextColor(oneUiBlue);
            tvHint.setGravity(Gravity.CENTER);
            tvHint.setPadding(0, dp(d, 6), 0, 0);
            tvHint.setVisibility(View.GONE);
            root.addView(tvHint);

            // One UI Blue Checkbox
            ColorStateList cbTint = new ColorStateList(
                    new int[][] { new int[] { android.R.attr.state_checked }, new int[] {} },
                    new int[] { oneUiBlue, uncheckedCol });
            final CheckBox cb = new CheckBox(act);
            cb.setText(cbText);
            cb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15.5f);
            cb.setTypeface(Typeface.create("sans-serif-medium", Typeface.NORMAL));
            cb.setTextColor(textPrimary);
            cb.setButtonTintList(cbTint);
            cb.setPadding(dp(d, 6), dp(d, 10), 0, dp(d, 10));
            root.addView(cb);

            // Pill-shaped One UI OK button
            final TextView okBtn = new TextView(act);
            okBtn.setText("OK");
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
            root.addView(okBtn, new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

            // Center horizontally and shift slightly below vertical center (One UI ergonomic position)
            int sheetW = Math.round(act.getResources().getDisplayMetrics().widthPixels * 0.92f);
            FrameLayout.LayoutParams sheetLp = new FrameLayout.LayoutParams(
                    sheetW, FrameLayout.LayoutParams.WRAP_CONTENT, Gravity.CENTER);
            sheetLp.topMargin = dp(d, 30);
            backdrop.addView(root, sheetLp);

            // Smart scroll-to-bottom lock: lock checkbox only if ScrollView actually overflows
            final boolean[] unlocked = new boolean[] { false };
            Runnable checkScrollBottom = () -> {
                if (unlocked[0]) return;
                int contentH = cards.getHeight();
                int viewH = sv.getHeight();
                if (viewH <= 0) return;
                if (contentH <= viewH + dp(d, 12) || (sv.getScrollY() + viewH) >= contentH - dp(d, 24)) {
                    unlocked[0] = true;
                    cb.setEnabled(true);
                    cb.setAlpha(1.0f);
                    tvHint.setVisibility(View.GONE);
                } else {
                    cb.setEnabled(false);
                    cb.setAlpha(0.42f);
                    tvHint.setVisibility(View.VISIBLE);
                }
            };
            sv.post(checkScrollBottom);
            sv.getViewTreeObserver().addOnScrollChangedListener(checkScrollBottom::run);

            final Dialog dlg = new Dialog(act, android.R.style.Theme_Translucent_NoTitleBar);
            dlg.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dlg.setContentView(backdrop);
            dlg.setCancelable(false);
            dlg.setCanceledOnTouchOutside(false);

            Window win = dlg.getWindow();
            if (win != null) {
                win.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                win.setStatusBarColor(0x00000000);
                win.setNavigationBarColor(0x00000000);
                win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (Build.VERSION.SDK_INT >= 31) {
                    win.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    try {
                        WindowManager.LayoutParams lp = win.getAttributes();
                        lp.setBlurBehindRadius(90);
                        win.setAttributes(lp);
                    } catch (Throwable ignored) {}
                }
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

            dlg.setOnDismissListener(di -> sShowing = false);
            dlg.show();
        } catch (Throwable t) {
            sShowing = false;
        }
    }

    private static void applyOneUiWindowBlur(View view, int radius, int tintColor) {
        if (view == null) return;
        try {
            Class<?> blurInfoCls = Class.forName("android.view.SemBlurInfo");
            Class<?> builderCls = Class.forName("android.view.SemBlurInfo$Builder");
            Object builder = builderCls.getConstructor(int.class).newInstance(0); // BLUR_MODE_WINDOW
            try { builderCls.getMethod("setRadius", int.class).invoke(builder, radius); } catch (Throwable ignored) {}
            try { builderCls.getMethod("setBackgroundColor", int.class).invoke(builder, tintColor); } catch (Throwable ignored) {}
            Object info = builderCls.getMethod("build").invoke(builder);
            Method setBlur = View.class.getMethod("semSetBlurInfo", blurInfoCls);
            setBlur.invoke(view, info);
        } catch (Throwable ignored) {}
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
