package mc.krakow.data.tables

import org.jetbrains.exposed.sql.insert
import java.io.File
import java.io.FileNotFoundException

/** GTFS: calendar_dates.txt */
object CalendarDatesTable : PopulatableTable("calendar_dates") {
    val serviceId = varchar("service_id", 64)
    val date = integer("date")
    val exceptionType = integer("exception_type")

    override fun populate(sourceFile: File) {
        if (!sourceFile.exists()) {
            throw FileNotFoundException("${sourceFile.absolutePath} does not exist")
        }

        sourceFile.useLines { lines ->
            lines.drop(1).forEach { line ->
                val cols = line.split(',')
                if (cols.size >= 3) {
                    insert { table ->
                        table[serviceId] = cols[0]
                        table[date] = cols[1].toInt()
                        table[exceptionType] = cols[2].toInt()
                    }
                }
            }
        }
    }
}
