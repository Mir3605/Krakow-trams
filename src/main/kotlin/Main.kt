package mc.krakow

import mc.krakow.data.imports.GtfsDatabaseImporter
import mc.krakow.data.imports.GtfsDownloader
import java.io.File

fun main() {
    val workDir = File("tmp")
    val downloader = GtfsDownloader("https://gtfs.ztp.krakow.pl/GTFS_KRK_T.zip", workDir)
    val extractedFiles: Collection<File> = downloader.downloadAndExtract()
    val importer = GtfsDatabaseImporter(extractedFiles)
    importer.importData()
}