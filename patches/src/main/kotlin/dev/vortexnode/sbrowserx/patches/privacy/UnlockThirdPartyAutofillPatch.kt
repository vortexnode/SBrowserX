package dev.vortexnode.sbrowserx.patches.privacy

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val AUTOFILL_MANAGER_WRAPPER_CLASS =
    "Lorg/chromium/components/autofill/AutofillManagerWrapper;"
private const val TIN_CONTENT_VIEW_WITH_AUTOFILL_CLASS =
    "Lcom/sec/terrace/content/browser/TinContentViewWithAutofill;"

@Suppress("unused")
val unlockThirdPartyAutofillPatch = bytecodePatch(
    name = "Third-Party Autofill Support",
    description = "Allows password managers such as Bitwarden to detect login and password fields and autofill directly in the browser.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        val spassCheck = findMethodOrThrow(
            classType = AUTOFILL_MANAGER_WRAPPER_CLASS,
            methodName = "checkAutofillServiceIsSPass",
            parameterTypes = emptyList(),
            returnType = "Z",
        )
        spassCheck.addInstructions(
            0,
            """
                invoke-virtual {p0}, Lorg/chromium/components/autofill/AutofillManagerWrapper;->getAutofillServiceComponentName()Ljava/lang/String;
                move-result-object v0
                invoke-static {v0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->isAnyAutofillServiceAllowed(Ljava/lang/String;)Z
                move-result v0
                return v0
            """.trimIndent(),
        )

        val importantMethod = findMethodOrThrow(
            classType = TIN_CONTENT_VIEW_WITH_AUTOFILL_CLASS,
            methodName = "setImportantForAutofillForVirtualView",
            parameterTypes = listOf("Z"),
            returnType = "V",
        )
        importantMethod.addInstructions(
            0,
            """
                invoke-static {p0, p1}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->adjustImportantForAutofill(Landroid/view/View;Z)Z
                move-result p1
            """.trimIndent(),
        )

        for (methodName in listOf("autofill", "onProvideAutofillVirtualStructure")) {
            val method = findMethodOrThrow(
                classType = TIN_CONTENT_VIEW_WITH_AUTOFILL_CLASS,
                methodName = methodName,
                returnType = "V",
            )
            val insns = method.implementation?.instructions
                ?: error("Missing instructions in TinContentViewWithAutofill.$methodName")
            var patched = false
            for (i in 0 until insns.size - 1) {
                val mRef = (insns[i] as? ReferenceInstruction)?.reference as? MethodReference ?: continue
                if (mRef.name != "getPersonalDataSyncSetFlag") continue
                val move = insns[i + 1]
                if (move.opcode != Opcode.MOVE_RESULT) continue
                val reg = (move as OneRegisterInstruction).registerA
                method.addInstructions(
                    i + 2,
                    """
                        invoke-static {p0, v$reg}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->shouldUseTerraceAutofill(Landroid/view/View;Z)Z
                        move-result v$reg
                    """.trimIndent(),
                )
                patched = true
                break
            }
            check(patched) { "Failed to patch getPersonalDataSyncSetFlag in $methodName" }
        }
    }
}
