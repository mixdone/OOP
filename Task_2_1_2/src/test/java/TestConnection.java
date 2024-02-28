import org.junit.jupiter.api.Test;

/**
 * Тест.
 */
public class TestConnection {

    static class MyThread extends Thread {
        private final int num;

        public MyThread(int n) {
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
        for (int i = 0; i < 3; i++) {
            new MyThread(i);
        }
    }
}
