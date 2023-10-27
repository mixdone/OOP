import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Тесты для ребра.
 */
public class EdgeTest {

    @ParameterizedTest
    @MethodSource("getTestWeight")
    void distanceTest(int expected, Edge<String> edge) {
        Assertions.assertEquals(expected, edge.getEdgeWeight());
    }

    @ParameterizedTest
    @MethodSource("getTestGetVertices")
    void valueOperationsTest(Vertex<String> v1, Vertex<String> v2, Edge<String> edge) {
        Assertions.assertTrue(edge.getVertices().contains(v1.getCurrentVertexNumber())
                && edge.getVertices().contains(v2.getCurrentVertexNumber()));
    }

    static Stream<Arguments> getTestWeight() {
        Vertex<String> v1   = new Vertex<>("hello");
        Vertex<String> v2   = new Vertex<>("world");
        Vertex<String> v3   = new Vertex<>("12345");
        Edge<String> e1     = new Edge<>(v1, v2, 1);
        Edge<String> e2     = new Edge<>(v2, v3, 1);
        Edge<String> e3     = new Edge<>(v3, v1, 1);

        e2.changeEdgeWeight(3);
        e3.changeEdgeWeight(8);
        return Stream.of(
                Arguments.of(1, e1),
                Arguments.of(3, e2),
                Arguments.of(8, e3));
    }

    static Stream<Arguments> getTestGetVertices() {
        Vertex<String> v1   = new Vertex<>("hello");
        Vertex<String> v2   = new Vertex<>("world");
        Vertex<String> v3   = new Vertex<>("12345");
        Edge<String> e1     = new Edge<>(v1, v2, 1);
        Edge<String> e2     = new Edge<>(v2, v3, 1);
        Edge<String> e3     = new Edge<>(v3, v1, 1);

        return Stream.of(
                Arguments.of(v1, v2, e1),
                Arguments.of(v2, v3, e2),
                Arguments.of(v3, v1, e3));
    }
}
