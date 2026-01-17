package mc.krakow.data.imports

import mc.krakow.data.DBSettings
import mc.krakow.data.tables.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException

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
 */
open class GtfsDatabaseImporter(
    protected val gtfsDir: File,
    protected val db: Database = DBSettings.db
) {
    private val log: Logger = LoggerFactory.getLogger(GtfsDatabaseImporter::class.java)
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
            createAndPopulate(table)
        }
    }

    private fun createAndPopulate(table: PopulatableTable) {
        transaction {
            SchemaUtils.create(table)
            tryPopulate(table)
        }
    }

    private fun tryPopulate(table: PopulatableTable) {
        try {
            table.populate(File("${gtfsDir.absolutePath}/${table.name}.txt"))
        } catch (e: FileNotFoundException) {
            log.error("Table ${table.name} not imported, because the exception occurred.", e)
        }
    }

    /**
     * A wrapper for org.jetbrains.exposed.sql.transactions.transaction that uses db that was passed in the constructor
     */
    protected fun <T> transaction(statement: Transaction.() -> T): T =
        org.jetbrains.exposed.sql.transactions.transaction(db, statement)
}