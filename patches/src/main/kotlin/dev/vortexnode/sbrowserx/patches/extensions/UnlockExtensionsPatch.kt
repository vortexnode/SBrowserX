package dev.vortexnode.sbrowserx.patches.extensions

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow
import dev.vortexnode.sbrowserx.shared.returnConstantBoolean

private const val SIX_STATUS_CLASS =
    "Lcom/sec/android/app/sbrowser/common/extensions/SixStatus;"
private const val SIX_PKG_UPDATER_CLASS =
    "Lcom/sec/android/app/sbrowser/extensions/SixPkgUpdater;"
private const val SIX_DEFAULT_LOADER_CLASS =
    "Lcom/sec/android/app/sbrowser/extensions/SixDefaultExtLoader;"

@Suppress("unused")
val unlockExtensionsPatch = bytecodePatch(
    name = "Unlock Chrome Web Store & Extensions",
    description = "Force-enables SIX extensions engine, unlocks extensions WebUI & Chrome Web Store, and prevents SixPkgUpdater from auto-uninstalling sideloaded CRX extensions on restart.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)

    execute {
        listOf(
            "isEnabled",
            "isDefaultExtEnabled",
            "isSupportedExtContainer",
            "isSupportedExtContainerPin",
            "isSupportedExtWebUI",
        ).forEach { methodName ->
            findMethodOrThrow(
                classType = SIX_STATUS_CLASS,
                methodName = methodName,
                parameterTypes = emptyList(),
                returnType = "Z",
            ).returnConstantBoolean(true)
        }

        findMethodOrThrow(
            classType = SIX_STATUS_CLASS,
            methodName = "isDefaultExtEnabled",
            parameterTypes = listOf("Z"),
            returnType = "Z",
        ).returnConstantBoolean(true)

        // Prevent SixPkgUpdater startup sync from auto-uninstalling CRX extensions that have no Samsung SIX APK
        findMethodOrThrow(
            classType = SIX_PKG_UPDATER_CLASS,
            methodName = "handleUninstallExt",
            parameterTypes = listOf("Ljava/lang/String;", "Ljava/lang/String;"),
            returnType = "V",
        ).addInstruction(0, "return-void")

        // Prevent SixDefaultExtLoader from auto-uninstalling built-in extensions (like webtrans.crx)
        findMethodOrThrow(
            classType = SIX_DEFAULT_LOADER_CLASS,
            methodName = "uninstallDefaultExts",
            parameterTypes = emptyList(),
            returnType = "Z",
        ).returnConstantBoolean(false)
    }
}
