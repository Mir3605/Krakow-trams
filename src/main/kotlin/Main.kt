package mc.krakow

import mc.krakow.data.imports.GtfsDownloader
import java.io.File

fun main() {
    val downloader = GtfsDownloader("https://gtfs.ztp.krakow.pl/GTFS_KRK_T.zip", File("tmp"))
    downloader.downloadAndExtract()
}