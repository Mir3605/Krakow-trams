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
 * Imports GTFS data from the gtfsFiles into an SQLite database.
 */
open class GtfsDatabaseImporter(
    protected val gtfsFiles: Collection<File>,
    protected val db: Database = DBSettings.db
) {
    private val log: Logger = LoggerFactory.getLogger(GtfsDatabaseImporter::class.java)

    /**
     * Tries to populate all the tables with the gtfsFiles. Logs message when unsuccessful.
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
        val tableFile: File? = gtfsFileForTable(table)
        if (tableFile == null) {
            log.warn(
                "Gtfs table file was not passed in the constructor for table ${table.name}. " +
                        "Skipping import of this table."
            )
            return
        }
        try {
            table.populate(tableFile)
        } catch (e: FileNotFoundException) {
            log.error("Table ${table.name} not imported, because the exception occurred.", e)
        }
    }

    /**
     * A wrapper for org.jetbrains.exposed.sql.transactions.transaction that uses db that was passed in the constructor
     */
    protected fun <T> transaction(statement: Transaction.() -> T): T =
        org.jetbrains.exposed.sql.transactions.transaction(db, statement)

    private fun gtfsFileForTable(table: PopulatableTable): File? =
        gtfsFiles.find { file -> file.name.equals("${table.name}.txt") }
}