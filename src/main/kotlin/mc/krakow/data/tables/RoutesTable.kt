package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insertIgnore
import java.io.File
import java.io.FileNotFoundException

/** GTFS: routes.txt */
object RoutesTable : PopulatableTable("routes") {
    val routeId = varchar("route_id", 64)
    val agencyId = varchar("agency_id", 64)
    val routeShortName = varchar("route_short_name", 64)
    val routeLongName = varchar("route_long_name", 64)
    val routeDesc = varchar("route_desc", 128).nullable()
    val routeType = integer("route_type")
    val routeUrl = varchar("route_url", 128).nullable()
    val routeColor = varchar("route_color", 128).nullable()
    val routeTextColor = varchar("route_text_color", 128).nullable()
    override val primaryKey = PrimaryKey(routeId)

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 9) {
                    insertIgnore { table ->
                        table[routeId] = cols[0]
                        table[agencyId] = cols[1]
                        table[routeShortName] = cols[2]
                        table[routeLongName] = cols[3]
                        table[routeDesc] = cols[4].takeIf { it.isNotBlank() }
                        table[routeType] = cols[5].toInt()
                        table[routeUrl] = cols[6].takeIf { it.isNotBlank() }
                        table[routeColor] = cols[7].takeIf { it.isNotBlank() }
                        table[routeTextColor] = cols[8].takeIf { it.isNotBlank() }
                    }
                }
            }
        }
    }
}
