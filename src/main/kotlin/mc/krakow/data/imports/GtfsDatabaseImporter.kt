package mc.krakow.data.imports

import mc.krakow.data.DBSettings
import mc.krakow.data.tables.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

private val tables = arrayOf(
    AgencyTable,
    CalendarDatesTable,
    CalendarTable,
    FeedInfoTable,
    RoutesTable,
    ShapesTable,
    StopsTable,
    StopTimesTable,
    TripsTable
)

/**
 * Imports GTFS data from an extracted directory into an SQLite database.
 * Uses Exposed DSL instead of raw SQL.
 */
open class GtfsDatabaseImporter(
    protected val gtfsDir: File
) {

    init {
        require(gtfsDir.exists() && gtfsDir.isDirectory) {
            "GTFS directory does not exist: ${gtfsDir.absolutePath}"
        }
    }


    /**
     * Reads all supported GTFS files found in the directory
     * and imports them into the database.
     */
    fun importData() {
        tables.forEach { table ->
            transaction(DBSettings.db) {
                SchemaUtils.create(table)
                table.populate(File("${gtfsDir.absolutePath}/${table.name}.txt"))
            }
        }
    }
}