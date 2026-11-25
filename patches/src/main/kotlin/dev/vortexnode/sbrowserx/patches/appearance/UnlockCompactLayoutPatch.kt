package dev.vortexnode.sbrowserx.patches.appearance

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val DEBUG_SETTINGS_CLASS =
    "Lcom/sec/android/app/sbrowser/common/device/DebugSettings;"

@Suppress("unused")
val unlockCompactLayoutPatch = bytecodePatch(
    name = "Unlock Compact Layout",
    description = "Restores Samsung Internet's native Compact toolbar layout option in Layout and Menu settings (single floating bottom address + navigation bar).",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)

    execute {
        // DebugSettings.isCompactTypeSupported()Z (.locals 2)
        // Enables the stock "Compact" option in LayoutSelectionPreference,
        // LayoutPreferenceFragment, DeviceFeatureUtils, and BrowserSettings.
        val compactMethod = findMethodOrThrow(
            classType = DEBUG_SETTINGS_CLASS,
            methodName = "isCompactTypeSupported",
            parameterTypes = emptyList(),
            returnType = "Z",
        )
        compactMethod.addInstructions(
            0,
            """
                const/4 v0, 0x1
                return v0
            """.trimIndent(),
        )
    }
}
