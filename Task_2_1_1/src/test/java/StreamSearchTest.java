import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class StreamSearchTest {
    @Test
    public void test1() {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        Assertions.assertTrue(StreamSearch.notPrimeSearch(list));
    }

    @Test
    public void test2() {
        List<Integer> list = Arrays.asList(20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        Assertions.assertFalse(StreamSearch.notPrimeSearch(list));
    }
}
