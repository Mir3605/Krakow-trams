package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insert
import java.io.File
import java.io.FileNotFoundException

/** GTFS: stops.txt */
object StopsTable : PopulatableTable("stops") {
    val stopId = varchar("stop_id", 64)
    val stopCode = varchar("stop_code", 128).nullable()
    val stopName = varchar("stop_name", 64)
    val stopDesc = varchar("stop_desc", 64)
    val stopLat = double("stop_lat")
    val stopLon = double("stop_lon")
    val zoneId = varchar("zone_id", 128).nullable()
    val stopUrl = varchar("stop_url", 128).nullable()
    val locationType = integer("location_type")
    val parentStation = varchar("parent_station", 128).nullable()
    val stopTimezone = varchar("stop_timezone", 128).nullable()
    val wheelchairBoarding = integer("wheelchair_boarding").nullable()
    val platformCode = double("platform_code").nullable()
    override val primaryKey = PrimaryKey(stopId)

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 13) {
                    insert { table ->
                        table[stopId] = cols[0]
                        table[stopCode] = cols[1].takeIf { it.isNotBlank() }
                        table[stopName] = cols[2]
                        table[stopDesc] = cols[3]
                        table[stopLat] = cols[4].toDouble()
                        table[stopLon] = cols[5].toDouble()
                        table[zoneId] = cols[6].takeIf { it.isNotBlank() }
                        table[stopUrl] = cols[7].takeIf { it.isNotBlank() }
                        table[locationType] = cols[8].toInt()
                        table[parentStation] = cols[9].takeIf { it.isNotBlank() }
                        table[stopTimezone] = cols[10].takeIf { it.isNotBlank() }
                        table[wheelchairBoarding] = cols[11].takeIf { it.isNotBlank() }?.toIntOrNull()
                        table[platformCode] = cols[12].takeIf { it.isNotBlank() }?.toDoubleOrNull()
                    }
                }
            }
        }
    }
}
