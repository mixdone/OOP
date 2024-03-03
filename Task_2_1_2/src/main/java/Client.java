import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Client class. Connected to server, get task, calculate, return result.
 */
public class Client {
    private static Socket socket;

    /**
     * Client.
     *
     * @param args - main.
     */
    public static void main(String[] args) {
        try {
            socket = new Socket("localhost", 8080);
            var in = new Scanner(socket.getInputStream());
            var out = new PrintWriter(socket.getOutputStream());

            while (true) {
                if (in.hasNextBoolean()) {
                    in.nextBoolean();
                    out.println(true);
                    out.flush();
                }

                int len = 0;
                if (in.hasNextInt()) {
                    len = in.nextInt();
                }

                if (len == 0) {
                    break;
                }
                ArrayList<Integer> list = new ArrayList<>();
                for (int i = 0; i < len; i++) {
                    if (in.hasNextInt()) {
                        list.add(in.nextInt());
                    }
                }
                NotPrimeSearch search = new NotPrimeSearch();
                boolean result = search.notPrimeSearch(list);
                if (result) {
                    out.println(true);
                    out.flush();
                } else {
                    out.println(false);
                    out.flush();
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                socket.close();
            } catch (IOException ignored) {}
        }
    }
}
