package dev.vortexnode.sbrowserx.patches.core

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch
import dev.vortexnode.sbrowserx.patches.branding.SBrowserPackageRenameTarget
import dev.vortexnode.sbrowserx.shared.Constants.ANDROID_NS
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow
import dev.vortexnode.sbrowserx.shared.returnConstantBoolean
import org.w3c.dom.Document
import org.w3c.dom.Element

private const val SBROWSER_APP_CLASS = "Lcom/sec/android/app/sbrowser/SBrowserApplication;"
private const val SBROWSER_MAIN_ACTIVITY_CLASS = "Lcom/sec/android/app/sbrowser/SBrowserMainActivity;"
private const val SBROWSER_CMDLINE_CLASS = "Lcom/sec/android/app/sbrowser/init/SBrowserCommandLine;"
private const val BADGE_MANAGER_CLASS = "Lcom/sec/android/app/sbrowser/toolbar/BadgeManager;"
private const val DEBUG_SETTINGS_CLASS = "Lcom/sec/android/app/sbrowser/common/device/DebugSettings;"
private const val PREF_FRAGMENT_CUSTOM_CLASS = "Lcom/sec/android/app/sbrowser/common/settings/PreferenceFragmentCustom;"

private const val SETTINGS_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXSettingsActivity"
private const val TRANSLATE_SETTINGS_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXTranslateSettingsActivity"
private const val EXT_MANAGER_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXExtensionsActivity"
private const val WEBRTC_DIALOG_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXWebRtcDialogActivity"
private const val EXT_BRIDGE_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXExtensionsBridgeActivity"
private const val EXT_DL_DIALOG_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXExternalDownloaderDialogActivity"
private const val TRANSPARENCY_DIALOG_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXTransparencyDialogActivity"
private const val COMPACT_BTN_DIALOG_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXCompactButtonDialogActivity"
private const val BACKUP_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXBackupActivity"
private const val BROWSING_ASSIST_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXBrowsingAssistActivity"
private const val AI_SUMMARY_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXAiSummaryActivity"
private const val APP_MENU_HANDLER_CLASS =
    "Lcom/sec/android/app/sbrowser/main_view/menu/AppMenuHandler;"
private const val PRIVACY_PREF_FRAGMENT_CLASS =
    "Lcom/sec/android/app/sbrowser/settings/security_panel/PrivacyPreferenceFragment;"

private fun createIntentPref(
    xml: Document,
    key: String,
    title: String,
    summary: String,
    targetPkg: String,
    targetClass: String,
): Element {
    val pref = xml.createElement("com.sec.android.app.sbrowser.common.settings.PreferenceCustom")
    pref.setAttributeNS(ANDROID_NS, "android:key", key)
    pref.setAttributeNS(ANDROID_NS, "android:title", title)
    pref.setAttributeNS(ANDROID_NS, "android:summary", summary)
    val intent = xml.createElement("intent")
    intent.setAttributeNS(ANDROID_NS, "android:targetPackage", targetPkg)
    intent.setAttributeNS(ANDROID_NS, "android:targetClass", targetClass)
    pref.appendChild(intent)
    return pref
}

private fun createSwitchPref(
    xml: Document,
    key: String,
    title: String,
    summary: String,
    defaultValue: Boolean,
): Element {
    val pref = xml.createElement("com.sec.android.app.sbrowser.common.settings.SwitchPreferenceCustom")
    pref.setAttributeNS(ANDROID_NS, "android:key", key)
    pref.setAttributeNS(ANDROID_NS, "android:title", title)
    pref.setAttributeNS(ANDROID_NS, "android:summary", summary)
    pref.setAttributeNS(ANDROID_NS, "android:defaultValue", defaultValue.toString())
    pref.setAttributeNS(ANDROID_NS, "android:persistent", "false")
    return pref
}

private fun createCategory(xml: Document, key: String, title: String = "SBrowserX"): Element {
    val cat = xml.createElement("androidx.preference.PreferenceCategory")
    cat.setAttributeNS(ANDROID_NS, "android:selectable", "false")
    cat.setAttributeNS(ANDROID_NS, "android:key", key)
    if (title.isNotEmpty()) {
        cat.setAttributeNS(ANDROID_NS, "android:title", title)
    }
    return cat
}

