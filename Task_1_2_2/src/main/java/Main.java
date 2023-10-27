import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Main class.
 */
public class Main {

    /**
     * Чтение из файла и вызов Дейкстры.
     */
    public static void main(String[] args) {
        try (BufferedReader reader =
                     new BufferedReader(new FileReader("./src/main/resources/input.txt"))) {
            String line;
            HashMap<String, Vertex<String>> buffer  = new HashMap<>();
            IncidenceList<String> graph             = new IncidenceList<>();

            line = reader.readLine();
            Vertex<String> vertex = new Vertex<>(line.split(" ")[0]);
            buffer.put(line.split(" ")[0], vertex);
            while ((line = reader.readLine()) != null) {
                Vertex<String> v1;
                Vertex<String> v2;
                String[] splitedLine = line.split(" ");
                if (!buffer.containsKey(splitedLine[0])) {
                    v1 = new Vertex<>(splitedLine[0]);
                } else {
                    v1 = buffer.get(splitedLine[0]);
                }

                if (!buffer.containsKey(splitedLine[1])) {
                    v2 = new Vertex<>(splitedLine[1]);
                } else {
                    v2 = buffer.get(splitedLine[1]);
                }

                graph.addEdge(v1, v2, toInt(splitedLine[2]));
                buffer.put(splitedLine[0], v1);
                buffer.put(splitedLine[1], v2);
            }

            ArrayList<String> list = graph.dijkstra(vertex.getCurrentVertexNumber());
            for (String s : list) {
                System.out.print(s);
                System.out.print(" ");
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     *  Приводит строку к числу.
     * @param s - сторка.
     * @return Искомое число.
     */
    public static int toInt(String s) {
        int ans = 0;
        for (int i = 0; i < s.length(); ++i) {
            ans += (s.charAt(i) * (int) Math.pow(10, s.length() - i - 1));
        }

        return ans;
    }
}
