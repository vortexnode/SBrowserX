package dev.vortexnode.sbrowserx.patches.downloads

import app.morphe.patcher.extensions.InstructionExtensions.addInstructions
import app.morphe.patcher.patch.bytecodePatch
import dev.vortexnode.sbrowserx.patches.core.sbrowserXCorePatch
import dev.vortexnode.sbrowserx.shared.Constants.COMPATIBILITY_SBROWSER
import dev.vortexnode.sbrowserx.shared.findMethodOrThrow

private const val DOWNLOAD_HANDLER_CLASS =
    "Lcom/sec/android/app/sbrowser/download/DownloadHandler;"
private const val TAB_CLASS =
    "Lcom/sec/android/app/sbrowser/tab/Tab;"

@Suppress("unused")
val externalDownloaderPatch = bytecodePatch(
    name = "External downloader support",
    description = "Forwards HTTP/HTTPS file downloads AND context-menu image/video/link downloads to external download managers (1DM, ADM, ABDM, FDM, Seal, etc.) via SBrowserX settings.",
    default = true,
) {
    compatibleWith(COMPATIBILITY_SBROWSER)
    dependsOn(sbrowserXCorePatch)

    execute {
        // 1. Standard file downloads (DownloadHandler.startDownload(Activity)V)
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

        // 2. Context-menu downloads ("Save image", "Save link", "Save video")
        // Tab.startContextMenuDownload(Ljava/lang/String;Z)V (.locals 1)
        val ctxDlMethod = findMethodOrThrow(
            classType = TAB_CLASS,
            methodName = "startContextMenuDownload",
            parameterTypes = listOf("Ljava/lang/String;", "Z"),
            returnType = "V",
        )
        ctxDlMethod.addInstructions(
            0,
            """
                invoke-static {p1, p2}, Ldev/vortexnode/sbrowserx/extension/SBrowserXHooks;->tryHandOffDirectUrlDownload(Ljava/lang/String;Z)Z
                move-result v0
                if-eqz v0, :sbrowserx_stock_ctx_dl
                return-void
                :sbrowserx_stock_ctx_dl
                nop
            """.trimIndent(),
        )
    }
}