internal val sbrowserXCoreResourcePatch = resourcePatch(
    description = "Registers SBrowserX activities/dialogs and mirrors all SBrowserX toggles as native in-place switches & dialogs inside Samsung Internet's own settings sections (zero redirects to SBrowserX hub).",
) {
    finalize {
        val targetPkg = SBrowserPackageRenameTarget.packageName

        document("AndroidManifest.xml").use { manifest ->
            val root = manifest.documentElement ?: error("Missing <manifest>")
            val application = root.getElementsByTagName("application").item(0) as? Element
                ?: error("Missing <application>")

            ensureActivity(manifest, application, SETTINGS_ACTIVITY_CLASS, "@android:style/Theme.DeviceDefault.NoActionBar")
            ensureActivity(manifest, application, TRANSLATE_SETTINGS_ACTIVITY_CLASS, "@android:style/Theme.DeviceDefault.NoActionBar")
            ensureActivity(manifest, application, EXT_MANAGER_ACTIVITY_CLASS, "@android:style/Theme.DeviceDefault.NoActionBar")
            ensureActivity(manifest, application, WEBRTC_DIALOG_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
            ensureActivity(manifest, application, EXT_BRIDGE_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
            ensureActivity(manifest, application, EXT_DL_DIALOG_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
            ensureActivity(manifest, application, TRANSPARENCY_DIALOG_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
            ensureActivity(manifest, application, COMPACT_BTN_DIALOG_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
            ensureActivity(manifest, application, BACKUP_ACTIVITY_CLASS, "@android:style/Theme.DeviceDefault.NoActionBar")
            ensureActivity(manifest, application, BROWSING_ASSIST_ACTIVITY_CLASS, "@android:style/Theme.DeviceDefault.NoActionBar")
            ensureActivity(manifest, application, AI_SUMMARY_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
        }

        // 1. Main Settings: Standalone SBrowserX card -> Sync & Backup + Browsing Assist card -> Extensions inside Privacy card
        document("res/xml/settings_fragment.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in settings_fragment.xml")

            val hubPref = createIntentPref(
                xml, "sbrowserx_settings_hub", "SBrowserX  \u25CF",
                "Extensions, WebRTC, Translate, Downloads & AMOLED",
                targetPkg, SETTINGS_ACTIVITY_CLASS,
            )
            val cardDivider = createCategory(xml, "sbrowserx_top_card_divider", "")
            val syncBackupPref = createIntentPref(
                xml, "sbrowserx_sync_backup", "Sync & Backup",
                "Local scheduled backup & Google Drive",
                targetPkg, BACKUP_ACTIVITY_CLASS,
            )
            syncBackupPref.setAttributeNS(ANDROID_NS, "android:singleLineTitle", "false")
            val assistPref = createIntentPref(
                xml, "sbrowserx_browsing_assist", "Browsing assist",
                "AI Summary (Gemini API) & Built-in Translator",
                targetPkg, BROWSING_ASSIST_ACTIVITY_CLASS,
            )

            val firstChild = screen.firstChild
            if (firstChild != null) {
                screen.insertBefore(hubPref, firstChild)
                screen.insertBefore(cardDivider, firstChild)
                screen.insertBefore(syncBackupPref, firstChild)
                screen.insertBefore(assistPref, firstChild)
            } else {
                screen.appendChild(hubPref)
                screen.appendChild(cardDivider)
                screen.appendChild(syncBackupPref)
                screen.appendChild(assistPref)
            }

            // Place "Extensions" down inside the Privacy & Security card (right above "privacy")
            val extPref = createIntentPref(
                xml, "sbrowserx_settings_extensions", "@string/extensions_title",
                "Manage extensions & Chrome Web Store",
                targetPkg, EXT_BRIDGE_ACTIVITY_CLASS,
            )
            val children = screen.childNodes
            var extInserted = false
            for (i in 0 until children.length) {
                val node = children.item(i) as? Element ?: continue
                if (node.getAttributeNS(ANDROID_NS, "key") == "privacy") {
                    screen.insertBefore(extPref, node)
                    extInserted = true
                    break
                }
            }
            if (!extInserted) {
                screen.appendChild(extPref)
            }
        }

        // 2. Privacy & Security: WebRTC in-place dialog + native in-place switches
        document("res/xml/privacy_preferences.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in privacy_preferences.xml")

            val cat = createCategory(xml, "sbrowserx_webrtc_card_category", "SBrowserX")
            val webrtcPref = createIntentPref(
                xml, "sbrowserx_privacy_webrtc", "WebRTC IP handling policy",
                "WebRTC leak protection", targetPkg, WEBRTC_DIALOG_ACTIVITY_CLASS,
            )
            val fpPref = createSwitchPref(
                xml, "sbrowserx_privacy_fingerprint", "Fingerprint protection",
                "Native WebAudio & Screen anti-fingerprinting", true,
            )
            val nodes = screen.childNodes
            var inserted = false
            for (i in 0 until nodes.length) {
                val node = nodes.item(i) as? Element ?: continue
                if (node.getAttributeNS(ANDROID_NS, "key") == "enhanced_clean_category") {
                    screen.insertBefore(cat, node)
                    screen.insertBefore(webrtcPref, node)
                    screen.insertBefore(fpPref, node)
                    inserted = true
                    break
                }
            }
            if (!inserted) {
                screen.appendChild(cat)
                screen.appendChild(webrtcPref)
                screen.appendChild(fpPref)
            }
        }

        // 3. Sites & Downloads: External downloader dialog + native in-place switch for images
        document("res/xml/sites_content_preferences.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in sites_content_preferences.xml")

            screen.appendChild(
                createIntentPref(
                    xml, "sbrowserx_external_downloader", "External download manager",
                    "ADM, 1DM, ABDM, Seal", targetPkg, EXT_DL_DIALOG_ACTIVITY_CLASS,
                ),
            )
            screen.appendChild(
                createSwitchPref(
                    xml, "sbrowserx_external_downloader_images", "Also forward images to downloader",
                    "ON: all files & images. OFF: browser saves images natively", false,
                ),
            )
        }

        // 4. Dark Mode: Native in-place switches (AMOLED, Vivid Images, Force Day Dark)
        // Neutralize Samsung's Compact-mode statusbar & navigationbar white/grey gradient drawables
        // (eliminates the white/grey top & bottom stripes and QuickAccess wallpaper haze everywhere).
        listOf(
            "res/drawable/navigationbar_gradient_background.xml",
            "res/drawable/navigationbar_gradient_background_dark.xml",
            "res/drawable/statusbar_gradient_background.xml",
            "res/drawable/statusbar_gradient_background_dark.xml",
        ).forEach { drawablePath ->
            try {
                document(drawablePath).use { xml ->
                    val gradients = xml.getElementsByTagName("gradient")
                    for (i in 0 until gradients.length) {
                        val el = gradients.item(i) as? Element ?: continue
                        el.setAttribute("android:startColor", "#00000000")
                        el.setAttribute("android:endColor", "#00000000")
                    }
                }
            } catch (_: Throwable) {}
        }

        document("res/xml/manage_dark_mode_preference.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in manage_dark_mode_preference.xml")

            screen.appendChild(
                createSwitchPref(
                    xml, "sbrowserx_dark_amoled", "AMOLED Mode",
                    "Pure #000000 for browser UI and darkened websites", false,
                ),
            )
        }

        // 5. Layout & Menu: In-place One UI dialogs for Panel Transparency & Compact AI-slot button
        document("res/xml/layout_preferences.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in layout_preferences.xml")

            screen.appendChild(createCategory(xml, "sbrowserx_layout_category", "SBrowserX"))
            screen.appendChild(
                createIntentPref(
                    xml, "sbrowserx_layout_transparency", "Panel & address bar translucency",
                    "Bottom bar, address bar, compact pill & side menu blur",
                    targetPkg, TRANSPARENCY_DIALOG_ACTIVITY_CLASS,
                ),
            )
            screen.appendChild(
                createIntentPref(
                    xml, "sbrowserx_layout_compact_button", "Compact bar custom button (replaces AI)",
                    "New tab",
                    targetPkg, COMPACT_BTN_DIALOG_ACTIVITY_CLASS,
                ),
            )
        }

        // 6. Useful Features: Built-in Translator settings screen
        document("res/xml/useful_feature_preferences.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in useful_feature_preferences.xml")

            screen.appendChild(createCategory(xml, "sbrowserx_useful_category", "SBrowserX"))
            screen.appendChild(
                createIntentPref(
                    xml, "sbrowserx_useful_translate", "Built-in Translator (SBrowserX)",
                    "Google, Microsoft, DeepL, Gemini 2.5 Flash",
                    targetPkg, TRANSLATE_SETTINGS_ACTIVITY_CLASS,
                ),
            )
            screen.appendChild(
                createSwitchPref(
                    xml, "sbrowserx_unlimited_tabs", "Remove 99-tab limit",
                    "Allows up to 9999 open tabs instead of 99", true,
                ),
            )
        }
    }
}

