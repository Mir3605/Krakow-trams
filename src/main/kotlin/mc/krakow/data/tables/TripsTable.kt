package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insert
import java.io.File
import java.io.FileNotFoundException

/** GTFS: trips.txt */
object TripsTable : PopulatableTable("trips") {
    val tripId = varchar("trip_id", 64)
    val routeId = varchar("route_id", 64)
    val serviceId = varchar("service_id", 64)
    val tripHeadsign = varchar("trip_headsign", 64)
    val tripShortName = varchar("trip_short_name", 64)
    val directionId = integer("direction_id")
    val blockId = varchar("block_id", 64)
    val shapeId = varchar("shape_id", 64)
    val wheelchairAccessible = integer("wheelchair_accessible")
    override val primaryKey = PrimaryKey(tripId)

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 9) {
                    insert { table ->
                        table[tripId] = cols[0]
                        table[routeId] = cols[1]
                        table[serviceId] = cols[2]
                        table[tripHeadsign] = cols[3]
                        table[tripShortName] = cols[4]
                        table[directionId] = cols[5].toInt()
                        table[blockId] = cols[6]
                        table[shapeId] = cols[7]
                        table[wheelchairAccessible] = cols[8].toInt()
                    }
                }
            }
        }
    }
}
