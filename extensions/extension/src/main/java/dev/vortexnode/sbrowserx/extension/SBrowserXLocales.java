package dev.vortexnode.sbrowserx.extension;

import java.util.HashMap;
import java.util.Map;

/**
 * Natural, idiomatic UI translations for 20 major world languages
 * (EN, RU, UK, ES, PT, DE, FR, IT, PL, TR, AR, FA, HI, ZH, JA, KO, ID, VI, NL, SW).
 */
final class SBrowserXLocales {
    private SBrowserXLocales() {}

    static void applyAll(Map<String, Map<String, String>> catalog) {
        // --- 5 new full languages: Persian (fa), Indonesian (id), Vietnamese (vi), Dutch (nl), Swahili (sw) ---
        putCore(catalog, "fa",
            "SBrowserX", "افزونه‌ها", "حریم خصوصی و امنیت", "ویژگی‌ها", "ظاهر و رابط کاربری", "نشانگر ویژگی‌های جدید",
            "نصب افزونه از فایل (.crx / .zip)", "نصب مستقیم فایل محلی .crx یا .zip",
            "بسته افزونه‌های پیشنهادی (6)", "uBlock Origin Lite، SponsorBlock، VOT، Fingerprint Defender، AdGuard، Bitwarden",
            "مدیریت افزونه‌ها", "باز کردن Chrome Web Store",
            "در حال نصب افزونه...", "افزونه نصب شد", "خطا در نصب: ", "موتور افزونه‌ها آماده نیست", "نصب",
            "سیاست IP در WebRTC", "پیش‌فرض (بدون محدودیت)", "فقط IP عمومی و خصوصی", "فقط IP عمومی", "غیرفعال کردن UDP بدون پروکسی (توصیه می‌شود)",
            "محافظت در برابر اثر انگشت", "محافظت بومی WebAudio و صفحه در برابر ردیابی (نیاز به راه‌اندازی مجدد)",
            "مترجم داخلی صفحه", "ترجمه فوری صفحات با Google، Bing، Yandex، DeepL یا Gemini",
            "دانلودر خارجی", "ارسال لینک‌های دانلود به ADM، 1DM و سایر دانلودرها", "باز کردن با دانلودر...",
            "بستن خودکار برگه‌های غیرفعال پس از ۱ روز", "افزودن گزینه «پس از ۱ روز» به تنظیمات بستن خودکار برگه‌ها",
            "حالت تاریک AMOLED", "پس‌زمینه کاملاً مشکی (#000000) برای نمایشگرهای OLED",
            "نوار پایین نیمه‌شفاف و تار", "افکت شیشه مات One UI برای نوار پایین",
            "مدت نمایش نشانگر ویژگی جدید", "خاموش", "%d روز",
            "علامت‌گذاری همه به‌عنوان دیده‌شده", "همه نشانگرها پاک شدند",
            "راه‌اندازی مجدد مرورگر", "اعمال تغییرات موتور و رابط کاربری", "برای اعمال تغییر نیاز به راه‌اندازی مجدد است", "راه‌اندازی مجدد", "بعداً");

        putCore(catalog, "id",
            "SBrowserX", "Ekstensi", "Privasi & Keamanan", "Fitur", "Tampilan & Antarmuka", "Sorotan fitur baru",
            "Pasang ekstensi dari file (.crx / .zip)", "Instalasi langsung paket .crx atau .zip lokal",
            "Paket ekstensi rekomendasi (6)", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard, Bitwarden",
            "Kelola ekstensi", "Buka Chrome Web Store",
            "Memasang ekstensi...", "Ekstensi terpasang", "Gagal memasang: ", "Mesin ekstensi belum siap", "Pasang",
            "Kebijakan IP WebRTC", "Default (tanpa batas)", "IP publik & privat saja", "IP publik saja", "Nonaktifkan UDP non-proksi (disarankan)",
            "Perlindungan sidik jari", "Perlindungan bawaan WebAudio & layar dari pelacakan (perlu mulai ulang)",
            "Penerjemah halaman bawaan", "Terjemahan instan lewat Google, Bing, Yandex, DeepL, atau Gemini",
            "Pengunduh eksternal", "Teruskan unduhan ke ADM, 1DM, atau pengunduh lain", "Buka dengan pengunduh...",
            "Tutup tab tidak aktif setelah 1 hari", "Menambahkan opsi 1 hari di pengaturan Tutup tab otomatis",
            "Hitam pekat AMOLED", "Latar hitam murni (#000000) khusus layar OLED",
            "Bilah bawah kaca buram", "Efek kaca buram One UI pada bilah bawah",
            "Durasi titik fitur baru", "Mati", "%d hari",
            "Tandai semua sudah dilihat", "Semua titik fitur baru dihapus",
            "Mulai ulang peramban", "Terapkan perubahan mesin dan tampilan", "Perlu mulai ulang untuk menerapkan perubahan", "Mulai ulang", "Nanti");

        putCore(catalog, "vi",
            "SBrowserX", "Tiện ích mở rộng", "Quyền riêng tư & Bảo mật", "Tính năng", "Giao diện", "Điểm nổi bật tính năng mới",
            "Cài tiện ích từ tệp (.crx / .zip)", "Cài trực tiếp gói .crx hoặc .zip cục bộ",
            "Bộ tiện ích đề xuất (6)", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard, Bitwarden",
            "Quản lý tiện ích", "Mở Chrome Web Store",
            "Đang cài đặt tiện ích...", "Đã cài tiện ích", "Cài đặt thất bại: ", "Trình tiện ích chưa sẵn sàng", "Cài đặt",
            "Chính sách IP WebRTC", "Mặc định (không giới hạn)", "Chỉ IP công khai & nội bộ", "Chỉ IP công khai", "Tắt UDP không qua proxy (khuyên dùng)",
            "Chống lấy dấu vân tay", "Bảo vệ WebAudio và màn hình khỏi theo dõi (cần khởi động lại)",
            "Dịch trang tích hợp", "Dịch trang nhanh qua Google, Bing, Yandex, DeepL hoặc Gemini",
            "Trình tải xuống ngoài", "Chuyển tệp tải về sang ADM, 1DM hoặc trình tải khác", "Mở bằng trình tải...",
            "Tự đóng thẻ không dùng sau 1 ngày", "Thêm tùy chọn «Sau 1 ngày» trong mục tự đóng thẻ",
            "Chế độ tối AMOLED", "Nền đen thuần (#000000) cho màn hình OLED",
            "Thanh dưới kính mờ", "Hiệu ứng kính mờ One UI cho thanh điều hướng dưới",
            "Thời gian hiện chấm tính năng mới", "Tắt", "%d ngày",
            "Đánh dấu tất cả đã xem", "Đã xóa toàn bộ chấm thông báo",
            "Khởi động lại trình duyệt", "Áp dụng thay đổi giao diện và lõi trình duyệt", "Cần khởi động lại để áp dụng", "Khởi động lại", "Để sau");

        putCore(catalog, "nl",
            "SBrowserX", "Extensies", "Privacy & beveiliging", "Functies", "Weergave & interface", "Nieuwe functies markeren",
            "Extensie installeren vanuit bestand (.crx / .zip)", "Lokaal .crx- of .zip-pakket direct installeren",
            "Aanbevolen extensiepakket (6)", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard, Bitwarden",
            "Extensies beheren", "Chrome Web Store openen",
            "Extensie installeren...", "Extensie geïnstalleerd", "Installatie mislukt: ", "Extensie-engine is nog niet gereed", "Installeren",
            "WebRTC IP-beleid", "Standaard (onbeperkt)", "Alleen publieke en lokale IP's", "Alleen publiek IP", "Niet-geproxyde UDP uitschakelen (aanbevolen)",
            "Anti-fingerprinting", "Ingebouwde WebAudio- en schermbescherming tegen tracking (herstart vereist)",
            "Ingebouwde paginavertaler", "Direct vertalen via Google, Bing, Yandex, DeepL of Gemini",
            "Externe downloader", "Downloads doorsturen naar ADM, 1DM of een andere downloader", "Openen met downloader...",
            "Inactieve tabbladen sluiten na 1 dag", "Voegt de optie «Na 1 dag» toe aan automatisch sluiten",
            "AMOLED-zwart", "Echt zwart (#000000) voor OLED-schermen",
            "Matglazen onderbalk", "One UI-vervagingseffect voor de onderste balk",
            "Duur van nieuwe-functiestip", "Uit", "%d dagen",
            "Alles als bekeken markeren", "Alle stippen gewist",
            "Browser herstarten", "Engine- en interfacewijzigingen toepassen", "Herstart vereist om wijzigingen toe te passen", "Herstarten", "Later");

        putCore(catalog, "sw",
            "SBrowserX", "Viendelezi", "Faragha na Usalama", "Vipengele", "Mwonekano", "Alama ya vipengele vipya",
            "Sakinisha kiendelezi kutoka faili (.crx / .zip)", "Usakinishaji wa moja kwa moja wa faili ya .crx au .zip",
            "Kifurushi cha viendelezi bora (6)", "uBlock Origin Lite, SponsorBlock, VOT, Fingerprint Defender, AdGuard, Bitwarden",
            "Dhibiti viendelezi", "Fungua Chrome Web Store",
            "Inasakinisha kiendelezi...", "Kiendelezi kimesakinishwa", "Imeshindwa kusakinisha: ", "Injini ya viendelezi bado haijawa tayari", "Sakinisha",
            "Sera ya IP ya WebRTC", "Chaguo-msingi", "IP ya umma na binafsi pekee", "IP ya umma pekee", "Zima UDP isiyo na proksi (inapendekezwa)",
            "Ulinzi dhidi ya ufuatiliaji", "Ulinzi wa WebAudio na skrini dhidi ya ufuatiliaji (inahitaji kuanza upya)",
            "Mtafsiri wa kurasa", "Tafsiri kurasa papo hapo kwa Google, Bing, Yandex, DeepL au Gemini",
            "Kipakuzi cha nje", "Tuma vipakuliwa kwa ADM, 1DM au kipakuzi kingine", "Fungua na kipakuzi...",
            "Funga vichupo visivyotumika baada ya siku 1", "Huongeza chaguo la siku 1 kwenye mipangilio ya kufunga vichupo",
            "Hali ya giza ya AMOLED", "Mandharinyuma nyeusi kabisa (#000000) kwa skrini za OLED",
            "Upau wa chini wenye ukungu", "Mwonekano wa kioo cha One UI kwenye upau wa chini",
            "Muda wa nukta ya kipengele kipya", "Zima", "Siku %d",
            "Weka vyote kama vimeonekana", "Alama zote zimeondolewa",
            "Anzisha upya kivinjari", "Tekeleza mabadiliko ya injini na mwonekano", "Anzisha upya kivinjari kutekeleza mabadiliko", "Anzisha upya", "Baadaye");

        // --- Modern v1.3.x strings across ALL 18 non-EN/RU languages (natural, idiomatic) ---
        putModern(catalog, "uk",
            "Прозорість і розмиття панелей", "Напівпрозорий адресний рядок", "Напівпрозора компактна панель",
            "Кнопка компактної панелі", "Утримання «Додому» закриває фонові вкладки", "Зняти ліміт у 99 вкладок",
            "Перенесення тексту при зумі", "Яскраві картинки у темній темі", "Примусово затемнювати світлі сайти",
            "Завантажувати й картинки через менеджер", "Керування розширеннями",
            "Стартова сторінка SBrowserX", "Шпалери", "Погода", "Ярлики", "Сітка ярликів", "4 колонки", "5 колонок",
            "Заповнювати плитку іконкою", "Розтягувати іконки сайтів на всю плитку", "Оновити шпалери зараз",
            "Асистент перегляду", "Синхронізація та бекап", "Закладки", "Джерело патчів Morphe", "Офіційний репозиторій",
            "Manifest V2 не підтримується",
            "Це розширення використовує застарілий Manifest V2, підтримку якого видалено в Chromium 145 (Samsung Internet 31).\n\nВстановлення заблоковано, щоб запобігти збою браузера або зникненню розширення після перезапуску.\n\nВикористовуйте версію на Manifest V3 — наприклад, uBlock Origin Lite із Chrome Web Store.");

        putModern(catalog, "es",
            "Transparencia de barras y dirección", "Barra de direcciones semitransparente", "Barra compacta semitransparente",
            "Botón de barra compacta", "Mantener Inicio cierra pestañas en segundo plano", "Quitar límite de 99 pestañas",
            "Ajuste de texto al hacer zoom", "Imágenes vívidas en modo oscuro", "Forzar modo oscuro en sitios claros",
            "Enviar también imágenes al gestor", "Gestionar extensiones",
            "Página de inicio SBrowserX", "Fondo de pantalla", "Clima", "Accesos directos", "Cuadrícula", "4 columnas", "5 columnas",
            "Llenar mosaico con el icono", "Ajustar el icono del sitio de borde a borde", "Cambiar fondo ahora",
            "Asistente de navegación", "Sincronización y copia", "Marcadores", "Fuente de parches Morphe", "Repositorio oficial",
            "Manifest V2 no es compatible",
            "Esta extensión usa Manifest V2, que fue eliminado en Chromium 145 (Samsung Internet 31).\n\nSe bloqueó la instalación para evitar cierres inesperados o que desaparezca al reiniciar.\n\nInstala una versión Manifest V3 (por ejemplo, uBlock Origin Lite desde Chrome Web Store).");

        putModern(catalog, "pt",
            "Transparência das barras", "Barra de endereços semitransparente", "Barra compacta semitransparente",
            "Botão da barra compacta", "Segurar Início fecha abas em segundo plano", "Remover limite de 99 abas",
            "Ajuste de texto no zoom", "Imagens vívidas no modo escuro", "Forçar modo escuro em sites claros",
            "Enviar imagens ao gerenciador", "Gerenciar extensões",
            "Página inicial SBrowserX", "Papel de parede", "Clima", "Atalhos", "Grade de atalhos", "4 colunas", "5 colunas",
            "Preencher bloco com o ícone", "Expandir ícones dos sites de ponta a ponta", "Trocar papel de parede agora",
            "Assistente de navegação", "Sincronização e backup", "Favoritos", "Fonte de patches Morphe", "Repositório oficial",
            "Manifest V2 não suportado",
            "Esta extensão usa Manifest V2, removido no Chromium 145 (Samsung Internet 31).\n\nA instalação foi bloqueada para evitar travamentos ou remoção ao reiniciar.\n\nUse uma versão Manifest V3 (por exemplo, uBlock Origin Lite na Chrome Web Store).");

        putModern(catalog, "de",
            "Transparenz für Leisten & Adressfeld", "Halbtransparente Adressleiste", "Halbtransparente Kompaktleiste",
            "Taste der Kompaktleiste", "Home gedrückt halten schließt Hintergrundtabs", "99-Tab-Limit aufheben",
            "Textumbruch beim Zoomen", "Klare Bilder im Dunkelmodus", "Dunkelmodus für helle Seiten erzwingen",
            "Bilder ebenfalls an Downloader senden", "Erweiterungen verwalten",
            "SBrowserX-Startseite", "Hintergrundbild", "Wetter", "Verknüpfungen", "Kachelraster", "4 Spalten", "5 Spalten",
            "Kachel mit Symbol füllen", "Website-Symbole randlos in Kacheln darstellen", "Hintergrund jetzt wechseln",
            "Browsing-Assistent", "Sync & Backup", "Lesezeichen", "Morphe-Patchquelle", "Offizielles Repository",
            "Manifest V2 wird nicht unterstützt",
            "Diese Erweiterung nutzt Manifest V2, das in Chromium 145 (Samsung Internet 31) entfernt wurde.\n\nDie Installation wurde blockiert, um Abstürze oder ein Verschwinden nach dem Neustart zu verhindern.\n\nBitte installiere eine Manifest-V3-Version (z. B. uBlock Origin Lite aus dem Chrome Web Store).");

        putModern(catalog, "fr",
            "Transparence des barres", "Barre d'adresse translucide", "Barre compacte translucide",
            "Bouton de la barre compacte", "Appui long sur Accueil ferme les onglets inactifs", "Supprimer la limite de 99 onglets",
            "Ajustement du texte au zoom", "Images nettes en mode sombre", "Forcer le mode sombre sur sites clairs",
            "Transférer aussi les images", "Gérer les extensions",
            "Page d'accueil SBrowserX", "Fond d'écran", "Météo", "Raccourcis", "Grille des raccourcis", "4 colonnes", "5 colonnes",
            "Remplir la tuile avec l'icône", "Afficher l'icône du site bord à bord", "Changer de fond maintenant",
            "Assistant de navigation", "Synchronisation et sauvegarde", "Favoris", "Source de patchs Morphe", "Dépôt officiel",
            "Manifest V2 non pris en charge",
            "Cette extension utilise Manifest V2, supprimé dans Chromium 145 (Samsung Internet 31).\n\nL'installation a été bloquée pour éviter un plantage ou sa disparition au redémarrage.\n\nInstallez plutôt une version Manifest V3 (par exemple uBlock Origin Lite depuis le Chrome Web Store).");

        putModern(catalog, "it",
            "Trasparenza barre e indirizzi", "Barra indirizzi semitrasparente", "Barra compatta semitrasparente",
            "Pulsante barra compatta", "Tieni premuto Home per chiudere le altre schede", "Rimuovi limite di 99 schede",
            "Adatta testo allo zoom", "Immagini nitide in modalità scura", "Forza tema scuro sui siti chiari",
            "Invia anche le immagini al gestore", "Gestisci estensioni",
            "Pagina iniziale SBrowserX", "Sfondo", "Meteo", "Scorciatoie", "Griglia scorciatoie", "4 colonne", "5 colonne",
            "Riempi riquadro con l'icona", "Espandi le icone dei siti a tutto riquadro", "Cambia sfondo ora",
            "Assistente di navigazione", "Sincronizzazione e backup", "Preferiti", "Sorgente patch Morphe", "Repository ufficiale",
            "Manifest V2 non supportato",
            "Questa estensione usa Manifest V2, rimosso in Chromium 145 (Samsung Internet 31).\n\nL'installazione è stata bloccata per evitare crash o la rimozione al riavvio.\n\nInstalla una versione Manifest V3 (ad esempio uBlock Origin Lite dal Chrome Web Store).");

        putModern(catalog, "pl",
            "Przezroczystość pasków", "Półprzezroczysty pasek adresu", "Półprzezroczysty pasek kompaktowy",
            "Przycisk paska kompaktowego", "Przytrzymanie Start zamyka karty w tle", "Usuń limit 99 kart",
            "Zawijanie tekstu przy zbliżeniu", "Żywe obrazy w trybie ciemnym", "Wymuś tryb ciemny na jasnych stronach",
            "Przekazuj też obrazy do menedżera", "Zarządzaj rozszerzeniami",
            "Strona startowa SBrowserX", "Tapeta", "Pogoda", "Skróty", "Siatka skrótów", "4 kolumny", "5 kolumn",
            "Wypełnij kafelek ikoną", "Rozciągnij ikony stron na cały kafelek", "Zmień tapetę teraz",
            "Asystent przeglądania", "Synchronizacja i kopia", "Zakładki", "Źródło łatek Morphe", "Oficjalne repozytorium",
            "Manifest V2 nie jest obsługiwany",
            "To rozszerzenie używa przestarzałego Manifest V2, usuniętego w Chromium 145 (Samsung Internet 31).\n\nInstalacja została zablokowana, aby uniknąć awarii lub zniknięcia po restarcie.\n\nZainstaluj wersję Manifest V3 (np. uBlock Origin Lite z Chrome Web Store).");

        putModern(catalog, "tr",
            "Çubuk ve adres satırı şeffaflığı", "Yarı şeffaf adres çubuğu", "Yarı şeffaf kompakt çubuk",
            "Kompakt çubuk düğmesi", "Ana Sayfa'ya uzun basınca arka plan sekmelerini kapat", "99 sekme sınırını kaldır",
            "Yakınlaştırmada metin kaydırma", "Karanlık modda canlı görseller", "Açık sitelerde karanlık modu zorla",
            "Görselleri de indirme yöneticisine gönder", "Uzantıları yönet",
            "SBrowserX Başlangıç Sayfası", "Duvar kağıdı", "Hava durumu", "Kısayollar", "Kısayol ızgarası", "4 sütun", "5 sütun",
            "Kutuyu simgeyle doldur", "Site simgelerini kutuya tam sığdır", "Duvar kağıdını şimdi yenile",
            "Gezinme Asistanı", "Eşitleme ve Yedekleme", "Yer işaretleri", "Morphe yama kaynağı", "Resmi depo",
            "Manifest V2 desteklenmiyor",
            "Bu uzantı, Chromium 145 (Samsung Internet 31) sürümünde kaldırılan Manifest V2'yi kullanıyor.\n\nTarayıcının çökmesini veya yeniden başlatmada silinmesini önlemek için kurulum engellendi.\n\nLütfen Manifest V3 sürümünü kurun (örneğin Chrome Web Store'dan uBlock Origin Lite).");

        putModern(catalog, "ar",
            "شفافية الأشرطة وشريط العنوان", "شريط عنوان شبه شفاف", "شريط مدمج شبه شفاف",
            "زر الشريط المدمج", "الضغط المطوّل على الرئيسية يغلق علامات التبويب الخلفية", "إزالة حد 99 علامة تبويب",
            "التفاف النص عند التكبير", "صور واضحة في الوضع الداكن", "فرض الوضع الداكن على المواقع الفاتحة",
            "إرسال الصور أيضاً لمدير التنزيل", "إدارة الإضافات",
            "صفحة البداية SBrowserX", "الخلفية", "الطقس", "الاختصارات", "شبكة الاختصارات", "4 أعمدة", "5 أعمدة",
            "ملء المربع بالأيقونة", "تمديد أيقونات المواقع لتملأ المربع بالكامل", "تحديث الخلفية الآن",
            "مساعد التصفح", "المزامنة والنسخ الاحتياطي", "الإشارات المرجعية", "مصدر تصحيحات Morphe", "المستودع الرسمي",
            "Manifest V2 غير مدعوم",
            "تستخدم هذه الإضافة Manifest V2 الذي تمت إزالته في Chromium 145 (Samsung Internet 31).\n\nتم حظر التثبيت لمنع تعطل المتصفح أو اختفاء الإضافة بعد إعادة التشغيل.\n\nيرجى تثبيت إصدار Manifest V3 (مثل uBlock Origin Lite من سوق Chrome الإلكتروني).");

        putModern(catalog, "fa",
            "شفافیت نوارها و نوار آدرس", "نوار آدرس نیمه‌شفاف", "نوار فشرده نیمه‌شفاف",
            "دکمه نوار فشرده", "نگه داشتن دکمه خانه برگه‌های پس‌زمینه را می‌بندد", "حذف محدودیت ۹۹ برگه",
            "تنظیم خودکار متن هنگام بزرگ‌نمایی", "تصاویر شفاف در حالت تاریک", "اجبار حالت تاریک برای سایت‌های روشن",
            "ارسال تصاویر به دانلودر خارجی", "مدیریت افزونه‌ها",
            "صفحه شروع SBrowserX", "تصویر زمینه", "آب‌وهوا", "میانبرها", "چیدمان میانبرها", "۴ ستون", "۵ ستون",
            "پر کردن کاشی با آیکون", "نمایش تمام‌کادر آیکون سایت در کاشی", "تغییر تصویر زمینه اکنون",
            "دستیار مرور", "همگام‌سازی و پشتیبان‌گیری", "نشانک‌ها", "منبع پچ Morphe", "مخزن رسمی",
            "Manifest V2 پشتیبانی نمی‌شود",
            "این افزونه از Manifest V2 استفاده می‌کند که در Chromium 145 (Samsung Internet 31) حذف شده است.\n\nنصب برای جلوگیری از کرش مرورگر یا حذف خودکار پس از راه‌اندازی مجدد مسدود شد.\n\nلطفاً نسخه Manifest V3 (مثلاً uBlock Origin Lite از Chrome Web Store) را نصب کنید.");

        putModern(catalog, "hi",
            "बार और एड्रेस बार पारदर्शिता", "अर्ध-पारदर्शी एड्रेस बार", "अर्ध-पारदर्शी कॉम्पैक्ट बार",
            "कॉम्पैक्ट बार बटन", "होम दबाए रखने पर बैकग्राउंड टैब बंद हों", "99 टैब सीमा हटाएँ",
            "ज़ूम पर टेक्स्ट रीफ़्लो", "डार्क मोड में स्पष्ट तस्वीरें", "लाइट साइट्स पर डार्क मोड लागू करें",
            "तस्वीरें भी डाउनलोडर को भेजें", "एक्सटेंशन प्रबंधित करें",
            "SBrowserX स्टार्ट पेज", "वॉलपेपर", "मौसम", "शॉर्टकट", "शॉर्टकट ग्रिड", "4 कॉलम", "5 कॉलम",
            "आइकन से टाइल भरें", "साइट आइकन को पूरी टाइल में फैलाएँ", "वॉलपेपर अभी बदलें",
            "ब्राउज़िंग असिस्ट", "सिंक और बैकअप", "बुकमार्क", "Morphe पैच स्रोत", "आधिकारिक रिपॉज़िटरी",
            "Manifest V2 समर्थित नहीं है",
            "यह एक्सटेंशन Manifest V2 का उपयोग करता है, जिसे Chromium 145 (Samsung Internet 31) से हटा दिया गया है।\n\nब्राउज़र क्रैश या रीस्टार्ट पर हटने से रोकने के लिए इंस्टॉलेशन रोक दिया गया।\n\nकृपया Manifest V3 संस्करण इंस्टॉल करें (जैसे Chrome Web Store से uBlock Origin Lite)।");

        putModern(catalog, "zh",
            "工具栏与地址栏透明度", "半透明地址栏", "半透明紧凑栏",
            "紧凑栏快捷按钮", "长按主页键关闭后台标签页", "解除 99 个标签页上限",
            "缩放自动重排文字", "深色模式下保持图片原色", "强制为浅色网站启用深色模式",
            "图片也交给外部下载器", "管理扩展程序",
            "SBrowserX 起始页", "壁纸", "天气", "快捷方式", "图标网格", "4 列", "5 列",
            "图标铺满磁贴", "让网站图标无留白铺满圆角磁贴", "立即切换壁纸",
            "浏览助手", "同步与备份", "书签", "Morphe 补丁源", "官方仓库",
            "不支持 Manifest V2",
            "此扩展使用已在 Chromium 145（Samsung Internet 31）中移除的 Manifest V2。\n\n为防止浏览器崩溃或重启后扩展消失，已阻止安装。\n\n请改用 Manifest V3 版本（例如从 Chrome 应用商店安装 uBlock Origin Lite）。");

        putModern(catalog, "ja",
            "バーとアドレスバーの透過度", "半透明アドレスバー", "半透明コンパクトバー",
            "コンパクトバーのボタン", "ホーム長押しで背景タブを閉じる", "99タブの上限を解除",
            "ズーム時のテキスト折り返し", "ダークモードでも画像を鮮明に表示", "明るいサイトを強制ダーク化",
            "画像も外部ダウンローダーへ転送", "拡張機能を管理",
            "SBrowserX スタートページ", "壁紙", "天気", "ショートカット", "グリッド列数", "4列", "5列",
            "アイコンをタイルいっぱいに表示", "サイトアイコンを余白なしで表示", "壁紙を今すぐ更新",
            "ブラウジングアシスト", "同期とバックアップ", "ブックマーク", "Morphe パッチソース", "公式リポジトリ",
            "Manifest V2 は非対応です",
            "この拡張機能は Chromium 145（Samsung Internet 31）で廃止された Manifest V2 を使用しています。\n\nクラッシュや再起動後の消失を防ぐため、インストールをブロックしました。\n\nManifest V3 対応版（Chrome ウェブストアの uBlock Origin Lite など）をご利用ください。");

        putModern(catalog, "ko",
            "툴바 및 주소창 투명도", "반투명 주소창", "반투명 컴팩트 바",
            "컴팩트 바 버튼", "홈 버튼 길게 눌러 백그라운드 탭 닫기", "99개 탭 제한 해제",
            "확대 시 텍스트 자동 줄바꿈", "다크 모드에서 이미지 원본 밝기 유지", "밝은 사이트 강제 다크 모드",
            "이미지도 외부 다운로더로 전달", "확장 프로그램 관리",
            "SBrowserX 시작 페이지", "배경화면", "날씨", "바로가기", "바로가기 배열", "4열", "5열",
            "아이콘으로 타일 채우기", "사이트 아이콘을 타일에 꽉 차게 표시", "배경화면 지금 변경",
            "브라우징 어시스트", "동기화 및 백업", "북마크", "Morphe 패치 소스", "공식 저장소",
            "Manifest V2 미지원",
            "이 확장 프로그램은 Chromium 145(Samsung Internet 31)에서 제거된 Manifest V2를 사용합니다.\n\n브라우저 충돌이나 재시작 후 삭제를 방지하기 위해 설치를 차단했습니다.\n\nManifest V3 버전(예: Chrome 웹 스토어의 uBlock Origin Lite)을 설치해 주세요.");

        putModern(catalog, "id",
            "Transparansi bilah & alamat", "Bilah alamat semitransparan", "Bilah ringkas semitransparan",
            "Tombol bilah ringkas", "Tahan Beranda untuk tutup tab latar belakang", "Hapus batas 99 tab",
            "Penyesuaian teks saat zoom", "Gambar cerah di mode gelap", "Paksa mode gelap di situs terang",
            "Teruskan gambar ke pengunduh", "Kelola ekstensi",
            "Halaman Awal SBrowserX", "Wallpaper", "Cuaca", "Pintasan", "Kisi pintasan", "4 kolom", "5 kolom",
            "Isi ubin dengan ikon", "Tampilkan ikon situs penuh tanpa tepi", "Ganti wallpaper sekarang",
            "Asisten Penjelajahan", "Sinkronisasi & Cadangan", "Markah", "Sumber patch Morphe", "Repositori resmi",
            "Manifest V2 tidak didukung",
            "Ekstensi ini menggunakan Manifest V2 yang telah dihapus di Chromium 145 (Samsung Internet 31).\n\nPemasangan diblokir untuk mencegah crash atau ekstensi hilang saat dimulai ulang.\n\nSilakan pasang versi Manifest V3 (misalnya uBlock Origin Lite dari Chrome Web Store).");

        putModern(catalog, "vi",
            "Độ mờ thanh công cụ & địa chỉ", "Thanh địa chỉ trong mờ", "Thanh gọn trong mờ",
            "Nút trên thanh gọn", "Nhấn giữ Trang chủ để đóng thẻ nền", "Bỏ giới hạn 99 thẻ",
            "Tự xuống dòng khi phóng to", "Giữ ảnh sáng rõ ở chế độ tối", "Ép chế độ tối cho trang sáng",
            "Chuyển cả ảnh sang trình tải", "Quản lý tiện ích",
            "Trang khởi đầu SBrowserX", "Hình nền", "Thời tiết", "Lối tắt", "Lưới lối tắt", "4 cột", "5 cột",
            "Lấp đầy ô bằng biểu tượng", "Hiển thị biểu tượng trang tràn viền", "Đổi hình nền ngay",
            "Trợ lý duyệt web", "Đồng bộ & Sao lưu", "Dấu trang", "Nguồn bản vá Morphe", "Kho lưu trữ chính thức",
            "Không hỗ trợ Manifest V2",
            "Tiện ích này dùng Manifest V2 đã bị loại bỏ trong Chromium 145 (Samsung Internet 31).\n\nQuá trình cài đặt bị chặn để tránh văng trình duyệt hoặc tự mất sau khi khởi động lại.\n\nHãy cài bản Manifest V3 (ví dụ: uBlock Origin Lite từ Chrome Web Store).");

        putModern(catalog, "nl",
            "Transparantie van balken", "Semitransparante adresbalk", "Semitransparante compacte balk",
            "Knop compacte balk", "Home ingedrukt houden sluit achtergrondtabs", "Limiet van 99 tabbladen opheffen",
            "Tekstomloop bij inzoomen", "Heldere afbeeldingen in donkere modus", "Donkere modus forceren op lichte sites",
            "Afbeeldingen ook naar downloader sturen", "Extensies beheren",
            "SBrowserX-startpagina", "Achtergrond", "Weer", "Snelkoppelingen", "Raster", "4 kolommen", "5 kolommen",
            "Tegel vullen met pictogram", "Sitepictogrammen randloos weergeven", "Achtergrond nu vernieuwen",
            "Browse-assistent", "Synchronisatie & back-up", "Bladwijzers", "Morphe-patchbron", "Officiële repository",
            "Manifest V2 wordt niet ondersteund",
            "Deze extensie gebruikt Manifest V2, dat in Chromium 145 (Samsung Internet 31) is verwijderd.\n\nDe installatie is geblokkeerd om crashes of verdwijnen na herstart te voorkomen.\n\nInstalleer een Manifest V3-versie (bijv. uBlock Origin Lite uit de Chrome Web Store).");

        putModern(catalog, "sw",
            "Uwazi wa pau na anwani", "Upau wa anwani wenye uwazi", "Upau mfupi wenye uwazi",
            "Kitufe cha upau mfupi", "Bonyeza Nyumbani muda mrefu kufunga vichupo vingine", "Ondoa kikomo cha vichupo 99",
            "Panga maandishi ukikuza", "Picha angavu kwenye hali ya giza", "Lazimisha hali ya giza kwenye tovuti nyeupe",
            "Tuma picha pia kwa kipakuzi", "Dhibiti viendelezi",
            "Ukurasa wa Mwanzo SBrowserX", "Mandhari", "Hali ya hewa", "Njia za mkato", "Mpangilio wa gridi", "Safu 4", "Safu 5",
            "Jaza kigae kwa ikoni", "Panua ikoni za tovuti zijaze kigae chote", "Badilisha mandhari sasa",
            "Msaidizi wa Kuvinjari", "Usawazishaji na Hifadhi", "Alamisho", "Chanzo cha viraka Morphe", "Hifadhi rasmi",
            "Manifest V2 haitumiki",
            "Kiendelezi hiki kinatumia Manifest V2 ambayo imeondolewa kwenye Chromium 145 (Samsung Internet 31).\n\nUsakinishaji umezuiwa kuzuia kivinjari kuzima au kiendelezi kupotea.\n\nTafadhali sakinisha toleo la Manifest V3 (mfano uBlock Origin Lite kutoka Chrome Web Store).");
    }

