import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;



/**
 * Тесты для списка инцедентности.
 */
public class IncidenceListTest {

    @ParameterizedTest
    @MethodSource("getTestVertexAddRemove")
    void vertexAddRemoveTest(boolean expected, Vertex<String> vertex, IncidenceList<String> graph) {
        Assertions.assertEquals(expected,
                graph.returnVertices().containsKey(vertex.getCurrentVertexNumber()));
    }

    @ParameterizedTest
    @MethodSource("getTestVertexChange")
    void changeValueTest(int expected, Vertex<Integer> vertex) {
        Assertions.assertEquals(expected, vertex.getValue());
    }

    @Test
    void getVertexByNumberTest() {
        Vertex<Integer> v1 = new Vertex<>(1);

        IncidenceList<Integer> graph = new IncidenceList<>();

        graph.addVertex(v1);

        Assertions.assertEquals(v1, graph.getVertexByNumber(v1.getCurrentVertexNumber()));
    }

    @ParameterizedTest
    @MethodSource("getTestEdgeAddRemove")
    void edgeAddRemoveTest(IncidenceList<String> graph,  Edge<String> edge, boolean expected) {
        Assertions.assertEquals(expected, graph.returnEdges().containsKey(edge.getEdgeNumber()));
    }

    @ParameterizedTest
    @MethodSource("getTestEdgeChange")
    void edgeChangeWeightTest(int expected, Edge<String> edge, IncidenceList<String> graph) {
        Assertions.assertEquals(expected,
                graph.returnEdges().get(edge.getEdgeNumber()).getEdgeWeight());
    }

    @Test
    void dijkstraTest() {
        Vertex<String> v1 = new Vertex<>("A");
        Vertex<String> v2 = new Vertex<>("B");
        Vertex<String> v3 = new Vertex<>("C");
        Vertex<String> v4 = new Vertex<>("D");
        Vertex<String> v5 = new Vertex<>("E");
        Vertex<String> v6 = new Vertex<>("F");
        Vertex<String> v7 = new Vertex<>("G");

        IncidenceList<String> graph = new IncidenceList<>();

        Edge<String> e1 = graph.addEdge(v1, v2, 5);
        Edge<String> e2 = graph.addEdge(v1, v4, 12);
        Edge<String> e3 = graph.addEdge(v1, v7, 25);
        Edge<String> e4 = graph.addEdge(v2, v4, 8);
        Edge<String> e5 = graph.addEdge(v3, v4, 2);

        Edge<String> e6 = graph.addEdge(v3, v5, 4);
        Edge<String> e7 = graph.addEdge(v3, v7, 10);
        Edge<String> e8 = graph.addEdge(v3, v6, 5);
        Edge<String> e9 = graph.addEdge(v5, v7, 5);
        Edge<String> e10 = graph.addEdge(v6, v7, 10);

        ArrayList<String> expected = new ArrayList<>(List.of("C", "D", "E", "F", "G", "B", "A"));

        Assertions.assertArrayEquals(expected.toArray(),
                graph.dijkstra(v3.getCurrentVertexNumber()).toArray());

    }

    static Stream<Arguments> getTestVertexAddRemove() {
        Vertex<String> v1 = new Vertex<>("hello");
        Vertex<String> v2 = new Vertex<>("world");
        Vertex<String> v3 = new Vertex<>("12345");
        final Vertex<String> v4 = new Vertex<>("12345");

        IncidenceList<String> graph = new IncidenceList<>();

        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        graph.removeVertex(v2);
        graph.removeVertexByNumber(v3.getCurrentVertexNumber());

        return Stream.of(
                Arguments.of( true, v1, graph),
                Arguments.of(false, v2, graph),
                Arguments.of(false, v3, graph),
                Arguments.of(false, v4, graph));
    }

    static Stream<Arguments> getTestVertexChange() {
        Vertex<Integer> v1 = new Vertex<>(1);
        Vertex<Integer> v2 = new Vertex<>(1);
        Vertex<Integer> v3 = new Vertex<>(1);

        IncidenceList<Integer> graph = new IncidenceList<>();
        graph.addVertex(v1);
        graph.addVertex(v2);
        graph.addVertex(v3);

        graph.changeVertexValue(v2, 4);
        graph.changeVertexValueByNumber(v3.getCurrentVertexNumber(), 9);
        return Stream.of(
                Arguments.of(1, v1),
                Arguments.of(4, v2),
                Arguments.of(9, v3));
    }

    static Stream<Arguments> getTestEdgeAddRemove() {
        Vertex<String> v1 = new Vertex<>("hello");
        Vertex<String> v2 = new Vertex<>("world");
        Vertex<String> v3 = new Vertex<>("12345");
        Vertex<String> v4 = new Vertex<>("12345");

        IncidenceList<String> graph = new IncidenceList<>();

        graph.addVertex(v1);

        Edge<String> e1 = graph.addEdge(v1, v2, 1);
        Edge<String> e2 = graph.addEdge(v3, v4, 2);
        Edge<String> e3 = graph.addEdge(v1, v3, 3);
        Edge<String> e4 = graph.addEdge(v1, v4, 4);
        Edge<String> e5 = graph.addEdge(v2, v3, 5);

        graph.removeEdgeByNumber(e4.getEdgeNumber());

        return Stream.of(
                Arguments.of(graph, e1, true),
                Arguments.of(graph, e2, true),
                Arguments.of(graph, e3, true),
                Arguments.of(graph, e4, false),
                Arguments.of(graph, e5, true));
    }

    static Stream<Arguments> getTestEdgeChange() {
        Vertex<String> v1 = new Vertex<>("hello");
        Vertex<String> v2 = new Vertex<>("world");
        Vertex<String> v3 = new Vertex<>("word");
        Vertex<String> v4 = new Vertex<>("123");

        IncidenceList<String> graph = new IncidenceList<>();
        Edge<String> e1 = graph.addEdge(v1, v2, 1);
        Edge<String> e2 = graph.addEdge(v3, v4, 1);
        Edge<String> e3 = graph.addEdge(v1, v3, 1);

        graph.changeEdgeWeightByNumber(e2.getEdgeNumber(), 2);
        graph.changeEdgeWeightByNumber(e3.getEdgeNumber(), 3);
        return Stream.of(
                Arguments.of(1, e1, graph),
                Arguments.of(2, e2, graph),
                Arguments.of(3, e3, graph));
    }
}
