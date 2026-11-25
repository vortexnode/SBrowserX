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
private const val WEBRTC_DIALOG_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXWebRtcDialogActivity"
private const val EXT_BRIDGE_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXExtensionsBridgeActivity"
private const val EXT_DL_DIALOG_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXExternalDownloaderDialogActivity"

internal val sbrowserXCoreResourcePatch = resourcePatch(
    description = "Registers SBrowserX activities, adds SBrowserX & Extensions to Settings, WebRTC card in Privacy, and External Downloader in Sites & Downloads.",
) {
    finalize {
        val targetPkg = SBrowserPackageRenameTarget.packageName

        document("AndroidManifest.xml").use { manifest ->
            val root = manifest.documentElement ?: error("Missing <manifest>")
            val application = root.getElementsByTagName("application").item(0) as? Element
                ?: error("Missing <application>")

            ensureActivity(manifest, application, SETTINGS_ACTIVITY_CLASS, "@android:style/Theme.DeviceDefault.NoActionBar")
            ensureActivity(manifest, application, TRANSLATE_SETTINGS_ACTIVITY_CLASS, "@android:style/Theme.DeviceDefault.NoActionBar")
            ensureActivity(manifest, application, WEBRTC_DIALOG_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
            ensureActivity(manifest, application, EXT_BRIDGE_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
            ensureActivity(manifest, application, EXT_DL_DIALOG_ACTIVITY_CLASS, "@android:style/Theme.Translucent.NoTitleBar")
        }

        document("res/xml/settings_fragment.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in settings_fragment.xml")

            val hubPref = xml.createElement("com.sec.android.app.sbrowser.common.settings.PreferenceCustom")
            hubPref.setAttributeNS(ANDROID_NS, "android:key", "sbrowserx_settings_hub")
            hubPref.setAttributeNS(ANDROID_NS, "android:title", "SBrowserX  \u25CF")
            hubPref.setAttributeNS(ANDROID_NS, "android:summary", "Extensions, WebRTC, Translate, Downloads & AMOLED")
            val hubIntent = xml.createElement("intent")
            hubIntent.setAttributeNS(ANDROID_NS, "android:targetPackage", targetPkg)
            hubIntent.setAttributeNS(ANDROID_NS, "android:targetClass", SETTINGS_ACTIVITY_CLASS)
            hubPref.appendChild(hubIntent)

            val extPref = xml.createElement("com.sec.android.app.sbrowser.common.settings.PreferenceCustom")
            extPref.setAttributeNS(ANDROID_NS, "android:key", "sbrowserx_settings_extensions")
            extPref.setAttributeNS(ANDROID_NS, "android:title", "@string/extensions_title")
            extPref.setAttributeNS(ANDROID_NS, "android:summary", "internet://extensions & Chrome Web Store")
            val extIntent = xml.createElement("intent")
            extIntent.setAttributeNS(ANDROID_NS, "android:targetPackage", targetPkg)
            extIntent.setAttributeNS(ANDROID_NS, "android:targetClass", EXT_BRIDGE_ACTIVITY_CLASS)
            extPref.appendChild(extIntent)

            val firstChild = screen.firstChild
            if (firstChild != null) {
                screen.insertBefore(hubPref, firstChild)
                screen.insertBefore(extPref, firstChild)
            } else {
                screen.appendChild(hubPref)
                screen.appendChild(extPref)
            }
        }

        document("res/xml/privacy_preferences.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in privacy_preferences.xml")

            val cat = xml.createElement("androidx.preference.PreferenceCategory")
            cat.setAttributeNS(ANDROID_NS, "android:selectable", "false")
            cat.setAttributeNS(ANDROID_NS, "android:key", "sbrowserx_webrtc_card_category")

            val pref = xml.createElement("com.sec.android.app.sbrowser.common.settings.PreferenceCustom")
            pref.setAttributeNS(ANDROID_NS, "android:key", "sbrowserx_privacy_webrtc")
            pref.setAttributeNS(ANDROID_NS, "android:title", "WebRTC IP handling policy")
            pref.setAttributeNS(ANDROID_NS, "android:summary", "SBrowserX \u2022 WebRTC leak protection")

            val intent = xml.createElement("intent")
            intent.setAttributeNS(ANDROID_NS, "android:targetPackage", targetPkg)
            intent.setAttributeNS(ANDROID_NS, "android:targetClass", WEBRTC_DIALOG_ACTIVITY_CLASS)
            pref.appendChild(intent)

            val nodes = screen.childNodes
            var inserted = false
            for (i in 0 until nodes.length) {
                val node = nodes.item(i) as? Element ?: continue
                if (node.getAttributeNS(ANDROID_NS, "key") == "enhanced_clean_category") {
                    screen.insertBefore(cat, node)
                    screen.insertBefore(pref, node)
                    inserted = true
                    break
                }
            }
            if (!inserted) {
                screen.appendChild(cat)
                screen.appendChild(pref)
            }
        }

        document("res/xml/sites_content_preferences.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in sites_content_preferences.xml")

            val pref = xml.createElement("com.sec.android.app.sbrowser.common.settings.PreferenceCustom")
            pref.setAttributeNS(ANDROID_NS, "android:key", "sbrowserx_external_downloader")
            pref.setAttributeNS(ANDROID_NS, "android:title", "External download manager")
            pref.setAttributeNS(ANDROID_NS, "android:summary", "SBrowserX \u2022 ADM, 1DM, etc.")

            val intent = xml.createElement("intent")
            intent.setAttributeNS(ANDROID_NS, "android:targetPackage", targetPkg)
            intent.setAttributeNS(ANDROID_NS, "android:targetClass", EXT_DL_DIALOG_ACTIVITY_CLASS)
            pref.appendChild(intent)

            screen.appendChild(pref)
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
    description = "Adds SBrowserX settings hub (15 languages, One UI blue switches, teal badges), native .crx/.zip installer, 6-extension pack, WebRTC card in Privacy, External Downloader in Sites & Downloads, reversible Hide Samsung Ad Blockers toggle, and fixes Extensions Manager launch.",
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


        findMethodOrThrow(
            classType = PREF_FRAGMENT_CUSTOM_CLASS,
            methodName = "onResume",
            parameterTypes = emptyList(),
            returnType = "V",
        ).addInstruction(
            0,
            "invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->localizeCustomPreferences(Ljava/lang/Object;)V",
        )

        findMethodOrThrow(
            classType = DEBUG_SETTINGS_CLASS,
            methodName = "isContentBlockerSupported",
            parameterTypes = emptyList(),
            returnType = "Z",
        ).addInstructions(
            0,
            """
                invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->isHideSamsungAdBlockersEnabled()Z
                move-result v0
                if-eqz v0, :sbrowserx_cb_stock
                const/4 v0, 0x0
                return v0
                :sbrowserx_cb_stock
                nop
            """.trimIndent(),
        )
    }
}