    private static void putCore(Map<String, Map<String, String>> catalog, String lang, String... v) {
        String[] keys = new String[] {
            SBrowserXStrings.TITLE_HUB, SBrowserXStrings.CAT_EXTENSIONS, SBrowserXStrings.CAT_PRIVACY,
            SBrowserXStrings.CAT_FEATURES, SBrowserXStrings.CAT_APPEARANCE, SBrowserXStrings.CAT_HIGHLIGHT,
            SBrowserXStrings.EXT_INSTALL_FILE, SBrowserXStrings.EXT_INSTALL_FILE_SUMMARY,
            SBrowserXStrings.EXT_PACK_TITLE, SBrowserXStrings.EXT_PACK_SUMMARY,
            SBrowserXStrings.EXT_OPEN_MANAGER, SBrowserXStrings.EXT_OPEN_CWS,
            SBrowserXStrings.EXT_INSTALLING, SBrowserXStrings.EXT_INSTALL_OK, SBrowserXStrings.EXT_INSTALL_FAIL,
            SBrowserXStrings.EXT_ENGINE_NOT_READY, SBrowserXStrings.BTN_INSTALL,
            SBrowserXStrings.WEBRTC_TITLE, SBrowserXStrings.WEBRTC_DEFAULT, SBrowserXStrings.WEBRTC_PUB_PRIV,
            SBrowserXStrings.WEBRTC_PUB_ONLY, SBrowserXStrings.WEBRTC_DISABLE_UDP,
            SBrowserXStrings.FINGERPRINT_TITLE, SBrowserXStrings.FINGERPRINT_SUMMARY,
            SBrowserXStrings.GTRANS_TITLE, SBrowserXStrings.GTRANS_SUMMARY,
            SBrowserXStrings.EXT_DL_TITLE, SBrowserXStrings.EXT_DL_SUMMARY, SBrowserXStrings.EXT_DL_CHOOSER,
            SBrowserXStrings.AUTOCLOSE_1D_TITLE, SBrowserXStrings.AUTOCLOSE_1D_SUMMARY,
            SBrowserXStrings.AMOLED_TITLE, SBrowserXStrings.AMOLED_SUMMARY,
            SBrowserXStrings.BOTTOM_BLUR_TITLE, SBrowserXStrings.BOTTOM_BLUR_SUMMARY,
            SBrowserXStrings.HIGHLIGHT_DAYS_TITLE, SBrowserXStrings.HIGHLIGHT_DAYS_OFF, SBrowserXStrings.HIGHLIGHT_DAYS_FMT,
            SBrowserXStrings.MARK_ALL_VIEWED, SBrowserXStrings.MARK_ALL_VIEWED_DONE,
            SBrowserXStrings.RESTART_TITLE, SBrowserXStrings.RESTART_SUMMARY,
            SBrowserXStrings.RESTART_REQUIRED, SBrowserXStrings.BTN_RESTART, SBrowserXStrings.BTN_LATER
        };
        Map<String, String> m = catalog.get(lang);
        if (m == null) {
            m = new HashMap<>();
            catalog.put(lang, m);
        }
        for (int i = 0; i < keys.length && i < v.length; i++) {
            m.put(keys[i], v[i]);
        }
    }

