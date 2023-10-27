import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class VertexTest {
    @ParameterizedTest
    @MethodSource("getTestDistance")
    void distanceTest(int expected, Vertex<String> vertex) {
        Assertions.assertEquals(expected, vertex.getDistance());
    }

    @ParameterizedTest
    @MethodSource("getTestValueOperations")
    void valueOperationsTest(int expected, Vertex<Integer> vertex) {
        Assertions.assertEquals(expected, vertex.getValue());
    }

    static Stream<Arguments> getTestDistance() {
        Vertex<String> v1 = new Vertex<>("hello");
        Vertex<String> v2 = new Vertex<>("world");
        Vertex<String> v3 = new Vertex<>("12345");
        v2.setDistance(0);
        v3.setDistance(100);
        return Stream.of(
                Arguments.of(-1, v1),
                Arguments.of(0, v2),
                Arguments.of(100, v3));
    }

    static Stream<Arguments> getTestValueOperations() {
        Vertex<Integer> v1 = new Vertex<>(1);
        Vertex<Integer> v2 = new Vertex<>(1);
        Vertex<Integer> v3 = new Vertex<>(1);
        v2.changeVertexValue(0);
        v3.changeVertexValue(12345);
        return Stream.of(
                Arguments.of(1, v1),
                Arguments.of(0, v2),
                Arguments.of(12345, v3));
    }
}
