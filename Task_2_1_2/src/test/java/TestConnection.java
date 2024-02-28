import org.junit.jupiter.api.Test;

/**
 * Тест.
 */
public class TestConnection {

    static class Thread1 extends Thread {
        private final int num;

        public Thread1(int n) {
            num = n;
            start();
        }

        @Override
        public void run() {
            if (num == 0) {
                Server.main(new String[]{""});
            } else {
                Client.main(new String[]{""});
            }
        }
    }

    @Test
    public void test() {
        for (int i = 0; i < 10; i++) {
            new Thread1(i);
        }
    }

}
