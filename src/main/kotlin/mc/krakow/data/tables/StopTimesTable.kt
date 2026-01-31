package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insert
import java.io.File
import java.io.FileNotFoundException

/** GTFS: stop_times.txt */
object StopTimesTable : PopulatableTable("stop_times") {
    val tripId = varchar("trip_id", 64)
    val arrivalTime = varchar("arrival_time", 64)
    val departureTime = varchar("departure_time", 64)
    val stopId = varchar("stop_id", 64)
    val stopSequence = integer("stop_sequence")
    val stopHeadsign = varchar("stop_headsign", 64)
    val pickupType = integer("pickup_type")
    val dropOffType = integer("drop_off_type")
    val shapeDistTraveled = double("shape_dist_traveled")
    val timepoint = integer("timepoint")
    override val primaryKey = PrimaryKey(tripId, stopSequence)

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 10) {
                    insert { table ->
                        table[tripId] = cols[0]
                        table[arrivalTime] = cols[1]
                        table[departureTime] = cols[2]
                        table[stopId] = cols[3]
                        table[stopSequence] = cols[4].toInt()
                        table[stopHeadsign] = cols[5]
                        table[pickupType] = cols[6].toInt()
                        table[dropOffType] = cols[7].toInt()
                        table[shapeDistTraveled] = cols[8].toDouble()
                        table[timepoint] = cols[9].toInt()
                    }
                }
            }
        }
    }
}
