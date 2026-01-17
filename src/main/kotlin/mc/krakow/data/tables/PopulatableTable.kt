package mc.krakow.data.tables

import org.jetbrains.exposed.sql.Table
import java.io.File
import java.io.FileNotFoundException

abstract class PopulatableTable(val name: String) : Table(name) {
    /**
     * Populates the database table with the contents from the file.
     * @param sourceFile the file that contains records to populate the table.
     * @throws FileNotFoundException when the sourceFile cannot be accessed.
     */
    abstract fun populate(sourceFile: File)
}