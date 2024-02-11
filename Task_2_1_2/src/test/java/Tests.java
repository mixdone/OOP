import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

/**
 * Тест.
 */
public class Tests {

    static class MyThread extends Thread {
        private int num;

        public MyThread(int n) {
            num = n;
            start();
        }

        @Override
        public void run() {
            try {
                String[] s = {"", "", ""};
                if (num == 0) {
                    Server.main(s);
                } else {
                    Client.main(s);
                }
            } catch (IOException e) {
                throw new RuntimeException();
            }
        }
    }

    @Test
    public void test() throws IOException {
        ArrayList<MyThread> threads = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            threads.add(new MyThread(i));
        }
    }
}
