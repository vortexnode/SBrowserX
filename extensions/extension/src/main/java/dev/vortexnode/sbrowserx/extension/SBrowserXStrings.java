package dev.vortexnode.sbrowserx.extension;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class SBrowserXStrings {
    public static final String TITLE_HUB = "title_hub";
    public static final String CAT_PRIVACY = "cat_privacy";
    public static final String CAT_APPEARANCE = "cat_appearance";
    public static final String CAT_HIGHLIGHT = "cat_highlight";
    public static final String WEBRTC_TITLE = "webrtc_title";
    public static final String WEBRTC_DEFAULT = "webrtc_default";
    public static final String WEBRTC_PUB_PRIV = "webrtc_pub_priv";
    public static final String WEBRTC_PUB_ONLY = "webrtc_pub_only";
    public static final String WEBRTC_DISABLE_UDP = "webrtc_disable_udp";
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
        // English (default fallback)
        put("en",
            "SBrowserX", "Privacy & Security", "Appearance & UI", "New Features Highlight",
            "WebRTC IP handling policy",
            "Default", "Default public and private interfaces", "Default public interface only", "Disable non-proxied UDP",
            "AMOLED Black Theme", "Use pure #000000 black surfaces in Dark mode",
            "Semi-transparent bottom bar", "Enable blur and translucency on the bottom navigation bar",
            "Highlight new settings", "Off", "%d d",
            "Mark all as viewed", "All new badges cleared",
            "Restart browser", "Apply WebRTC and appearance changes immediately",
            "Restart SBrowserX to apply this change?", "Restart", "Later");

        // Russian
        put("ru",
            "SBrowserX", "Конфиденциальность и безопасность", "Внешний вид и интерфейс", "Подсветка новых настроек",
            "Политика WebRTC IP",
            "По умолчанию", "Публичные и локальные интерфейсы", "Только публичный интерфейс", "Отключить non-proxied UDP (рекомендуется)",
            "AMOLED-чёрная тема", "Чистый чёрный #000000 для панелей в тёмной теме",
            "Полупрозрачная нижняя панель", "Включить размытие и полупрозрачность нижней навигации",
            "Срок подсветки новых пунктов", "Выкл", "%d дн.",
            "Отметить всё просмотренным", "Все метки новизны сброшены",
            "Перезапустить браузер", "Применить изменения WebRTC и интерфейса сейчас",
            "Перезапустить SBrowserX, чтобы применить изменения?", "Перезапустить", "Позже");

        // Ukrainian
        put("uk",
            "SBrowserX", "Конфіденційність і безпека", "Зовнішній вигляд та інтерфейс", "Підсвічування нових функцій",
            "Політика WebRTC IP",
            "За замовчуванням", "Публічні та локальні інтерфейси", "Лише публічний інтерфейс", "Вимкнути non-proxied UDP (рекомендовано)",
            "AMOLED-чорна тема", "Чистий чорний #000000 для панелей у темній темі",
            "Напівпрозора нижня панель", "Увімкнути розмиття та напівпрозорість нижньої панелі",
            "Тривалість підсвічування", "Вимк", "%d дн.",
            "Позначити все переглянутим", "Усі позначки новизни очищено",
            "Перезапустити браузер", "Застосувати зміни WebRTC та інтерфейсу зараз",
            "Перезапустити SBrowserX для застосування змін?", "Перезапустити", "Пізніше");

        // Spanish
        put("es",
            "SBrowserX", "Privacidad y seguridad", "Apariencia e interfaz", "Resaltar novedades",
            "Política de IP de WebRTC",
            "Predeterminado", "Interfaces públicas y privadas", "Solo interfaz pública", "Desactivar UDP sin proxy",
            "Tema negro AMOLED", "Usar negro puro #000000 en modo oscuro",
            "Barra inferior semitransparente", "Activar desenfoque en la barra de navegación inferior",
            "Duración del resaltado", "No", "%d d",
            "Marcar todo como visto", "Indicadores borrados",
            "Reiniciar navegador", "Aplicar cambios de WebRTC y apariencia ahora",
            "¿Reiniciar SBrowserX para aplicar los cambios?", "Reiniciar", "Más tarde");

        // Portuguese
        put("pt",
            "SBrowserX", "Privacidade e segurança", "Aparência e interface", "Destaque de novidades",
            "Política de IP WebRTC",
            "Padrão", "Interfaces públicas e privadas", "Apenas interface pública", "Desativar UDP sem proxy",
            "Tema preto AMOLED", "Usar preto puro #000000 no modo escuro",
            "Barra inferior semitransparente", "Ativar desfoque na barra de navegação inferior",
            "Duração do destaque", "Desl.", "%d d",
            "Marcar tudo como visto", "Marcadores removidos",
            "Reiniciar navegador", "Aplicar alterações de WebRTC e aparência agora",
            "Reiniciar o SBrowserX para aplicar as alterações?", "Reiniciar", "Depois");

        // German
        put("de",
            "SBrowserX", "Datenschutz & Sicherheit", "Design & Oberfläche", "Neue Funktionen hervorheben",
            "WebRTC-IP-Richtlinie",
            "Standard", "Öffentliche & private Schnittstellen", "Nur öffentliche Schnittstelle", "Nicht-Proxy-UDP deaktivieren",
            "AMOLED-Schwarz-Design", "Reines #000000 Schwarz im Dunkelmodus nutzen",
            "Halbtransparente untere Leiste", "Unschärfe für die untere Navigationsleiste aktivieren",
            "Hervorhebungsdauer", "Aus", "%d T.",
            "Alle als gesehen markieren", "Alle Markierungen entfernt",
            "Browser neu starten", "WebRTC- und Design-Änderungen sofort anwenden",
            "SBrowserX neu starten, um Änderungen anzuwenden?", "Neu starten", "Später");

        // French
        put("fr",
            "SBrowserX", "Confidentialité et sécurité", "Apparence et interface", "Mise en avant des nouveautés",
            "Politique IP WebRTC",
            "Par défaut", "Interfaces publiques et privées", "Interface publique uniquement", "Désactiver UDP sans proxy",
            "Thème noir AMOLED", "Utiliser le noir pur #000000 en mode sombre",
            "Barre inférieure semi-transparente", "Activer le flou sur la barre de navigation inférieure",
            "Durée de mise en avant", "Non", "%d j",
            "Tout marquer comme vu", "Badges effacés",
            "Redémarrer le navigateur", "Appliquer WebRTC et l'apparence maintenant",
            "Redémarrer SBrowserX pour appliquer les modifications ?", "Redémarrer", "Plus tard");

        // Italian
        put("it",
            "SBrowserX", "Privacy e sicurezza", "Aspetto e interfaccia", "Evidenzia novità",
            "Politica IP WebRTC",
            "Predefinito", "Interfacce pubbliche e private", "Solo interfaccia pubblica", "Disattiva UDP senza proxy",
            "Tema nero AMOLED", "Usa nero puro #000000 in modalità scura",
            "Barra inferiore semitrasparente", "Attiva sfocatura sulla barra di navigazione inferiore",
            "Durata evidenziazione", "Off", "%d g",
            "Segna tutto come visto", "Indicatori rimossi",
            "Riavvia browser", "Applica subito modifiche WebRTC e aspetto",
            "Riavviare SBrowserX per applicare le modifiche?", "Riavvia", "Dopo");

        // Polish
        put("pl",
            "SBrowserX", "Prywatność i bezpieczeństwo", "Wygląd i interfejs", "Podświetlanie nowości",
            "Polityka IP WebRTC",
            "Domyślna", "Interfejsy publiczne i prywatne", "Tylko interfejs publiczny", "Wyłącz UDP bez proxy",
            "Czarny motyw AMOLED", "Czysta czerń #000000 w trybie ciemnym",
            "Półprzezroczysty dolny pasek", "Włącz rozmycie dolnego paska nawigacji",
            "Czas podświetlania", "Wył.", "%d d.",
            "Oznacz wszystko jako przeczytane", "Wyczyszczono znaczniki",
            "Uruchom ponownie przeglądarkę", "Zastosuj zmiany WebRTC i wyglądu teraz",
            "Uruchomić ponownie SBrowserX, aby zastosować zmiany?", "Uruchom ponownie", "Później");

        // Turkish
        put("tr",
            "SBrowserX", "Gizlilik ve Güvenlik", "Görünüm ve Arayüz", "Yeni Özellik Vurgusu",
            "WebRTC IP politikası",
            "Varsayılan", "Genel ve özel arayüzler", "Yalnızca genel arayüz", "Proxy dışı UDP'yi devre dışı bırak",
            "AMOLED Siyah Tema", "Karanlık modda saf #000000 siyah kullan",
            "Yarı saydam alt çubuk", "Alt gezinme çubuğunda bulanıklığı etkinleştir",
            "Vurgu süresi", "Kapalı", "%d g",
            "Tümünü görüldü işaretle", "Tüm rozetler temizlendi",
            "Tarayıcıyı yeniden başlat", "WebRTC ve görünüm değişikliklerini uygula",
            "Değişiklikleri uygulamak için SBrowserX yeniden başlatılsın mı?", "Yeniden başlat", "Sonra");

        // Arabic
        put("ar",
            "SBrowserX", "الخصوصية والأمان", "المظهر والواجهة", "تمييز الميزات الجديدة",
            "سياسة عنوان IP لـ WebRTC",
            "افتراضي", "الواجهات العامة والخاصة", "الواجهة العامة فقط", "تعطيل UDP بدون بروكسي",
            "الوضع الأسود AMOLED", "استخدام اللون الأسود النقي #000000 في الوضع الداكن",
            "شريط سفلي شبه شفاف", "تفعيل التمويه والشفافية للشريط السفلي",
            "مدة تمييز الإعدادات الجديدة", "إيقاف", "%d ي",
            "تحديد الكل كمقروء", "تم مسح جميع العلامات",
            "إعادة تشغيل المتصفح", "تطبيق تغييرات WebRTC والمظهر الآن",
            "هل تريد إعادة تشغيل SBrowserX لتطبيق التغييرات؟", "إعادة التشغيل", "لاحقاً");

        // Hindi
        put("hi",
            "SBrowserX", "गोपनीयता और सुरक्षा", "दिखावट और इंटरफ़ेस", "नई सुविधाएँ हाइलाइट",
            "WebRTC IP नीति",
            "डिफ़ॉल्ट", "सार्वजनिक और निजी इंटरफ़ेस", "केवल सार्वजनिक इंटरफ़ेस", "गैर-प्रॉक्सी UDP अक्षम करें",
            "AMOLED ब्लैक थीम", "डार्क मोड में शुद्ध #000000 काला उपयोग करें",
            "अर्ध-पारदर्शी निचला बार", "निचले नेविगेशन बार पर ब्लर सक्षम करें",
            "हाइलाइट अवधि", "बंद", "%d दिन",
            "सभी को देखा गया चिह्नित करें", "सभी बैज हटाए गए",
            "ब्राउज़र पुनरारंभ करें", "WebRTC और थीम परिवर्तन अभी लागू करें",
            "परिवर्तन लागू करने के लिए SBrowserX पुनरारंभ करें?", "पुनरारंभ करें", "बाद में");

        // Chinese (Simplified)
        put("zh",
            "SBrowserX", "隐私与安全", "外观与界面", "新功能高亮提示",
            "WebRTC IP 处理策略",
            "默认", "默认公共和私有接口", "仅默认公共接口", "禁用非代理 UDP",
            "AMOLED 纯黑主题", "在深色模式下使用纯黑 #000000",
            "半透明底部导航栏", "开启底部导航栏的模糊与半透明效果",
            "新设置高亮天数", "关闭", "%d 天",
            "全部标记为已读", "已清除所有新功能提示",
            "重启浏览器", "立即应用 WebRTC 与外观更改",
            "是否立即重启 SBrowserX 以应用更改？", "重启", "稍后");

        // Japanese
        put("ja",
            "SBrowserX", "プライバシーとセキュリティ", "外観とUI", "新機能のハイライト",
            "WebRTC IP 処理ポリシー",
            "デフォルト", "パブリックおよびプライベート", "パブリックインターフェースのみ", "プロキシ経由以外の UDP を無効化",
            "AMOLED ブラックテーマ", "ダークモードで純黒 #000000 を使用",
            "半透明の下部ナビゲーションバー", "下部バーのぼかしと半透明を有効にする",
            "ハイライト期間", "オフ", "%d 日",
            "すべて既読にする", "バッジをすべて消去しました",
            "ブラウザを再起動", "WebRTCと外観の変更を今すぐ適用",
            "変更を適用するために SBrowserX を再起動しますか？", "再起動", "後で");

        // Korean
        put("ko",
            "SBrowserX", "개인정보 보호 및 보안", "화면 및 인터페이스", "새 기능 강조 표시",
            "WebRTC IP 처리 정책",
            "기본값", "공용 및 사설 인터페이스", "공용 인터페이스만 사용", "비프록시 UDP 비활성화",
            "AMOLED 블랙 테마", "다크 모드에서 순수 #000000 블랙 사용",
            "반투명 하단 탐색 바", "하단 탐색 바의 블러 및 반투명 효과 사용",
            "새 설정 강조 기간", "끄기", "%d일",
            "모두 읽음으로 표시", "모든 새 기능 표시가 제거되었습니다",
            "브라우저 다시 시작", "WebRTC 및 테마 변경 사항 즉시 적용",
            "변경 사항을 적용하려면 SBrowserX를 다시 시작할까요?", "다시 시작", "나중에");
    }

    private SBrowserXStrings() {}

    private static void put(String lang, String... values) {
        String[] keys = new String[] {
            TITLE_HUB, CAT_PRIVACY, CAT_APPEARANCE, CAT_HIGHLIGHT,
            WEBRTC_TITLE, WEBRTC_DEFAULT, WEBRTC_PUB_PRIV, WEBRTC_PUB_ONLY, WEBRTC_DISABLE_UDP,
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
