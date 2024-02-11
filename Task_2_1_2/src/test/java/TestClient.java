import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class TestClient {
    @Test
    public void test() {
        Assertions.assertTrue(
                Client.NotPrimeSearch.notPrimeSearch(
                        Arrays.asList(4, 4, 4, 4, 4, 4, 4, 5, 4, 4 ,4)
                )
        );
    }
}
