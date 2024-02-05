import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class ThreadSearchTest {
    @Test
    public void test1() throws InterruptedException {
        List<Integer> list = Arrays.asList(6, 8, 7, 13, 5, 9, 4);
        Assertions.assertTrue(ThreadSearch.notPrimeSearch(list, 1));
    }

    @Test
    public void test2() throws InterruptedException {
        List<Integer> list = Arrays.asList(20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165149, 6998051, 6998053);
        Assertions.assertFalse(ThreadSearch.notPrimeSearch(list, 1));
    }
}
