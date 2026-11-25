package dev.vortexnode.sbrowserx.patches.core

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.resourcePatch
import dev.vortexnode.sbrowserx.patches.branding.SBrowserPackageRenameTarget
import dev.vortexnode.sbrowserx.shared.Constants.ANDROID_NS
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow
import org.w3c.dom.Element

private const val SBROWSER_APP_CLASS = "Lcom/sec/android/app/sbrowser/SBrowserApplication;"
private const val SETTINGS_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXSettingsActivity"
private const val WEBRTC_DIALOG_ACTIVITY_CLASS =
    "dev.vortexnode.sbrowserx.extension.settings.SBrowserXWebRtcDialogActivity"

internal val sbrowserXCoreResourcePatch = resourcePatch(
    description = "Registers SBrowserX activities in AndroidManifest.xml and injects SBrowserX Hub & WebRTC entry into Settings and Privacy screens.",
) {
    finalize {
        val targetPkg = SBrowserPackageRenameTarget.packageName

        // 1. Register SBrowserXSettingsActivity and SBrowserXWebRtcDialogActivity in AndroidManifest.xml
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

        // 2. Inject SBrowserX Hub at the top of res/xml/settings_fragment.xml
        document("res/xml/settings_fragment.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in settings_fragment.xml")
            val pref = xml.createElement("com.sec.android.app.sbrowser.common.settings.PreferenceCustom")
            pref.setAttributeNS(ANDROID_NS, "android:key", "sbrowserx_settings_hub")
            pref.setAttributeNS(ANDROID_NS, "android:title", "SBrowserX  \u25CF")
            pref.setAttributeNS(ANDROID_NS, "android:summary", "WebRTC, AMOLED, UI & Extensions")

            val intent = xml.createElement("intent")
            intent.setAttributeNS(ANDROID_NS, "android:targetPackage", targetPkg)
            intent.setAttributeNS(ANDROID_NS, "android:targetClass", SETTINGS_ACTIVITY_CLASS)
            pref.appendChild(intent)

            val firstChild = screen.firstChild
            if (firstChild != null) {
                screen.insertBefore(pref, firstChild)
            } else {
                screen.appendChild(pref)
            }
        }

        // 3. Inject native WebRTC IP handling policy duplicate into res/xml/privacy_preferences.xml
        document("res/xml/privacy_preferences.xml").use { xml ->
            val screen = xml.documentElement ?: error("Missing PreferenceScreen in privacy_preferences.xml")
            val pref = xml.createElement("com.sec.android.app.sbrowser.common.settings.PreferenceCustom")
            pref.setAttributeNS(ANDROID_NS, "android:key", "sbrowserx_privacy_webrtc")
            pref.setAttributeNS(ANDROID_NS, "android:title", "WebRTC IP handling policy")
            pref.setAttributeNS(ANDROID_NS, "android:summary", "SBrowserX \u2022 WebRTC leak protection")

            val intent = xml.createElement("intent")
            intent.setAttributeNS(ANDROID_NS, "android:targetPackage", targetPkg)
            intent.setAttributeNS(ANDROID_NS, "android:targetClass", WEBRTC_DIALOG_ACTIVITY_CLASS)
            pref.appendChild(intent)

            // Insert right after anti_tracking_level if present, otherwise append
            val nodes = screen.childNodes
            var inserted = false
            for (i in 0 until nodes.length) {
                val node = nodes.item(i) as? Element ?: continue
                if (node.getAttributeNS(ANDROID_NS, "key") == "anti_tracking_level") {
                    val next = node.nextSibling
                    if (next != null) {
                        screen.insertBefore(pref, next)
                    } else {
                        screen.appendChild(pref)
                    }
                    inserted = true
                    break
                }
            }
            if (!inserted) {
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
    name = "SBrowserX Settings & WebRTC Protection",
    description = "Adds the SBrowserX settings hub (15 languages, teal new-feature badges), system-level WebRTC IP leak protection (duplicated in Privacy), AMOLED Black theme, and bottom navigation bar transparency toggle.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    extendWith("extensions/sbrowserx.rve")
    dependsOn(sbrowserXCoreResourcePatch)

    execute {
        // Hook SBrowserApplication.onCreate() before Terrace/Chromium reads Default/Preferences
        findMethodOrThrow(
            classType = SBROWSER_APP_CLASS,
            methodName = "onCreate",
            parameterTypes = emptyList(),
            returnType = "V",
        ).addInstruction(
            0,
            "invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->onApplicationCreate(Landroid/app/Application;)V",
        )
    }
}
