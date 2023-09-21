import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class HeapSortTestTest {

    @ParameterizedTest
    @MethodSource("getTestArg")
    void checkHeapsort(int[] input, int[] expected) {
        assertArrayEquals(HeapSort.heapsort(input), expected);
    }


    static Stream<Arguments> getTestArg() {
        return Stream.of(
                Arguments.of(new int[] {5, 4, 3, 2, 1}, new int[] {1, 2, 3, 4, 5}),
                Arguments.of(new int[] {-1, 2, 1000000, 1, 1, 2, 3}, new int[] {-1, 1, 1, 2, 2, 3, 1000000}),
                Arguments.of(new int[] {-2, -1, 0, 1, 2, 3, 4}, new int[] {-2, -1, 0, 1, 2, 3, 4}),
                Arguments.of(new int[] {}, new int[] {}),
                Arguments.of(new int[] {1}, new int[] {1}));
    }
}
