package dev.vortexnode.sbrowserx.extension;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public final class SBrowserXStrings {
    public static final String TITLE_HUB = "title_hub";
    public static final String CAT_EXTENSIONS = "cat_extensions";
    public static final String CAT_PRIVACY = "cat_privacy";
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
            "SBrowserX", "Extensions", "Privacy & Security", "Appearance & UI", "New Features Highlight",
            "Install extension from file (.crx / .zip)", "Pick any local .crx or .zip extension to install natively",
            "Install recommended extensions", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Extensions manager (internet://extensions)", "Open Chrome Web Store",
            "Installing extension…", "Extension installed!", "Installation error: ", "Open any webpage first so the extensions engine initializes", "Install",
            "WebRTC IP handling policy", "Default", "Default public and private interfaces", "Default public interface only", "Disable non-proxied UDP",
            "Fingerprint protection", "Native WebAudio & Screen anti-fingerprinting protection (restart to apply)",
            "AMOLED Black Theme", "Use pure #000000 black surfaces in Dark mode (works with bottom bar blur)",
            "Semi-transparent bottom bar", "Enable blur and translucency on the bottom navigation bar",
            "Highlight new settings", "Off", "%d d",
            "Mark all as viewed", "All new badges cleared",
            "Restart browser", "Apply privacy and appearance changes immediately",
            "Restart SBrowserX to apply this change?", "Restart", "Later");

        put("ru",
            "SBrowserX", "Расширения", "Конфиденциальность и безопасность", "Внешний вид и интерфейс", "Подсветка новых настроек",
            "Установить расширение из файла (.crx / .zip)", "Нативная установка локального .crx или .zip архива",
            "Набор рекомендуемых расширений", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Менеджер расширений (internet://extensions)", "Открыть Chrome Web Store",
            "Установка расширения…", "Расширение установлено!", "Ошибка установки: ", "Сначала откройте любую страницу, чтобы запустился движок расширений", "Установить",
            "Политика WebRTC IP", "По умолчанию", "Публичные и локальные интерфейсы", "Только публичный интерфейс", "Отключить non-proxied UDP (рекомендуется)",
            "Защита от фингерпринтинга", "Нативная защита WebAudio и экрана от отслеживания (нужен перезапуск)",
            "AMOLED-чёрная тема", "Чистый чёрный #000000 в тёмной теме (совместимо с полупрозрачностью)",
            "Полупрозрачная нижняя панель", "Включить размытие и полупрозрачность нижней навигации",
            "Срок подсветки новых пунктов", "Выкл", "%d дн.",
            "Отметить всё просмотренным", "Все метки новизны сброшены",
            "Перезапустить браузер", "Применить изменения приватности и интерфейса сейчас",
            "Перезапустить SBrowserX, чтобы применить изменения?", "Перезапустить", "Позже");

        put("uk",
            "SBrowserX", "Розширення", "Конфіденційність і безпека", "Зовнішній вигляд та інтерфейс", "Підсвічування нових функцій",
            "Встановити розширення з файлу (.crx / .zip)", "Нативне встановлення локального .crx або .zip архіву",
            "Набір рекомендованих розширень", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Менеджер розширень (internet://extensions)", "Відкрити Chrome Web Store",
            "Встановлення розширення…", "Розширення встановлено!", "Помилка встановлення: ", "Спочатку відкрийте будь-яку сторінку для запуску рушія розширень", "Встановити",
            "Політика WebRTC IP", "За замовчуванням", "Публічні та локальні інтерфейси", "Лише публічний інтерфейс", "Вимкнути non-proxied UDP (рекомендовано)",
            "Захист від фінгерпринтингу", "Нативний захист WebAudio та екрана (потрібен перезапуск)",
            "AMOLED-чорна тема", "Чистий чорний #000000 у темній темі (сумісно з напівпрозорістю)",
            "Напівпрозора нижня панель", "Увімкнути розмиття та напівпрозорість нижньої панелі",
            "Тривалість підсвічування", "Вимк", "%d дн.",
            "Позначити все переглянутим", "Усі позначки новизни очищено",
            "Перезапустити браузер", "Застосувати зміни конфіденційності та інтерфейсу зараз",
            "Перезапустити SBrowserX для застосування змін?", "Перезапустити", "Пізніше");

        put("es",
            "SBrowserX", "Extensiones", "Privacidad y seguridad", "Apariencia e interfaz", "Resaltar novedades",
            "Instalar extensión (.crx / .zip)", "Instala cualquier archivo .crx o .zip local",
            "Extensiones recomendadas", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Gestor de extensiones (internet://extensions)", "Abrir Chrome Web Store",
            "Instalando extensión…", "¡Extensión instalada!", "Error: ", "Abre primero una página web para iniciar el motor", "Instalar",
            "Política de IP de WebRTC", "Predeterminado", "Interfaces públicas y privadas", "Solo interfaz pública", "Desactivar UDP sin proxy",
            "Protección contra huellas (Fingerprint)", "Protección nativa de WebAudio y pantalla (requiere reinicio)",
            "Tema negro AMOLED", "Negro puro #000000 en modo oscuro (compatible con desenfoque)",
            "Barra inferior semitransparente", "Activar desenfoque en la barra de navegación inferior",
            "Duración del resaltado", "No", "%d d",
            "Marcar todo como visto", "Indicadores borrados",
            "Reiniciar navegador", "Aplicar cambios ahora",
            "¿Reiniciar SBrowserX para aplicar los cambios?", "Reiniciar", "Más tarde");

        put("pt",
            "SBrowserX", "Extensões", "Privacidade e segurança", "Aparência e interface", "Destaque de novidades",
            "Instalar extensão (.crx / .zip)", "Instale qualquer arquivo .crx ou .zip local",
            "Extensões recomendadas", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Gerenciador de extensões (internet://extensions)", "Abrir Chrome Web Store",
            "Instalando extensão…", "Extensão instalada!", "Erro: ", "Abra uma página primeiro para iniciar o motor", "Instalar",
            "Política de IP WebRTC", "Padrão", "Interfaces públicas e privadas", "Apenas interface pública", "Desativar UDP sem proxy",
            "Proteção contra Fingerprint", "Proteção nativa de WebAudio e tela (requer reinício)",
            "Tema preto AMOLED", "Preto puro #000000 no modo escuro (compatível com desfoque)",
            "Barra inferior semitransparente", "Ativar desfoque na barra de navegação inferior",
            "Duração do destaque", "Desl.", "%d d",
            "Marcar tudo como visto", "Marcadores removidos",
            "Reiniciar navegador", "Aplicar alterações agora",
            "Reiniciar o SBrowserX para aplicar as alterações?", "Reiniciar", "Depois");

        put("de",
            "SBrowserX", "Erweiterungen", "Datenschutz & Sicherheit", "Design & Oberfläche", "Neue Funktionen hervorheben",
            "Erweiterung aus Datei (.crx / .zip)", "Lokale .crx- oder .zip-Erweiterung nativ installieren",
            "Empfohlene Erweiterungen", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Erweiterungs-Manager (internet://extensions)", "Chrome Web Store öffnen",
            "Erweiterung wird installiert…", "Erweiterung installiert!", "Fehler: ", "Öffne zuerst eine Webseite, um die Engine zu starten", "Installieren",
            "WebRTC-IP-Richtlinie", "Standard", "Öffentliche & private Schnittstellen", "Nur öffentliche Schnittstelle", "Nicht-Proxy-UDP deaktivieren",
            "Fingerprinting-Schutz", "Nativer WebAudio- & Bildschirm-Schutz (Neustart erforderlich)",
            "AMOLED-Schwarz-Design", "Reines #000000 Schwarz (kompatibel mit Transparenz)",
            "Halbtransparente untere Leiste", "Unschärfe für die untere Navigationsleiste aktivieren",
            "Hervorhebungsdauer", "Aus", "%d T.",
            "Alle als gesehen markieren", "Alle Markierungen entfernt",
            "Browser neu starten", "Änderungen sofort anwenden",
            "SBrowserX neu starten, um Änderungen anzuwenden?", "Neu starten", "Später");

        put("fr",
            "SBrowserX", "Extensions", "Confidentialité et sécurité", "Apparence et interface", "Mise en avant des nouveautés",
            "Installer depuis un fichier (.crx / .zip)", "Installer nativement un fichier .crx ou .zip local",
            "Extensions recommandées", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Gestionnaire d'extensions (internet://extensions)", "Ouvrir le Chrome Web Store",
            "Installation…", "Extension installée !", "Erreur : ", "Ouvrez d'abord une page web pour initialiser le moteur", "Installer",
            "Politique IP WebRTC", "Par défaut", "Interfaces publiques et privées", "Interface publique uniquement", "Désactiver UDP sans proxy",
            "Protection anti-empreinte", "Protection native WebAudio et écran (redémarrage requis)",
            "Thème noir AMOLED", "Noir pur #000000 en mode sombre (compatible avec le flou)",
            "Barre inférieure semi-transparente", "Activer le flou sur la barre de navigation inférieure",
            "Durée de mise en avant", "Non", "%d j",
            "Tout marquer comme vu", "Badges effacés",
            "Redémarrer le navigateur", "Appliquer les modifications maintenant",
            "Redémarrer SBrowserX pour appliquer les modifications ?", "Redémarrer", "Plus tard");

        put("it",
            "SBrowserX", "Estensioni", "Privacy e sicurezza", "Aspetto e interfaccia", "Evidenzia novità",
            "Installa da file (.crx / .zip)", "Installa nativamente un file .crx o .zip locale",
            "Estensioni consigliate", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Gestore estensioni (internet://extensions)", "Apri Chrome Web Store",
            "Installazione…", "Estensione installata!", "Errore: ", "Apri prima una pagina web per avviare il motore", "Installa",
            "Politica IP WebRTC", "Predefinito", "Interfacce pubbliche e private", "Solo interfaccia pubblica", "Disattiva UDP senza proxy",
            "Protezione Fingerprint", "Protezione nativa WebAudio e schermo (riavvio richiesto)",
            "Tema nero AMOLED", "Nero puro #000000 in modalità scura (compatibile con sfocatura)",
            "Barra inferiore semitrasparente", "Attiva sfocatura sulla barra di navigazione inferiore",
            "Durata evidenziazione", "Off", "%d g",
            "Segna tutto come visto", "Indicatori rimossi",
            "Riavvia browser", "Applica subito le modifiche",
            "Riavviare SBrowserX per applicare le modifiche?", "Riavvia", "Dopo");

        put("pl",
            "SBrowserX", "Rozszerzenia", "Prywatność i bezpieczeństwo", "Wygląd i interfejs", "Podświetlanie nowości",
            "Zainstaluj z pliku (.crx / .zip)", "Natywna instalacja lokalnego pliku .crx lub .zip",
            "Polecane rozszerzenia", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Menedżer rozszerzeń (internet://extensions)", "Otwórz Chrome Web Store",
            "Instalowanie…", "Rozszerzenie zainstalowane!", "Błąd: ", "Najpierw otwórz dowolną stronę, aby uruchomić silnik", "Zainstaluj",
            "Polityka IP WebRTC", "Domyślna", "Interfejsy publiczne i prywatne", "Tylko interfejs publiczny", "Wyłącz UDP bez proxy",
            "Ochrona przed fingerprintingiem", "Natywna ochrona WebAudio i ekranu (wymaga restartu)",
            "Czarny motyw AMOLED", "Czysta czerń #000000 (działa z przezroczystością)",
            "Półprzezroczysty dolny pasek", "Włącz rozmycie dolnego paska nawigacji",
            "Czas podświetlania", "Wył.", "%d d.",
            "Oznacz wszystko jako przeczytane", "Wyczyszczono znaczniki",
            "Uruchom ponownie przeglądarkę", "Zastosuj zmiany teraz",
            "Uruchomić ponownie SBrowserX, aby zastosować zmiany?", "Uruchom ponownie", "Później");

        put("tr",
            "SBrowserX", "Uzantılar", "Gizlilik ve Güvenlik", "Görünüm ve Arayüz", "Yeni Özellik Vurgusu",
            "Dosyadan uzantı yükle (.crx / .zip)", "Yerel .crx veya .zip uzantısını doğrudan yükle",
            "Önerilen uzantılar", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "Uzantı yöneticisi (internet://extensions)", "Chrome Web Mağazasını Aç",
            "Yükleniyor…", "Uzantı yüklendi!", "Hata: ", "Motorun başlaması için önce bir web sayfası açın", "Yükle",
            "WebRTC IP politikası", "Varsayılan", "Genel ve özel arayüzler", "Yalnızca genel arayüz", "Proxy dışı UDP'yi devre dışı bırak",
            "Parmak izi koruması", "Yerel WebAudio ve ekran koruması (yeniden başlatma gerekir)",
            "AMOLED Siyah Tema", "Karanlık modda saf #000000 siyah (bulanıklıkla uyumlu)",
            "Yarı saydam alt çubuk", "Alt gezinme çubuğunda bulanıklığı etkinleştir",
            "Vurgu süresi", "Kapalı", "%d g",
            "Tümünü görüldü işaretle", "Tüm rozetler temizlendi",
            "Tarayıcıyı yeniden başlat", "Değişiklikleri şimdi uygula",
            "Değişiklikleri uygulamak için SBrowserX yeniden başlatılsın mı?", "Yeniden başlat", "Sonra");

        put("ar",
            "SBrowserX", "الإضافات", "الخصوصية والأمان", "المظهر والواجهة", "تمييز الميزات الجديدة",
            "تثبيت إضافة من ملف (.crx / .zip)", "تثبيت ملف .crx أو .zip محلياً",
            "الإضافات الموصى بها", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "مدير الإضافات (internet://extensions)", "فتح سوق Chrome الإلكتروني",
            "جارٍ التثبيت…", "تم تثبيت الإضافة!", "خطأ: ", "افتح أي صفحة ويب أولاً لتشغيل محرك الإضافات", "تثبيت",
            "سياسة عنوان IP لـ WebRTC", "افتراضي", "الواجهات العامة والخاصة", "الواجهة العامة فقط", "تعطيل UDP بدون بروكسي",
            "الحماية من البصمة الرقمية", "حماية أصلية لـ WebAudio والشاشة (تتطلب إعادة التشغيل)",
            "الوضع الأسود AMOLED", "أسود نقي #000000 في الوضع الداكن (متوافق مع الشفافية)",
            "شريط سفلي شبه شفاف", "تفعيل التمويه والشفافية للشريط السفلي",
            "مدة تمييز الإعدادات الجديدة", "إيقاف", "%d ي",
            "تحديد الكل كمقروء", "تم مسح جميع العلامات",
            "إعادة تشغيل المتصفح", "تطبيق التغييرات الآن",
            "هل تريد إعادة تشغيل SBrowserX لتطبيق التغييرات؟", "إعادة التشغيل", "لاحقاً");

        put("hi",
            "SBrowserX", "एक्सटेंशन", "गोपनीयता और सुरक्षा", "दिखावट और इंटरफ़ेस", "नई सुविधाएँ हाइलाइट",
            "फ़ाइल से एक्सटेंशन इंस्टॉल करें (.crx / .zip)", "स्थानीय .crx या .zip फ़ाइल से सीधे इंस्टॉल करें",
            "अनुशंसित एक्सटेंशन", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "एक्सटेंशन प्रबंधक (internet://extensions)", "Chrome वेब स्टोर खोलें",
            "इंस्टॉल हो रहा है…", "एक्सटेंशन इंस्टॉल हो गया!", "त्रुटि: ", "इंजन शुरू करने के लिए पहले कोई वेबपेज खोलें", "इंस्टॉल करें",
            "WebRTC IP नीति", "डिफ़ॉल्ट", "सार्वजनिक और निजी इंटरफ़ेस", "केवल सार्वजनिक इंटरफ़ेस", "गैर-प्रॉक्सी UDP अक्षम करें",
            "फ़िंगरप्रिंट सुरक्षा", "नेटिव WebAudio और स्क्रीन सुरक्षा (पुनरारंभ आवश्यक)",
            "AMOLED ब्लैक थीम", "डार्क मोड में शुद्ध #000000 काला (ब्लर के साथ काम करता है)",
            "अर्ध-पारदर्शी निचला बार", "निचले नेविगेशन बार पर ब्लर सक्षम करें",
            "हाइलाइट अवधि", "बंद", "%d दिन",
            "सभी को देखा गया चिह्नित करें", "सभी बैज हटाए गए",
            "ब्राउज़र पुनरारंभ करें", "परिवर्तन अभी लागू करें",
            "परिवर्तन लागू करने के लिए SBrowserX पुनरारंभ करें?", "पुनरारंभ करें", "बाद में");

        put("zh",
            "SBrowserX", "扩展程序", "隐私与安全", "外观与界面", "新功能高亮提示",
            "从本地安装扩展 (.crx / .zip)", "选择本地 .crx 或 .zip 文件原生安装",
            "安装推荐扩展包", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "扩展管理器 (internet://extensions)", "打开 Chrome 应用商店",
            "正在安装扩展…", "扩展安装成功！", "安装失败：", "请先打开任意网页以初始化扩展引擎", "安装",
            "WebRTC IP 处理策略", "默认", "默认公共和私有接口", "仅默认公共接口", "禁用非代理 UDP",
            "指纹防护 (Fingerprint)", "原生 WebAudio 与屏幕防指纹保护（重启生效）",
            "AMOLED 纯黑主题", "深色模式下使用纯黑 #000000（支持底栏半透明模糊）",
            "半透明底部导航栏", "开启底部导航栏的模糊与半透明效果",
            "新设置高亮天数", "关闭", "%d 天",
            "全部标记为已读", "已清除所有新功能提示",
            "重启浏览器", "立即应用隐私与外观更改",
            "是否立即重启 SBrowserX 以应用更改？", "重启", "稍后");

        put("ja",
            "SBrowserX", "拡張機能", "プライバシーとセキュリティ", "外観とUI", "新機能のハイライト",
            "ファイルから拡張機能をインストール (.crx / .zip)", "ローカルの .crx または .zip をネイティブインストール",
            "おすすめ拡張機能パック", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "拡張機能マネージャー (internet://extensions)", "Chrome ウェブストアを開く",
            "インストール中…", "拡張機能をインストールしました！", "エラー: ", "先に任意のページを開いてエンジンを初期化してください", "インストール",
            "WebRTC IP 処理ポリシー", "デフォルト", "パブリックおよびプライベート", "パブリックインターフェースのみ", "プロキシ経由以外の UDP を無効化",
            "フィンガープリント保護", "ネイティブ WebAudio & 画面保護（再起動後に適用）",
            "AMOLED ブラックテーマ", "ダークモードで純黒 #000000 を使用（半透明ぼかし対応）",
            "半透明の下部ナビゲーションバー", "下部バーのぼかしと半透明を有効にする",
            "ハイライト期間", "オフ", "%d 日",
            "すべて既読にする", "バッジをすべて消去しました",
            "ブラウザを再起動", "変更を今すぐ適用",
            "変更を適用するために SBrowserX を再起動しますか？", "再起動", "後で");

        put("ko",
            "SBrowserX", "확장 프로그램", "개인정보 보호 및 보안", "화면 및 인터페이스", "새 기능 강조 표시",
            "파일에서 확장 프로그램 설치 (.crx / .zip)", "로컬 .crx 또는 .zip 파일을 네이티브로 설치",
            "추천 확장 프로그램 설치", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard",
            "확장 프로그램 관리 (internet://extensions)", "Chrome 웹 스토어 열기",
            "설치 중…", "확장 프로그램이 설치되었습니다!", "오류: ", "엔진 초기화를 위해 먼저 웹페이지를 여세요", "설치",
            "WebRTC IP 처리 정책", "기본값", "공용 및 사설 인터페이스", "공용 인터페이스만 사용", "비프록시 UDP 비활성화",
            "핑거프린트 보호", "네이티브 WebAudio 및 화면 추적 방지 (재시작 필요)",
            "AMOLED 블랙 테마", "다크 모드에서 순수 #000000 블랙 사용 (하단 바 블러 호환)",
            "반투명 하단 탐색 바", "하단 탐색 바의 블러 및 반투명 효과 사용",
            "새 설정 강조 기간", "끄기", "%d일",
            "모두 읽음으로 표시", "모든 새 기능 표시가 제거되었습니다",
            "브라우저 다시 시작", "변경 사항 즉시 적용",
            "변경 사항을 적용하려면 SBrowserX를 다시 시작할까요?", "다시 시작", "나중에");
    }

    private SBrowserXStrings() {}

    private static void put(String lang, String... values) {
        String[] keys = new String[] {
            TITLE_HUB, CAT_EXTENSIONS, CAT_PRIVACY, CAT_APPEARANCE, CAT_HIGHLIGHT,
            EXT_INSTALL_FILE, EXT_INSTALL_FILE_SUMMARY, EXT_PACK_TITLE, EXT_PACK_SUMMARY,
            EXT_OPEN_MANAGER, EXT_OPEN_CWS, EXT_INSTALLING, EXT_INSTALL_OK, EXT_INSTALL_FAIL,
            EXT_ENGINE_NOT_READY, BTN_INSTALL,
            WEBRTC_TITLE, WEBRTC_DEFAULT, WEBRTC_PUB_PRIV, WEBRTC_PUB_ONLY, WEBRTC_DISABLE_UDP,
            FINGERPRINT_TITLE, FINGERPRINT_SUMMARY,
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
