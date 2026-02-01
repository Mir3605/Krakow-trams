package mc.krakow.data

import mc.krakow.data.browse.entries.RouteId
import mc.krakow.data.browse.entries.TripId
import mc.krakow.data.tables.RoutesTable
import mc.krakow.data.tables.StopTimesTable
import mc.krakow.data.tables.StopsTable
import mc.krakow.data.tables.TripsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import java.io.File
import java.nio.file.Files

abstract class DatabaseTest {
    private lateinit var dbFile: File
    protected lateinit var db: Database

    protected val testRouteId = RouteId("route_31")
    protected val tripIdsToFirstTerminus = setOf(
        TripId("block_2_trip_3_service_1"),
        TripId("block_2_trip_5_service_1"),
        TripId("block_2_trip_7_service_1")
    )
    protected val tripIdsToSecondTerminus = setOf(
        TripId("block_2_trip_2_service_1"),
        TripId("block_2_trip_4_service_1"),
        TripId("block_2_trip_6_service_1")
    )

    @BeforeEach
    open fun setUp() {
        dbFile = Files.createTempFile("test", ".db").toFile()
        db = Database.connect(
            url = "jdbc:sqlite:${dbFile.absolutePath}",
            driver = "org.sqlite.JDBC"
        )
    }

    @AfterEach
    fun tearDown() {
        dbFile.delete()
    }

    protected fun insertTestData() {
        transaction(db) {
            SchemaUtils.create(RoutesTable)
            insertRoutes()
            SchemaUtils.create(StopsTable)
            insertStops()
            SchemaUtils.create(TripsTable)
            insertTrips()
            SchemaUtils.create(StopTimesTable)
            insertStopTimes()
        }
    }

    private fun insertRoutes() {
        RoutesTable.insert {
            it[RoutesTable.routeId] = testRouteId.routeId
            it[RoutesTable.agencyId] = "agency_1"
            it[RoutesTable.routeShortName] = "1"
            it[RoutesTable.routeLongName] = "1"
            it[RoutesTable.routeType] = 900
        }

        RoutesTable.insert {
            it[RoutesTable.routeId] = "route_46"
            it[RoutesTable.agencyId] = "agency_1"
            it[RoutesTable.routeShortName] = "22"
            it[RoutesTable.routeLongName] = "22"
            it[RoutesTable.routeType] = 900
        }

        RoutesTable.insert {
            it[RoutesTable.routeId] = "route_53"
            it[RoutesTable.agencyId] = "agency_1"
            it[RoutesTable.routeShortName] = "62"
            it[RoutesTable.routeLongName] = "62"
            it[RoutesTable.routeType] = 900
        }
    }

    private fun insertStops() {
        StopsTable.insert {
            it[StopsTable.stopId] = "stop_293_61329"
            it[StopsTable.stopName] = "Grodzki Urząd Pracy"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.0873
            it[StopsTable.stopLon] = 20.0596
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_292_61229"
            it[StopsTable.stopName] = "Kościuszkowców"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.016651
            it[StopsTable.stopLon] = 19.929831
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_285_58429"
            it[StopsTable.stopName] = "Solvay"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.014606
            it[StopsTable.stopLon] = 19.927852
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_284_57729"
            it[StopsTable.stopName] = "Stefana Batorego"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.066586
            it[StopsTable.stopLon] = 19.929432
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_283_57629"
            it[StopsTable.stopName] = "Nullo"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.060001
            it[StopsTable.stopLon] = 19.966793
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_282_57529"
            it[StopsTable.stopName] = "Salwator"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.060173
            it[StopsTable.stopLon] = 19.967382
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_957_333829"
            it[StopsTable.stopName] = "Wzg. Krzesławickie Start"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.060173
            it[StopsTable.stopLon] = 19.967382
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_226_36129"
            it[StopsTable.stopName] = "Wzg. Krzesławickie"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.060173
            it[StopsTable.stopLon] = 19.967382
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_224_35919"
            it[StopsTable.stopName] = "Wzg. Krzesławickie Mid"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.060173
            it[StopsTable.stopLon] = 19.967382
            it[StopsTable.locationType] = 0
        }

        StopsTable.insert {
            it[StopsTable.stopId] = "stop_221_32519"
            it[StopsTable.stopName] = "Wzg. Krzesławickie End"
            it[StopsTable.stopDesc] = ""
            it[StopsTable.stopLat] = 50.060173
            it[StopsTable.stopLon] = 19.967382
            it[StopsTable.locationType] = 0
        }
    }

