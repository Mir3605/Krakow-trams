package mc.krakow.data.browse

import mc.krakow.data.DatabaseTest
import mc.krakow.data.browse.entries.RouteName
import mc.krakow.data.browse.entries.StopId
import mc.krakow.data.browse.entries.TripId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class DataBrowserTest : DatabaseTest() {
    private lateinit var dataBrowser: DataBrowser

    @BeforeEach
    override fun setUp() {
        super.setUp()
        insertTestData()
        dataBrowser = DataBrowser(db)
    }

    @Test
    fun `routeNameToId returns proper route id`() {
        val routeId = dataBrowser.routeNameToId(RouteName("1"))
        assertEquals(testRouteId, routeId)
    }

    @Test
    fun `routeIdToName returns proper route name`() {
        val routeName = dataBrowser.routeIdToName(testRouteId)
        assertEquals(RouteName("1"), routeName)
    }

    @Test
    fun `routeIdToTripIds returns proper trip ids`() {
        val tripIds = dataBrowser.routeIdToTripIds(testRouteId)
        val bothDirectionsPresentInTripIds =
            (tripIdsToFirstTerminus.contains(tripIds.first) && tripIdsToSecondTerminus.contains(tripIds.second)) ||
                    (tripIdsToFirstTerminus.contains(tripIds.second) && tripIdsToSecondTerminus.contains(tripIds.first))
        assert(bothDirectionsPresentInTripIds)
    }

    @Test
    fun `getTimeForTripId returns proper trip duration`() {
        val tripDuration = dataBrowser.getTimeForTripId(TripId("block_2_trip_3_service_1"))
        assertEquals(900L, tripDuration)
    }

    @Test
    fun `getStopName returns proper stop name`() {
        val stopName = dataBrowser.getStopName(StopId("stop_293_61329"))
        assertEquals("Grodzki Urząd Pracy", stopName.name)
    }

    @Test
    fun `getTerminusNames returns proper terminus names`() {
        val terminusNames = dataBrowser.getTerminusNames(TripId("block_2_trip_3_service_1"))
        assertEquals("stop_221_32519", terminusNames.first.name)
        assertEquals("stop_293_61329", terminusNames.second.name)
    }

    @Test
    fun `getRouteDetails returns proper route details`() {
        val routeDetails = dataBrowser.getRouteDetails(testRouteId)
        assertEquals(testRouteId, routeDetails.routeId)
        assertEquals("1", routeDetails.routeName)
    }
}
