package dev.vortexnode.sbrowserx.patches.appearance

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val DARK_MODE_UTILS_CLASS =
    "Lcom/sec/android/app/sbrowser/common/device/DarkModeUtils;"
private const val TERRACE_PREF_BRIDGE_CLASS =
    "Lcom/sec/terrace/browser/prefs/TerracePrefServiceBridge;"

@Suppress("unused")
val forceAdaptiveDarkModePatch = bytecodePatch(
    name = "Adaptive Dark Mode & Vivid Images Fix",
    description = "Eliminates Samsung Internet's washed-out/dimmed images in dark mode (disables NightDim and restores 100% VAX contrast + Chromium CIELAB selective dark engine) and adds an optional toggle for adaptive website dark mode.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        // 1. DarkModeUtils.isContentDarkModeEnabled()Z (.locals 3)
        // Forces Chromium/Terrace web content dark mode ON even in Light UI mode when enabled.
        val contentDarkMethod = findMethodOrThrow(
            classType = DARK_MODE_UTILS_CLASS,
            methodName = "isContentDarkModeEnabled",
            parameterTypes = emptyList(),
            returnType = "Z",
        )
        contentDarkMethod.addInstructions(
            0,
            """
                invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->isForceAdaptiveDarkEnabled()Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_dark
                const/4 v0, 0x1
                return v0
                :sbrowserx_stock_dark
                nop
            """.trimIndent(),
        )

        // 2. DarkModeUtils.getWebDarkModeBrightness()F (.locals 2)
        // Restores full 1.0f brightness instead of Samsung's washed-out 0.5f default.
        val brightnessMethod = findMethodOrThrow(
            classType = DARK_MODE_UTILS_CLASS,
            methodName = "getWebDarkModeBrightness",
            parameterTypes = emptyList(),
            returnType = "F",
        )
        brightnessMethod.addInstructions(
            0,
            """
                invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->isDarkBrightImagesEnabled()Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_bright
                const/high16 v0, 0x3f800000
                return v0
                :sbrowserx_stock_bright
                nop
            """.trimIndent(),
        )

        // 3. TerracePrefServiceBridge.setForceDarkBehavior(I)V (.locals 1)
        // Uses adaptive behavior (2 = prefer website's native dark theme, selective force-dark fallback)
        // when either Vivid Images or Force Adaptive Dark Mode is enabled.
        val forceDarkMethod = findMethodOrThrow(
            classType = TERRACE_PREF_BRIDGE_CLASS,
            methodName = "setForceDarkBehavior",
            parameterTypes = listOf("I"),
            returnType = "V",
        )
        forceDarkMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->adjustForceDarkBehavior(I)I
                move-result p0
            """.trimIndent(),
        )

        // 4. TerracePrefServiceBridge.setUseNightDim(ZI)V (.locals 0)
        // Disables Samsung's NightDim grey veil over web photos/images in dark mode.
        val nightDimMethod = findMethodOrThrow(
            classType = TERRACE_PREF_BRIDGE_CLASS,
            methodName = "setUseNightDim",
            parameterTypes = listOf("Z", "I"),
            returnType = "V",
        )
        nightDimMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->adjustNightDimEnabled(Z)Z
                move-result p0
                invoke-static {p1}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->adjustNightDimGain(I)I
                move-result p1
            """.trimIndent(),
        )

        // 5. TerracePrefServiceBridge.setVAXContrastFactor(I)V (.locals 1)
        // Keeps full 100% image/media vibrancy instead of Samsung's 50% dim factor.
        val vaxMethod = findMethodOrThrow(
            classType = TERRACE_PREF_BRIDGE_CLASS,
            methodName = "setVAXContrastFactor",
            parameterTypes = listOf("I"),
            returnType = "V",
        )
        vaxMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->adjustVaxContrastFactor(I)I
                move-result p0
            """.trimIndent(),
        )
    }
}
