package mc.krakow.data.imports

import mc.krakow.data.tables.AgencyTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals

class GtfsDatabaseImporterTest {

    private lateinit var gtfsDir: File
    private lateinit var testDB: Database
    private lateinit var dbFile: File

    @BeforeEach
    fun setUp() {
        gtfsDir = Files.createTempDirectory("gtfs").toFile()
        dbFile = Files.createTempFile("test", ".db").toFile()
        testDB = Database.connect(
            url = "jdbc:sqlite:${dbFile.absolutePath}",
            driver = "org.sqlite.JDBC"
        )
    }

    @Test
    fun `importer creates tables and imports data`() {
        File(gtfsDir, "agency.txt").writeText(
            """
            agency_id,agency_name,agency_url,agency_timezone,agency_lang,agency_phone,agency_fare_url,agency_email
            agency_1,"ZTP  Kraków",http://ztp.krakow.pl/,Europe/Warsaw,pl,+48 12 616 8669,,
            """.trimIndent()
        )

        val importer = GtfsDatabaseImporter(gtfsDir, testDB)
        importer.importData()

        transaction(testDB) {
            assertEquals(1, AgencyTable.selectAll().count())
        }
    }

    @AfterEach
    fun tearDown() {
        gtfsDir.deleteRecursively()
        dbFile.delete()
    }
}