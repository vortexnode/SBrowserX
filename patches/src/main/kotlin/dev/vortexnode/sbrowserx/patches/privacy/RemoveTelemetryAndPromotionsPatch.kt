package dev.vortexnode.sbrowserx.patches.privacy

import app.morphe.patcher.extensions.InstructionExtensions.addInstruction
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow
import dev.vortexnode.sbrowserx.shared.returnConstantBoolean
import dev.vortexnode.sbrowserx.shared.returnVoidEarly

private const val SA_LOGGING_CLASS =
    "Lcom/sec/android/app/sbrowser/SALoggingInitializer;"
private const val SI_LOG_CLASS =
    "Lcom/sec/android/app/sbrowser/si_log/SILog;"
private const val SMP_CONTROLLER_CLASS =
    "Lcom/sec/android/app/sbrowser/smp/SmpController;"
private const val PROMOTION_CONTROLLER_CLASS =
    "Lcom/sec/android/app/sbrowser/promotion/PromotionController;"
private const val KINDRED_FACTORY_CLASS =
    "Lcom/sec/android/app/sbrowser/kindred/KindredControllerFactory;"
private const val UPDATE_MANAGER_CLASS =
    "Lcom/sec/android/app/sbrowser/stub/UpdateManager;"

@Suppress("unused")
val removeTelemetryAndPromotionsPatch = bytecodePatch(
    name = "Remove telemetry, promotions & updates",
    description = "Disables Samsung Analytics, SILog, SMP marketing push, Promotion Engine, Kindred shopping/coupons, and Galaxy Store update checks while preserving site Web Push notifications.",
    use = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)

    execute {
        // 1. Samsung Analytics
        findMethodOrThrow(
            classType = SA_LOGGING_CLASS,
            methodName = "initialize",
            parameterTypes = listOf("Landroid/app/Application;"),
            returnType = "V",
        ).returnVoidEarly()

        // 2. Samsung Internet Logging (SILog)
        findMethodOrThrow(
            classType = SI_LOG_CLASS,
            methodName = "initialize",
            parameterTypes = emptyList(),
            returnType = "V",
        ).returnVoidEarly()

        findMethodOrThrow(
            classType = SI_LOG_CLASS,
            methodName = "sendInternal",
            returnType = "V",
        ).returnVoidEarly()

        mutableClassDefBy(SI_LOG_CLASS)
            .methods
            .filter { it.name == "sendStatusLogInternal" && it.returnType == "V" }
            .forEach { it.returnVoidEarly() }

        // 3. Samsung Marketing Push (SMP) — keeps WebPushNotificationManager & GCM intact
        findMethodOrThrow(
            classType = SMP_CONTROLLER_CLASS,
            methodName = "initialize",
            returnType = "V",
        ).returnVoidEarly()

        // 4. Promotion Engine
        findMethodOrThrow(
            classType = PROMOTION_CONTROLLER_CLASS,
            methodName = "isSupport",
            parameterTypes = emptyList(),
            returnType = "Z",
        ).returnConstantBoolean(false)

        // 5. Kindred / Smart Shopping / Coupons (force enabled = false -> returns EmptyKindredController)
        findMethodOrThrow(
            classType = KINDRED_FACTORY_CLASS,
            methodName = "createKindredController",
        ).addInstruction(0, "const/4 p0, 0x0")

        // 6. Galaxy Store & Play Store update checks
        findMethodOrThrow(
            classType = UPDATE_MANAGER_CLASS,
            methodName = "checkUpdate",
            parameterTypes = emptyList(),
            returnType = "V",
        ).returnVoidEarly()
    }
}
