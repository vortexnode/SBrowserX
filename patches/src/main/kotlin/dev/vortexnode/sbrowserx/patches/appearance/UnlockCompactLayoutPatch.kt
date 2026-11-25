package dev.vortexnode.sbrowserx.patches.appearance

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val DEBUG_SETTINGS_CLASS =
    "Lcom/sec/android/app/sbrowser/common/device/DebugSettings;"
private const val MORE_MENU_HANDLER_CLASS =
    "Lcom/sec/android/app/sbrowser/toolbar/MoreMenuHandler;"

@Suppress("unused")
val unlockCompactLayoutPatch = bytecodePatch(
    name = "Unlock Compact Layout",
    description = "Restores Samsung Internet's native Compact toolbar layout option in Layout and Menu settings and fixes the Compact More-Menu popup height oscillation bug.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        // 1. DebugSettings.isCompactTypeSupported()Z (.locals 2)
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

        // 2. MoreMenuHandler.getHeight()I (.locals 4)
        // Fixes Samsung's stock bug where mBottomMenuLayout height was omitted from the
        // content-height comparison against maxHeight, causing infinite maxH <-> WRAP_CONTENT
        // oscillation on every layout pass / scroll in Compact mode.
        val menuHeightMethod = findMethodOrThrow(
            classType = MORE_MENU_HANDLER_CLASS,
            methodName = "getHeight",
            parameterTypes = emptyList(),
            returnType = "I",
        )
        menuHeightMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->computeStableCompactMenuHeight(Ljava/lang/Object;)I
                move-result v0
                if-eqz v0, :sbrowserx_stock_menu_height
                return v0
                :sbrowserx_stock_menu_height
                nop
            """.trimIndent(),
        )
    }
}
