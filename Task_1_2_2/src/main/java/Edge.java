import java.util.ArrayList;
import java.util.List;

/**
 * Реборо класс.
 */
public class Edge<T> {
    private final int firstVertexNumber;
    private final int secondVertexNumber;
    private final int edgeNumber;
    private int edgeWeight;
    private static int edgeCounter = 0;

    /**
     * Конструктор.
     *
     * @param v1        - Вершина 1.
     * @param v2        - Вершина 2.
     * @param weight    - Вес ребра.
     */
    public Edge(Vertex<T> v1, Vertex<T> v2, int weight) {
        this.firstVertexNumber  = v1.getCurrentVertexNumber();
        this.secondVertexNumber = v2.getCurrentVertexNumber();
        this.edgeNumber         = edgeCounter;
        this.edgeWeight         = weight;
        ++edgeCounter;
    }

    /**
     * Изменяет вес ребра.
     *
     * @param newWeight - новый вес.
     */
    public void changeEdgeWeight(int newWeight) {
        this.edgeWeight = newWeight;
    }

    /**
     * Возвращает ребро, как список вершин.
     *
     * @return edge - [Vertex1, Vertex2]
     */
    public List<Integer> getVertices() {
        List<Integer> edge = new ArrayList<>();
        edge.add(this.firstVertexNumber);
        edge.add(this.secondVertexNumber);
        return edge;
    }

    /**
     * Возврашает вес ребра.
     */
    public int getEdgeWeight() {
        return this.edgeWeight;
    }

    /**
     * Возвращает номер ребра.
     */
    public int getEdgeNumber() {
        return this.edgeNumber;
    }
}
