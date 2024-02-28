import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test server.
 */
public class TestServer {
    /**
     * Tread.
     */
    static class AlsoThread extends Thread {
        public AlsoThread() {
            start();
        }

        @Override
        public void run() {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Client.main(new String[]{""});
        }
    }

    @Test
    public void test() {
        for (int i = 0; i < 3; i++) {
            new AlsoThread();
        }
        Assertions.assertTrue(Server.forTest(new ArrayList<Integer>(Arrays.asList(
                20319251, 6997901, 6997927, 6997937, 17858849, 6997967,
                6998009, 6998029, 6998039, 20165140, 6998051, 6998053))));

    }


}
