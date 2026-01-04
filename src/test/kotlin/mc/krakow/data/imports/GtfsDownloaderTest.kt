package mc.krakow.data.imports

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.io.TempDir
import java.io.File
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream


class GtfsDownloaderTest {


    @TempDir
    lateinit var tempDir: File


    private fun createTestZip(): File {
        val zipFile = File(tempDir, "test.zip")
        ZipOutputStream(zipFile.outputStream()).use { zos ->
            val entry = ZipEntry("stops.txt")
            zos.putNextEntry(entry)
            zos.write("stop_id,stop_name\n1,Test Stop".toByteArray())
            zos.closeEntry()
        }
        return zipFile
    }


    @Test
    fun `extract should unpack files from zip`() {
        val zip = createTestZip()
        val downloader = TestableGtfsDownloader(tempDir, zip)

        val extracted = downloader.downloadAndExtract()

        assertEquals(1, extracted.size)
        val extractedFile = extracted.first()
        assertTrue(extractedFile.exists())
        assertEquals("stops.txt", extractedFile.name)
    }

    class TestableGtfsDownloader(
        workDir: File,
        private val zipToReturn: File
    ) : GtfsDownloader("http://unused", workDir) {

        override fun download(): File = zipToReturn
    }

}