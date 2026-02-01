package mc.krakow.graph

import org.jgrapht.graph.DefaultWeightedEdge
import org.jgrapht.graph.SimpleDirectedWeightedGraph

class TramConnectionsGraph {

    private val graph =
        SimpleDirectedWeightedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge::class.java)

    fun addStop(stopId: String) {
        graph.addVertex(stopId)
    }

    fun addConnection(
        fromStop: String,
        toStop: String,
        weight: Double
    ) {
        graph.addVertex(fromStop)
        graph.addVertex(toStop)

        val edge = graph.addEdge(fromStop, toStop)
        if (edge != null) {
            graph.setEdgeWeight(edge, weight)
        }
    }

    fun stops(): Set<String> = graph.vertexSet()

    fun connections(): Set<DefaultWeightedEdge> = graph.edgeSet()

    fun weight(fromStop: String, toStop: String): Double? =
        graph.getEdge(fromStop, toStop)?.let { graph.getEdgeWeight(it) }

    internal fun asJGraphT(): SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> =
        graph
}
