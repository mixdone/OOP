import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Клиент. Подклбчается к серверу и ждет от него работы.
 * Своего рода наемник, завершая вычисления, отрубается.
 */
public class Client {
    private static Socket clientSocket;
    private static BufferedReader receive;
    private static BufferedWriter send;

    /**
     * Реализует логиуку взааимодействия с сервером.
     *
     * @param args main...
     */
    public static void main(String[] args) {
        try {
            try {
                try {
                    clientSocket = new Socket("localhost", 8080);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                receive = new BufferedReader(new
                        InputStreamReader(clientSocket.getInputStream()));
                send = new BufferedWriter(new
                        OutputStreamWriter(clientSocket.getOutputStream()));
                while (true) {
                    int len = receive.read();
                    if (len == -1) {
                        break;
                    }
                    ArrayList<Integer> numberList = new ArrayList<>();

                    send.write(String.valueOf(true));
                    send.flush();

                    for (int i = 0; i < len; i++) {
                        numberList.add(receive.read());
                    }
                    send.write(String.valueOf(true));
                    send.flush();

                    NotPrimeSearch search = new NotPrimeSearch();
                    send.write(String.valueOf(search.notPrimeSearch(numberList)));
                    send.flush();
                }
            } finally {
                clientSocket.close();
                receive.close();
                send.close();

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
