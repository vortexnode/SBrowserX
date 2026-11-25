package dev.vortexnode.sbrowserx.patches.core

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch
import dev.vortexnode.sbrowserx.patches.branding.SBrowserPackageRenameTarget
import dev.vortexnode.sbrowserx.shared.Constants.ANDROID_NS
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow
import dev.vortexnode.sbrowserx.shared.returnConstantBoolean
import org.w3c.dom.Element

private const val SBROWSER_APP_CLASS = "Lcom/sec/android/app/sbrowser/SBrowserApplication;"
private const val SBROWSER_CMDLINE_CLASS = "Lcom/sec/android/app/sbrowser/init/SBrowserCommandLine;"
private const val BADGE_MANAGER_CLASS = "Lcom/sec/android/app/sbrowser/toolbar/BadgeManager;"
private const val SETTINGS_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXSettingsActivity"
private const val WEBRTC_DIALOG_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXWebRtcDialogActivity"

internal val sbrowserXCoreResourcePatch = resourcePatch(
    description = "Registers SBrowserX activities in AndroidManifest.xml, adds SBrowserX & Extensions to Settings, and puts WebRTC in its own card inside Privacy.",
) {
    finalize {
        val targetPkg = SBrowserPackageRenameTarget.packageName

        document("AndroidManifest.xml").use { manifest ->
            val root = manifest.documentElement ?: error("Missing <manifest>")
            val application = root.getElementsByTagName("application").item(0) as? Element
                ?: error("Missing <application>")

            if (!hasActivity(application, SETTINGS_ACTIVITY_CLASS)) {
                val settingsActivity = manifest.createElement("activity")
                settingsActivity.setAttributeNS(ANDROID_NS, "android:name", SETTINGS_ACTIVITY_CLASS)
                settingsActivity.setAttributeNS(ANDROID_NS, "android:exported", "false")
                settingsActivity.setAttributeNS(
                    ANDROID_NS,
                    "android:theme",
                    "@android:style/Theme.DeviceDefault.NoActionBar",
                )
                application.appendChild(settingsActivity)
            }

            if (!hasActivity(application, WEBRTC_DIALOG_ACTIVITY_CLASS)) {
                val dialogActivity = manifest.createElement("activity")
                dialogActivity.setAttributeNS(ANDROID_NS, "android:name", WEBRTC_DIALOG_ACTIVITY_CLASS)
                dialogActivity.setAttributeNS(ANDROID_NS, "android:exported", "false")
                dialogActivity.setAttributeNS(
                    ANDROID_NS,
                    "android:theme",
                    "@android:style/Theme.Translucent.NoTitleBar",
                )
                application.appendChild(dialogActivity)
            }
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
            extPref.setAttributeNS(ANDROID_NS, "android:title", "@string/extensions")
            extPref.setAttributeNS(ANDROID_NS, "android:summary", "internet://extensions & Chrome Web Store")
            val extIntent = xml.createElement("intent")
            extIntent.setAttributeNS(ANDROID_NS, "android:action", "android.intent.action.VIEW")
            extIntent.setAttributeNS(ANDROID_NS, "android:data", "internet://extensions")
            extIntent.setAttributeNS(ANDROID_NS, "android:targetPackage", targetPkg)
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
    }
}

private fun hasActivity(application: Element, className: String): Boolean {
    val activities = application.getElementsByTagName("activity")
    for (i in 0 until activities.length) {
        val el = activities.item(i) as? Element ?: continue
        if (el.getAttributeNS(ANDROID_NS, "name") == className) return true
    }
    return false
}

@Suppress("unused")
val sbrowserXCorePatch = bytecodePatch(
    name = "SBrowserX Settings, Extensions & Privacy Core",
    description = "Adds SBrowserX settings hub (15 languages, high-contrast switches, teal badges), native .crx/.zip installer, 6-extension pack (+Bitwarden), separate WebRTC card in Privacy, native anti-fingerprint protection, and removes stuck orange Extensions badge.",
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
    }
}
