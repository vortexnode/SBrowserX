package dev.vortexnode.sbrowserx.patches.extensions

import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow
import dev.vortexnode.sbrowserx.shared.returnConstantBoolean

private const val SIX_STATUS_CLASS =
    "Lcom/sec/android/app/sbrowser/common/extensions/SixStatus;"

@Suppress("unused")
val unlockExtensionsPatch = bytecodePatch(
    name = "Unlock Chrome Web Store & Extensions",
    description = "Unlocks the built-in extensions WebUI, toolbar container, pinning, and Chrome Web Store support.",
    use = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)

    execute {
        listOf(
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
    }
}
