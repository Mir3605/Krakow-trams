package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insert
import java.io.File
import java.io.FileNotFoundException

/** GTFS: feed_info.txt */
object FeedInfoTable : PopulatableTable("feed_info") {
    val feedPublisherName = varchar("feed_publisher_name", 64)
    val feedPublisherUrl = varchar("feed_publisher_url", 64)
    val feedLang = varchar("feed_lang", 64)
    val feedStartDate = varchar("feed_start_date", 128).nullable()
    val feedEndDate = varchar("feed_end_date", 128).nullable()
    val feedVersion = varchar("feed_version", 64)

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 6) {
                    insert { table ->
                        table[feedPublisherName] = cols[0]
                        table[feedPublisherUrl] = cols[1]
                        table[feedLang] = cols[2]
                        table[feedStartDate] = cols[3].takeIf { it.isNotBlank() }
                        table[feedEndDate] = cols[4].takeIf { it.isNotBlank() }
                        table[feedVersion] = cols[5]
                    }
                }
            }
        }
    }
}
