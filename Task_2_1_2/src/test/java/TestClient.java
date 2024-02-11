import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Тесты.
 */
public class TestClient {
    @Test
    public void test0() {
        Assertions.assertTrue(
                Client.NotPrimeSearch.notPrimeSearch(
                        Arrays.asList(4, 4, 4, 4, 4, 4, 4, 5, 4, 4, 4)
                )
        );
    }

    @Test
    public void test1() {
        Assertions.assertTrue(
                Client.NotPrimeSearch.notPrimeSearch(
                        Arrays.asList(4, 4, 4, 4, 4, 4, 4, 1, 4, 4, 4)
                )
        );
    }

    @Test
    public void test3() {
        Assertions.assertTrue(
                Client.NotPrimeSearch.notPrimeSearch(
                        Arrays.asList(4, 4, 4, 4, 4, 4, 4, 2, 4, 4, 4)
                )
        );
    }

    @Test
    public void test4() {
        Assertions.assertTrue(
                Client.NotPrimeSearch.notPrimeSearch(
                        Arrays.asList(4, 4, 4, 4, 4, 4, 4, 0, 4, 4, 4)
                )
        );
    }

    @Test
    public void test() {
        Assertions.assertFalse(
                Client.NotPrimeSearch.notPrimeSearch(
                    Arrays.asList(3, 3, 3, 3, 3, 5, 7, 11, 31)
                )
        );
    }
}
