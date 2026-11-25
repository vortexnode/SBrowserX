package dev.vortexnode.sbrowserx.patches.appearance

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val DEVICE_FEATURE_UTILS_CLASS =
    "Lcom/sec/android/app/sbrowser/common/device/DeviceFeatureUtils;"
private const val BOTTOMBAR_CLASS =
    "Lcom/sec/android/app/sbrowser/toolbar/Bottombar;"

@Suppress("unused")
val bottomBarBlurAndAmoledPatch = bytecodePatch(
    name = "AMOLED Black & Bottom Bar Transparency Toggle",
    description = "Adds runtime hooks for AMOLED pure black (#000000) dark mode surfaces and the toggle to disable/restore bottom navigation bar translucency and blur.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        // 1. Hook DeviceFeatureUtils.isToolbarBlurSupported()Z
        val blurMethod = findMethodOrThrow(
            classType = DEVICE_FEATURE_UTILS_CLASS,
            methodName = "isToolbarBlurSupported",
            parameterTypes = emptyList(),
            returnType = "Z",
        )
        val blurInsns = blurMethod.implementation?.instructions
            ?: error("Missing instructions in isToolbarBlurSupported")
        val returnIdx = blurInsns.size - 1
        blurMethod.addInstructions(
            returnIdx,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->isToolbarBlurAllowed(Z)Z
                move-result p0
            """.trimIndent(),
        )

        // 2. Hook Bottombar.getBackgroundColor(ThemeParam)I for pure AMOLED black (#FF000000)
        val bgMethod = findMethodOrThrow(
            classType = BOTTOMBAR_CLASS,
            methodName = "getBackgroundColor",
            parameterTypes = listOf("Lcom/sec/android/app/sbrowser/common/toolbar/ThemeParam;"),
            returnType = "I",
        )
        bgMethod.addInstructions(
            0,
            """
                invoke-virtual {p1}, Lcom/sec/android/app/sbrowser/common/toolbar/ThemeParam;->isNightMode()Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_bg
                invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->isAmoledBlackEnabled()Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_bg
                const/high16 v0, -0x1000000
                return v0
                :sbrowserx_stock_bg
                nop
            """.trimIndent(),
        )
    }
}
