# SBrowserX

Morphe patches for **Samsung Internet Browser** (`com.sec.android.app.sbrowser`, target `31.0.2.41` arm64-v8a).

## Current Patches (v0.1.0)
- **Change package and app name**: Install as a standalone clone (`sbrowserx.com.sec.android.app.sbrowser` / `SBrowserX`) alongside stock Samsung Internet.
- **Unlock Chrome Web Store & Extensions**: Unlocks the built-in extensions WebUI, toolbar container, icon pinning, and Chrome Web Store support.
- **Remove telemetry, promotions & updates**: Disables Samsung Analytics, SILog, SMP marketing push, Promotion Engine, Kindred shopping/coupons, and Galaxy Store/Play Store update checks while preserving website Web Push notifications.
- **Unlock Debug settings menu**: Exposes Samsung Internet's hidden Debug settings menu directly inside Settings.

## Roadmap
- Pre-bundled MV3 Chrome extensions (`uBlock Origin Lite`, `SponsorBlock`, `VOT`, `All Fingerprint Defender`, `AdGuard AdBlocker`)
- System-level WebRTC IP leak protection in Privacy & Security settings
- Built-in Google Translate backend inside native `TransBar`
- Forced Adaptive Dark Mode + AMOLED `#000000` theme + toggle for semi-transparent bottom navigation bar
- Local & Google Drive backup/restore via Android Storage Access Framework

## License
GPL-3.0