    private static void putModern(Map<String, Map<String, String>> catalog, String lang, String... v) {
        String[] keys = new String[] {
            SBrowserXStrings.LAYOUT_TRANSPARENCY_TITLE, SBrowserXStrings.URL_BAR_BLUR_TITLE, SBrowserXStrings.COMPACT_BAR_BLUR_TITLE,
            SBrowserXStrings.COMPACT_AI_BTN_TITLE, SBrowserXStrings.HOME_LONG_PRESS_TITLE, SBrowserXStrings.UNLIMITED_TABS_TITLE,
            SBrowserXStrings.TEXT_REFLOW_TITLE, SBrowserXStrings.DARK_BRIGHT_IMG_TITLE, SBrowserXStrings.DARK_FORCE_LIGHT_TITLE,
            SBrowserXStrings.EXT_DL_IMAGES_TITLE, SBrowserXStrings.EXT_MANAGE_TITLE,
            SBrowserXStrings.SP_TITLE, SBrowserXStrings.SP_CAT_WALLPAPER, SBrowserXStrings.SP_CAT_WEATHER, SBrowserXStrings.SP_CAT_SHORTCUTS,
            SBrowserXStrings.SP_SC_GRID_COLS, SBrowserXStrings.SP_SC_GRID_4, SBrowserXStrings.SP_SC_GRID_5,
            SBrowserXStrings.SP_SC_TILE_FILL, SBrowserXStrings.SP_SC_TILE_FILL_SUM, SBrowserXStrings.SP_WP_NEXT_ONLINE,
            SBrowserXStrings.ASSIST_TITLE, SBrowserXStrings.SYNC_BACKUP_TITLE, "sbrowserx_bookmarks_title",
            "sbrowserx_morphe_source", "sbrowserx_official_repo",
            SBrowserXStrings.EXT_MV2_BLOCKED_TITLE, SBrowserXStrings.EXT_MV2_BLOCKED_MSG
        };
        Map<String, String> m = catalog.get(lang);
        if (m == null) {
            m = new HashMap<>();
            catalog.put(lang, m);
        }
        for (int i = 0; i < keys.length && i < v.length; i++) {
            m.put(keys[i], v[i]);
        }
    }

