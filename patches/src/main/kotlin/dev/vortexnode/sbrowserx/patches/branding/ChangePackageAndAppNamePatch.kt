package dev.vortexnode.sbrowserx.patches.branding

import app.morphe.patcher.patch.resourcePatch
import app.morphe.patcher.patch.stringOption
import dev.vortexnode.sbrowserx.shared.Constants.ANDROID_NS
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.Constants.SBROWSER_DEFAULT_APP_NAME
import dev.vortexnode.sbrowserx.shared.Constants.SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME
import dev.vortexnode.sbrowserx.shared.Constants.SBROWSER_PACKAGE_NAME
import org.w3c.dom.Element

private val PACKAGE_NAME_REGEX = Regex("^[a-zA-Z][a-zA-Z0-9_]*(\\.[a-zA-Z][a-zA-Z0-9_]*)+$")
private const val LEGACY_BROWSER_PREFIX = "com.sec.android.app.browser"

@Suppress("unused")
val changePackageAndAppNamePatch = resourcePatch(
    name = "Change package and app name",
    description = "Changes the package name, custom permissions, provider authorities, and app display name so SBrowserX can be installed cleanly alongside stock Samsung Internet.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)

    val targetPackageName = stringOption(
        key = "package_name",
        default = SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME,
        values = linkedMapOf(
            SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME to SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME,
            SBROWSER_PACKAGE_NAME to SBROWSER_PACKAGE_NAME,
        ),
        title = "Package name",
        description = "Custom Android package name for the patched browser.",
        required = true,
    ) { value ->
        value != null && PACKAGE_NAME_REGEX.matches(value.trim())
    }

    val targetAppName = stringOption(
        key = "app_name",
        default = SBROWSER_DEFAULT_APP_NAME,
        values = linkedMapOf(
            SBROWSER_DEFAULT_APP_NAME to SBROWSER_DEFAULT_APP_NAME,
            "Samsung Internet" to "Samsung Internet",
        ),
        title = "App name",
        description = "Custom launcher display name for the patched browser.",
        required = true,
    ) { value ->
        !value.isNullOrBlank()
    }

    finalize {
        val newPackage = checkNotNull(targetPackageName.value).trim()
        val newAppName = checkNotNull(targetAppName.value).trim()
        require(PACKAGE_NAME_REGEX.matches(newPackage)) {
            "Invalid package name: '$newPackage'"
        }
        require(newAppName.isNotEmpty()) {
            "App name cannot be blank"
        }

        document("AndroidManifest.xml").use { manifest ->
            val root = manifest.documentElement
                ?: error("AndroidManifest.xml is missing root <manifest> element")

            val application = root.getElementsByTagName("application").item(0) as? Element
                ?: error("AndroidManifest.xml is missing <application> element")

            if (newPackage != SBROWSER_PACKAGE_NAME) {
                root.setAttribute("package", newPackage)
                rewriteManifestForClone(root, application, SBROWSER_PACKAGE_NAME, newPackage)
            }

            application.setAttributeNS(ANDROID_NS, "android:label", newAppName)
        }
    }
}

private fun rewriteManifestForClone(
    root: Element,
    application: Element,
    oldPkg: String,
    newPkg: String,
) {
    // 1. Rename EVERY declared <permission> (including com.sec.android.app.browser.permission.*)
    val declaredPermRemap = mutableMapOf<String, String>()
    val permissionNodes = root.getElementsByTagName("permission")
    for (i in 0 until permissionNodes.length) {
        val el = permissionNodes.item(i) as? Element ?: continue
        val permName = el.getAttributeNS(ANDROID_NS, "name")
        if (permName.isEmpty()) continue
        val renamed = when {
            permName.startsWith("$oldPkg.") -> newPkg + permName.removePrefix(oldPkg)
            permName.startsWith("$LEGACY_BROWSER_PREFIX.") ->
                "$newPkg.legacy" + permName.removePrefix(LEGACY_BROWSER_PREFIX)
            else -> "$newPkg.$permName"
        }
        declaredPermRemap[permName] = renamed
        el.setAttributeNS(ANDROID_NS, "android:name", renamed)
    }

    // 2. Rewrite permission references across <uses-permission>, <activity>, <service>, <receiver>, <provider>
    val allElements = root.getElementsByTagName("*")
    for (i in 0 until allElements.length) {
        val el = allElements.item(i) as? Element ?: continue
        when (el.tagName) {
            "uses-permission" -> remapAttrIfPresent(el, "name", declaredPermRemap)
            "activity", "activity-alias", "service", "receiver" ->
                remapAttrIfPresent(el, "permission", declaredPermRemap)
            "provider" -> {
                remapAttrIfPresent(el, "permission", declaredPermRemap)
                remapAttrIfPresent(el, "readPermission", declaredPermRemap)
                remapAttrIfPresent(el, "writePermission", declaredPermRemap)
            }
        }
    }

    // 3. Rewrite EVERY <provider> authority inside <application> (never touching <queries>)
    val appProviders = application.getElementsByTagName("provider")
    for (i in 0 until appProviders.length) {
        val el = appProviders.item(i) as? Element ?: continue
        val authorities = el.getAttributeNS(ANDROID_NS, "authorities")
        if (authorities.isEmpty()) continue
        val updated = authorities
            .split(";")
            .joinToString(";") { part ->
                val trimmed = part.trim()
                when {
                    trimmed.isEmpty() -> trimmed
                    trimmed == oldPkg || trimmed.startsWith("$oldPkg.") ->
                        newPkg + trimmed.removePrefix(oldPkg)
                    trimmed.startsWith("$newPkg.") -> trimmed
                    else -> "$newPkg.$trimmed"
                }
            }
        el.setAttributeNS(ANDROID_NS, "android:authorities", updated)
    }
}

private fun remapAttrIfPresent(
    el: Element,
    attrLocalName: String,
    remap: Map<String, String>,
) {
    val current = el.getAttributeNS(ANDROID_NS, attrLocalName)
    val updated = remap[current]
    if (updated != null) {
        el.setAttributeNS(ANDROID_NS, "android:$attrLocalName", updated)
    }
}
