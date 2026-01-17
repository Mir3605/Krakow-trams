package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insertIgnore
import java.io.File
import java.io.FileNotFoundException

/** GTFS: shapes.txt */
object ShapesTable : PopulatableTable("shapes") {
    val shapeId = varchar("shape_id", 64)
    val shapePtLat = double("shape_pt_lat")
    val shapePtLon = double("shape_pt_lon")
    val shapePtSequence = integer("shape_pt_sequence")
    val shapeDistTraveled = varchar("shape_dist_traveled", 128).nullable()
    override val primaryKey = PrimaryKey(shapeId)

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 5) {
                    insertIgnore { table ->
                        table[shapeId] = cols[0]
                        table[shapePtLat] = cols[1].toDouble()
                        table[shapePtLon] = cols[2].toDouble()
                        table[shapePtSequence] = cols[3].toInt()
                        table[shapeDistTraveled] = cols[4].takeIf { it.isNotBlank() }
                    }
                }
            }
        }
    }
}
