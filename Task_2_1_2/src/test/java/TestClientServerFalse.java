import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

public class TestClientServerFalse {

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
    public void testFalse() {
        var list = new ArrayList<>(Arrays.asList(3, 5, 7, 11, 13, 17));
        var server  = new Thread(new ServerThread(new Server(), list));
        var c1      = new Thread(new ClientThread());
        var c2      = new Thread(new ClientThread());

        server.start();
        c1.start();
        c2.start();

        try {
            c1.join();
            c2.join();
            server.join();

            Assertions.assertFalse(result);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }


}
