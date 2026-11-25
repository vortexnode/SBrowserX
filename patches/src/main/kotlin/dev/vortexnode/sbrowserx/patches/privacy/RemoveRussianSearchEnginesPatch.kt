package dev.vortexnode.sbrowserx.patches.privacy

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val SEARCH_ENGINE_HELPER_CLASS =
    "Lcom/sec/android/app/sbrowser/searchengine/SettingSearchEngineHelper;"

@Suppress("unused")
val removeRussianSearchEnginesPatch = bytecodePatch(
    name = "Remove Yandex, Mail.ru & Russian Search Engines",
    description = "Completely removes Yandex, Mail.ru, Rambler, and other Russian search engines from Samsung Internet's search engine list and address bar picker.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        // 1. SettingSearchEngineHelper.removePolicyRestrictedEngines(List)V (.locals 0)
        findMethodOrThrow(
            classType = SEARCH_ENGINE_HELPER_CLASS,
            methodName = "removePolicyRestrictedEngines",
            parameterTypes = listOf("Ljava/util/List;"),
            returnType = "V",
        ).addInstructions(
            0,
            """
                invoke-static {p1}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->filterBlockedSearchEngines(Ljava/util/List;)V
            """.trimIndent(),
        )

        // 2. SettingSearchEngineHelper.getListAvailableEngines()ArrayList (.locals 0)
        val availMethod = findMethodOrThrow(
            classType = SEARCH_ENGINE_HELPER_CLASS,
            methodName = "getListAvailableEngines",
            parameterTypes = emptyList(),
            returnType = "Ljava/util/ArrayList;",
        )
        val availInsns = availMethod.implementation?.instructions
            ?: error("Missing instructions in getListAvailableEngines")
        availMethod.addInstructions(
            availInsns.size - 1,
            """
                invoke-static {p0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->filterBlockedSearchEngines(Ljava/util/List;)V
            """.trimIndent(),
        )

        // 3. SettingSearchEngineHelper.getEditableSearchEngineList()List (returns v0)
        val editMethod = findMethodOrThrow(
            classType = SEARCH_ENGINE_HELPER_CLASS,
            methodName = "getEditableSearchEngineList",
            parameterTypes = emptyList(),
            returnType = "Ljava/util/List;",
        )
        val editInsns = editMethod.implementation?.instructions
            ?: error("Missing instructions in getEditableSearchEngineList")
        editMethod.addInstructions(
            editInsns.size - 1,
            """
                invoke-static {v0}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->filterBlockedSearchEngines(Ljava/util/List;)V
            """.trimIndent(),
        )
    }
}
