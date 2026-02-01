package mc.krakow.graph

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TramConnectionsGraphTest {

    private lateinit var graph: TramConnectionsGraph

    @BeforeEach
    fun setUp() {
        graph = TramConnectionsGraph()
    }

    @Test
    fun `adding a stop should register it in the graph`() {
        graph.addStop("STOP_A")

        assertTrue(graph.stops().contains("STOP_A"))
        assertEquals(1, graph.stops().size)
    }

    @Test
    fun `adding a connection should add both stops automatically`() {
        graph.addConnection(
            fromStop = "STOP_A",
            toStop = "STOP_B",
            weight = 3.5
        )

        assertEquals(setOf("STOP_A", "STOP_B"), graph.stops())
    }

    @Test
    fun `adding a connection should create a weighted edge`() {
        graph.addConnection(
            fromStop = "STOP_A",
            toStop = "STOP_B",
            weight = 7.0
        )

        assertEquals(7.0, graph.weight("STOP_A", "STOP_B"))
        assertEquals(1, graph.connections().size)
    }

    @Test
    fun `requesting weight for non-existing connection should return null`() {
        graph.addStop("STOP_A")
        graph.addStop("STOP_B")

        assertNull(graph.weight("STOP_A", "STOP_B"))
    }

    @Test
    fun `graph should be directed`() {
        graph.addConnection(
            fromStop = "STOP_A",
            toStop = "STOP_B",
            weight = 1.0
        )

        assertNotNull(graph.weight("STOP_A", "STOP_B"))
        assertNull(graph.weight("STOP_B", "STOP_A"))
    }
}
