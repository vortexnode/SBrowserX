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
private const val HIDE_TOOLBAR_COMPACT_CLASS =
    "Lcom/sec/android/app/sbrowser/hide_toolbar/HideToolbarHandlerCompact;"
private const val CUSTOMIZE_MENU_MODEL_CLASS =
    "Lcom/sec/android/app/sbrowser/common/customize_toolbar/CustomizeMenuModel;"

@Suppress("unused")
val unlockCompactLayoutPatch = bytecodePatch(
    name = "Unlock Compact Layout",
    description = "Restores Samsung Internet's native Compact toolbar layout, fixes More-Menu height oscillation and Compact pill scroll/refresh jitter, and replaces the Galaxy AI button with a user-configurable button.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        // 1. DebugSettings.isCompactTypeSupported()Z (.locals 2)
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

        // 3. HideToolbarHandlerCompact.updateToolbarByBottomOffsetY(SBrowserTabDelegate, F, F)Z (.locals 3)
        // Eliminates Android-View <-> GPU-Bitmap swap jitter on all websites by smoothly translating
        // the live Compact pill directly with a 4px deadzone and loading lock.
        val compactOffsetMethod = findMethodOrThrow(
            classType = HIDE_TOOLBAR_COMPACT_CLASS,
            methodName = "updateToolbarByBottomOffsetY",
            parameterTypes = listOf(
                "Lcom/sec/android/app/sbrowser/common/model/sbrowser_tab/SBrowserTabDelegate;",
                "F",
                "F",
            ),
            returnType = "Z",
        )
        compactOffsetMethod.addInstructions(
            0,
            """
                invoke-static {p0, p1, p2}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->handleCompactBottomOffsetY(Ljava/lang/Object;Ljava/lang/Object;F)Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_compact_offset
                return v0
                :sbrowserx_stock_compact_offset
                nop
            """.trimIndent(),
        )

        // 4. HideToolbarHandlerCompact.resetBottomControlViewPosition()V (.locals 1)
        val compactResetMethod = findMethodOrThrow(
            classType = HIDE_TOOLBAR_COMPACT_CLASS,
            methodName = "resetBottomControlViewPosition",
            parameterTypes = emptyList(),
            returnType = "V",
        )
        compactResetMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->resetCompactBarTranslation(Ljava/lang/Object;)V
            """.trimIndent(),
        )

        // 5. HideToolbarHandlerCompact.adjustToolbarScrollByBottomControls(SBrowserTabDelegate, F, F)V (.locals 2)
        // Prevents Samsung's unfinished 0.85x scale/fade jitter from ever firing on the Compact pill.
        val compactScrollMethod = findMethodOrThrow(
            classType = HIDE_TOOLBAR_COMPACT_CLASS,
            methodName = "adjustToolbarScrollByBottomControls",
            parameterTypes = listOf(
                "Lcom/sec/android/app/sbrowser/common/model/sbrowser_tab/SBrowserTabDelegate;",
                "F",
                "F",
            ),
            returnType = "V",
        )
        compactScrollMethod.addInstructions(
            0,
            """
                invoke-static {p0, p1}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->shouldStabilizeCompactBarOnLoad(Ljava/lang/Object;Ljava/lang/Object;)Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_compact_scroll
                return-void
                :sbrowserx_stock_compact_scroll
                nop
            """.trimIndent(),
        )

        // 6. CustomizeMenuModel.getToolbarRightItems()Ljava/util/List; (.locals 0, single return!)
        // Replaces Samsung's Galaxy AI (action_browsing_assist) icon in Compact mode with user-selected button.
        val rightItemsMethod = findMethodOrThrow(
            classType = CUSTOMIZE_MENU_MODEL_CLASS,
            methodName = "getToolbarRightItems",
            parameterTypes = emptyList(),
            returnType = "Ljava/util/List;",
        )
        rightItemsMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->recordCustomizeMenuModel(Ljava/lang/Object;)V
            """.trimIndent(),
        )
        val rightInsns = rightItemsMethod.implementation?.instructions
            ?: error("Missing instructions in getToolbarRightItems")
        rightItemsMethod.addInstructions(
            rightInsns.size - 1,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->replaceCompactAiButton(Ljava/util/List;)Ljava/util/List;
                move-result-object p0
            """.trimIndent(),
        )

        // 7. CustomizeMenuModel.getToolbarLeftItems()Ljava/util/List; (.locals 0, single return!)
        val leftItemsMethod = findMethodOrThrow(
            classType = CUSTOMIZE_MENU_MODEL_CLASS,
            methodName = "getToolbarLeftItems",
            parameterTypes = emptyList(),
            returnType = "Ljava/util/List;",
        )
        leftItemsMethod.addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->recordCustomizeMenuModel(Ljava/lang/Object;)V
            """.trimIndent(),
        )
        val leftInsns = leftItemsMethod.implementation?.instructions
            ?: error("Missing instructions in getToolbarLeftItems")
        leftItemsMethod.addInstructions(
            leftInsns.size - 1,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->replaceCompactAiButton(Ljava/util/List;)Ljava/util/List;
                move-result-object p0
            """.trimIndent(),
        )
    }
}