    static String translateMenuLabel(String lang) {
        switch (lang) {
            case "ru": return "Перевести...";
            case "uk": return "Перекласти...";
            case "es": return "Traducir...";
            case "pt": return "Traduzir...";
            case "de": return "Übersetzen...";
            case "fr": return "Traduire...";
            case "it": return "Traduci...";
            case "pl": return "Przetłumacz...";
            case "tr": return "Çevir...";
            case "ar": return "ترجمة...";
            case "fa": return "ترجمه...";
            case "hi": return "अनुवाद करें...";
            case "zh": return "翻译...";
            case "ja": return "翻訳...";
            case "ko": return "번역...";
            case "id": return "Terjemahkan...";
            case "vi": return "Dịch...";
            case "nl": return "Vertalen...";
            case "sw": return "Tafsiri...";
            default: return "Translate...";
        }
    }

    static String bannerTranslated(String lang) {
        switch (lang) {
            case "ru": return "Страница переведена";
            case "uk": return "Сторінку перекладено";
            case "es": return "Página traducida";
            case "pt": return "Página traduzida";
            case "de": return "Seite übersetzt";
            case "fr": return "Page traduite";
            case "it": return "Pagina tradotta";
            case "pl": return "Strona przetłumaczona";
            case "tr": return "Sayfa çevrildi";
            case "ar": return "تمت ترجمة الصفحة";
            case "fa": return "صفحه ترجمه شد";
            case "hi": return "पृष्ठ अनुवादित";
            case "zh": return "页面已翻译";
            case "ja": return "ページを翻訳しました";
            case "ko": return "페이지 번역됨";
            case "id": return "Halaman diterjemahkan";
            case "vi": return "Đã dịch trang";
            case "nl": return "Pagina vertaald";
            case "sw": return "Ukurasa umetafsiriwa";
            default: return "Page translated";
        }
    }

    static String bannerOriginal(String lang) {
        switch (lang) {
            case "ru": return "Оригинал";
            case "uk": return "Оригінал";
            case "es": return "Original";
            case "pt": return "Original";
            case "de": return "Original";
            case "fr": return "Original";
            case "it": return "Originale";
            case "pl": return "Oryginał";
            case "tr": return "Orijinal";
            case "ar": return "الأصلي";
            case "fa": return "اصلی";
            case "hi": return "मूल";
            case "zh": return "原文";
            case "ja": return "原文";
            case "ko": return "원본";
            case "id": return "Asli";
            case "vi": return "Bản gốc";
            case "nl": return "Origineel";
            case "sw": return "Asili";
            default: return "Original";
        }
    }
}
