import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapSortTestTest {

    @Test
    void checkHeapsort(){
        assertArrayEquals(HeapSort.heapsort(new int[] {5, 4, 3, 2, 1}), new int[] {1, 2, 3, 4, 5});
        assertArrayEquals(HeapSort.heapsort(new int[] {-1, 2, 1000000, 1, 1, 2, 3}), new int[] {-1, 1, 1, 2, 2, 3, 1000000});
        assertArrayEquals(HeapSort.heapsort(new int[] {-2, -1, 0, 1, 2, 3, 4}), new int[] {-2, -1, 0, 1, 2, 3, 4});

    }

}