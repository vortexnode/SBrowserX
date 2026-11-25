package dev.vortexnode.sbrowserx.patches.downloads

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val DOWNLOAD_HANDLER_CLASS =
    "Lcom/sec/android/app/sbrowser/download/DownloadHandler;"

@Suppress("unused")
val externalDownloaderPatch = bytecodePatch(
    name = "External downloader support",
    description = "Allows forwarding HTTP/HTTPS file downloads to external download managers (1DM, ADM, FDM, Seal, etc.) via a toggle in SBrowserX settings.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        val method = findMethodOrThrow(
            classType = DOWNLOAD_HANDLER_CLASS,
            methodName = "startDownload",
            parameterTypes = listOf("Landroid/app/Activity;"),
            returnType = "V",
        )
        method.addInstructions(
            0,
            """
                invoke-static {p0, p1}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryHandOffDownload(Ljava/lang/Object;Landroid/app/Activity;)Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_dl
                return-void
                :sbrowserx_stock_dl
                nop
            """.trimIndent(),
        )
    }
}
