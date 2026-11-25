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
}
