import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Сервер. Ждет, пока надется необходимое количество добравольцев,
 * готовых работать. Раздает им задачи и ждет, пока те все сделают.
 */
public class Server {

    public static AtomicBoolean hasNotPrime = new AtomicBoolean(false);
    public static AtomicInteger finish = new AtomicInteger(0);

    /**
     * Потоки взаимодействия с клиентами.
     */
    static class OneServerThread extends Thread {

        private Socket socket;
        private Scanner receive;
        private BufferedWriter send;
        private List<Integer> list;

        /**
         * Конструктор.
         *
         * @param socket сокет.
         * @throws IOException ошибки :( .
         */
        public OneServerThread(Socket socket) throws IOException {
            this.socket = socket;
            receive = new Scanner(new InputStreamReader(socket.getInputStream()));
            send = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }

        /**
         * Установка списка для конкретного клиента.
         *
         * @param newList список.
         */
        public void initList(List<Integer> newList) {
            list = newList;
        }

        /**
         * Логика взаимодействия с клиентом.
         */
        @Override
        public void run() {

            try {
                int len = list.size();
                send.write(len);
                send.flush();
                for (int i = 0; i < len; i++) {
                    send.write(list.get(i));
                    send.flush();
                }
                if (receive.nextBoolean()) {
                    hasNotPrime.set(true);
                }
            } catch (IOException e) {
                System.out.println("Ошибка при работе с частью списка:");
                for (Integer integer : list) {
                    System.out.print(integer + ", ");
                }
            } finally {
                finish.getAndIncrement();
                try {
                    socket.close();
                } catch (IOException ignored) {}
            }
        }

    }

    public static final int PORT = 8080;
    public static List<OneServerThread> serverList = new LinkedList<>();
    public static int clientNumber = 0;
    public static int maxClientNumber = 2;

    /**
     *
     * @param args main.
     * @throws IOException ошибки.
     */
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(PORT);
        try {
            while (clientNumber < maxClientNumber) {
                Socket socket = server.accept();
                clientNumber++;
                try {
                    serverList.add(new OneServerThread(socket));
                } catch (IOException e) {
                    socket.close();
                    clientNumber--;
                }
            }

            var list = Arrays.asList(4, 4, 4, 4, 4, 4, 4, 5, 4, 4 ,4); // Пример
            int part = list.size() / clientNumber;

            for (int i = 0; i < clientNumber - 1; i++) {
                serverList.get(i).initList(list.subList(i * part, (i + 1) * part));
                serverList.get(i).start();
            }

            serverList.get(clientNumber - 1).initList(list.subList((clientNumber - 1)
                    * part, list.size()));
            serverList.get(clientNumber - 1).start();


            for (int i = 0; i < clientNumber; i++) {
                try {
                    serverList.get(i).join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            System.out.print(hasNotPrime.get());

        } finally {
            server.close();
        }
    }
}