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

@Suppress("unused")
val changePackageAndAppNamePatch = resourcePatch(
    name = "Change package and app name",
    description = "Changes the package name and app display name so SBrowserX can be installed alongside stock Samsung Internet.",
    use = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)

    val targetPackageName by stringOption(
        key = "packageName",
        default = SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME,
        title = "Package name",
        description = "Custom Android package name for the patched browser.",
        required = true,
    )

    val targetAppName by stringOption(
        key = "appName",
        default = SBROWSER_DEFAULT_APP_NAME,
        title = "App name",
        description = "Custom launcher display name for the patched browser.",
        required = true,
    )

    execute {
        val newPackage = targetPackageName!!.trim()
        val newAppName = targetAppName!!.trim()
        require(PACKAGE_NAME_REGEX.matches(newPackage)) {
            "Invalid package name: '$newPackage'"
        }
        require(newAppName.isNotEmpty()) {
            "App name cannot be blank"
        }

        document("AndroidManifest.xml").use { manifest ->
            val root = manifest.documentElement
                ?: error("AndroidManifest.xml is missing root <manifest> element")

            if (newPackage != SBROWSER_PACKAGE_NAME) {
                root.setAttribute("package", newPackage)
                rewriteManifestReferences(root, SBROWSER_PACKAGE_NAME, newPackage)
            }

            val application = root.getElementsByTagName("application").item(0) as? Element
                ?: error("AndroidManifest.xml is missing <application> element")
            application.setAttributeNS(ANDROID_NS, "android:label", newAppName)
        }
    }
}

private fun rewriteManifestReferences(
    root: Element,
    oldPkg: String,
    newPkg: String,
) {
    val allElements = root.getElementsByTagName("*")
    for (i in 0 until allElements.length) {
        val el = allElements.item(i) as? Element ?: continue
        when (el.tagName) {
            "provider" -> {
                val authorities = el.getAttributeNS(ANDROID_NS, "authorities")
                if (authorities.isNotEmpty() && authorities.contains(oldPkg)) {
                    val updated = authorities
                        .split(";")
                        .joinToString(";") { part ->
                            if (part == oldPkg || part.startsWith("$oldPkg.")) {
                                newPkg + part.removePrefix(oldPkg)
                            } else {
                                part
                            }
                        }
                    el.setAttributeNS(ANDROID_NS, "android:authorities", updated)
                }
                rewritePermissionAttr(el, "readPermission", oldPkg, newPkg)
                rewritePermissionAttr(el, "writePermission", oldPkg, newPkg)
                rewritePermissionAttr(el, "permission", oldPkg, newPkg)
            }
            "permission", "uses-permission" -> {
                rewritePermissionAttr(el, "name", oldPkg, newPkg)
            }
            "activity", "activity-alias", "service", "receiver" -> {
                rewritePermissionAttr(el, "permission", oldPkg, newPkg)
            }
        }
    }
}

private fun rewritePermissionAttr(
    el: Element,
    attrLocalName: String,
    oldPkg: String,
    newPkg: String,
) {
    val value = el.getAttributeNS(ANDROID_NS, attrLocalName)
    if (value.startsWith("$oldPkg.")) {
        el.setAttributeNS(ANDROID_NS, "android:$attrLocalName", newPkg + value.removePrefix(oldPkg))
    }
}
