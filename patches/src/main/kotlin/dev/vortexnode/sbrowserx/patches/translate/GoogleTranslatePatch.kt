package dev.vortexnode.sbrowserx.patches.translate

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val SIX_TRANS_SERVICE_CLASS =
    "Lcom/sec/android/app/sbrowser/extensions/trans/SixTransService;"

@Suppress("unused")
val googleTranslatePatch = bytecodePatch(
    name = "Built-in Google Translate engine",
    description = "Routes Samsung Internet's native TransBar page translation through Google Translate (Chrome's translation engine) instead of Samsung SCS.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        val method = findMethodOrThrow(
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
        )
        method.addInstructions(
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
    }
}
