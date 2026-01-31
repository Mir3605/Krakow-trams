package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insert
import java.io.File
import java.io.FileNotFoundException

/** GTFS: calendar.txt */
object CalendarTable : PopulatableTable("calendar") {
    val serviceId = varchar("service_id", 64)
    val monday = integer("monday")
    val tuesday = integer("tuesday")
    val wednesday = integer("wednesday")
    val thursday = integer("thursday")
    val friday = integer("friday")
    val saturday = integer("saturday")
    val sunday = integer("sunday")
    val startDate = integer("start_date")
    val endDate = integer("end_date")
    override val primaryKey = PrimaryKey(serviceId)

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 10) {
                    insert { table ->
                        table[serviceId] = cols[0]
                        table[monday] = cols[1].toInt()
                        table[tuesday] = cols[2].toInt()
                        table[wednesday] = cols[3].toInt()
                        table[thursday] = cols[4].toInt()
                        table[friday] = cols[5].toInt()
                        table[saturday] = cols[6].toInt()
                        table[sunday] = cols[7].toInt()
                        table[startDate] = cols[8].toInt()
                        table[endDate] = cols[9].toInt()
                    }
                }
            }
        }
    }
}
