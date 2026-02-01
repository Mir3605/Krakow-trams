package mc.krakow.data.browse

import mc.krakow.data.browse.entries.*
import mc.krakow.data.tables.RoutesTable
import mc.krakow.data.tables.StopTimesTable
import mc.krakow.data.tables.StopsTable
import mc.krakow.data.tables.TripsTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class DataBrowser(private val db: Database) {
    private val routeNameToId: MutableMap<RouteName, RouteId> = mutableMapOf()
    private val routeIdToName: MutableMap<RouteId, RouteName> = mutableMapOf()

    fun routeNameToId(routeName: RouteName): RouteId {
        if (routeNameToId.containsKey(routeName)) {
            return routeNameToId[routeName]!!
        }

        val routeId: RouteId = transaction(db) {
            val stringRouteId = RoutesTable
                .selectAll()
                .where { RoutesTable.routeShortName eq routeName.name }
                .map { it[RoutesTable.routeId] }
                .single()
            RouteId(stringRouteId)
        }

        routeNameToId[routeName] = routeId
        return routeId
    }

    fun routeIdToName(routeId: RouteId): RouteName {
        if (routeIdToName.containsKey(routeId)) {
            return routeIdToName[routeId]!!
        }

        val routeNameString: String = transaction(db) {
            RoutesTable
                .selectAll()
                .where { RoutesTable.routeId eq routeId.routeId }
                .map { it[RoutesTable.routeShortName] }
                .single()
        }

        val routeName = RouteName(routeNameString)
        routeIdToName[routeId] = routeName
        return routeName
    }

    /**
     * If the inspected route connects terminus A and B, this
     * function will return average trip from A to B and average trip from B to A. It will consider only
     * full trips between A and B. The assumption is made that there are no more popular trips on the inspected
     * route that from A to B and from B to A.
     * @param routeId id of the inspected route.
     * @return pair of 2 trips on the inspected route.
     */
    fun routeIdToTripIds(routeId: RouteId): Pair<TripId, TripId> {
        val top2TripsOnRoute: List<String> = transaction(db) {
            val allTripsOnRoute = TripsTable
                .selectAll()
                .where { TripsTable.routeId eq routeId.routeId }

            val tripsByShape = allTripsOnRoute
                .groupBy { it[TripsTable.shapeId] }
                .toList()

            val topTwoShapesByTripCount = tripsByShape
                .sortedBy { (_, trips) -> trips.size }
                .takeLast(2)

            topTwoShapesByTripCount
                .map { (_, trips) -> trips[trips.size / 2] }  // Get median trip
                .map { trip -> trip[TripsTable.tripId] }
        }

        return Pair(TripId(top2TripsOnRoute[0]), TripId(top2TripsOnRoute[1]))
    }

    fun getTimeForTripId(tripId: TripId): Long {
        val stopTimes = transaction(db) {
            StopTimesTable
                .selectAll()
                .where { StopTimesTable.tripId eq tripId.tripId }
                .toList()
                .sortedBy { resultRow -> resultRow[StopTimesTable.stopSequence] }
                .map { resultRow -> resultRow[StopTimesTable.arrivalTime] }
        }
        return (GTFSTime(stopTimes.last()) - GTFSTime(stopTimes.first())).toSeconds()
    }

    fun getStopName(stopId: StopId): StopName {
        val stopNameString: String = transaction(db) {
            StopsTable
                .selectAll()
                .where { StopsTable.stopId eq stopId.id }
                .single()
                .get(StopsTable.stopName)
        }
        return StopName(stopNameString)
    }

    fun getTerminusNames(tripId: TripId): Pair<StopName, StopName> {
        val stopIds = transaction(db) {
            StopTimesTable
                .selectAll()
                .where { StopTimesTable.tripId eq tripId.tripId }
                .toList()
                .sortedBy { resultRow -> resultRow[StopTimesTable.stopSequence] }
                .map { resultRow -> resultRow[StopTimesTable.stopId] }
        }
        return Pair(StopName(stopIds.first()), StopName(stopIds.last()))
    }

    fun getRouteDetails(routeId: RouteId): RouteDetails {
        val routeData = transaction(db) {
            RoutesTable
                .selectAll()
                .where { RoutesTable.routeId eq routeId.routeId }
                .single()
        }

        val trips = routeIdToTripIds(routeId)
        val terminusNames = getTerminusNames(trips.first)

        return RouteDetails(
            routeId,
            routeData[RoutesTable.routeShortName],
            terminusNames.first,
            terminusNames.second,
            getTimeForTripId(trips.first),
            getTimeForTripId(trips.second)
        )
    }
}