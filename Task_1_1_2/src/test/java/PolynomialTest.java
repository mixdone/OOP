import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.MethodSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PolynomialTest {
    @ParameterizedTest
    @MethodSource("getTestToString")
    void toStringTest(Polynomial p, String expected) {
        assertEquals(expected, p.toString());
    }

    @ParameterizedTest
    @MethodSource("getTestPlus")
    void plusTest(Polynomial p1, Polynomial p2, String expected) {
        assertEquals(expected, p1.plus(p2).toString());
    }

    @ParameterizedTest
    @MethodSource("getTestMinus")
    void minusTest(Polynomial p1, Polynomial p2, String expected) {
        assertEquals(expected, p1.minus(p2).toString());
    }

    @ParameterizedTest
    @MethodSource("getTestMultiply")
    void multiplyTest(Polynomial p1, Polynomial p2, String expected) {
        assertEquals(expected, p1.multiply(p2).toString());
    }

    @ParameterizedTest
    @MethodSource("getTestEvaluate")
    void evaluateTest(Polynomial p, int point, int expected) {
        assertEquals(expected, p.evaluate(point));
    }

    @ParameterizedTest
    @MethodSource("getTestEquals")
    void equalsTest(Polynomial p1, Polynomial p2, boolean expected) {
        assertEquals(expected, p1.equals(p2));
    }

    @ParameterizedTest
    @MethodSource("getTestDifferentiate")
    void differentiateTest(Polynomial p, int n, String expected) {
        assertEquals(expected, p.differentiate(n).toString());
    }

    static Stream<Arguments> getTestToString() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[] {1, 2, 3}), "3x^2 + 2x + 1"),
                Arguments.of(new Polynomial(new int[] {3, 2, 1}), "x^2 + 2x + 3"),
                Arguments.of(new Polynomial(new int[] {}), "0"));

    }

    static  Stream<Arguments> getTestPlus() {
        return  Stream.of(
                Arguments.of(new Polynomial(new int[] {1, 2, 3}),
                        new Polynomial(new int[] {1, 2, 3}),
                        "6x^2 + 4x + 2"),
                Arguments.of(new Polynomial(new int[] {-1, -2, -1}),
                        new Polynomial(new int[] {0}),
                        "-x^2 - 2x - 1"));
    }

    static  Stream<Arguments> getTestMinus() {
        return  Stream.of(
                Arguments.of(new Polynomial(new int[] {1, 2, 3}),
                        new Polynomial(new int[] {1, 2, 3}),
                        "0"),
                Arguments.of(new Polynomial(new int[] {-1, -2, -1}),
                        new Polynomial(new int[] {0}),
                        "-x^2 - 2x - 1"),
                Arguments.of(new Polynomial(new int[] {1, 2, 3}),
                        new Polynomial(new int[] {2, 4, 6}),
                        "-3x^2 - 2x - 1"));
    }

    static  Stream<Arguments> getTestMultiply() {
        return  Stream.of(
                Arguments.of(new Polynomial(new int[] {1, 2, 3}),
                        new Polynomial(new int[] {1, 2, 3}),
                        "9x^4 + 12x^3 + 10x^2 + 4x + 1"),
                Arguments.of(new Polynomial(new int[] {-1, -2, -1}),
                        new Polynomial(new int[] {0}),
                        "0"),
                Arguments.of(new Polynomial(new int[] {1, 2, 3}),
                        new Polynomial(new int[] {2, 4, 6}),
                        "18x^4 + 24x^3 + 20x^2 + 8x + 2"));
    }

    static  Stream<Arguments> getTestEvaluate() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[] {5, 4, 3, 2, 1}), 7, 3267),
                Arguments.of(new Polynomial(new int[] {12, 3, 12}), 6, 462),
                Arguments.of(new Polynomial(new int[] {697, 2, 3, 1, 4}), 0, 697));
    }

    static Stream<Arguments> getTestEquals() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[] {1, 2, 3}),
                        new Polynomial(new int[] {1, 2, 3}),
                        true),
                Arguments.of(new Polynomial(new int[] {-1, -2, -1}),
                        new Polynomial(new int[] {0}),
                        false),
                Arguments.of(new Polynomial(new int[] {1, 2, 3}),
                        new Polynomial(new int[] {2, 4, 6}),
                        false),
                Arguments.of(new Polynomial(new int[] {}),
                        new Polynomial(new int[] {0}),
                        true));
    }

    static Stream<Arguments> getTestDifferentiate() {
        return Stream.of(
                Arguments.of(new Polynomial(new int[] {1, 2, 3, 4, 5}), 1, "20x^3 + 12x^2 + 6x + 2"),
                Arguments.of(new Polynomial(new int[] {1, 2, 3, 4, 5}), 2, "60x^2 + 24x + 6"),
                Arguments.of(new Polynomial(new int[] {1, 2, 3, 4, 5}), 3, "120x + 24"),
                Arguments.of(new Polynomial(new int[] {1, 2, 3, 4, 5}), 4, "120"),
                Arguments.of(new Polynomial(new int[] {1, 2, 3, 4, 5}), 5, "0"));
    }
}
