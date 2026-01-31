package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insert
import java.io.File
import java.io.FileNotFoundException

/** GTFS: agency.txt */
object AgencyTable : PopulatableTable("agency") {
    val agencyId = varchar("agency_id", 64)
    val agencyName = varchar("agency_name", 64)
    val agencyUrl = varchar("agency_url", 64)
    val agencyTimezone = varchar("agency_timezone", 64)
    val agencyLang = varchar("agency_lang", 64)
    val agencyPhone = varchar("agency_phone", 64)
    val agencyFareUrl = varchar("agency_fare_url", 128).nullable()
    val agencyEmail = varchar("agency_email", 128).nullable()
    override val primaryKey = PrimaryKey(agencyId)

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 8) {
                    insert { table ->
                        table[agencyId] = cols[0]
                        table[agencyName] = cols[1]
                        table[agencyUrl] = cols[2]
                        table[agencyTimezone] = cols[3]
                        table[agencyLang] = cols[4]
                        table[agencyPhone] = cols[5]
                        table[agencyFareUrl] = cols[6].takeIf { it.isNotBlank() }
                        table[agencyEmail] = cols[7].takeIf { it.isNotBlank() }
                    }
                }
            }
        }
    }
}
