package dev.vortexnode.sbrowserx.extension;

/**
 * Localized strings for the SBrowserX "What's New" (v1.4.0) and first-launch "Welcome" sheets
 * across all 20 supported languages.
 * Array format (11 items):
 * [0] title, [1] promoHeader, [2] promoBody,
 * [3] addedHeader, [4] addedBody,
 * [5] improvedHeader, [6] improvedBody,
 * [7] removedHeader, [8] removedBody,
 * [9] checkboxText, [10] scrollHint
 */
public final class SBrowserXChangelogLocales {
    private SBrowserXChangelogLocales() {}

    public static String[] get(String lang) {
        if (lang == null) lang = "en";
        switch (lang) {
            case "ru": return new String[] {
                "Что нового в SBrowserX",
                "★ Главное в v1.4.0",
                "Стабильная биометрия в Секретном режиме (Инкогнито), исправление вылетов расширений при вводе с клавиатуры и умная кнопка перезапуска без наложений.",
                "Улучшения и возможности",
                "• Секретный режим (Инкогнито): вход по отпечатку переведён на стандартный Android BiometricPrompt без зависимости от Knox SDP — работает стабильно на Samsung и не-Samsung устройствах.\n"
                + "• ИИ-сводка на всех смартфонах: кнопка «Ассистент по просмотру» (Gemini AI) теперь доступна и на устройствах не от Samsung (Pixel, OnePlus, Xiaomi и др.).\n"
                + "• Умная кнопка перезапуска: в настройках автоматически обходит кнопки тулбара («Готово», «Сбросить», поиск) — сдвигается левее или опускается вниз вправо.",
                "Исправления и стабильность",
                "• Попапы расширений (Bitwarden и др.): устранён вылет при вводе с клавиатуры во всплывающих окнах расширений.\n"
                + "• Кнопка «Домой» и Стартовая страница: исправлен долгий тап по «Домой» (без пустого URL) и убран региональный редирект новой вкладки на google.com.",
                "",
                "",
                "Спасибо, всё понятно",
                "↓ Пролистайте список до конца"
            };
            case "uk": return new String[] {
                "Що нового в SBrowserX",
                "★ Головне у v1.4.0",
                "Стабільна біометрія у Секретному режимі (Інкогніто), виправлення вильотів розширень під час введення з клавіатури та розумна кнопка перезапуску без накладень.",
                "Покращення та можливості",
                "• Секретний режим (Інкогніто): вхід за відбитком переведено на стандартний Android BiometricPrompt без залежності від Knox SDP — стабільно працює на Samsung і не-Samsung пристроях.\n"
                + "• ШІ-зведення на всіх смартфонах: кнопка «Асистент перегляду» (Gemini AI) тепер доступна і на пристроях не від Samsung (Pixel, OnePlus, Xiaomi тощо).\n"
                + "• Розумна кнопка перезапуску: у налаштуваннях автоматично оминає кнопки панелі («Готово», «Скинути», пошук) — зсувається ліворуч або опускається вниз праворуч.",
                "Виправлення та стабільність",
                "• Попапи розширень (Bitwarden тощо): усунено виліт під час введення з клавіатури у спливаючих вікнах розширень.\n"
                + "• Кнопка «Додому» та Стартова сторінка: виправлено довге натискання «Додому» (без порожнього URL) і прибрано регіональний редирект нової вкладки на google.com.",
                "",
                "",
                "Дякую, усе зрозуміло",
                "↓ Прогорніть список до кінця"
            };
            case "es": return new String[] {
                "Novedades en SBrowserX",
                "★ Destacado en v1.4.0",
                "Biometría estable en Modo Secreto (Incógnito), corrección de cierres al escribir en extensiones y botón de reinicio inteligente sin solapamientos.",
                "Mejoras y funciones",
                "• Modo Secreto (Incógnito): inicio por huella migrado a Android BiometricPrompt estándar sin depender de Knox SDP — funciona sin fallos en dispositivos Samsung y no Samsung.\n"
                + "• Resumen IA en todos los teléfonos: el botón «Asistente de navegación» (Gemini AI) ahora está disponible también en Pixel, OnePlus, Xiaomi y otros.\n"
                + "• Botón de reinicio inteligente: detecta botones de la barra superior («Listo», «Restablecer», buscar) y se desplaza a la izquierda o abajo a la derecha.",
                "Correcciones y estabilidad",
                "• Ventanas de extensiones (Bitwarden, etc.): corregido el cierre inesperado al usar el teclado dentro de los popups de extensiones.\n"
                + "• Botón Inicio y Página de inicio: corregida la pulsación larga en Inicio y eliminada la redirección regional de nueva pestaña a google.com.",
                "",
                "",
                "Gracias, entendido",
                "↓ Desplázate hasta el final"
            };
            case "pt": return new String[] {
                "Novidades no SBrowserX",
                "★ Destaques da v1.4.0",
                "Biometria estável no Modo Secreto (Anônimo), correção de travamentos ao digitar em extensões e botão de reinício inteligente sem sobreposição.",
                "Melhorias e recursos",
                "• Modo Secreto (Anônimo): desbloqueio por digital migrado para o Android BiometricPrompt padrão sem depender do Knox SDP — estável em aparelhos Samsung e não Samsung.\n"
                + "• Resumo IA em todos os celulares: o botão «Assistente de navegação» (Gemini AI) agora funciona também em Pixel, OnePlus, Xiaomi e outros.\n"
                + "• Botão de reinício inteligente: evita automaticamente botões da barra superior («Concluir», «Redefinir», busca), movendo-se para a esquerda ou para o canto inferior direito.",
                "Correções e estabilidade",
                "• Popups de extensões (Bitwarden, etc.): corrigido o travamento ao digitar com o teclado nas janelas de extensões.\n"
                + "• Botão Início e Página Inicial: corrigido o toque longo em Início e removido o redirecionamento regional da nova aba para google.com.",
                "",
                "",
                "Obrigado, entendi",
                "↓ Role até o final"
            };
            case "de": return new String[] {
                "Neu in SBrowserX",
                "★ Highlights in v1.4.0",
                "Stabile Biometrie im Geheimen Modus (Inkognito), Fix für Tastatur-Abstürze in Erweiterungen und smarter Neustart-Button ohne Überlappung.",
                "Verbesserungen & Funktionen",
                "• Geheimer Modus (Inkognito): Fingerabdruck-Login nutzt jetzt Android BiometricPrompt ohne Knox-SDP-Abhängigkeit — stabil auf Samsung- und Nicht-Samsung-Geräten.\n"
                + "• KI-Zusammenfassung auf allen Geräten: «Browsing-Assistent» (Gemini AI) ist nun auch auf Pixel, OnePlus, Xiaomi usw. freigeschaltet.\n"
                + "• Smarter Neustart-Button: erkennt automatisch Toolbar-Buttons («Fertig», «Zurücksetzen», Suche) und weicht nach links oder unten rechts aus.",
                "Fehlerbehebungen & Stabilität",
                "• Erweiterungs-Popups (Bitwarden usw.): Absturz bei Tastatureingaben in Erweiterungs-Popups behoben.\n"
                + "• Home-Button & Startseite: Langes Drücken auf Home korrigiert und regionale Weiterleitung neuer Tabs auf google.com entfernt.",
                "",
                "",
                "Danke, alles klar",
                "↓ Bis zum Ende scrollen"
            };
            case "fr": return new String[] {
                "Nouveautés de SBrowserX",
                "★ Points forts de la v1.4.0",
                "Biométrie fiable en Mode Secret (Navigation privée), correctif des crashs clavier dans les extensions et bouton de redémarrage intelligent sans chevauchement.",
                "Améliorations et nouveautés",
                "• Mode Secret : l'empreinte utilise désormais Android BiometricPrompt sans dépendre de Knox SDP — stable sur Samsung et autres appareils.\n"
                + "• Résumé IA sur tous les téléphones : le bouton « Assistant de navigation » (Gemini AI) est désormais disponible sur Pixel, OnePlus, Xiaomi, etc.\n"
                + "• Bouton de redémarrage intelligent : évite automatiquement les boutons de la barre d'outils (« Terminé », « Réinitialiser », recherche) en se décalant à gauche ou en bas à droite.",
                "Correctifs et stabilité",
                "• Popups d'extensions (Bitwarden, etc.) : correction du plantage lors de la saisie au clavier dans les fenêtres d'extensions.\n"
                + "• Bouton Accueil et Page de démarrage : appui long sur Accueil corrigé et redirection régionale des nouveaux onglets vers google.com supprimée.",
                "",
                "",
                "Merci, c'est compris",
                "↓ Faites défiler jusqu'en bas"
            };
            case "it": return new String[] {
                "Novità in SBrowserX",
                "★ In evidenza nella v1.4.0",
                "Biometria stabile in Modalità Segreta (Incognito), fix dei crash della tastiera nelle estensioni e pulsante di riavvio intelligente senza sovrapposizioni.",
                "Miglioramenti e funzioni",
                "• Modalità Segreta: accesso con impronta migrato ad Android BiometricPrompt senza dipendere da Knox SDP — stabile su Samsung e altri dispositivi.\n"
                + "• Riepilogo IA su tutti i telefoni: il pulsante «Assistente navigazione» (Gemini AI) è ora disponibile anche su Pixel, OnePlus, Xiaomi, ecc.\n"
                + "• Pulsante riavvio intelligente: evita automaticamente i pulsanti della barra superiore («Fine», «Ripristina», cerca) spostandosi a sinistra o in basso a destra.",
                "Correzioni e stabilità",
                "• Popup delle estensioni (Bitwarden, ecc.): risolto il crash durante l'uso della tastiera nei popup delle estensioni.\n"
                + "• Pulsante Home e Pagina iniziale: corretta la pressione prolungata su Home e rimosso il reindirizzamento regionale delle nuove schede su google.com.",
                "",
                "",
                "Grazie, tutto chiaro",
                "↓ Scorri fino in fondo"
            };
            case "pl": return new String[] {
                "Co nowego w SBrowserX",
                "★ Najważniejsze w v1.4.0",
                "Stabilna biometria w Trybie Tajnym (Incognito), poprawka awarii klawiatury w rozszerzeniach oraz inteligentny przycisk restartu bez nakładania się.",
                "Ulepszenia i funkcje",
                "• Tryb Tajny (Incognito): logowanie odciskiem palca korzysta ze standardowego Android BiometricPrompt bez Knox SDP — stabilnie na Samsungach i innych telefonach.\n"
                + "• Podsumowanie AI na wszystkich telefonach: przycisk «Asystent przeglądania» (Gemini AI) działa teraz także na Pixel, OnePlus, Xiaomi itd.\n"
                + "• Inteligentny przycisk restartu: automatycznie omija przyciski paska («Gotowe», «Resetuj», szukaj), przesuwając się w lewo lub w prawy dolny róg.",
                "Poprawki i stabilność",
                "• Okienka rozszerzeń (Bitwarden itp.): naprawiono zamykanie przeglądarki przy wpisywaniu z klawiatury w popupach rozszerzeń.\n"
                + "• Przycisk Strona główna i Start Page: naprawiono długie przytrzymanie Home i usunięto regionalne przekierowanie nowej karty na google.com.",
                "",
                "",
                "Dzięki, wszystko jasne",
                "↓ Przewiń do końca"
            };
            case "tr": return new String[] {
                "SBrowserX Yenilikleri",
                "★ v1.4.0 Öne Çıkanlar",
                "Gizli Modda (Incognito) kararlı biyometri, eklentilerde klavye çökmesi düzeltmesi ve üst üste binmeyen akıllı yeniden başlatma düğmesi.",
                "İyileştirmeler ve Özellikler",
                "• Gizli Mod: parmak izi girişi Knox SDP olmadan standart Android BiometricPrompt kullanır — Samsung ve diğer cihazlarda sorunsuz çalışır.\n"
                + "• Tüm telefonlarda Yapay Zeka Özeti: «Tarama asistanı» (Gemini AI) düğmesi artık Pixel, OnePlus, Xiaomi gibi cihazlarda da açık.\n"
                + "• Akıllı yeniden başlatma düğmesi: üst çubuktaki düğmeleri («Bitti», «Sıfırla», ara) algılar ve sola ya da sağ alta kayar.",
                "Düzeltmeler ve Kararlılık",
                "• Eklenti pencereleri (Bitwarden vb.): eklenti açılır pencerelerinde klavye yazarken oluşan çökme giderildi.\n"
                + "• Ana Sayfa düğmesi ve Başlangıç Sayfası: uzun basma düzeltildi ve yeni sekmenin bölgesel google.com yönlendirmesi kaldırıldı.",
                "",
                "",
                "Teşekkürler, anladım",
                "↓ Listenin sonuna kaydırın"
            };
            case "ar": return new String[] {
                "ما الجديد في SBrowserX",
                "★ أبرز ما في v1.4.0",
                "بصمة مستقرة في الوضع السري (التصفح المتخفي)، إصلاح تعطل الإضافات عند الكتابة، وزر إعادة تشغيل ذكي دون تداخل.",
                "التحسينات والميزات",
                "• الوضع السري: تم نقل الدخول بالبصمة إلى Android BiometricPrompt القياسي دون الاعتماد على Knox SDP — يعمل بثبات على أجهزة Samsung وغيرها.\n"
                + "• ملخص الذكاء الاصطناعي لكل الهواتف: زر «مساعد التصفح» (Gemini AI) أصبح متاحاً أيضاً على Pixel وOnePlus وXiaomi وغيرها.\n"
                + "• زر إعادة التشغيل الذكي: يتجنب تلقائياً أزرار الشريط العلوي (تم، إعادة تعيين، بحث) بالتحرك يساراً أو للأسفل.",
                "الإصلاحات والاستقرار",
                "• نوافذ الإضافات (مثل Bitwarden): إصلاح الإغلاق المفاجئ عند استخدام لوحة المفاتيح داخل نوافذ الإضافات.\n"
                + "• زر الرئيسية وصفحة البداية: إصلاح الضغط المطول على الرئيسية وإزالة إعادة التوجيه الإقليمي للتبويب الجديد إلى google.com.",
                "",
                "",
                "شكراً، فهمت",
                "↓ مرر إلى الأسفل للمتابعة"
            };
            case "fa": return new String[] {
                "تازه‌های SBrowserX",
                "★ مهم‌ترین تغییرات v1.4.0",
                "بیومتریک پایدار در حالت محرمانه (Incognito)، رفع کرش افزونه‌ها هنگام تایپ و دکمه راه‌اندازی مجدد هوشمند بدون هم‌پوشانی.",
                "بهبودها و قابلیت‌ها",
                "• حالت محرمانه: ورود با اثر انگشت به Android BiometricPrompt استاندارد بدون وابستگی به Knox SDP منتقل شد — پایدار در دستگاه‌های سامسونگ و غیرسامسونگ.\n"
                + "• خلاصه هوش مصنوعی در همه گوشی‌ها: دکمه «دستیار مرور» (Gemini AI) اکنون در Pixel، OnePlus، Xiaomi و غیره نیز فعال است.\n"
                + "• دکمه راه‌اندازی مجدد هوشمند: به‌طور خودکار از دکمه‌های نوار بالا فاصله می‌گیرد و به چپ یا پایین راست منتقل می‌شود.",
                "رفع خطاها و پایداری",
                "• پنجره افزونه‌ها (Bitwarden و غیره): رفع کرش هنگام استفاده از کیبورد در پنجره بازشوی افزونه‌ها.\n"
                + "• دکمه خانه و صفحه شروع: اصلاح لمس طولانی خانه و حذف هدایت منطقه‌ای تب جدید به google.com.",
                "",
                "",
                "ممنون، متوجه شدم",
                "↓ تا انتها اسکرول کنید"
            };
            case "hi": return new String[] {
                "SBrowserX में नया क्या है",
                "★ v1.4.0 की मुख्य बातें",
                "सीक्रेट मोड (इनकॉग्निटो) में स्थिर बायोमेट्रिक लॉगिन, एक्सटेंशन में कीबोर्ड क्रैश फिक्स और ओवरलैप न करने वाला स्मार्ट रीस्टार्ट बटन।",
                "सुधार और सुविधाएँ",
                "• सीक्रेट मोड (इनकॉग्निटो): Knox SDP के बिना स्टैंडर्ड Android BiometricPrompt पर शिफ्ट — Samsung और अन्य सभी फ़ोनों पर बिना क्रैश के काम करता है।\n"
                + "• सभी फ़ोनों पर AI सारांश: «ब्राउज़िंग असिस्ट» (Gemini AI) बटन अब Pixel, OnePlus, Xiaomi आदि पर भी उपलब्ध है।\n"
                + "• स्मार्ट रीस्टार्ट बटन: टूलबार के बटन (Done, Reset, Search) से टकराव होने पर अपने-आप बाईं या नीचे दाईं ओर शिफ्ट हो जाता है।",
                "फिक्स और स्थिरता",
                "• एक्सटेंशन पॉपअप (Bitwarden आदि): एक्सटेंशन पॉपअप में कीबोर्ड टाइपिंग के दौरान होने वाला क्रैश ठीक किया गया।\n"
                + "• होम बटन और स्टार्ट पेज: होम पर लॉन्ग-प्रेस फिक्स किया गया और नए टैब का क्षेत्रीय google.com रीडायरेक्ट हटाया गया।",
                "",
                "",
                "धन्यवाद, समझ गया",
                "↓ नीचे तक स्क्रॉल करें"
            };
            case "zh": return new String[] {
                "SBrowserX 更新日志",
                "★ v1.4.0 重点更新",
                "保密模式（无痕）指纹稳定可用、修复扩展弹窗输入崩溃，以及不遮挡按钮的智能重启按钮。",
                "改进与新特性",
                "• 保密模式（无痕）：指纹解锁改用标准 Android BiometricPrompt 并禁用 Knox SDP 依赖，三星与非三星机型均稳定无闪退。\n"
                + "• 全机型解锁 AI 摘要：「浏览助手」（Gemini AI）按钮现已支持 Pixel、OnePlus、小米等非三星设备。\n"
                + "• 智能重启按钮：自动检测顶部工具栏按钮（完成、重置、搜索等），避免遮挡并自动左移或移至右下角。",
                "修复与稳定性",
                "• 扩展弹窗（Bitwarden 等）：修复在扩展弹窗中使用输入法/键盘时的闪退问题。\n"
                + "• 主页键与起始页：修复长按主页键行为，并移除新标签页的区域性 google.com 跳转。",
                "",
                "",
                "谢谢，我明白了",
                "↓ 请滚动到底部"
            };
            case "ja": return new String[] {
                "SBrowserX の新機能",
                "★ v1.4.0 のハイライト",
                "シークレットモードの生体認証を安定化、拡張機能ポップアップの入力クラッシュを修正、重ならないスマート再起動ボタンを搭載。",
                "改善と機能",
                "• シークレットモード：Knox SDP 依存を外し標準の Android BiometricPrompt に移行 — Samsung および他社製端末でも安定動作。\n"
                + "• 全端末で AI 要約対応：「ブラウジングアシスト」（Gemini AI）ボタンが Pixel・OnePlus・Xiaomi 等でも利用可能に。\n"
                + "• スマート再起動ボタン：ツールバーのボタン（完了・リセット・検索）を自動回避し、左または右下へ移動します。",
                "修正と安定性",
                "• 拡張機能ポップアップ（Bitwarden 等）：キーボード入力時に発生するクラッシュを修正。\n"
                + "• ホームボタンとスタートページ：ホーム長押しを修正し、新しいタブの地域別 google.com リダイレクトを無効化。",
                "",
                "",
                "了解しました",
                "↓ 最後までスクロールしてください"
            };
            case "ko": return new String[] {
                "SBrowserX 새 기능",
                "★ v1.4.0 주요 변경 사항",
                "비밀 모드(시크릿) 생체 인증 안정화, 확장 프로그램 키보드 입력 충돌 수정, 겹침 없는 스마트 재시작 버튼 적용.",
                "개선 및 기능",
                "• 비밀 모드(시크릿): Knox SDP 의존성을 제거하고 표준 Android BiometricPrompt를 사용해 삼성 및 타사 기기에서 충돌 없이 작동합니다.\n"
                + "• 모든 기기에서 AI 요약 지원: Pixel, OnePlus, Xiaomi 등 비삼성 기기에서도 «브라우징 어시스트»(Gemini AI) 버튼이 표시됩니다.\n"
                + "• 스마트 재시작 버튼: 상단 툴바 버튼(완료, 초기화, 검색)과 겹치지 않도록 자동으로 왼쪽이나 우측 하단으로 이동합니다.",
                "수정 및 안정성",
                "• 확장 프로그램 팝업(Bitwarden 등): 팝업 내 키보드 입력 시 발생하던 강제 종료를 수정했습니다.\n"
                + "• 홈 버튼 및 시작 페이지: 홈 길게 누르기 동작을 개선하고 새 탭의 지역별 google.com 리디렉션을 제거했습니다.",
                "",
                "",
                "확인했습니다",
                "↓ 끝까지 스크롤하세요"
            };
            case "id": return new String[] {
                "Yang Baru di SBrowserX",
                "★ Sorotan v1.4.0",
                "Biometrik Mode Rahasia (Incognito) stabil, perbaikan crash keyboard di ekstensi, dan tombol restart pintar tanpa tumpang tindih.",
                "Peningkatan & Fitur",
                "• Mode Rahasia: login sidik jari menggunakan Android BiometricPrompt standar tanpa Knox SDP — stabil di perangkat Samsung maupun non-Samsung.\n"
                + "• Ringkasan AI di semua ponsel: tombol «Asisten Penjelajahan» (Gemini AI) kini tersedia juga di Pixel, OnePlus, Xiaomi, dll.\n"
                + "• Tombol restart pintar: otomatis menghindari tombol toolbar (Selesai, Reset, Cari) dengan bergeser ke kiri atau kanan bawah.",
                "Perbaikan & Stabilitas",
                "• Popup ekstensi (Bitwarden, dll.): memperbaiki crash saat mengetik dengan keyboard di dalam popup ekstensi.\n"
                + "• Tombol Beranda & Halaman Awal: memperbaiki tekan lama Beranda dan menghapus pengalihan regional tab baru ke google.com.",
                "",
                "",
                "Terima kasih, paham",
                "↓ Gulir ke bagian bawah"
            };
            case "vi": return new String[] {
                "Có gì mới trong SBrowserX",
                "★ Nổi bật trong v1.4.0",
                "Sinh trắc học Chế độ Bí mật (Ẩn danh) ổn định, sửa lỗi văng khi gõ trong tiện ích mở rộng và nút khởi động lại thông minh không bị đè.",
                "Cải tiến & Tính năng",
                "• Chế độ Bí mật: dùng Android BiometricPrompt chuẩn không phụ thuộc Knox SDP — hoạt động ổn định trên cả máy Samsung lẫn máy khác.\n"
                + "• Tóm tắt AI trên mọi điện thoại: nút «Trợ lý duyệt web» (Gemini AI) nay đã khả dụng trên Pixel, OnePlus, Xiaomi, v.v.\n"
                + "• Nút khởi động lại thông minh: tự động tránh các nút trên thanh công cụ (Xong, Đặt lại, Tìm kiếm) bằng cách dịch sang trái hoặc xuống góc phải.",
                "Sửa lỗi & Ổn định",
                "• Cửa sổ tiện ích (Bitwarden, v.v.): sửa lỗi thoát ứng dụng khi dùng bàn phím trong popup tiện ích.\n"
                + "• Nút Trang chủ & Trang bắt đầu: sửa nhấn giữ nút Trang chủ và bỏ chuyển hướng vùng tab mới sang google.com.",
                "",
                "",
                "Cảm ơn, tôi đã hiểu",
                "↓ Cuộn xuống cuối danh sách"
            };
            case "nl": return new String[] {
                "Nieuw in SBrowserX",
                "★ Hoogtepunten van v1.4.0",
                "Stabiele biometrie in Geheime modus (Incognito), fix voor toetsenbord-crashes in extensies en slimme herstartknop zonder overlap.",
                "Verbeteringen & functies",
                "• Geheime modus: vingerafdruk werkt nu via standaard Android BiometricPrompt zonder Knox SDP — stabiel op Samsung en andere toestellen.\n"
                + "• AI-samenvatting op alle telefoons: de knop «Browse-assistent» (Gemini AI) is nu ook beschikbaar op Pixel, OnePlus, Xiaomi, enz.\n"
                + "• Slimme herstartknop: ontwijkt automatisch knoppen in de bovenbalk (Gereed, Resetten, Zoeken) door naar links of rechtsonder te schuiven.",
                "Oplossingen & stabiliteit",
                "• Extensie-popups (Bitwarden, enz.): crash bij toetsenbord-invoer in extensie-popups verholpen.\n"
                + "• Home-knop & Startpagina: lang indrukken op Home hersteld en regionale omleiding van nieuwe tabbladen naar google.com verwijderd.",
                "",
                "",
                "Bedankt, begrepen",
                "↓ Scroll naar beneden"
            };
            case "sw": return new String[] {
                "Mapya katika SBrowserX",
                "★ Muhimu katika v1.4.0",
                "Alama ya vidole imara katika Hali ya Siri (Incognito), marekebisho ya kufunga ghafla kwenye viendelezi na kitufe cha kuanzisha upya kisichofunika vifungo.",
                "Maboresho na Vipengele",
                "• Hali ya Siri: kuingia kwa kidole sasa kunatumia Android BiometricPrompt bila Knox SDP — imara kwenye Samsung na simu nyingine.\n"
                + "• Muhtasari wa AI kwenye simu zote: kitufe cha «Msaidizi wa kuvinjari» (Gemini AI) sasa kinapatikana pia kwenye Pixel, OnePlus, Xiaomi n.k.\n"
                + "• Kitufe mahiri cha kuanzisha upya: kinaepuka vitufe vya juu kiotomatiki kwa kusogea kushoto au chini kulia.",
                "Marekebisho na Uthabiti",
                "• Madirisha ya viendelezi (Bitwarden n.k.): hitilafu ya kufunga programu wakati wa kuandika kwa kibodi imerekebishwa.\n"
                + "• Kitufe cha Nyumbani na Ukurasa wa Mwanzo: kubonyeza kwa muda mrefu kumerekebishwa na uelekezaji wa google.com umeondolewa.",
                "",
                "",
                "Asante, nimeelewa",
                "↓ Sogeza hadi chini"
            };
            default: return new String[] {
                "What's New in SBrowserX",
                "★ Highlights in v1.4.0",
                "Rock-solid Secret Mode (Incognito) biometrics, fixed keyboard crashes inside extension popups, and a smart collision-free Restart button.",
                "Improvements & Features",
                "• Secret Mode (Incognito): fingerprint unlock now uses standard Android BiometricPrompt with Knox SDP bypassed — works smoothly on both Samsung and non-Samsung devices.\n"
                + "• AI Summary on all phones: the Browsing Assist (Gemini AI) button is now unlocked on Pixel, OnePlus, Xiaomi, and other non-Samsung devices.\n"
                + "• Smart Restart button: automatically detects overlapping toolbar actions (Done, Reset, Search) and shifts left or drops to bottom-right.",
                "Fixes & Stability",
                "• Extension popups (Bitwarden, etc.): fixed a crash triggered by keyboard/IME input inside extension action popups.\n"
                + "• Home button & Start Page: fixed long-press Home reliability and removed the regional google.com New Tab redirect.",
                "",
                "",
                "Thanks, got it",
                "↓ Scroll to the bottom"
            };
        }
    }

