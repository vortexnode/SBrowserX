package dev.vortexnode.sbrowserx.patches.privacy

import app.morphe.patcher.extensions.InstructionExtensions.replaceInstruction
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.NarrowLiteralInstruction
import com.android.tools.smali.dexlib2.iface.instruction.OneRegisterInstruction
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val SETTING_PREFERENCE_CLASS =
    "Lcom/sec/android/app/sbrowser/common/device/setting_preference/SettingPreference;"

@Suppress("unused")
val blockThirdPartyCookiesPatch = bytecodePatch(
    name = "Block third-party cookies by default",
    description = "Sets third-party cookies to Blocked by default on fresh installs while still allowing the user to re-enable them in Settings.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)

    execute {
        val method = findMethodOrThrow(
            classType = SETTING_PREFERENCE_CLASS,
            methodName = "isAllowThirdPartyCookiesEnabled",
            parameterTypes = emptyList(),
            returnType = "Z",
        )
        val instructions = method.implementation?.instructions
            ?: error("Missing implementation in isAllowThirdPartyCookiesEnabled")
        val literalIndex = instructions.indexOfFirst { insn ->
            insn.opcode == Opcode.CONST_4 && (insn as? NarrowLiteralInstruction)?.narrowLiteral == 1
        }
        check(literalIndex >= 0) {
            "Could not find default true literal (const/4 v*, 0x1) in isAllowThirdPartyCookiesEnabled"
        }
        val reg = (instructions[literalIndex] as OneRegisterInstruction).registerA
        method.replaceInstruction(literalIndex, "const/4 v$reg, 0x0")
    }
}
