package dev.vortexnode.sbrowserx.patches.translate

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val SIX_STATUS_CLASS =
    "Lcom/sec/android/app/sbrowser/common/extensions/SixStatus;"
private const val SETTING_PREFERENCE_CLASS =
    "Lcom/sec/android/app/sbrowser/common/device/setting_preference/SettingPreference;"
private const val SIX_TRANS_SERVICE_CLASS =
    "Lcom/sec/android/app/sbrowser/extensions/trans/SixTransService;"

@Suppress("unused")
val googleTranslatePatch = bytecodePatch(
    name = "Built-in Google Translate engine",
    description = "Unlocks the Translate button in the side popup menu without Galaxy AI/SCS, routes page translation through Google Translate, and adds Chrome-style Translate settings.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        // 1. Force SixStatus.isTranslatorEnabled() -> true when Google Translate is enabled
        findMethodOrThrow(
            classType = SIX_STATUS_CLASS,
            methodName = "isTranslatorEnabled",
            parameterTypes = emptyList(),
            returnType = "Z",
        ).addInstructions(
            0,
            """
                invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->isGoogleTranslateEnabled()Z
                move-result v0
                if-eqz v0, :sbrowserx_trans_en_stock
                const/4 v0, 0x1
                return v0
                :sbrowserx_trans_en_stock
                nop
            """.trimIndent(),
        )

        // 2. Skip Galaxy AI Browsing Assist FTU popup when tapping Translate in the side menu
        findMethodOrThrow(
            classType = SETTING_PREFERENCE_CLASS,
            methodName = "isBrowsingAssistFTU",
            parameterTypes = emptyList(),
            returnType = "Z",
        ).addInstructions(
            0,
            """
                invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->isGoogleTranslateEnabled()Z
                move-result v0
                if-eqz v0, :sbrowserx_ftu_stock
                const/4 v0, 0x0
                return v0
                :sbrowserx_ftu_stock
                nop
            """.trimIndent(),
        )

        // 3. SixTransService.getSupport(String, int, int, long)V
        findMethodOrThrow(
            classType = SIX_TRANS_SERVICE_CLASS,
            methodName = "getSupport",
            parameterTypes = listOf("Ljava/lang/String;", "I", "I", "J"),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p0, p4, p5}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryTransGetSupport(Ljava/lang/Object;J)Z
                move-result p1
                if-eqz p1, :sbrowserx_sup_stock
                return-void
                :sbrowserx_sup_stock
                nop
            """.trimIndent(),
        )

        // 4. SixTransService.getMode(String, int, int, long)V
        findMethodOrThrow(
            classType = SIX_TRANS_SERVICE_CLASS,
            methodName = "getMode",
            parameterTypes = listOf("Ljava/lang/String;", "I", "I", "J"),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p0, p4, p5}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryTransGetMode(Ljava/lang/Object;J)Z
                move-result v0
                if-eqz v0, :sbrowserx_mode_stock
                return-void
                :sbrowserx_mode_stock
                nop
            """.trimIndent(),
        )

        // 5. SixTransService.createTrans(String, int, int, long)V
        findMethodOrThrow(
            classType = SIX_TRANS_SERVICE_CLASS,
            methodName = "createTrans",
            parameterTypes = listOf("Ljava/lang/String;", "I", "I", "J"),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p0, p4, p5}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryTransCreate(Ljava/lang/Object;J)Z
                move-result p1
                if-eqz p1, :sbrowserx_create_stock
                return-void
                :sbrowserx_create_stock
                nop
            """.trimIndent(),
        )

        // 6. SixTransService.getLangList(String, String, String, String, int, int, long)V
        findMethodOrThrow(
            classType = SIX_TRANS_SERVICE_CLASS,
            methodName = "getLangList",
            parameterTypes = listOf(
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "I",
                "I",
                "J",
            ),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p0, p3, p7, p8}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryTransGetLangList(Ljava/lang/Object;Ljava/lang/String;J)Z
                move-result p1
                if-eqz p1, :sbrowserx_langlist_stock
                return-void
                :sbrowserx_langlist_stock
                nop
            """.trimIndent(),
        )

        // 7. SixTransService.detectionLang(String, String, String, int, int, long)V
        findMethodOrThrow(
            classType = SIX_TRANS_SERVICE_CLASS,
            methodName = "detectionLang",
            parameterTypes = listOf(
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "I",
                "I",
                "J",
            ),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p0, p3, p6, p7}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryTransDetectLang(Ljava/lang/Object;Ljava/lang/String;J)Z
                move-result p1
                if-eqz p1, :sbrowserx_detect_stock
                return-void
                :sbrowserx_detect_stock
                nop
            """.trimIndent(),
        )

        // 8. SixTransService.translate(...)V
        findMethodOrThrow(
            classType = SIX_TRANS_SERVICE_CLASS,
            methodName = "translate",
            parameterTypes = listOf(
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "Ljava/lang/String;",
                "[Ljava/lang/String;",
                "Ljava/lang/Boolean;",
                "Ljava/lang/Boolean;",
                "I",
                "I",
                "J",
            ),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->setActiveTransService(Ljava/lang/Object;)V
                invoke-static {p4, p5, p6, p11, p12}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryGoogleTranslate(Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;J)Z
                move-result p1
                if-eqz p1, :sbrowserx_stock_trans
                return-void
                :sbrowserx_stock_trans
                nop
            """.trimIndent(),
        )

        // 9. SixTransService.openSettingsImpl()V -> open Chrome-style SBrowserXTranslateSettingsActivity
        findMethodOrThrow(
            classType = SIX_TRANS_SERVICE_CLASS,
            methodName = "openSettingsImpl",
            parameterTypes = emptyList(),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryOpenTranslateSettings(Ljava/lang/Object;)Z
                move-result v0
                if-eqz v0, :sbrowserx_openset_stock
                return-void
                :sbrowserx_openset_stock
                nop
            """.trimIndent(),
        )
    }
}