    private fun insertTrips() {
        // Insert Trips for route_31
        // Salwator -> Wzg. Krzesławickie (unconventional shape_23346)
        TripsTable.insert {
            it[TripsTable.tripId] = "block_2_trip_1_service_1"
            it[TripsTable.routeId] = testRouteId.routeId
            it[TripsTable.serviceId] = "service_1"
            it[TripsTable.tripHeadsign] = "Salwator"
            it[TripsTable.tripShortName] = ""
            it[TripsTable.directionId] = 0
            it[TripsTable.blockId] = "block_2"
            it[TripsTable.shapeId] = "shape_23348"
            it[TripsTable.wheelchairAccessible] = 0
        }

        tripIdsToFirstTerminus.forEach { trip ->
            TripsTable.insert {
                it[TripsTable.tripId] = trip.tripId
                it[TripsTable.routeId] = testRouteId.routeId
                it[TripsTable.serviceId] = "service_1"
                it[TripsTable.tripHeadsign] = "Wzg. Krzesławickie"
                it[TripsTable.tripShortName] = ""
                it[TripsTable.directionId] = 0
                it[TripsTable.blockId] = "block_2"
                it[TripsTable.shapeId] = "shape_23346"
                it[TripsTable.wheelchairAccessible] = 0
            }
        }

        tripIdsToSecondTerminus.forEach { trip ->
            TripsTable.insert {
                it[TripsTable.tripId] = trip.tripId
                it[TripsTable.routeId] = testRouteId.routeId
                it[TripsTable.serviceId] = "service_1"
                it[TripsTable.tripHeadsign] = "Salwator"
                it[TripsTable.tripShortName] = ""
                it[TripsTable.directionId] = 1
                it[TripsTable.blockId] = "block_2"
                it[TripsTable.shapeId] = "shape_23347"
                it[TripsTable.wheelchairAccessible] = 0
            }
        }
    }

    private fun insertStopTimes() {
        // block_2_trip_1_service_1
        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_1_service_1"
            it[StopTimesTable.arrivalTime] = "06:16:00"
            it[StopTimesTable.departureTime] = "06:16:00"
            it[StopTimesTable.stopId] = "stop_293_61329"
            it[StopTimesTable.stopSequence] = 2
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 0.39
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_1_service_1"
            it[StopTimesTable.arrivalTime] = "06:33:00"
            it[StopTimesTable.departureTime] = "06:33:00"
            it[StopTimesTable.stopId] = "stop_219_32219"
            it[StopTimesTable.stopSequence] = 14
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 5.418
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_1_service_1"
            it[StopTimesTable.arrivalTime] = "06:39:00"
            it[StopTimesTable.departureTime] = "06:39:00"
            it[StopTimesTable.stopId] = "stop_213_31119"
            it[StopTimesTable.stopSequence] = 18
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 7.152
            it[StopTimesTable.timepoint] = 1
        }

