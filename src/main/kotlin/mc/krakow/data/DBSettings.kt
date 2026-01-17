package mc.krakow.data

import org.jetbrains.exposed.sql.Database

object DBSettings {
    private const val DB_FILE_PATH = "gtfs.db"

    val db by lazy {
        connectToDB()
    }

    private fun connectToDB(): Database = Database.connect(
        url = "jdbc:sqlite:${DB_FILE_PATH}",
        driver = "org.sqlite.JDBC"
    )
}