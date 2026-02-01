package mc.krakow.data.imports

import mc.krakow.data.DatabaseTest
import mc.krakow.data.tables.AgencyTable
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.io.TempDir
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

class GtfsDatabaseImporterTest : DatabaseTest() {

    @TempDir
    private lateinit var gtfsDir: File

    @Test
    fun `importer creates tables and imports data`() {
        val agencyFile = File(gtfsDir, "agency.txt")
        agencyFile.writeText(
            """
            agency_id,agency_name,agency_url,agency_timezone,agency_lang,agency_phone,agency_fare_url,agency_email
            agency_1,"ZTP  Kraków",http://ztp.krakow.pl/,Europe/Warsaw,pl,+48 12 616 8669,,
            """.trimIndent()
        )

        val importer = GtfsDatabaseImporter(listOf(agencyFile), db)
        importer.importData()

        transaction(db) {
            assertEquals(1, AgencyTable.selectAll().count())
        }
    }
}