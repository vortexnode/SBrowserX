package dev.vortexnode.sbrowserx.extension;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class SBrowserXStrings {
    public static final String TITLE_HUB = "title_hub";
    public static final String CAT_EXTENSIONS = "cat_extensions";
    public static final String CAT_PRIVACY = "cat_privacy";
    public static final String CAT_FEATURES = "cat_features";
    public static final String CAT_APPEARANCE = "cat_appearance";
    public static final String CAT_HIGHLIGHT = "cat_highlight";
    public static final String EXT_INSTALL_FILE = "ext_install_file";
    public static final String EXT_INSTALL_FILE_SUMMARY = "ext_install_file_summary";
    public static final String EXT_PACK_TITLE = "ext_pack_title";
    public static final String EXT_PACK_SUMMARY = "ext_pack_summary";
    public static final String EXT_OPEN_MANAGER = "ext_open_manager";
    public static final String EXT_OPEN_CWS = "ext_open_cws";
    public static final String EXT_INSTALLING = "ext_installing";
    public static final String EXT_INSTALL_OK = "ext_install_ok";
    public static final String EXT_INSTALL_FAIL = "ext_install_fail";
    public static final String EXT_ENGINE_NOT_READY = "ext_engine_not_ready";
    public static final String BTN_INSTALL = "btn_install";
    public static final String WEBRTC_TITLE = "webrtc_title";
    public static final String WEBRTC_DEFAULT = "webrtc_default";
    public static final String WEBRTC_PUB_PRIV = "webrtc_pub_priv";
    public static final String WEBRTC_PUB_ONLY = "webrtc_pub_only";
    public static final String WEBRTC_DISABLE_UDP = "webrtc_disable_udp";
    public static final String FINGERPRINT_TITLE = "fingerprint_title";
    public static final String FINGERPRINT_SUMMARY = "fingerprint_summary";
    public static final String GTRANS_TITLE = "gtrans_title";
    public static final String GTRANS_SUMMARY = "gtrans_summary";
    public static final String EXT_DL_TITLE = "ext_dl_title";
    public static final String EXT_DL_SUMMARY = "ext_dl_summary";
    public static final String EXT_DL_CHOOSER = "ext_dl_chooser";
    public static final String AUTOCLOSE_1D_TITLE = "autoclose_1d_title";
    public static final String AUTOCLOSE_1D_SUMMARY = "autoclose_1d_summary";
    public static final String AMOLED_TITLE = "amoled_title";
    public static final String AMOLED_SUMMARY = "amoled_summary";
    public static final String BOTTOM_BLUR_TITLE = "bottom_blur_title";
    public static final String BOTTOM_BLUR_SUMMARY = "bottom_blur_summary";
    public static final String HIGHLIGHT_DAYS_TITLE = "highlight_days_title";
    public static final String HIGHLIGHT_DAYS_OFF = "highlight_days_off";
    public static final String HIGHLIGHT_DAYS_FMT = "highlight_days_fmt";
    public static final String MARK_ALL_VIEWED = "mark_all_viewed";
    public static final String MARK_ALL_VIEWED_DONE = "mark_all_viewed_done";
    public static final String RESTART_TITLE = "restart_title";
    public static final String RESTART_SUMMARY = "restart_summary";
    public static final String RESTART_REQUIRED = "restart_required";
    public static final String BTN_RESTART = "btn_restart";
    public static final String BTN_LATER = "btn_later";
    public static final String AUTOCLOSE_1D_OPTION = "autoclose_1d_option";
    public static final String HIDE_ADBLOCKERS_TITLE = "hide_adblockers_title";
    public static final String HIDE_ADBLOCKERS_SUMMARY = "hide_adblockers_summary";
    public static final String GTRANS_SETTINGS_TITLE = "gtrans_settings_title";
    public static final String GTRANS_SETTINGS_SUMMARY = "gtrans_settings_summary";
    public static final String GTRANS_TARGET_LANG = "gtrans_target_lang";
    public static final String GTRANS_AUTO_TITLE = "gtrans_auto_title";
    public static final String GTRANS_AUTO_SUMMARY = "gtrans_auto_summary";
    public static final String GTRANS_NEVER_LANGS = "gtrans_never_langs";
    public static final String GTRANS_ALWAYS_LANGS = "gtrans_always_langs";
    public static final String GTRANS_RESET = "gtrans_reset";
    public static final String GTRANS_RESET_DONE = "gtrans_reset_done";
    public static final String GTRANS_NONE = "gtrans_none";
    public static final String EXT_DL_DIALOG_MSG = "ext_dl_dialog_msg";
    public static final String BTN_ENABLE = "btn_enable";
    public static final String BTN_DISABLE = "btn_disable";
    public static final String EXT_OPEN_NATIVE = "ext_open_native";
    public static final String BOTTOM_BLUR_LEVEL_TITLE = "bottom_blur_level_title";
    public static final String URL_BAR_BLUR_TITLE = "url_bar_blur_title";
    public static final String URL_BAR_BLUR_SUMMARY = "url_bar_blur_summary";
    public static final String URL_BAR_BLUR_LEVEL_TITLE = "url_bar_blur_level_title";
    public static final String COMPACT_BAR_BLUR_TITLE = "compact_bar_blur_title";
    public static final String COMPACT_BAR_BLUR_SUMMARY = "compact_bar_blur_summary";
    public static final String COMPACT_BAR_BLUR_LEVEL_TITLE = "compact_bar_blur_level_title";
    public static final String FORCE_DARK_TITLE = "force_dark_title";
    public static final String FORCE_DARK_SUMMARY = "force_dark_summary";
    public static final String EXT_DL_IMAGES_TITLE = "ext_dl_images_title";
    public static final String EXT_DL_IMAGES_SUMMARY = "ext_dl_images_summary";
    public static final String DARK_BRIGHT_IMG_TITLE = "dark_bright_img_title";
    public static final String DARK_BRIGHT_IMG_SUMMARY = "dark_bright_img_summary";
    public static final String LAYOUT_TRANSPARENCY_TITLE = "layout_transparency_title";
    public static final String COMPACT_AI_BTN_TITLE = "compact_ai_btn_title";

    private static final Map<String, Map<String, String>> CATALOG = new HashMap<>();

    static {
        put("en",
            "SBrowserX", "Extensions", "Privacy & Security", "Features & Downloads", "Appearance & UI", "New Features Highlight",
            "Install extension from file (.crx / .zip)", "Pick any local .crx or .zip extension to install natively",
            "Install recommended extensions (6)", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard, Bitwarden",
            "Extensions manager (internet://extensions)", "Open Chrome Web Store",
            "Installing extension…", "Extension installed!", "Installation error: ", "Open any webpage first so the extensions engine initializes", "Install",
            "WebRTC IP handling policy", "Default", "Default public and private interfaces", "Default public interface only", "Disable non-proxied UDP",
            "Fingerprint protection", "Native WebAudio & Screen anti-fingerprinting (restart to apply)",
            "Built-in Google Translate", "Use Google Chrome's translation engine inside the native translate bar",
            "External downloader", "Send file downloads to an external app (1DM, ADM, FDM, Seal…)", "Download with…",
            "Auto-close unused tabs after 1 day", "Shortens the 7-day auto-close threshold to 1 day (24 hours)",
            "AMOLED Black Theme", "Use pure #000000 black surfaces in Dark mode (works with bottom bar blur)",
            "Semi-transparent bottom bar", "Enable blur and translucency on the bottom navigation bar",
            "Highlight new settings", "Off", "%d d",
            "Mark all as viewed", "All new badges cleared",
            "Restart browser", "Apply privacy and appearance changes immediately",
            "Restart SBrowserX to apply this change?", "Restart", "Later");

        put("ru",
            "SBrowserX", "Расширения", "Конфиденциальность и безопасность", "Функции и загрузки", "Внешний вид и интерфейс", "Подсветка новых настроек",
            "Установить расширение из файла (.crx / .zip)", "Нативная установка локального .crx или .zip архива",
            "Набор рекомендуемых расширений (6)", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard, Bitwarden",
            "Менеджер расширений (internet://extensions)", "Открыть Chrome Web Store",
            "Установка расширения…", "Расширение установлено!", "Ошибка установки: ", "Сначала откройте любую страницу, чтобы запустился движок расширений", "Установить",
            "Политика WebRTC IP", "По умолчанию", "Публичные и локальные интерфейсы", "Только публичный интерфейс", "Отключить non-proxied UDP (рекомендуется)",
            "Защита от фингерпринтинга", "Нативная защита WebAudio и экрана от отслеживания (нужен перезапуск)",
            "Встроенный Google Переводчик", "Использовать движок Google Translate в родной панели перевода",
            "Сторонний загрузчик файлов", "Передавать скачивание в 1DM, ADM, FDM, Seal и другие загрузчики", "Скачать через…",
            "Автозакрытие вкладок через 1 день", "Сокращает порог «Через 7 дней» до 1 дня (24 часа)",
            "AMOLED-чёрная тема", "Чистый чёрный #000000 в тёмной теме (совместимо с полупрозрачностью)",
            "Полупрозрачная нижняя панель", "Включить размытие и полупрозрачность нижней навигации",
            "Срок подсветки новых пунктов", "Выкл", "%d дн.",
            "Отметить всё просмотренным", "Все метки новизны сброшены",
            "Перезапустить браузер", "Применить изменения приватности и интерфейса сейчас",
            "Перезапустить SBrowserX, чтобы применить изменения?", "Перезапустить", "Позже");

        put("uk",
            "SBrowserX", "Розширення", "Конфіденційність і безпека", "Функції та завантаження", "Зовнішній вигляд та інтерфейс", "Підсвічування нових функцій",
            "Встановити розширення з файлу (.crx / .zip)", "Нативне встановлення локального .crx або .zip архіву",
            "Набір рекомендованих розширень (6)", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard, Bitwarden",
            "Менеджер розширень (internet://extensions)", "Відкрити Chrome Web Store",
            "Встановлення розширення…", "Розширення встановлено!", "Помилка встановлення: ", "Спочатку відкрийте будь-яку сторінку для запуску рушія розширень", "Встановити",
            "Політика WebRTC IP", "За замовчуванням", "Публічні та локальні інтерфейси", "Лише публічний інтерфейс", "Вимкнути non-proxied UDP (рекомендовано)",
            "Захист від фінгерпринтингу", "Нативний захист WebAudio та екрана (потрібен перезапуск)",
            "Вбудований Google Перекладач", "Використовувати рушій Google Translate у рідній панелі перекладу",
            "Зовнішній завантажувач", "Передавати завантаження файлів у 1DM, ADM, FDM, Seal тощо", "Завантажити через…",
            "Автозакриття вкладок через 1 день", "Скорочує поріг «Через 7 днів» до 1 дня (24 години)",
            "AMOLED-чорна тема", "Чистий чорний #000000 у темній темі (сумісно з напівпрозорістю)",
            "Напівпрозора нижня панель", "Увімкнути розмиття та напівпрозорість нижньої панелі",
            "Тривалість підсвічування", "Вимк", "%d дн.",
            "Позначити все переглянутим", "Усі позначки новизни очищено",
            "Перезапустити браузер", "Застосувати зміни конфіденційності та інтерфейсу зараз",
            "Перезапустити SBrowserX для застосування змін?", "Перезапустити", "Пізніше");

        for (String lang : new String[] {"es", "pt", "de", "fr", "it", "pl", "tr", "ar", "hi", "zh", "ja", "ko"}) {
            CATALOG.put(lang, new HashMap<>(CATALOG.get("en")));
        }
        override("es", "Extensiones", "Privacidad y seguridad", "Funciones y descargas", "Apariencia e interfaz", "Traductor de Google integrado", "Descargador externo", "Cerrar pestañas tras 1 día");
        override("pt", "Extensões", "Privacidade e segurança", "Recursos e downloads", "Aparência e interface", "Google Tradutor integrado", "Gerenciador de downloads externo", "Fechar abas após 1 dia");
        override("de", "Erweiterungen", "Datenschutz & Sicherheit", "Funktionen & Downloads", "Design & Oberfläche", "Integrierter Google Übersetzer", "Externer Downloader", "Tabs nach 1 Tag schließen");
        override("fr", "Extensions", "Confidentialité et sécurité", "Fonctions et téléchargements", "Apparence et interface", "Google Traduction intégré", "Téléchargeur externe", "Fermer les onglets après 1 jour");
        override("it", "Estensioni", "Privacy e sicurezza", "Funzioni e download", "Aspetto e interfaccia", "Google Traduttore integrato", "Downloader esterno", "Chiudi schede dopo 1 giorno");
        override("pl", "Rozszerzenia", "Prywatność i bezpieczeństwo", "Funkcje i pobieranie", "Wygląd i interfejs", "Wbudowany Tłumacz Google", "Zewnętrzny menedżer pobierania", "Zamykaj karty po 1 dniu");
        override("tr", "Uzantılar", "Gizlilik ve Güvenlik", "Özellikler ve İndirmeler", "Görünüm ve Arayüz", "Dahili Google Çeviri", "Harici indirici", "Sekmeleri 1 gün sonra kapat");
        override("ar", "الإضافات", "الخصوصية والأمان", "الميزات والتنزيلات", "المظهر والواجهة", "ترجمة Google المدمجة", "تطبيق تنزيل خارجي", "إغلاق التبويبات بعد يوم واحد");
        override("hi", "एक्सटेंशन", "गोपनीयता और सुरक्षा", "सुविधाएँ और डाउनलोड", "दिखावट और इंटरफ़ेस", "बिल्ट-इन Google अनुवाद", "बाहरी डाउनलोडर", "1 दिन बाद टैब बंद करें");
        override("zh", "扩展程序", "隐私与安全", "功能与下载", "外观与界面", "内置 Google 翻译", "外部下载器", "1 天后自动关闭标签页");
        override("ja", "拡張機能", "プライバシーとセキュリティ", "機能とダウンロード", "外観とUI", "内蔵 Google 翻訳", "外部ダウンローダー", "1日後に未使用タブを閉じる");
        override("ko", "확장 프로그램", "개인정보 보호 및 보안", "기능 및 다운로드", "화면 및 인터페이스", "내장 Google 번역", "외부 다운로더", "1일 후 미사용 탭 자동 닫기");
    }

    private SBrowserXStrings() {}

    private static void override(String lang, String catExt, String catPriv, String catFeat, String catApp, String gtrans, String extDl, String autoClose) {
        Map<String, String> m = CATALOG.get(lang);
        if (m == null) return;
        m.put(CAT_EXTENSIONS, catExt);
        m.put(CAT_PRIVACY, catPriv);
        m.put(CAT_FEATURES, catFeat);
        m.put(CAT_APPEARANCE, catApp);
        m.put(GTRANS_TITLE, gtrans);
        m.put(EXT_DL_TITLE, extDl);
        m.put(AUTOCLOSE_1D_TITLE, autoClose);

        putExtra("en",
            "After 1 day",
            "Hide Samsung Ad Blockers", "Safely hide Samsung's built-in Ad Blockers from the menu (reversible, restart to apply)",
            "Google Translate settings", "Target language, auto-translate & language rules (like Chrome)",
            "Translate into language", "Auto-translate pages", "Automatically translate pages not in your Never-translate list",
            "Never translate these languages", "Always translate these languages",
            "Reset translate settings", "Translate settings reset", "None",
            "Send file downloads to an external download manager (ADM, 1DM, etc.)?", "Enable", "Disable");
        putExtra("ru",
            "Через 1 день",
            "Скрыть блокировщики рекламы Samsung", "Безопасно скрывает штатный пункт «Блокировщики рекламы» из меню (обратимо, нужен перезапуск)",
            "Настройки Google Переводчика", "Язык перевода, автоперевод и списки языков (как в Chrome)",
            "Переводить на язык", "Автоперевод страниц", "Автоматически переводить страницы на других языках",
            "Никогда не переводить языки", "Всегда переводить языки",
            "Сбросить настройки переводчика", "Настройки переводчика сброшены", "Не выбрано",
            "Передавать загрузку файлов в сторонний менеджер загрузок (ADM, 1DM и др.)?", "Включить", "Выключить");
        putExtra("uk",
            "Через 1 день",
            "Сховати блокувальники реклами Samsung", "Безпечно ховає штатний пункт «Блокувальники реклами» з меню (зворотно, потрібен перезапуск)",
            "Налаштування Google Перекладача", "Мова перекладу, автопереклад та списки мов (як у Chrome)",
            "Перекладати мовою", "Автопереклад сторінок", "Автоматично перекладати сторінки іншими мовами",
            "Ніколи не перекладати мови", "Завжди перекладати мови",
            "Скинути налаштування перекладача", "Налаштування перекладача скинуто", "Не вибрано",
            "Передавати завантаження файлів до стороннього менеджера (ADM, 1DM тощо)?", "Увімкнути", "Вимкнути");
        putExtra("es",
            "Después de 1 día",
            "Ocultar bloqueadores de Samsung", "Oculta el menú nativo de bloqueadores de Samsung (reversible, reiniciar)",
            "Ajustes de Google Translate", "Idioma de destino, traducción automática y reglas (como Chrome)",
            "Traducir al idioma", "Traducir páginas automáticamente", "Traducir automáticamente páginas en otros idiomas",
            "Nunca traducir estos idiomas", "Traducir siempre estos idiomas",
            "Restablecer ajustes", "Ajustes restablecidos", "Ninguno",
            "¿Enviar descargas a un gestor externo (ADM, 1DM, etc.)?", "Activar", "Desactivar");
        putExtra("pt",
            "Após 1 dia",
            "Ocultar bloqueadores da Samsung", "Oculta o menu nativo de bloqueadores da Samsung (reversível, reiniciar)",
            "Configurações do Google Tradutor", "Idioma de destino, tradução automática e regras (como no Chrome)",
            "Traduzir para o idioma", "Traduzir páginas automaticamente", "Traduzir automaticamente páginas em outros idiomas",
            "Nunca traduzir estes idiomas", "Sempre traduzir estes idiomas",
            "Redefinir configurações", "Configurações redefinidas", "Nenhum",
            "Enviar downloads para um gerenciador externo (ADM, 1DM, etc.)?", "Ativar", "Desativar");
        putExtra("de",
            "Nach 1 Tag",
            "Samsung-Werbeblocker ausblenden", "Blendet Samsungs Werbeblocker-Menü sicher aus (umkehrbar, Neustart)",
            "Google Übersetzer-Einstellungen", "Zielsprache, automatische Übersetzung & Sprachregeln (wie Chrome)",
            "In Sprache übersetzen", "Seiten automatisch übersetzen", "Seiten in anderen Sprachen automatisch übersetzen",
            "Diese Sprachen nie übersetzen", "Diese Sprachen immer übersetzen",
            "Einstellungen zurücksetzen", "Zurückgesetzt", "Keine",
            "Downloads an einen externen Manager (ADM, 1DM usw.) übergeben?", "Aktivieren", "Deaktivieren");
        putExtra("fr",
            "Après 1 jour",
            "Masquer les bloqueurs Samsung", "Masque les bloqueurs intégrés Samsung du menu (réversible, redémarrer)",
            "Paramètres Google Traduction", "Langue cible, traduction auto et règles (comme Chrome)",
            "Traduire vers la langue", "Traduire automatiquement", "Traduire automatiquement les pages dans d'autres langues",
            "Ne jamais traduire ces langues", "Toujours traduire ces langues",
            "Réinitialiser les paramètres", "Paramètres réinitialisés", "Aucun",
            "Envoyer les téléchargements vers un gestionnaire externe (ADM, 1DM) ?", "Activer", "Désactiver");
        putExtra("it",
            "Dopo 1 giorno",
            "Nascondi Ad Blocker Samsung", "Nasconde i blocchi pubblicità Samsung dal menu (reversibile, riavvia)",
            "Impostazioni Google Traduttore", "Lingua di destinazione, traduzione automatica e regole (come Chrome)",
            "Traduci in lingua", "Traduci pagine automaticamente", "Traduci automaticamente pagine in altre lingue",
            "Non tradurre mai queste lingue", "Traduci sempre queste lingue",
            "Ripristina impostazioni", "Impostazioni ripristinate", "Nessuna",
            "Inviare i download a un gestore esterno (ADM, 1DM, ecc.)?", "Attiva", "Disattiva");
        putExtra("pl",
            "Po 1 dniu",
            "Ukryj blokery reklam Samsung", "Bezpiecznie ukrywa wbudowane blokery reklam z menu (odwracalne, restart)",
            "Ustawienia Tłumacza Google", "Język docelowy, autotłumaczenie i reguły (jak w Chrome)",
            "Tłumacz na język", "Automatyczne tłumaczenie", "Automatycznie tłumacz strony w innych językach",
            "Nigdy nie tłumacz tych języków", "Zawsze tłumacz te języki",
            "Resetuj ustawienia", "Zresetowano ustawienia", "Brak",
            "Przekazywać pobieranie do zewnętrznego menedżera (ADM, 1DM)?", "Włącz", "Wyłącz");
        putExtra("tr",
            "1 gün sonra",
            "Samsung Reklam Engelleyicileri Gizle", "Samsung reklam engelleyici menüsünü güvenle gizler (geri alınabilir)",
            "Google Çeviri Ayarları", "Hedef dil, otomatik çeviri ve dil kuralları (Chrome gibi)",
            "Hedef dil", "Sayfaları otomatik çevir", "Diğer dillerdeki sayfaları otomatik çevir",
            "Bu dilleri asla çevirme", "Bu dilleri her zaman çevir",
            "Ayarları sıfırla", "Ayarlar sıfırlandı", "Yok",
            "İndirmeler harici indiriciye (ADM, 1DM vb.) gönderilsin mi?", "Etkinleştir", "Devre dışı");
        putExtra("ar",
            "بعد يوم واحد",
            "إخفاء مانعات إعلانات سامسونج", "إخفاء قائمة مانعات الإعلانات من القائمة بأمان (قابل للعكس)",
            "إعدادات ترجمة Google", "لغة الترجمة والترجمة التلقائية وقواعد اللغات (مثل Chrome)",
            "الترجمة إلى اللغة", "الترجمة التلقائية للصفحات", "ترجمة الصفحات باللغات الأخرى تلقائياً",
            "عدم ترجمة هذه اللغات أبداً", "ترجمة هذه اللغات دائماً",
            "إعادة تعيين الإعدادات", "تمت إعادة التعيين", "لا شيء",
            "إرسال التنزيلات إلى مدير تنزيل خارجي (ADM, 1DM)؟", "تفعيل", "تعطيل");
        putExtra("hi",
            "1 दिन बाद",
            "Samsung विज्ञापन अवरोधक छिपाएँ", "मेनू से Samsung विज्ञापन अवरोधक सुरक्षित रूप से छिपाएँ (उलटने योग्य)",
            "Google अनुवाद सेटिंग्स", "लक्ष्य भाषा, ऑटो-अनुवाद और भाषा नियम (Chrome जैसा)",
            "इस भाषा में अनुवाद करें", "पेज ऑटो-अनुवाद करें", "अन्य भाषाओं के पेज अपने आप अनुवाद करें",
            "इन भाषाओं का कभी अनुवाद न करें", "इन भाषाओं का हमेशा अनुवाद करें",
            "सेटिंग्स रीसेट करें", "सेटिंग्स रीसेट हुईं", "कोई नहीं",
            "क्या डाउनलोड को बाहरी मैनेजर (ADM, 1DM) पर भेजें?", "सक्षम करें", "अक्षम करें");
        putExtra("zh",
            "1 天后",
            "隐藏三星广告拦截器", "从菜单中安全隐藏三星自带广告拦截器（可恢复，需重启）",
            "Google 翻译设置", "目标语言、自动翻译与语言规则（类似 Chrome）",
            "翻译为语言", "自动翻译网页", "自动翻译非母语网页",
            "永不翻译这些语言", "始终翻译这些语言",
            "重置翻译设置", "已重置翻译设置", "无",
            "将文件下载交由外部下载器（ADM、1DM 等）处理？", "启用", "禁用");
        putExtra("ja",
            "1日後",
            "Samsung広告ブロッカーを非表示", "メニューからSamsung標準の広告ブロッカーを安全に非表示（再起動必要）",
            "Google翻訳の設定", "翻訳先言語、自動翻訳、言語ルール（Chrome風）",
            "翻訳先の言語", "ページを自動翻訳", "他の言語のページを自動的に翻訳する",
            "翻訳しない言語", "常に翻訳する言語",
            "設定をリセット", "設定をリセットしました", "なし",
            "外部ダウンローダー（ADM、1DMなど）にダウンロードを渡しますか？", "有効", "無効");
        putExtra("ko",
            "1일 후",
            "삼성 광고 차단기 숨기기", "메뉴에서 삼성 기본 광고 차단기를 안전하게 숨깁니다 (재시작 필요)",
            "Google 번역 설정", "대상 언어, 자동 번역 및 언어 규칙 (Chrome 스타일)",
            "번역 대상 언어", "페이지 자동 번역", "다른 언어 페이지를 자동으로 번역",
            "번역하지 않을 언어", "항상 번역할 언어",
            "번역 설정 초기화", "번역 설정이 초기화되었습니다", "없음",
            "파일 다운로드를 외부 다운로더(ADM, 1DM 등)로 전달할까요?", "사용", "사용 안 함");
    }


    private static void putExtra(String lang, String... v) {
        Map<String, String> m = CATALOG.get(lang);
        if (m == null) {
            m = new HashMap<>();
            CATALOG.put(lang, m);
        }
        String[] keys = new String[] {
            AUTOCLOSE_1D_OPTION,
            HIDE_ADBLOCKERS_TITLE, HIDE_ADBLOCKERS_SUMMARY,
            GTRANS_SETTINGS_TITLE, GTRANS_SETTINGS_SUMMARY,
            GTRANS_TARGET_LANG, GTRANS_AUTO_TITLE, GTRANS_AUTO_SUMMARY,
            GTRANS_NEVER_LANGS, GTRANS_ALWAYS_LANGS,
            GTRANS_RESET, GTRANS_RESET_DONE, GTRANS_NONE,
            EXT_DL_DIALOG_MSG, BTN_ENABLE, BTN_DISABLE
        };
        for (int i = 0; i < keys.length && i < v.length; i++) {
            m.put(keys[i], v[i]);
        }
    }

    private static void put(String lang, String... values) {
        String[] keys = new String[] {
            TITLE_HUB, CAT_EXTENSIONS, CAT_PRIVACY, CAT_FEATURES, CAT_APPEARANCE, CAT_HIGHLIGHT,
            EXT_INSTALL_FILE, EXT_INSTALL_FILE_SUMMARY, EXT_PACK_TITLE, EXT_PACK_SUMMARY,
            EXT_OPEN_MANAGER, EXT_OPEN_CWS, EXT_INSTALLING, EXT_INSTALL_OK, EXT_INSTALL_FAIL,
            EXT_ENGINE_NOT_READY, BTN_INSTALL,
            WEBRTC_TITLE, WEBRTC_DEFAULT, WEBRTC_PUB_PRIV, WEBRTC_PUB_ONLY, WEBRTC_DISABLE_UDP,
            FINGERPRINT_TITLE, FINGERPRINT_SUMMARY,
            GTRANS_TITLE, GTRANS_SUMMARY,
            EXT_DL_TITLE, EXT_DL_SUMMARY, EXT_DL_CHOOSER,
            AUTOCLOSE_1D_TITLE, AUTOCLOSE_1D_SUMMARY,
            AMOLED_TITLE, AMOLED_SUMMARY, BOTTOM_BLUR_TITLE, BOTTOM_BLUR_SUMMARY,
            HIGHLIGHT_DAYS_TITLE, HIGHLIGHT_DAYS_OFF, HIGHLIGHT_DAYS_FMT,
            MARK_ALL_VIEWED, MARK_ALL_VIEWED_DONE,
            RESTART_TITLE, RESTART_SUMMARY, RESTART_REQUIRED, BTN_RESTART, BTN_LATER
        };
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keys.length && i < values.length; i++) {
            map.put(keys[i], values[i]);
        }
        CATALOG.put(lang, map);
    }

    static {
        putOne("en", BOTTOM_BLUR_LEVEL_TITLE, "Bar transparency");
        putOne("ru", BOTTOM_BLUR_LEVEL_TITLE, "Прозрачность панели");
        putOne("uk", BOTTOM_BLUR_LEVEL_TITLE, "Прозорість панелі");
        putOne("es", BOTTOM_BLUR_LEVEL_TITLE, "Transparencia de la barra");
        putOne("pt", BOTTOM_BLUR_LEVEL_TITLE, "Transparência da barra");
        putOne("de", BOTTOM_BLUR_LEVEL_TITLE, "Leistentransparenz");
        putOne("fr", BOTTOM_BLUR_LEVEL_TITLE, "Transparence de la barre");
        putOne("it", BOTTOM_BLUR_LEVEL_TITLE, "Trasparenza della barra");
        putOne("pl", BOTTOM_BLUR_LEVEL_TITLE, "Przezroczystość paska");
        putOne("tr", BOTTOM_BLUR_LEVEL_TITLE, "Çubuk şeffaflığı");
        putOne("ar", BOTTOM_BLUR_LEVEL_TITLE, "شفافية الشريط");
        putOne("hi", BOTTOM_BLUR_LEVEL_TITLE, "बार पारदर्शिता");
        putOne("zh", BOTTOM_BLUR_LEVEL_TITLE, "工具栏透明度");
        putOne("ja", BOTTOM_BLUR_LEVEL_TITLE, "バーの透明度");
        putOne("ko", BOTTOM_BLUR_LEVEL_TITLE, "하단 바 투명도");
        putOne("en", URL_BAR_BLUR_TITLE, "Semi-transparent address bar");
        putOne("en", URL_BAR_BLUR_SUMMARY, "Independent translucency for the address bar capsule");
        putOne("en", URL_BAR_BLUR_LEVEL_TITLE, "Address bar transparency");
        putOne("ru", URL_BAR_BLUR_TITLE, "Полупрозрачная адресная строка");
        putOne("ru", URL_BAR_BLUR_SUMMARY, "Отдельная прозрачность капсулы адресной строки");
        putOne("ru", URL_BAR_BLUR_LEVEL_TITLE, "Прозрачность адресной строки");
        putOne("uk", URL_BAR_BLUR_TITLE, "Напівпрозорий адресний рядок");
        putOne("uk", URL_BAR_BLUR_SUMMARY, "Окрема прозорість капсули адресного рядка");
        putOne("uk", URL_BAR_BLUR_LEVEL_TITLE, "Прозорість адресного рядка");
        for (String lang : new String[] {"es", "pt", "de", "fr", "it", "pl", "tr", "ar", "hi", "zh", "ja", "ko"}) {
            putOne(lang, URL_BAR_BLUR_TITLE, "Semi-transparent address bar");
            putOne(lang, URL_BAR_BLUR_SUMMARY, "Independent translucency for the address bar capsule");
            putOne(lang, URL_BAR_BLUR_LEVEL_TITLE, "Address bar transparency");
        }
        putOne("en", COMPACT_BAR_BLUR_TITLE, "Semi-transparent compact bar");
        putOne("en", COMPACT_BAR_BLUR_SUMMARY, "Independent translucency & blur for the Compact floating pill");
        putOne("en", COMPACT_BAR_BLUR_LEVEL_TITLE, "Compact bar transparency");
        putOne("ru", COMPACT_BAR_BLUR_TITLE, "Полупрозрачная компактная панель");
        putOne("ru", COMPACT_BAR_BLUR_SUMMARY, "Отдельная прозрачность и размытие для Компактного макета");
        putOne("ru", COMPACT_BAR_BLUR_LEVEL_TITLE, "Прозрачность компактной панели");
        putOne("uk", COMPACT_BAR_BLUR_TITLE, "Напівпрозора компактна панель");
        putOne("uk", COMPACT_BAR_BLUR_SUMMARY, "Окрема прозорість та розмиття для Компактного макета");
        putOne("uk", COMPACT_BAR_BLUR_LEVEL_TITLE, "Прозорість компактної панелі");
        for (String lang : new String[] {"es", "pt", "de", "fr", "it", "pl", "tr", "ar", "hi", "zh", "ja", "ko"}) {
            putOne(lang, COMPACT_BAR_BLUR_TITLE, "Semi-transparent compact bar");
            putOne(lang, COMPACT_BAR_BLUR_SUMMARY, "Independent translucency & blur for the Compact floating pill");
            putOne(lang, COMPACT_BAR_BLUR_LEVEL_TITLE, "Compact bar transparency");
        }
        putOne("en", FORCE_DARK_TITLE, "Force adaptive dark mode for sites");
        putOne("en", FORCE_DARK_SUMMARY, "Forces dark mode on websites even in Light UI (restart required)");
        putOne("ru", FORCE_DARK_TITLE, "Адаптивная тёмная тема для сайтов");
        putOne("ru", FORCE_DARK_SUMMARY, "Принудительно затемняет сайты даже при светлом интерфейсе браузера (требуется перезапуск)");
        putOne("uk", FORCE_DARK_TITLE, "Адаптивна темна тема для сайтів");
        putOne("uk", FORCE_DARK_SUMMARY, "Примусово затемнює сайти (враховує рідну темну тему сайту, працює навіть у світлому режимі)");
        for (String lang : new String[] {"es", "pt", "de", "fr", "it", "pl", "tr", "ar", "hi", "zh", "ja", "ko"}) {
            putOne(lang, FORCE_DARK_TITLE, "Force adaptive dark mode for sites");
            putOne(lang, FORCE_DARK_SUMMARY, "Darken all websites adaptively (respects native site dark themes, works even in Light UI)");
        }
        putOne("en", EXT_DL_IMAGES_TITLE, "Also forward images to downloader");
        putOne("en", EXT_DL_IMAGES_SUMMARY, "ON: images, videos & files go to external downloader. OFF: browser saves images natively");
        putOne("en", DARK_BRIGHT_IMG_TITLE, "Vivid images in Dark Mode");
        putOne("en", DARK_BRIGHT_IMG_SUMMARY, "Disables NightDim & 50% dim veil, preserves true site colors & vivid photos (mutually exclusive with Samsung Webpage brightness in dark mode)");
        putOne("ru", EXT_DL_IMAGES_TITLE, "Скачивать и картинки через менеджер");
        putOne("ru", EXT_DL_IMAGES_SUMMARY, "ВКЛ: всё (включая картинки) идёт в сторонний менеджер. ВЫКЛ: картинки сохраняет сам браузер");
        putOne("ru", DARK_BRIGHT_IMG_TITLE, "Яркие картинки в тёмной теме");
        putOne("ru", DARK_BRIGHT_IMG_SUMMARY, "Отключает NightDim и 50%-фильтр, сохраняет цвета сайтов и яркость фото (взаимоисключаемо с «Яркость веб-страницы в режиме затемнения»)");
        putOne("uk", EXT_DL_IMAGES_TITLE, "Завантажувати й картинки через менеджер");
        putOne("uk", EXT_DL_IMAGES_SUMMARY, "УВІМК: усе (включно з картинками) йде у сторонній менеджер. ВИМК: картинки зберігає браузер");
        putOne("uk", DARK_BRIGHT_IMG_TITLE, "Яскраві картинки у темній темі");
        putOne("uk", DARK_BRIGHT_IMG_SUMMARY, "Вимикає NightDim і 50%-фільтр, зберігає кольори сайтів і яскравість фото (взаємовиключно з «Яскравість веб-сторінки в темному режимі»)");
        for (String lang : new String[] {"es", "pt", "de", "fr", "it", "pl", "tr", "ar", "hi", "zh", "ja", "ko"}) {
            putOne(lang, EXT_DL_IMAGES_TITLE, "Also forward images to downloader");
            putOne(lang, EXT_DL_IMAGES_SUMMARY, "ON: images, videos & files go to external downloader. OFF: browser saves images natively");
            putOne(lang, DARK_BRIGHT_IMG_TITLE, "Vivid images in Dark Mode");
            putOne(lang, DARK_BRIGHT_IMG_SUMMARY, "Disables NightDim & 50% dim veil, preserves true site colors & vivid photos (mutually exclusive with Samsung Webpage brightness in dark mode)");
        }
        putOne("en", LAYOUT_TRANSPARENCY_TITLE, "Panel & address bar translucency");
        putOne("en", COMPACT_AI_BTN_TITLE, "Compact bar button (replaces Samsung AI)");
        putOne("ru", LAYOUT_TRANSPARENCY_TITLE, "Прозрачность и размытие панелей");
        putOne("ru", COMPACT_AI_BTN_TITLE, "Кнопка компактной панели (вместо Samsung AI)");
        putOne("uk", LAYOUT_TRANSPARENCY_TITLE, "Прозорість і розмиття панелей");
        putOne("uk", COMPACT_AI_BTN_TITLE, "Кнопка компактної панелі (замість Samsung AI)");
        for (String lang : new String[] {"es", "pt", "de", "fr", "it", "pl", "tr", "ar", "hi", "zh", "ja", "ko"}) {
            putOne(lang, LAYOUT_TRANSPARENCY_TITLE, "Panel & address bar translucency");
            putOne(lang, COMPACT_AI_BTN_TITLE, "Compact bar button (replaces Samsung AI)");
        }
    }

    private static void putOne(String lang, String key, String value) {
        Map<String, String> m = CATALOG.get(lang);
        if (m != null) m.put(key, value);
    }

    public static String get(String key) {
        String lang = Locale.getDefault().getLanguage();
        Map<String, String> map = CATALOG.get(lang);
        if (map != null && map.containsKey(key)) {
            return map.get(key);
        }
        Map<String, String> en = CATALOG.get("en");
        return en != null && en.containsKey(key) ? en.get(key) : key;
    }

    public static String webrtcLabel(String policy) {
        if (SBrowserXPrefs.WEBRTC_DEFAULT.equals(policy)) return get(WEBRTC_DEFAULT);
        if (SBrowserXPrefs.WEBRTC_PUBLIC_AND_PRIVATE.equals(policy)) return get(WEBRTC_PUB_PRIV);
        if (SBrowserXPrefs.WEBRTC_PUBLIC_ONLY.equals(policy)) return get(WEBRTC_PUB_ONLY);
        return get(WEBRTC_DISABLE_UDP);
    }

    public static String getOpenNativeExtensionsLabel() {
        String lang = Locale.getDefault().getLanguage();
        if ("ru".equals(lang)) return "Нативный список расширений Samsung (без лагов)";
        if ("uk".equals(lang)) return "Нативний список розширень Samsung (без лагів)";
        if ("es".equals(lang)) return "Lista nativa de extensiones Samsung (rápida)";
        if ("pt".equals(lang)) return "Lista nativa de extensões Samsung (rápida)";
        if ("de".equals(lang)) return "Native Samsung-Erweiterungsliste (schnell)";
        if ("fr".equals(lang)) return "Liste native des extensions Samsung (rapide)";
        if ("it".equals(lang)) return "Elenco nativo estensioni Samsung (veloce)";
        if ("pl".equals(lang)) return "Natywna lista rozszerzeń Samsung (szybka)";
        if ("tr".equals(lang)) return "Yerel Samsung uzantı listesi (hızlı)";
        if ("ar".equals(lang)) return "قائمة إضافات سامسونج الأصلية (سريعة)";
        if ("hi".equals(lang)) return "नेटिव Samsung एक्सटेंशन सूची (तेज़)";
        if ("zh".equals(lang)) return "三星原生扩展列表（流畅无卡顿）";
        if ("ja".equals(lang)) return "Samsungネイティブ拡張機能一覧（軽量）";
        if ("ko".equals(lang)) return "삼성 기본 확장 프로그램 목록 (빠름)";
        return "Native Samsung Extensions list (fast, no lag)";
    }

    public static String compactButtonLabel(String code) {
        String lang = Locale.getDefault().getLanguage();
        boolean ru = "ru".equals(lang);
        boolean uk = "uk".equals(lang);
        if (SBrowserXPrefs.COMPACT_BTN_BOOKMARKS.equals(code)) return ru ? "Закладки" : (uk ? "Закладки" : "Bookmarks");
        if (SBrowserXPrefs.COMPACT_BTN_SHARE.equals(code)) return ru ? "Поделиться" : (uk ? "Поділитися" : "Share");
        if (SBrowserXPrefs.COMPACT_BTN_DOWNLOADS.equals(code)) return ru ? "Загрузки" : (uk ? "Завантаження" : "Downloads");
        if (SBrowserXPrefs.COMPACT_BTN_HISTORY.equals(code)) return ru ? "История" : (uk ? "Історія" : "History");
        if (SBrowserXPrefs.COMPACT_BTN_HOME.equals(code)) return ru ? "Домой" : (uk ? "Додому" : "Home");
        if (SBrowserXPrefs.COMPACT_BTN_HIDE.equals(code)) return ru ? "Скрыть кнопку" : (uk ? "Сховати кнопку" : "Hide button");
        return ru ? "Новая вкладка" : (uk ? "Нова вкладка" : "New tab");
    }
}
