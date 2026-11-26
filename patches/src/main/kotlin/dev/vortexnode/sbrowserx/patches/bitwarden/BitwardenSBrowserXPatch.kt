package dev.vortexnode.sbrowserx.patches.bitwarden

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import com.android.tools.smali.dexlib2.Opcode
import com.android.tools.smali.dexlib2.iface.instruction.ReferenceInstruction
import com.android.tools.smali.dexlib2.iface.reference.FieldReference
import com.android.tools.smali.dexlib2.iface.reference.MethodReference
import com.android.tools.smali.dexlib2.iface.reference.StringReference
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_BITWARDEN
import dev.vortexnode.sbrowserx.shared.Constants.SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME
import dev.vortexnode.sbrowserx.shared.Constants.SBROWSER_PACKAGE_NAME

@Suppress("unused")
val bitwardenSBrowserXPatch = bytecodePatch(
    name = "SBrowserX Browser Recognition",
    description = "Adds SBrowserX browser recognition to Bitwarden so it detects website addresses and login fields properly.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_BITWARDEN)

    execute {
        var patchedAutofillMap = false
        var patchedAccessibilityList = false

        val candidateClassTypes = mutableListOf<String>()
        classDefForEach { classDef ->
            val clinit = classDef.methods.firstOrNull { it.name == "<clinit>" } ?: return@classDefForEach
            val insns = clinit.implementation?.instructions ?: return@classDefForEach
            var hasSBrowser = false
            var hasLocationEdit = false
            for (insn in insns) {
                if (insn.opcode != Opcode.CONST_STRING && insn.opcode != Opcode.CONST_STRING_JUMBO) continue
                val str = ((insn as? ReferenceInstruction)?.reference as? StringReference)?.string ?: continue
                if (str == SBROWSER_PACKAGE_NAME) hasSBrowser = true
                if (str == "location_bar_edit_text") hasLocationEdit = true
                if (hasSBrowser && hasLocationEdit) break
            }
            if (hasSBrowser && hasLocationEdit) {
                candidateClassTypes.add(classDef.type)
            }
        }

        for (classType in candidateClassTypes) {
            val mutableClass = mutableClassDefBy(classType)
            val clinit = mutableClass.methods.firstOrNull { it.name == "<clinit>" } ?: continue
            val insns = clinit.implementation?.instructions ?: continue

            if (!patchedAutofillMap) {
                val mapField = insns.firstNotNullOfOrNull { insn ->
                    if (insn.opcode != Opcode.SPUT_OBJECT) return@firstNotNullOfOrNull null
                    val fRef = (insn as? ReferenceInstruction)?.reference as? FieldReference ?: return@firstNotNullOfOrNull null
                    if (fRef.definingClass == classType && fRef.type == "Ljava/util/Map;") fRef else null
                }
                if (mapField != null) {
                    val retIdx = insns.indexOfLast { it.opcode == Opcode.RETURN_VOID }
                    check(retIdx >= 0) { "Missing return-void in $classType.<clinit>" }
                    clinit.addInstructions(
                        retIdx,
                        """
                            new-instance v0, Ljava/util/LinkedHashMap;
                            sget-object v1, $classType->${mapField.name}:Ljava/util/Map;
                            invoke-direct {v0, v1}, Ljava/util/LinkedHashMap;-><init>(Ljava/util/Map;)V
                            const-string v1, "$SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME"
                            const-string v2, "compact_url_text"
                            invoke-interface {v0, v1, v2}, Ljava/util/Map;->put(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
                            sput-object v0, $classType->${mapField.name}:Ljava/util/Map;
                        """.trimIndent(),
                    )
                    patchedAutofillMap = true
                    continue
                }
            }

            if (!patchedAccessibilityList) {
                val listField = insns.firstNotNullOfOrNull { insn ->
                    if (insn.opcode != Opcode.SPUT_OBJECT) return@firstNotNullOfOrNull null
                    val fRef = (insn as? ReferenceInstruction)?.reference as? FieldReference ?: return@firstNotNullOfOrNull null
                    if (fRef.definingClass == classType && fRef.type == "Ljava/util/List;") fRef else null
                }
                val entryInitRef = insns.firstNotNullOfOrNull { insn ->
                    if (insn.opcode != Opcode.INVOKE_DIRECT) return@firstNotNullOfOrNull null
                    val mRef = (insn as? ReferenceInstruction)?.reference as? MethodReference ?: return@firstNotNullOfOrNull null
                    if (mRef.name == "<init>" &&
                        mRef.parameterTypes.map { it.toString() } == listOf("Ljava/lang/String;", "Ljava/lang/String;")
                    ) {
                        mRef
                    } else {
                        null
                    }
                }
                if (listField != null && entryInitRef != null) {
                    val retIdx = insns.indexOfLast { it.opcode == Opcode.RETURN_VOID }
                    check(retIdx >= 0) { "Missing return-void in $classType.<clinit>" }
                    val entryType = entryInitRef.definingClass
                    clinit.addInstructions(
                        retIdx,
                        """
                            new-instance v0, Ljava/util/ArrayList;
                            sget-object v1, $classType->${listField.name}:Ljava/util/List;
                            invoke-direct {v0, v1}, Ljava/util/ArrayList;-><init>(Ljava/util/Collection;)V
                            new-instance v1, $entryType
                            const-string v2, "$SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME"
                            const-string v3, "compact_url_text"
                            invoke-direct {v1, v2, v3}, $entryType-><init>(Ljava/lang/String;Ljava/lang/String;)V
                            invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z
                            new-instance v1, $entryType
                            const-string v2, "$SBROWSER_DEFAULT_PATCHED_PACKAGE_NAME"
                            const-string v3, "location_bar_edit_text"
                            invoke-direct {v1, v2, v3}, $entryType-><init>(Ljava/lang/String;Ljava/lang/String;)V
                            invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z
                            new-instance v1, $entryType
                            const-string v2, "$SBROWSER_PACKAGE_NAME"
                            const-string v3, "compact_url_text"
                            invoke-direct {v1, v2, v3}, $entryType-><init>(Ljava/lang/String;Ljava/lang/String;)V
                            invoke-interface {v0, v1}, Ljava/util/List;->add(Ljava/lang/Object;)Z
                            sput-object v0, $classType->${listField.name}:Ljava/util/List;
                        """.trimIndent(),
                    )
                    patchedAccessibilityList = true
                }
            }
        }

        check(patchedAutofillMap) { "Could not locate Bitwarden autofill browser map" }
        check(patchedAccessibilityList) { "Could not locate Bitwarden accessibility browser list" }
    }
}
