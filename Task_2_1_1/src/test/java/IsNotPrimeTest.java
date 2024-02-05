import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class IsNotPrimeTest {
    @ParameterizedTest
    @MethodSource("getTests")
    public void test(boolean expected, int num) {
        Assertions.assertEquals(expected, IsNotPrime.isNotPrime(num));
    }

    static Stream<Arguments> getTests() {
        return Stream.of(
                Arguments.of(true, 1),
                Arguments.of(true, 0),
                Arguments.of(false, 2),
                Arguments.of(false, 3),
                Arguments.of(true, 4),
                Arguments.of(false, 5),
                Arguments.of(true, 10000));
    }
}
