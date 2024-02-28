import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * Сервер. Ждет, пока надется необходимое количество добравольцев,
 * готовых работать. Раздает им задачи и ждет, пока те все сделают.
 */
public class Server {

    static final AtomicBoolean hasNotPrime = new AtomicBoolean(false);
    static final ArrayList<Boolean> computationComplete = new ArrayList<>();

    static class ServerSomthing extends Thread {

        private final Socket socket;
        private final Scanner receive;
        private final BufferedWriter send;
        private List<Integer> integerList;
        private int listPart;

        /**
         * Основной конструктор.
         *
         * @param socket - сокет.
         *
         * @throws IOException - ошибки потоков.
         */
        public ServerSomthing(Socket socket) throws IOException {
            this.socket = socket;
            receive     = new Scanner(socket.getInputStream());
            send        = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        }

        /**
         * Конструктор переиспользования сокета.
         *
         * @param socket    - сокет.
         * @param in        - сканер входящий сообщений от клиента
         * @param out       - поток отправки сообщений клиенту
         */
        public ServerSomthing(Socket socket, Scanner in, BufferedWriter out) {
            this.socket = socket;
            receive     = in;
            send        = out;
        }

        /**
         * Усталавливает область определения.
         *
         * @param intList   - список целых чисел.
         * @param part      - номер части массива, за которую отвечает данный поток.
         */
        public void addList(List<Integer> intList, int part) {
            integerList = intList;
            listPart = part;

        }

        /**
         * Получить данный сокет.
         *
         * @return socket.
         */
        public Socket getSocket() {
            return socket;
        }

        /**
         * Получить поток отправки сообщений клиенту.
         *
         * @return send.
         */
        public BufferedWriter getSend() {
            return send;
        }

        /**
         * Получить сканер входящий сообщений от клиента.
         *
         * @return receive
         */
        public Scanner getReceive() {
            return receive;
        }

        /**
         * run.
         */
        @Override
        public void run() {
            int listLength = integerList.size();
            try {
                send.write(String.valueOf(listLength));
                receive.nextBoolean();
                for (Integer x : integerList) {
                    send.write(String.valueOf(x));
                }
                receive.nextBoolean();
                if (receive.nextBoolean()) {
                    hasNotPrime.set(true);
                }
                computationComplete.set(listPart, true);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static final int PORT = 8080;
    private static int socketNumber = 0;

    private static boolean threadManager(LinkedList<ServerSomthing> sockets, ArrayList<Integer> integers) throws SocketException {
        int part = integers.size() / socketNumber;
        for (int i = 0; i < socketNumber - 1; i++) {
            sockets.get(i).addList(integers.subList(i * part, (i + 1) * part), i);
            sockets.get(i).start();
        }

        sockets.get(socketNumber - 1).addList(integers.subList((socketNumber - 1)
                * part, integers.size()), socketNumber - 1);
        sockets.get(socketNumber - 1).start();


        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (hasNotPrime.get()) {
            return true;
        }

        ArrayList<Integer> newList = new ArrayList<>();
        int uncompletedSocketNumber = 0;
        LinkedList<ServerSomthing> newSockets = new LinkedList<>();
        for (int i = 0; i < socketNumber - 1; i++) {
            if (!computationComplete.get(i)) {
                ++uncompletedSocketNumber;
                newList.addAll(integers.subList(i * part, (i + 1) * part));
            } else {
                newSockets.add(new ServerSomthing(sockets.get(i).getSocket(),
                        sockets.get(i).getReceive(), sockets.get(i).getSend()));
            }
        }

        if (!computationComplete.get(socketNumber - 1)) {
            ++uncompletedSocketNumber;
            newList.addAll(integers.subList((socketNumber - 1) * part, ((socketNumber - 1) + 1) * part));
        } else {
            newSockets.add(new ServerSomthing(sockets.get(socketNumber - 1).getSocket(),
                    sockets.get(socketNumber - 1).getReceive(), sockets.get(socketNumber - 1).getSend()));
        }


        if (uncompletedSocketNumber > 0) {
            socketNumber = newSockets.size();
            if (socketNumber == 0) {
                throw new SocketException();
            }
            return threadManager(newSockets, newList);
        }

        return false;
    }

    private static LinkedList<ServerSomthing> server() {
        LinkedList<ServerSomthing> serverList = new LinkedList<>();
        try {
            ServerSocket server = new ServerSocket(PORT);
            try (server) {
                server.setSoTimeout(5000);
                while (true) {
                    Socket socket = new Socket();
                    try {
                        socket = server.accept();
                        serverList.add(new ServerSomthing(socket));
                        ++socketNumber;
                        System.out.println("Success connection!!!");
                    } catch (SocketTimeoutException e) {
                        System.out.println(e);
                        break;
                    } catch (IOException e) {
                        socket.close();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return serverList;
    }

    private static ArrayList<Integer> reader() {
        Scanner stdin = new Scanner(System.in);
        int len = stdin.nextInt();
        ArrayList<Integer> integers = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            integers.add(stdin.nextInt());
        }

        return integers;
    }

    public static void main(String[] args) {
        LinkedList<ServerSomthing> serverList = server();
        if (socketNumber > 0) {
            try {
                System.out.println(threadManager(serverList, reader()));
            } catch (SocketException e) {
                System.out.println("Sorry, there was a critical error, try again later.");
            }
        } else {
            System.out.println("Not enough computing power.");
        }
    }
}