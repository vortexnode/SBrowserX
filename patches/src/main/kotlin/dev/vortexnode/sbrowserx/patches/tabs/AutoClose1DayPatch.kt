package dev.vortexnode.sbrowserx.patches.tabs

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import com.android.tools.smali.dexlib2.iface.instruction.WideLiteralInstruction
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER

private const val UNUSED_TAB_CLOSER_CLASS =
    "Lcom/sec/android/app/sbrowser/tab_manager/UnusedTabCloser;"

@Suppress("unused")
val autoClose1DayPatch = bytecodePatch(
    name = "Auto-close unused tabs after 1 day",
    description = "Adds a 1-day (24h) auto-close option in SBrowserX settings that overrides the 7-day threshold in UnusedTabCloser.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        val clazz = mutableClassDefBy(UNUSED_TAB_CLOSER_CLASS)
        val targetMethod = clazz.methods.firstOrNull { method ->
            method.parameterTypes.map { it.toString() } == listOf("J", "I") &&
                method.returnType == "I"
        } ?: error("Could not find UnusedTabCloser threshold method (JI)I")

        val insns = targetMethod.implementation?.instructions
            ?: error("Missing implementation in UnusedTabCloser threshold method")
        val sevenIdx = insns.indexOfFirst { insn ->
            insn.opcode == Opcode.CONST_WIDE_16 &&
                (insn as? WideLiteralInstruction)?.wideLiteral == 7L
        }
        check(sevenIdx >= 0) { "Could not find 7-day literal (const-wide/16 v*, 0x7) in UnusedTabCloser" }
        val reg = (insns[sevenIdx] as OneRegisterInstruction).registerA
        targetMethod.addInstructions(
            sevenIdx + 1,
            """
                invoke-static {}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->getAutoClose7DayOverride()J
                move-result-wide v$reg
            """.trimIndent(),
        )
    }
}
