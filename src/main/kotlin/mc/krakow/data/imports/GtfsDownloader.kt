package mc.krakow.data.imports

import java.io.File
import java.io.FileOutputStream
import java.net.URI
import java.nio.file.Files
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


/**
 * Downloads a GTFS ZIP archive from a given URL and extracts it to a target directory.
 * This class is intentionally self-contained and side-effect free except for filesystem I/O.
 */
open class GtfsDownloader(
    private val downloadUrl: String,
    private val workDir: File
) {


    init {
        require(workDir.exists() || workDir.mkdirs()) {
            "Unable to create working directory: ${workDir.absolutePath}"
        }
    }

    /**
     * Downloads and extracts the GTFS archive.
     */
    fun downloadAndExtract(): List<File> {
        val zip = download()
        return extract(zip)
    }

    /**
     * Downloads the GTFS ZIP file and returns the downloaded file reference.
     */
    protected open fun download(): File {
        val zipFile = File(workDir, "gtfs.zip")
        URI(downloadUrl).toURL().openStream().use { input ->
            FileOutputStream(zipFile).use { output ->
                input.copyTo(output)
            }
        }
        return zipFile
    }


    /**
     * Extracts the given ZIP file into the working directory.
     * Returns a list of extracted files.
     */
    protected fun extract(zipFile: File): List<File> =
        ZipInputStream(zipFile.inputStream().buffered()).use { zis ->
            generateSequence { zis.nextEntry }
                .mapNotNull { entry -> extractEntry(entry, zis) }
                .toList()
        }

    private fun extractEntry(entry: ZipEntry, zis: ZipInputStream): File? =
        entry
            .takeUnless { it.isDirectory }
            ?.let { fileEntry ->
                val resultFile = File(workDir, fileEntry.name)
                resultFile.createParentDirectoryIfNecessary()
                resultFile.outputStream().use(zis::copyTo)
                resultFile
            }

    private fun File.createParentDirectoryIfNecessary() {
        parentFile?.let { parent ->
            Files.createDirectories(parent.toPath())
        }
    }

}