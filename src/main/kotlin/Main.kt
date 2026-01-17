package mc.krakow

import mc.krakow.data.imports.GtfsDatabaseImporter
import mc.krakow.data.imports.GtfsDownloader
import java.io.File

fun main() {
    val workDir = File("tmp")
    val downloader = GtfsDownloader("https://gtfs.ztp.krakow.pl/GTFS_KRK_T.zip", workDir)
    downloader.downloadAndExtract()
    val importer = GtfsDatabaseImporter(workDir)
    importer.importData()
}