private fun ensureActivity(
    manifest: org.w3c.dom.Document,
    application: Element,
    className: String,
    theme: String,
) {
    val activities = application.getElementsByTagName("activity")
    for (i in 0 until activities.length) {
        val el = activities.item(i) as? Element ?: continue
        if (el.getAttributeNS(ANDROID_NS, "name") == className) return
    }
    val el = manifest.createElement("activity")
    el.setAttributeNS(ANDROID_NS, "android:name", className)
    el.setAttributeNS(ANDROID_NS, "android:exported", "false")
    el.setAttributeNS(ANDROID_NS, "android:theme", theme)
    application.appendChild(el)
}

@Suppress("unused")
val sbrowserXCorePatch = bytecodePatch(
    name = "SBrowserX Settings, Extensions & Privacy Core",
    description = "Adds SBrowserX settings hub and mirrors all toggles natively in-place across Samsung Internet's settings sections.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    extendWith("extensions/sbrowserx.rve")
    dependsOn(sbrowserXCoreResourcePatch)

    execute {
        findMethodOrThrow(
            classType = SBROWSER_APP_CLASS,
            methodName = "onCreate",
            parameterTypes = emptyList(),
            returnType = "V",
        ).addInstruction(
            0,
            "invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->onApplicationCreate(Landroid/app/Application;)V",
        )

        findMethodOrThrow(
            classType = SBROWSER_MAIN_ACTIVITY_CLASS,
            methodName = "onResume",
            parameterTypes = emptyList(),
            returnType = "V",
        ).addInstruction(
            0,
            "invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->onMainActivityResume(Landroid/app/Activity;)V",
        )

        val cmdMethod = findMethodOrThrow(
            classType = SBROWSER_CMDLINE_CLASS,
            methodName = "setCustomFeatures",
            parameterTypes = emptyList(),
            returnType = "V",
        )
        val cmdInsns = cmdMethod.implementation?.instructions
            ?: error("Missing implementation in SBrowserCommandLine.setCustomFeatures")
        cmdMethod.addInstruction(
            cmdInsns.size - 1,
            "invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->onCommandLineCustomFeatures()V",
        )

        findMethodOrThrow(
            classType = BADGE_MANAGER_CLASS,
            methodName = "getNewExtensionCount",
            parameterTypes = emptyList(),
            returnType = "I",
        ).returnConstantBoolean(false)

        val setScreenMethod = findMethodOrThrow(
            classType = PREF_FRAGMENT_CUSTOM_CLASS,
            methodName = "setPreferenceScreen",
            parameterTypes = listOf("Landroidx/preference/PreferenceScreen;"),
            returnType = "V",
        )
        val setScreenInsns = setScreenMethod.implementation?.instructions
            ?: error("Missing instructions in PreferenceFragmentCustom.setPreferenceScreen")
        setScreenMethod.addInstruction(
            setScreenInsns.size - 1,
            "invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->localizeCustomPreferences(Ljava/lang/Object;)V",
        )

        findMethodOrThrow(
            classType = PREF_FRAGMENT_CUSTOM_CLASS,
            methodName = "onViewCreated",
            parameterTypes = listOf("Landroid/view/View;", "Landroid/os/Bundle;"),
            returnType = "V",
        ).addInstruction(
            0,
            "invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->localizeCustomPreferences(Ljava/lang/Object;)V",
        )

        findMethodOrThrow(
            classType = PREF_FRAGMENT_CUSTOM_CLASS,
            methodName = "onResume",
            parameterTypes = emptyList(),
            returnType = "V",
        ).addInstruction(
            0,
            "invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->localizeCustomPreferences(Ljava/lang/Object;)V",
        )

        // Permanently disable Samsung's built-in Ad Blockers menu & partner recommendations
        findMethodOrThrow(
            classType = DEBUG_SETTINGS_CLASS,
            methodName = "isContentBlockerSupported",
            parameterTypes = emptyList(),
            returnType = "Z",
        ).returnConstantBoolean(false)

        // Hook PrivacyPreferenceFragment.showPreferenceOnReady()V so Russian localization applies
        // immediately when privacy_preferences.xml is inflated asynchronously (zero English flash!)
        val showReadyMethod = findMethodOrThrow(
            classType = PRIVACY_PREF_FRAGMENT_CLASS,
            methodName = "showPreferenceOnReady",
            parameterTypes = emptyList(),
            returnType = "V",
        )
        showReadyMethod.addInstruction(
            0,
            "invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->recordPrivacyFragment(Ljava/lang/Object;)V",
        )
        val showReadyInsns = showReadyMethod.implementation?.instructions
            ?: error("Missing instructions in PrivacyPreferenceFragment.showPreferenceOnReady")
        showReadyMethod.addInstruction(
            showReadyInsns.size - 1,
            "invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->localizePrivacyFragmentFromTL()V",
        )

        // Intercept More Menu -> "Browsing assist" click to launch Gemini 2.5 Flash AI Page Summary
        findMethodOrThrow(
            classType = APP_MENU_HANDLER_CLASS,
            methodName = "onBrowsingAssistClicked",
            parameterTypes = emptyList(),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->handleBrowsingAssistClick(Ljava/lang/Object;)Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_assist
                return-void
                :sbrowserx_stock_assist
                nop
            """.trimIndent(),
        )
    }
}
