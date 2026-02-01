package mc.krakow.data.browse

import mc.krakow.data.browse.entries.RouteId
import mc.krakow.data.browse.entries.StopName

data class RouteDetails(
    val routeId: RouteId,
    val routeName: String,
    val terminusAName: StopName,
    val terminusBName: StopName,
    val aToBTime: Long,
    val bToATime: Long
)
