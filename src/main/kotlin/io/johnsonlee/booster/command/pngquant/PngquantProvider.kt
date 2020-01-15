package io.johnsonlee.booster.command.pngquant

import com.didiglobal.booster.command.Command
import com.didiglobal.booster.command.CommandProvider
import com.didiglobal.booster.kotlinx.OS
import com.google.auto.service.AutoService

/**
 * Represents the pngquant command provider
 *
 * @author johnsonlee
 */
@AutoService(CommandProvider::class)
class PngquantProvider : CommandProvider {

    override fun get(): Collection<Command> = listOf(
            Command("pngquant${OS.executableSuffix}", javaClass.classLoader.getResource(PREBULT_PNGQUANT_EXECUTABLE)!!)
    )

}

internal val PREBULT_PNGQUANT_EXECUTABLE = "bin/" + when {
    OS.isLinux() -> "linux/" + when (OS.arch) {
        "x64", "x86_64", "amd64" -> "x64"
        else -> TODO("Unsupported architecture ${OS.arch}")
    }
    OS.isMac() -> "macosx/" + when (OS.arch) {
        "x64", "x86_64", "amd64" -> "x64"
        else -> TODO("Unsupported architecture ${OS.arch}")
    }
    OS.isWindows() -> "windows/" + when (OS.arch) {
        "x64", "x86_64", "amd64" -> "x64"
        else -> TODO("Unsupported architecture ${OS.arch}")
    }
    else -> TODO("Unsupported OS ${OS.name}")
}