import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Клиент. Подклбчается к серверу и ждет от него работы.
 * Своего рода наемник, завершая вычисления, отрубается.
 */
public class Client {
    static class NotPrimeSearch {

        /**
         * Проверка непростоты.
         *
         * @param num число.
         * @return boolean.
         */
        public static boolean isNotPrime(int num) {
            if (num == 0 || num == 1) {
                return true;
            } else if (num == 2) {
                return false;
            } else if (num % 2 == 0) {
                return true;
            }

            for (int i = 3; i <= Math.sqrt(num); i += 2) {
                if (num % i == 0) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Последовательный поиск непростого чисел.
         *
         * @param array список целых чисел.
         * @return boolean.
         */
        public static boolean notPrimeSearch(List<Integer> array) {
            for (Integer integer : array) {
                if (isNotPrime(integer)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static Socket clientSocket;
    private static BufferedReader receive;
    private static BufferedWriter send;
    private static final List<Integer> list = new ArrayList<>();

    /**
     * Реализует логиуку взааимодействия с сервером.
     *
     * @param args main...
     */
    public static void main(String args) {
        try {
            try {
                try {
                    clientSocket = new Socket("localhost", 8080);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                receive = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                send = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                int len = receive.read();
                for (int i = 0; i < len; i++) {
                    list.add(receive.read());
                }

                send.write(String.valueOf(NotPrimeSearch.notPrimeSearch(list)));
                send.flush();

            } finally {
                clientSocket.close();
                receive.close();
                send.close();

            }
        } catch (IOException e) {
            System.err.println(e);
        }

    }
}