    public static String[] getWelcome(String lang) {
        if (lang == null) lang = "en";
        switch (lang) {
            case "ru": return new String[] {
                "Добро пожаловать в SBrowserX",
                "★ С чего начать",
                "Всё управление модом собрано в «Настройки → SBrowserX». Загляните туда, чтобы в пару кликов поставить расширения, настроить стартовую страницу, оформление, приватность и резервное копирование.",
                "Что уже доступно",
                "• Расширения Chrome: установка из Chrome Web Store, файлов .crx/.zip или из готового набора (uBlock Origin Lite, Bitwarden, SponsorBlock и др.).\n"
                + "• Переводчик и ИИ-сводка: 5 движков перевода (Bing, Google, Яндекс, DeepL, Gemini AI) и быстрая сводка страниц в «Ассистенте по просмотру».\n"
                + "• Внешний вид и стартовая страница: настраиваемые ярлыки, обои, погода, AMOLED-тема и «живой» блюр панелей One UI.\n"
                + "• Приватность и свобода: защита от утечек WebRTC/Fingerprint, Секретный режим с биометрией и поддержка сторонних менеджеров паролей.",
                "Совет: подключите Gemini API",
                "Откройте «Настройки → Ассистент по просмотру», создайте бесплатный ключ Gemini API в Google AI Studio и вставьте его в поле ключа — это сразу включит ИИ-сводку страниц и самый точный контекстный перевод.",
                "",
                "",
                "Понятно, поехали!",
                "↓ Пролистайте список до конца"
            };
            case "uk": return new String[] {
                "Ласкаво просимо до SBrowserX",
                "★ З чого почати",
                "Усе керування модом зібрано в «Налаштування → SBrowserX». Зазирніть туди, щоб у кілька кліків встановити розширення, налаштувати стартову сторінку, вигляд, приватність і резервне копіювання.",
                "Що вже доступно",
                "• Розширення Chrome: встановлення з Chrome Web Store, файлів .crx/.zip або готового набору (uBlock Origin Lite, Bitwarden, SponsorBlock тощо).\n"
                + "• Перекладач і ШІ-зведення: 5 рушіїв перекладу (Bing, Google, Яндекс, DeepL, Gemini AI) та швидке зведення сторінок в «Асистенті перегляду».\n"
                + "• Вигляд і стартова сторінка: ярлики, шпалери, погода, AMOLED-тема та «живий» блюр панелей One UI.\n"
                + "• Приватність і свобода: захист від витоків WebRTC/Fingerprint, Секретний режим із біометрією та підтримка сторонніх менеджерів паролів.",
                "Порада: підключіть Gemini API",
                "Відкрийте «Налаштування → Асистент перегляду», створіть безкоштовний ключ Gemini API в Google AI Studio та вставте його — це одразу ввімкне ШІ-зведення сторінок і найточніший контекстний переклад.",
                "",
                "",
                "Зрозуміло, поїхали!",
                "↓ Прогорніть список до кінця"
            };
            case "es": return new String[] {
                "Bienvenido a SBrowserX",
                "★ Por dónde empezar",
                "Todos los ajustes del mod están en «Ajustes → SBrowserX». Ábrelo para instalar extensiones, personalizar la página de inicio, la apariencia, la privacidad y las copias de seguridad.",
                "Qué incluye",
                "• Extensiones de Chrome: instala desde Chrome Web Store, archivos .crx/.zip o el paquete recomendado (uBlock Origin Lite, Bitwarden, SponsorBlock, etc.).\n"
                + "• Traductor y Resumen IA: 5 motores de traducción (Bing, Google, Yandex, DeepL, Gemini AI) y resumen instantáneo en «Asistente de navegación».\n"
                + "• Diseño y Página de inicio: accesos directos, fondos, clima, tema AMOLED y desenfoque vivo de One UI.\n"
                + "• Privacidad y libertad: protección WebRTC/Fingerprint, Modo Secreto con biometría y gestores de contraseñas de terceros.",
                "Consejo: conecta Gemini API",
                "Abre «Ajustes → Asistente de navegación» y pega una clave gratuita de Gemini API de Google AI Studio para activar el resumen IA y la traducción más precisa.",
                "",
                "",
                "¡Entendido, vamos!",
                "↓ Desplázate hasta el final"
            };
            case "pt": return new String[] {
                "Bem-vindo ao SBrowserX",
                "★ Por onde começar",
                "Todos os controles do mod ficam em «Configurações → SBrowserX». Abra lá para instalar extensões, personalizar a página inicial, visual, privacidade e backup.",
                "O que já está disponível",
                "• Extensões do Chrome: instale pela Chrome Web Store, arquivos .crx/.zip ou pelo pacote recomendado (uBlock Origin Lite, Bitwarden, SponsorBlock, etc.).\n"
                + "• Tradutor e Resumo IA: 5 motores (Bing, Google, Yandex, DeepL, Gemini AI) e resumo rápido no «Assistente de navegação».\n"
                + "• Visual e Página Inicial: atalhos, papéis de parede, clima, tema AMOLED e desfoque ao vivo da One UI.\n"
                + "• Privacidade e liberdade: proteção WebRTC/Fingerprint, Modo Secreto com biometria e gerenciadores de senhas de terceiros.",
                "Dica: conecte a Gemini API",
                "Abra «Configurações → Assistente de navegação» e cole uma chave gratuita da Gemini API do Google AI Studio para ativar resumos com IA e tradução contextual.",
                "",
                "",
                "Entendi, vamos lá!",
                "↓ Role até o final"
            };
            case "de": return new String[] {
                "Willkommen bei SBrowserX",
                "★ Erste Schritte",
                "Alle Mod-Optionen findest du unter «Einstellungen → SBrowserX». Dort kannst du Erweiterungen installieren sowie Startseite, Design, Datenschutz und Backups einrichten.",
                "Das ist bereits dabei",
                "• Chrome-Erweiterungen: Installation aus dem Chrome Web Store, aus .crx/.zip-Dateien oder dem empfohlenen Paket (uBlock Origin Lite, Bitwarden, SponsorBlock usw.).\n"
                + "• Übersetzer & KI-Zusammenfassung: 5 Engines (Bing, Google, Yandex, DeepL, Gemini AI) plus Seitenzusammenfassung im «Browsing-Assistent».\n"
                + "• Design & Startseite: anpassbare Verknüpfungen, Hintergrundbilder, Wetter, AMOLED-Theme und Live-Blur im One-UI-Stil.\n"
                + "• Datenschutz & Freiheit: WebRTC-/Fingerprint-Schutz, Geheimer Modus mit Biometrie und Drittanbieter-Passwortmanager.",
                "Tipp: Gemini API verbinden",
                "Öffne «Einstellungen → Browsing-Assistent» und füge einen kostenlosen Gemini-API-Key aus Google AI Studio ein — für KI-Zusammenfassungen und präzise Übersetzungen.",
                "",
                "",
                "Verstanden, los geht's!",
                "↓ Bis zum Ende scrollen"
            };
            case "fr": return new String[] {
                "Bienvenue dans SBrowserX",
                "★ Pour bien démarrer",
                "Tous les réglages du mod sont regroupés dans « Paramètres → SBrowserX » : extensions, page de démarrage, apparence, confidentialité et sauvegarde.",
                "Ce qui est déjà inclus",
                "• Extensions Chrome : installez depuis le Chrome Web Store, des fichiers .crx/.zip ou le pack recommandé (uBlock Origin Lite, Bitwarden, SponsorBlock, etc.).\n"
                + "• Traducteur et Résumé IA : 5 moteurs (Bing, Google, Yandex, DeepL, Gemini AI) et résumé instantané dans « Assistant de navigation ».\n"
                + "• Design et Page de démarrage : raccourcis, fonds d'écran, météo, thème AMOLED et flou dynamique One UI.\n"
                + "• Confidentialité et liberté : protection WebRTC/Fingerprint, Mode Secret biométrique et gestionnaires de mots de passe tiers.",
                "Astuce : connectez Gemini API",
                "Ouvrez « Paramètres → Assistant de navigation » et collez une clé gratuite Gemini API (Google AI Studio) pour activer les résumés IA et la traduction contextuelle.",
                "",
                "",
                "Compris, c'est parti !",
                "↓ Faites défiler jusqu'en bas"
            };
            case "it": return new String[] {
                "Benvenuto in SBrowserX",
                "★ Da dove iniziare",
                "Tutte le opzioni della mod sono in «Impostazioni → SBrowserX»: installa estensioni e personalizza pagina iniziale, aspetto, privacy e backup.",
                "Cosa è già disponibile",
                "• Estensioni Chrome: dal Chrome Web Store, da file .crx/.zip o dal pacchetto consigliato (uBlock Origin Lite, Bitwarden, SponsorBlock, ecc.).\n"
                + "• Traduttore e Riepilogo IA: 5 motori (Bing, Google, Yandex, DeepL, Gemini AI) e riepilogo rapido nell'«Assistente navigazione».\n"
                + "• Aspetto e Pagina iniziale: scorciatoie, sfondi, meteo, tema AMOLED e sfocatura live One UI.\n"
                + "• Privacy e libertà: protezione WebRTC/Fingerprint, Modalità Segreta con biometria e gestori di password di terze parti.",
                "Consiglio: collega Gemini API",
                "Apri «Impostazioni → Assistente navigazione» e inserisci una chiave gratuita Gemini API di Google AI Studio per attivare i riepiloghi IA e la traduzione contestuale.",
                "",
                "",
                "Capito, iniziamo!",
                "↓ Scorri fino in fondo"
            };
            case "pl": return new String[] {
                "Witaj w SBrowserX",
                "★ Od czego zacząć",
                "Wszystkie ustawienia moda znajdziesz w «Ustawienia → SBrowserX» — zainstalujesz tam rozszerzenia, ustawisz stronę startową, wygląd, prywatność i kopie zapasowe.",
                "Co jest już dostępne",
                "• Rozszerzenia Chrome: z Chrome Web Store, plików .crx/.zip lub gotowego pakietu (uBlock Origin Lite, Bitwarden, SponsorBlock itp.).\n"
                + "• Tłumacz i Podsumowanie AI: 5 silników (Bing, Google, Yandex, DeepL, Gemini AI) oraz szybkie podsumowania w «Asystencie przeglądania».\n"
                + "• Wygląd i Strona startowa: skróty, tapety, pogoda, motyw AMOLED i żywy blur pasków One UI.\n"
                + "• Prywatność i swoboda: ochrona WebRTC/Fingerprint, Tryb Tajny z biometrią oraz zewnętrzne menedżery haseł.",
                "Wskazówka: podłącz Gemini API",
                "Otwórz «Ustawienia → Asystent przeglądania» i wklej darmowy klucz Gemini API z Google AI Studio — włączy to podsumowania AI i najdokładniejsze tłumaczenie.",
                "",
                "",
                "Jasne, zaczynajmy!",
                "↓ Przewiń do końca"
            };
            case "tr": return new String[] {
                "SBrowserX'e Hoş Geldiniz",
                "★ Nereden Başlamalı",
                "Tüm mod ayarları «Ayarlar → SBrowserX» altındadır. Eklentileri kurmak, başlangıç sayfasını, görünümü, gizliliği ve yedeklemeyi ayarlamak için oraya göz atın.",
                "Neler Sunuyor",
                "• Chrome Eklentileri: Chrome Web Mağazası'ndan, .crx/.zip dosyalarından veya önerilen paketten kurulum (uBlock Origin Lite, Bitwarden, SponsorBlock vb.).\n"
                + "• Çevirmen ve Yapay Zeka Özeti: 5 çeviri motoru (Bing, Google, Yandex, DeepL, Gemini AI) ve «Tarama asistanı» ile hızlı sayfa özeti.\n"
                + "• Görünüm ve Başlangıç Sayfası: kısayollar, duvar kağıtları, hava durumu, AMOLED tema ve canlı One UI bulanıklığı.\n"
                + "• Gizlilik ve Özgürlük: WebRTC/Fingerprint koruması, biyometrik Gizli Mod ve üçüncü taraf şifre yöneticileri.",
                "İpucu: Gemini API Bağlayın",
                "«Ayarlar → Tarama asistanı» bölümüne Google AI Studio'dan ücretsiz Gemini API anahtarı ekleyerek yapay zeka özetlerini ve en doğru çeviriyi açın.",
                "",
                "",
                "Anladım, başlayalım!",
                "↓ Listenin sonuna kaydırın"
            };
            case "ar": return new String[] {
                "مرحباً بك في SBrowserX",
                "★ من أين تبدأ",
                "تجد جميع إعدادات التعديل في «الإعدادات ← SBrowserX» لتثبيت الإضافات وتخصيص صفحة البداية والمظهر والخصوصية والنسخ الاحتياطي.",
                "ما المتوفر حالياً",
                "• إضافات Chrome: التثبيت من سوق Chrome أو ملفات .crx/.zip أو الحزمة المقترحة (uBlock Origin Lite وBitwarden وSponsorBlock وغيرها).\n"
                + "• المترجم وملخص الذكاء الاصطناعي: 5 محركات ترجمة (Bing وGoogle وYandex وDeepL وGemini AI) وملخص فوري في «مساعد التصفح».\n"
                + "• المظهر وصفحة البداية: اختصارات وخلفيات وطقس ووضع AMOLED وتضبيب One UI الحي.\n"
                + "• الخصوصية والحرية: حماية WebRTC/Fingerprint والوضع السري بالبصمة ودعم مديري كلمات المرور الخارجيين.",
                "نصيحة: اربط Gemini API",
                "افتح «الإعدادات ← مساعد التصفح» وأضف مفتاح Gemini API مجانياً من Google AI Studio لتفعيل ملخصات الذكاء الاصطناعي وأدق ترجمة سياقية.",
                "",
                "",
                "فهمت، لنبدأ!",
                "↓ مرر إلى الأسفل للمتابعة"
            };
            case "fa": return new String[] {
                "به SBrowserX خوش آمدید",
                "★ از کجا شروع کنیم",
                "همه تنظیمات مود در «تنظیمات ← SBrowserX» قرار دارد؛ از آنجا می‌توانید افزونه نصب کنید و صفحه شروع، ظاهر، حریم خصوصی و پشتیبان‌گیری را تنظیم کنید.",
                "امکانات آماده استفاده",
                "• افزونه‌های کروم: نصب از Chrome Web Store، فایل‌های .crx/.zip یا بسته پیشنهادی (uBlock Origin Lite، Bitwarden، SponsorBlock و غیره).\n"
                + "• مترجم و خلاصه هوش مصنوعی: ۵ موتور ترجمه (Bing، Google، Yandex، DeepL، Gemini AI) و خلاصه‌سازی صفحات در «دستیار مرور».\n"
                + "• ظاهر و صفحه شروع: میانبرها، پس‌زمینه، آب‌وهوا، تم AMOLED و محوشدگی زنده One UI.\n"
                + "• حریم خصوصی و آزادی: محافظت WebRTC/Fingerprint، حالت محرمانه با بیومتریک و پشتیبانی از مدیران رمز عبور.",
                "نکته: اتصال Gemini API",
                "در «تنظیمات ← دستیار مرور» یک کلید رایگان Gemini API از Google AI Studio وارد کنید تا خلاصه هوش مصنوعی و دقیق‌ترین ترجمه فعال شود.",
                "",
                "",
                "متوجه شدم، بزن بریم!",
                "↓ تا انتها اسکرول کنید"
            };
            case "hi": return new String[] {
                "SBrowserX में आपका स्वागत है",
                "★ शुरुआत कहाँ से करें",
                "मॉड की सभी सेटिंग्स «सेटिंग्स → SBrowserX» में हैं — वहाँ से एक्सटेंशन इंस्टॉल करें और स्टार्ट पेज, थीम, प्राइवेसी व बैकअप सेट करें।",
                "पहले से क्या उपलब्ध है",
                "• Chrome एक्सटेंशन: Chrome Web Store, .crx/.zip फ़ाइलों या सुझाए गए पैक से इंस्टॉल करें (uBlock Origin Lite, Bitwarden, SponsorBlock आदि)।\n"
                + "• ट्रांसलेटर और AI सारांश: 5 अनुवाद इंजन (Bing, Google, Yandex, DeepL, Gemini AI) और «ब्राउज़िंग असिस्ट» में तेज़ पेज सारांश।\n"
                + "• लुक और स्टार्ट पेज: कस्टम शॉर्टकट, वॉलपेपर, मौसम, AMOLED थीम और लाइव One UI ब्लर।\n"
                + "• प्राइवेसी और आज़ादी: WebRTC/Fingerprint सुरक्षा, बायोमेट्रिक सीक्रेट मोड और थर्ड-पार्टी पासवर्ड मैनेजर सपोर्ट।",
                "सुझाव: Gemini API जोड़ें",
                "«सेटिंग्स → ब्राउज़िंग असिस्ट» में Google AI Studio से मुफ़्त Gemini API कुंजी जोड़ें — इससे AI सारांश और सबसे सटीक अनुवाद चालू हो जाएगा।",
                "",
                "",
                "समझ गया, शुरू करें!",
                "↓ नीचे तक स्क्रॉल करें"
            };
            case "zh": return new String[] {
                "欢迎使用 SBrowserX",
                "★ 快速上手",
                "所有增强设置均集中在「设置 → SBrowserX」中，可一键安装扩展、定制起始页、外观、隐私保护及备份恢复。",
                "核心功能一览",
                "• Chrome 扩展：支持从 Chrome 网上应用店、.crx/.zip 文件或推荐套装安装（uBlock Origin Lite、Bitwarden、SponsorBlock 等）。\n"
                + "• 智能翻译与 AI 摘要：内置 5 大翻译引擎（Bing、Google、Yandex、DeepL、Gemini AI）及「浏览助手」网页摘要。\n"
                + "• 外观与起始页：自定义快捷方式、壁纸、天气、AMOLED 纯黑主题与 One UI 实时毛玻璃模糊。\n"
                + "• 隐私与自由：WebRTC/指纹防护、保密模式指纹解锁与第三方密码管理器自动填充。",
                "小贴士：配置 Gemini API",
                "前往「设置 → 浏览助手」填入 Google AI Studio 免费获取的 Gemini API 密钥，即可开启 AI 网页摘要与高质量上下文翻译。",
                "",
                "",
                "明白了，开始体验！",
                "↓ 请滚动到底部"
            };
            case "ja": return new String[] {
                "SBrowserX へようこそ",
                "★ はじめに",
                "すべてのカスタム設定は「設定 → SBrowserX」にまとまっています。拡張機能の導入、スタートページ、外観、プライバシー、バックアップをすぐに調整できます。",
                "利用できる主な機能",
                "• Chrome 拡張機能：Chrome ウェブストア、.crx/.zip、おすすめパック（uBlock Origin Lite、Bitwarden、SponsorBlock 等）から導入可能。\n"
                + "• 翻訳と AI 要約：5 つの翻訳エンジン（Bing・Google・Yandex・DeepL・Gemini AI）と「ブラウジングアシスト」の AI 要約。\n"
                + "• 外観とスタートページ：ショートカット、壁紙、天気、AMOLED テーマ、One UI のライブブラー。\n"
                + "• プライバシーと自由度：WebRTC/フィンガープリント保護、生体認証シークレットモード、外部パスワードマネージャー対応。",
                "ヒント：Gemini API を設定",
                "「設定 → ブラウジングアシスト」に Google AI Studio の無料 Gemini API キーを設定すると、AI 要約と高精度な文脈翻訳が使えます。",
                "",
                "",
                "OK、始めましょう！",
                "↓ 最後までスクロールしてください"
            };
            case "ko": return new String[] {
                "SBrowserX에 오신 것을 환영합니다",
                "★ 시작하기",
                "모든 모드 설정은 «설정 → SBrowserX»에 모여 있습니다. 확장 프로그램 설치, 시작 페이지, 디자인, 개인정보 보호, 백업을 간편하게 설정해 보세요.",
                "주요 기능",
                "• Chrome 확장 프로그램: Chrome 웹 스토어, .crx/.zip 파일 또는 추천 팩(uBlock Origin Lite, Bitwarden, SponsorBlock 등)으로 설치.\n"
                + "• 번역기 및 AI 요약: 5개 번역 엔진(Bing, Google, Yandex, DeepL, Gemini AI)과 «브라우징 어시스트» AI 페이지 요약.\n"
                + "• 디자인 및 시작 페이지: 바로가기, 배경화면, 날씨, AMOLED 테마, One UI 실시간 블러.\n"
                + "• 개인정보 및 자유도: WebRTC/지문 추적 방지, 생체 인증 비밀 모드, 타사 비밀번호 관리자 지원.",
                "팁: Gemini API 연결",
                "«설정 → 브라우징 어시스트»에 Google AI Studio의 무료 Gemini API 키를 입력하면 AI 요약과 가장 정확한 문맥 번역이 활성화됩니다.",
                "",
                "",
                "알겠어요, 시작할게요!",
                "↓ 끝까지 스크롤하세요"
            };
            case "id": return new String[] {
                "Selamat Datang di SBrowserX",
                "★ Mulai dari Sini",
                "Semua pengaturan mod ada di «Pengaturan → SBrowserX». Buka di sana untuk memasang ekstensi, mengatur halaman awal, tampilan, privasi, dan cadangan.",
                "Yang Sudah Tersedia",
                "• Ekstensi Chrome: pasang dari Chrome Web Store, file .crx/.zip, atau paket rekomendasi (uBlock Origin Lite, Bitwarden, SponsorBlock, dll.).\n"
                + "• Penerjemah & Ringkasan AI: 5 mesin terjemahan (Bing, Google, Yandex, DeepL, Gemini AI) dan ringkasan cepat di «Asisten Penjelajahan».\n"
                + "• Tampilan & Halaman Awal: pintasan, wallpaper, cuaca, tema AMOLED, dan blur langsung One UI.\n"
                + "• Privasi & Kebebasan: perlindungan WebRTC/Fingerprint, Mode Rahasia biometrik, dan pengelola sandi pihak ketiga.",
                "Tips: Hubungkan Gemini API",
                "Buka «Pengaturan → Asisten Penjelajahan» dan tempel kunci Gemini API gratis dari Google AI Studio untuk mengaktifkan ringkasan AI dan terjemahan paling akurat.",
                "",
                "",
                "Paham, ayo mulai!",
                "↓ Gulir ke bagian bawah"
            };
            case "vi": return new String[] {
                "Chào mừng đến với SBrowserX",
                "★ Bắt đầu nhanh",
                "Mọi tùy chỉnh mod đều nằm tại «Cài đặt → SBrowserX». Mở mục này để cài tiện ích mở rộng, tùy biến trang bắt đầu, giao diện, quyền riêng tư và sao lưu.",
                "Tính năng nổi bật",
                "• Tiện ích Chrome: cài từ Chrome Web Store, tệp .crx/.zip hoặc bộ đề xuất (uBlock Origin Lite, Bitwarden, SponsorBlock, v.v.).\n"
                + "• Dịch thuật & Tóm tắt AI: 5 công cụ dịch (Bing, Google, Yandex, DeepL, Gemini AI) và tóm tắt nhanh trong «Trợ lý duyệt web».\n"
                + "• Giao diện & Trang bắt đầu: lối tắt, hình nền, thời tiết, giao diện AMOLED và làm mờ động One UI.\n"
                + "• Riêng tư & Tự do: chống rò rỉ WebRTC/Fingerprint, Chế độ Bí mật sinh trắc học và hỗ trợ trình quản lý mật khẩu bên thứ ba.",
                "Mẹo: Kết nối Gemini API",
                "Vào «Cài đặt → Trợ lý duyệt web» và dán khóa Gemini API miễn phí từ Google AI Studio để bật tóm tắt AI và bản dịch theo ngữ cảnh chính xác nhất.",
                "",
                "",
                "Hiểu rồi, bắt đầu thôi!",
                "↓ Cuộn xuống cuối danh sách"
            };
            case "nl": return new String[] {
                "Welkom bij SBrowserX",
                "★ Zo begin je",
                "Alle mod-instellingen staan in «Instellingen → SBrowserX». Daar installeer je extensies en pas je startpagina, uiterlijk, privacy en back-ups aan.",
                "Wat er al klaarstaat",
                "• Chrome-extensies: installeer via de Chrome Web Store, .crx/.zip-bestanden of het aanbevolen pakket (uBlock Origin Lite, Bitwarden, SponsorBlock, enz.).\n"
                + "• Vertaler & AI-samenvatting: 5 vertaalengines (Bing, Google, Yandex, DeepL, Gemini AI) plus paginasamenvatting in «Browse-assistent».\n"
                + "• Uiterlijk & Startpagina: snelkoppelingen, achtergronden, weer, AMOLED-thema en live One UI-blur.\n"
                + "• Privacy & vrijheid: WebRTC-/Fingerprint-bescherming, Geheime modus met biometrie en externe wachtwoordmanagers.",
                "Tip: koppel Gemini API",
                "Open «Instellingen → Browse-assistent» en plak een gratis Gemini API-sleutel uit Google AI Studio voor AI-samenvattingen en nauwkeurige vertalingen.",
                "",
                "",
                "Begrepen, aan de slag!",
                "↓ Scroll naar beneden"
            };
            case "sw": return new String[] {
                "Karibu kwenye SBrowserX",
                "★ Mahali pa Kuanza",
                "Mipangilio yote ya mod iko kwenye «Mipangilio → SBrowserX». Fungua hapo ili kuweka viendelezi, kurekebisha ukurasa wa mwanzo, muonekano, faragha na nakala rudufu.",
                "Vilivyopo Tayari",
                "• Viendelezi vya Chrome: sakinisha kutoka Chrome Web Store, faili za .crx/.zip au kifurushi kilichopendekezwa (uBlock Origin Lite, Bitwarden, SponsorBlock n.k.).\n"
                + "• Mtafsiri na Muhtasari wa AI: injini 5 za tafsiri (Bing, Google, Yandex, DeepL, Gemini AI) na muhtasari wa haraka kwenye «Msaidizi wa kuvinjari».\n"
                + "• Muonekano na Ukurasa wa Mwanzo: njia za mkato, picha za usuli, hali ya hewa, mandhari ya AMOLED na ukungu wa moja kwa moja wa One UI.\n"
                + "• Faragha na Uhuru: ulinzi wa WebRTC/Fingerprint, Hali ya Siri kwa alama ya vidole na meneja wa nywila wa nje.",
                "Kidokezo: Unganisha Gemini API",
                "Fungua «Mipangilio → Msaidizi wa kuvinjari» na uweke ufunguo wa bure wa Gemini API kutoka Google AI Studio ili kuwasha muhtasari wa AI na tafsiri sahihi.",
                "",
                "",
                "Nimeelewa, tuanze!",
                "↓ Sogeza hadi chini"
            };
            default: return new String[] {
                "Welcome to SBrowserX",
                "★ Where to Start",
                "All mod controls live in Settings → SBrowserX. Open it to install extensions, customize your Start Page, appearance, privacy, and backups in a few taps.",
                "What's Already Built In",
                "• Chrome Extensions: install from the Chrome Web Store, .crx/.zip files, or the curated starter pack (uBlock Origin Lite, Bitwarden, SponsorBlock, etc.).\n"
                + "• Translator & AI Summary: 5 translation engines (Bing, Google, Yandex, DeepL, Gemini AI) plus instant page summaries in Browsing Assist.\n"
                + "• Look & Start Page: custom shortcuts, wallpapers, weather, AMOLED theme, and live One UI frosted blur.\n"
                + "• Privacy & Freedom: WebRTC/Fingerprint protection, biometric Secret Mode, and third-party password manager autofill.",
                "Tip: Connect Gemini API",
                "Open Settings → Browsing Assist and paste a free Gemini API key from Google AI Studio to unlock AI page summaries and the most accurate context-aware translations.",
                "",
                "",
                "Got it, let's go!",
                "↓ Scroll to the bottom"
            };
        }
    }
}
