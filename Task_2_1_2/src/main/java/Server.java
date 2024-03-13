import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Server class.
 */
public class Server {
    public AtomicBoolean hasNotPrime    = new AtomicBoolean(false);
    public ArrayList<Boolean> hasDone   = new ArrayList<>();

    private boolean threadManager(ArrayList<ClientHandler> threads,
                                  ArrayList<List<Integer>> matrix) {
        int len = matrix.size();
        for (int i = 0; i < len; i++) {
            hasDone.add(false);
            threads.get(i).setIntegerList(matrix.get(i));
            threads.get(i).start();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (hasNotPrime.get()) {
            for (ClientHandler x : threads) {
                x.interrupt();
                x.finish();
            }
            return true;
        }

        ArrayList<List<Integer>> newMatrix = new ArrayList<>();
        ArrayList<ClientHandler> newThreads = new ArrayList<>();

        var aliveSocketsNumber = 0;
        for (int i = 0; i < len; i++) {
            if (!hasDone.get(i)) {
                threads.get(i).interrupt();
                threads.get(i).finish();
                newMatrix.add(matrix.get(i));
            } else {
                aliveSocketsNumber++;
            }
        }

        if (aliveSocketsNumber == 0) {
            throw new RuntimeException("All connections are dead!");
        }

        if (!newMatrix.isEmpty()) {
            System.out.println(aliveSocketsNumber);
            newMatrix = split(merge(newMatrix), aliveSocketsNumber);
            int j = 0;
            for (int i = 0; i < len; i++) {
                if (hasDone.get(i)) {
                    newThreads.add(new ClientHandler(
                            threads.get(i).getClient(),
                            threads.get(i).getReceive(),
                            threads.get(i).getSend(),
                            j)
                    );
                }
                j++;
            }

            return threadManager(newThreads, newMatrix);
        }

        for (ClientHandler x : threads) {
            x.interrupt();
            x.finish();
        }
        return false;
    }

    /**
     * Controls client connections, creates handler threads for them.
     *
     * @return threads.
     */
    private ArrayList<ClientHandler> receiver(ServerSocket server) {
        var clientNumber = 0;
        var threads = new ArrayList<ClientHandler>();
        Socket socket = null;
        Scanner receive;
        PrintWriter send;

        while (true) {
            try {
                socket = server.accept();
                receive = new Scanner(socket.getInputStream());
                send = new PrintWriter(socket.getOutputStream());

                var newHandler = new ClientHandler(
                        socket,
                        receive,
                        send,
                        clientNumber
                );
                threads.add(newHandler);
                clientNumber++;
            } catch (SocketTimeoutException timeoutException) {
                break;
            } catch (IOException ioException) {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                }
            }

        }

        return threads;
    }

    /**
     * Splits the list into no more than the specified number of parts.
     *
     * @param list      - Input integers
     * @param number    - Required number of parts
     *
     * @return list of lists.
     */
    private ArrayList<List<Integer>> split(ArrayList<Integer> list, int number) {
        ArrayList<List<Integer>> matrix = new ArrayList<>();

        if (number > list.size()) {
            number = list.size();
        }

        int part = list.size() / number;

        for (int i = 0; i < number - 1; i++) {
            matrix.add(list.subList(i * part, (i + 1) * part));
        }
        matrix.add(list.subList((number - 1) * part, list.size()));

        return matrix;
    }

    /**
     * Merge matrix into list.
     *
     * @param matrix - input matrix.
     *
     * @return arraylist.
     */
    private ArrayList<Integer> merge(ArrayList<List<Integer>> matrix) {
        var list = new ArrayList<Integer>();
        for (List<Integer> x : matrix) {
            list.addAll(x);
        }
        return list;
    }

    /**
     * Start the server.
     */
    public boolean start(ArrayList<Integer> integers) {
        ServerSocket server = null;
        boolean result = false;

        try {
            try {
                server = new ServerSocket(8080);
                server.setSoTimeout(10000);
                ArrayList<ClientHandler> threads = receiver(server);
                var splittedList = split(integers, threads.size());
                result = threadManager(threads, splittedList);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            } finally {
                if (server != null) {
                    server.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return result;
    }

    /**
     * Client handler class.
     */
    class ClientHandler extends Thread {
        private List<Integer> integerList;

        private final Socket client;
        private final Scanner receive;
        private final PrintWriter send;
        private final int number;

        /**
         * The class constructor.
         *
         * @param client    - Client socket.
         * @param input     - Input stream(Scanner).
         * @param output    - Output stream(PrintWriter).
         * @param number    - Sublist number in the list.
         */
        public ClientHandler(Socket client, Scanner input, PrintWriter output, int number) {
            this.client     = client;
            this.receive    = input;
            this.send       = output;
            this.number     = number;
        }

        /**
         * Return client socket.
         *
         * @return client.
         */
        public Socket getClient() {
            return client;
        }

        /**
         * Return input stream.
         *
         * @return Scanner.
         */
        public Scanner getReceive() {
            return receive;
        }

        /**
         * Return output stream.
         *
         * @return PrintWriter.
         */
        public PrintWriter getSend() {
            return send;
        }

        /**
         * Return sublist number.
         *
         * @return number.
         */
        public int getNumber() {
            return number;
        }

        /**
         * Set list.
         *
         * @param integerList - integers.
         */
        public void setIntegerList(List<Integer> integerList) {
            this.integerList = integerList;
        }

        public void finish() {
            send.println(true);
            send.flush();
            send.println(0);
            try {
                Thread.sleep(100);
                client.close();
            } catch (InterruptedException ignored) {
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Runs this operation.
         */
        @Override
        public void run() {
            send.println(true);
            send.flush();

            if (receive.hasNextBoolean()) {
                receive.nextBoolean();
            }

            send.println(integerList.size());
            for (Integer x : integerList) {
                send.println(x);
            }
            send.flush();

            if (receive.hasNextBoolean()) {
                hasNotPrime.set(receive.nextBoolean());
                hasDone.set(number, true);
            }
        }
    }
}


