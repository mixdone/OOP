import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestServerClientDie {
    Boolean result = false;

    private static class ClientThread implements Runnable {

        @Override
        public void run() {
            Client.main(new String[]{""});
        }
    }

    private class ServerThread implements Runnable {
        Server server;
        ArrayList<Integer> list;

        public ServerThread(Server server, ArrayList<Integer> list) {
            this.server = server;
            this.list   = list;
        }

        @Override
        public void run() {
            result = server.start(list);
        }
    }

    @Test
    public void testTrue() {
        var list = new ArrayList<>(Arrays.asList(3, 5, 7, 11, 12, 13, 17));
        var server  = new Thread(new ServerThread(new Server(), list));
        var c1      = new Thread(new ClientThread());
        var c2      = new Thread(new ClientThread());
        var c3      = new Thread(new ClientThread());
        var c4      = new Thread(new ClientThread());

        server.start();
        c1.start();
        c2.start();
        c3.start();
        c4.start();

        try {
            c1.interrupt();
            c2.join();
            c3.join();
            c4.join();
            server.join();

            Assertions.assertTrue(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }
}
