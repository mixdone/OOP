import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * StreamSearchTest.
 */
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

    @Test
    public void test3() throws InterruptedException {
        List<Integer> list = Arrays.asList(2, 3, 5, 7, 0);
        Assertions.assertTrue(ThreadSearch.notPrimeSearch(list, 1));
    }

    @Test
    public void test4() throws InterruptedException {
        List<Integer> list = Arrays.asList(2, 3, 5, 7, 1);
        Assertions.assertTrue(ThreadSearch.notPrimeSearch(list, 1));
    }

    @Test
    public void test5() throws InterruptedException {
        List<Integer> list = Arrays.asList(1);
        Assertions.assertTrue(ThreadSearch.notPrimeSearch(list, 1));
    }
}
