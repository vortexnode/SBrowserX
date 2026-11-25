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
private const val WEB_DARK_CUSTOM_HANDLER_CLASS =
    "Lcom/sec/android/app/sbrowser/web_dark_custom/WebDarkCustomHandler;"

@Suppress("unused")
val forceAdaptiveDarkModePatch = bytecodePatch(
    name = "Unified Dark Mode, True AMOLED Websites & Vivid Images",
    description = "Syncs website dark mode cleanly with browser Dark/Light mode (system or manual), eliminates Samsung NightDim & 50% dim veil, preserves true site colors/icons, and applies pure AMOLED #000000 backgrounds to darkened websites when AMOLED Black is ON.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        // 1. DarkModeUtils.isContentDarkModeEnabled()Z (.locals 3)
        // Unified rule: if browser is in Dark Mode (system or manual), sites are dark;
        // if browser is in Light Mode, sites are light (unless Force Day Dark is explicitly ON).
        val contentDarkMethod = findMethodOrThrow(
            classType = DARK_MODE_UTILS_CLASS,
            methodName = "isContentDarkModeEnabled",
            parameterTypes = emptyList(),
            returnType = "Z",
        )
        contentDarkMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->resolveContentDarkModeOverride(Ljava/lang/Object;)I
                move-result v0
                if-ltz v0, :sbrowserx_stock_dark
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

        // 6. WebDarkCustomHandler.applyCustomCss()V (.locals 3)
        // Applies non-destructive AMOLED #000000 background upgrade & media vibrancy guard
        // (never shifts site hues like 4PDA navy, never dims images/icons/bells).
        val applyCssMethod = findMethodOrThrow(
            classType = WEB_DARK_CUSTOM_HANDLER_CLASS,
            methodName = "applyCustomCss",
            parameterTypes = emptyList(),
            returnType = "V",
        )
        applyCssMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->applyAmoledAndColorFixCss(Ljava/lang/Object;)V
            """.trimIndent(),
        )

        // 7. WebDarkCustomHandler.removeCustomCss()V (.locals 2)
        // Cleanly strips our AMOLED overlay when switching back to Light mode.
        val removeCssMethod = findMethodOrThrow(
            classType = WEB_DARK_CUSTOM_HANDLER_CLASS,
            methodName = "removeCustomCss",
            parameterTypes = emptyList(),
            returnType = "V",
        )
        removeCssMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->removeAmoledAndColorFixCss(Ljava/lang/Object;)V
            """.trimIndent(),
        )
    }
}