        // block_2_trip_2_service_1
        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_2_service_1"
            it[StopTimesTable.arrivalTime] = "06:57:00"
            it[StopTimesTable.departureTime] = "06:57:00"
            it[StopTimesTable.stopId] = "stop_213_31119"
            it[StopTimesTable.stopSequence] = 1
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 1
            it[StopTimesTable.shapeDistTraveled] = 0.0
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_2_service_1"
            it[StopTimesTable.arrivalTime] = "07:03:00"
            it[StopTimesTable.departureTime] = "07:03:00"
            it[StopTimesTable.stopId] = "stop_219_32229"
            it[StopTimesTable.stopSequence] = 5
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 1.613
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_2_service_1"
            it[StopTimesTable.arrivalTime] = "07:42:00"
            it[StopTimesTable.departureTime] = "07:42:00"
            it[StopTimesTable.stopId] = "stop_258_44219"
            it[StopTimesTable.stopSequence] = 28
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 13.374
            it[StopTimesTable.timepoint] = 1
        }

        // block_2_trip_3_service_1
        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_3_service_1"
            it[StopTimesTable.arrivalTime] = "07:56:00"
            it[StopTimesTable.departureTime] = "07:56:00"
            it[StopTimesTable.stopId] = "stop_258_44219"
            it[StopTimesTable.stopSequence] = 1
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 1
            it[StopTimesTable.shapeDistTraveled] = 0.0
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_3_service_1"
            it[StopTimesTable.arrivalTime] = "08:32:00"
            it[StopTimesTable.departureTime] = "08:32:00"
            it[StopTimesTable.stopId] = "stop_219_32219"
            it[StopTimesTable.stopSequence] = 24
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 11.239
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_3_service_1"
            it[StopTimesTable.arrivalTime] = "08:38:00"
            it[StopTimesTable.departureTime] = "08:38:00"
            it[StopTimesTable.stopId] = "stop_213_31119"
            it[StopTimesTable.stopSequence] = 28
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 12.973
            it[StopTimesTable.timepoint] = 1
        }

        // block_2_trip_4_service_1
        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_4_service_1"
            it[StopTimesTable.arrivalTime] = "08:57:00"
            it[StopTimesTable.departureTime] = "08:57:00"
            it[StopTimesTable.stopId] = "stop_213_31119"
            it[StopTimesTable.stopSequence] = 1
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 1
            it[StopTimesTable.shapeDistTraveled] = 0.0
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_4_service_1"
            it[StopTimesTable.arrivalTime] = "09:03:00"
            it[StopTimesTable.departureTime] = "09:03:00"
            it[StopTimesTable.stopId] = "stop_219_32229"
            it[StopTimesTable.stopSequence] = 5
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 1.613
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_4_service_1"
            it[StopTimesTable.arrivalTime] = "09:42:00"
            it[StopTimesTable.departureTime] = "09:42:00"
            it[StopTimesTable.stopId] = "stop_258_44219"
            it[StopTimesTable.stopSequence] = 28
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 13.374
            it[StopTimesTable.timepoint] = 1
        }

        // block_2_trip_5_service_1
        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_5_service_1"
            it[StopTimesTable.arrivalTime] = "09:56:00"
            it[StopTimesTable.departureTime] = "09:56:00"
            it[StopTimesTable.stopId] = "stop_258_44219"
            it[StopTimesTable.stopSequence] = 1
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 1
            it[StopTimesTable.shapeDistTraveled] = 0.0
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_5_service_1"
            it[StopTimesTable.arrivalTime] = "10:32:00"
            it[StopTimesTable.departureTime] = "10:32:00"
            it[StopTimesTable.stopId] = "stop_219_32219"
            it[StopTimesTable.stopSequence] = 24
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 11.239
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_5_service_1"
            it[StopTimesTable.arrivalTime] = "10:38:00"
            it[StopTimesTable.departureTime] = "10:38:00"
            it[StopTimesTable.stopId] = "stop_213_31119"
            it[StopTimesTable.stopSequence] = 28
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 12.973
            it[StopTimesTable.timepoint] = 1
        }

        // block_2_trip_6_service_1
        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_6_service_1"
            it[StopTimesTable.arrivalTime] = "10:57:00"
            it[StopTimesTable.departureTime] = "10:57:00"
            it[StopTimesTable.stopId] = "stop_213_31119"
            it[StopTimesTable.stopSequence] = 1
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 1
            it[StopTimesTable.shapeDistTraveled] = 0.0
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_6_service_1"
            it[StopTimesTable.arrivalTime] = "11:03:00"
            it[StopTimesTable.departureTime] = "11:03:00"
            it[StopTimesTable.stopId] = "stop_219_32229"
            it[StopTimesTable.stopSequence] = 5
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 1.613
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_6_service_1"
            it[StopTimesTable.arrivalTime] = "11:42:00"
            it[StopTimesTable.departureTime] = "11:42:00"
            it[StopTimesTable.stopId] = "stop_258_44219"
            it[StopTimesTable.stopSequence] = 28
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 13.374
            it[StopTimesTable.timepoint] = 1
        }

        // block_2_trip_7_service_1
        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_7_service_1"
            it[StopTimesTable.arrivalTime] = "11:56:00"
            it[StopTimesTable.departureTime] = "11:56:00"
            it[StopTimesTable.stopId] = "stop_258_44219"
            it[StopTimesTable.stopSequence] = 1
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 1
            it[StopTimesTable.shapeDistTraveled] = 0.0
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_7_service_1"
            it[StopTimesTable.arrivalTime] = "12:32:00"
            it[StopTimesTable.departureTime] = "12:32:00"
            it[StopTimesTable.stopId] = "stop_219_32219"
            it[StopTimesTable.stopSequence] = 24
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 0
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 11.239
            it[StopTimesTable.timepoint] = 1
        }

        StopTimesTable.insert {
            it[StopTimesTable.tripId] = "block_2_trip_7_service_1"
            it[StopTimesTable.arrivalTime] = "12:38:00"
            it[StopTimesTable.departureTime] = "12:38:00"
            it[StopTimesTable.stopId] = "stop_213_31119"
            it[StopTimesTable.stopSequence] = 28
            it[StopTimesTable.stopHeadsign] = ""
            it[StopTimesTable.pickupType] = 1
            it[StopTimesTable.dropOffType] = 0
            it[StopTimesTable.shapeDistTraveled] = 12.973
            it[StopTimesTable.timepoint] = 1
        }
    }
}