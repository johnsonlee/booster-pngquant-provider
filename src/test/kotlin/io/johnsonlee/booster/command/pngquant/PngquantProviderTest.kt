package io.johnsonlee.booster.command.pngquant

import com.didiglobal.booster.command.CommandService
import com.didiglobal.booster.kotlinx.OS
import kotlin.test.Test
import kotlin.test.assertNotNull

class PngquantProviderTest {

    @Test
    fun `load pngquant by spi`() {
        val pngquant = CommandService.get("pngquant${OS.executableSuffix}")
        assertNotNull(pngquant)
        println(pngquant.location)
    }

}