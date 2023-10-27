import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class IncidenceMatrix<T> implements Graph<T> {

    private final HashMap<Integer, Vertex<T>> vertices;
    private final HashMap<Integer, Edge<T>> edges;
    private boolean[][] matrix;
    private int verticesSize;
    private int edgesSize;

    public IncidenceMatrix() {
        vertices        = new HashMap<>();
        edges           = new HashMap<>();
        verticesSize    = 10;
        edgesSize       = 10;
        matrix          = new boolean[verticesSize][edgesSize];
    }

    /**
     * Увеличивает вместимость вершин в матрице.
     */
    private void extendMatrixVertecies() {
        boolean[][] tmp = matrix;

        matrix = new boolean[verticesSize * 2][edgesSize];

        for (int i = 0; i < verticesSize; ++i) {
            System.arraycopy(tmp[i], 0, matrix[i], 0, edgesSize);
        }

        verticesSize *= 2;
    }

    /**
     * Увеличивает вместимость ребер в матрице.
     */
    private void extendMatrixEdges() {
        boolean[][] tmp = matrix;

        matrix = new boolean[verticesSize][edgesSize * 2];

        for (int i = 0; i < verticesSize; ++i) {
            if (edgesSize >= 0) System.arraycopy(tmp[i], 0, matrix[i], 0, edgesSize);
        }

        edgesSize *= 2;
    }

    /**
     * Добавляет вершину.
     *
     * @param vertex - Добавляемая вершина.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.put(vertex.getCurrentVertexNumber(), vertex);
        while (vertex.getCurrentVertexNumber() >= verticesSize)
            extendMatrixVertecies();
    }

    /**
     * Удаляет вершину.
     *
     * @param vertex - Удаляемая вершинаю
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    @Override
    public void removeVertex(Vertex<T> vertex) throws NoSuchElementException {
        if (vertices.containsKey(vertex.getCurrentVertexNumber())) {
            vertices.remove(vertex.getCurrentVertexNumber());

            for (int i = 0; i < edgesSize; ++i) {
                matrix[vertex.getCurrentVertexNumber()][i] = false;
            }
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Удаляет вершину по ее порядковому номеру.
     *
     * @param number - Номер вершины.
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    @Override
    public void removeVertexByNumber(int number) throws NoSuchElementException {
        if (vertices.containsKey(number)) {
            this.removeVertex(vertices.get(number));
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Изменяет значение переданнной вершины.
     *
     * @param vertex    - Изменяемая вершина.
     * @param newValue  - Новое значение.
     * @throws NoSuchElementException - - Если такой вершины нет.
     */
    @Override
    public void changeVertexValue(Vertex<T> vertex, T newValue) throws NoSuchElementException {
        if (vertices.containsKey(vertex.getCurrentVertexNumber())) {
            vertex.changeVertexValue(newValue);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Изменяет значение вершины по ее порядковому номеру.
     *
     * @param number    - Номер вершины.
     * @param newValue  - Новое значение.
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    @Override
    public void changeVertexValueByNumber(int number, T newValue) throws NoSuchElementException {
       if (vertices.containsKey(number)) {
           vertices.get(number).changeVertexValue(newValue);
       } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Возвращает вершину по ее порядковому номеру.
     *
     * @param vertexNumber  - Номер вершины.
     * @return vertex       - Нужная вершина.
     * @throws NoSuchElementException   - Если такой вершины нет.
     */
    @Override
    public Vertex<T> getVertexByNumber(int vertexNumber) throws NoSuchElementException {
        if (vertices.containsKey(vertexNumber)) {
            return vertices.get(vertexNumber);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Добавляет ребро между вершинами и добавляет их при необходимости.
     *
     * @param v1     - Вершина 1.
     * @param v2     - Вершина 2.
     * @param weight - Вес ребра.
     */
    @Override
    public Edge<T> addEdge(Vertex<T> v1, Vertex<T> v2, int weight) {
        if (!vertices.containsKey(v1.getCurrentVertexNumber())) {
            this.addVertex(v1);
        }
        if (!vertices.containsKey(v2.getCurrentVertexNumber())) {
            this.addVertex(v2);
        }

        Edge<T> edge = new Edge<>(v1, v2, weight);
        edges.put(edge.getEdgeNumber(), edge);

        while (edgesSize <= edge.getEdgeNumber()) {
            this.extendMatrixEdges();
        }

        matrix[v1.getCurrentVertexNumber()][edge.getEdgeNumber()] = true;
        matrix[v2.getCurrentVertexNumber()][edge.getEdgeNumber()] = true;

        return edge;
    }


    /**
     * Возвращает ребро по его номеру.
     *
     * @param number - Номер ребра.
     * @return edge   - Искомое.
     * @throws NoSuchElementException - Если не существует ребра с данным порядковым номером.
     */
    @Override
    public Edge<T> getEdgeByNumber(int number) throws NoSuchElementException {
        if (edges.containsKey(number)) {
            return edges.get(number);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Удаляет ребро по номеру.
     *
     * @param number - Порядковый номер ребра.
     * @throws NoSuchElementException - Если не существует ребра с данным порядковым номером.
     */
    @Override
    public void removeEdgeByNumber(int number) throws NoSuchElementException {
        if (edges.containsKey(number)) {
            edges.remove(number);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Перевешивает ребро.
     *
     * @param number - Порядковый номер ребра.
     * @param weight - Новый вес.
     * @throws NoSuchElementException - Если не существует ребра с данным порядковым номером.
     */
    @Override
    public void changeEdgeWeightByNumber(int number, int weight) throws NoSuchElementException {
        if (edges.containsKey(number)) {
            edges.get(number).changeEdgeWeight(weight);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Алгоритм Дейкстры.
     *
     * @param startVertex - Стартовая вершина.
     * @return Список вершин по удаленности от стартовой.
     */
    @Override
    public ArrayList<T> dijkstra(int startVertex) {
        vertices.forEach((k, v) -> v.setDistance(-1));
        ArrayList<T> ansList                = new ArrayList<>();
        ArrayList<Vertex<T>> visitedList    = new ArrayList<>();
        visitedList.add(this.getVertexByNumber(startVertex));
        visitedList.get(0).setDistance(0);
        Vertex<T> current;

        while (!visitedList.isEmpty()) {
            int minDist = visitedList.get(0).getDistance();
            int minPos = 0;
            for (int i = 0; i < visitedList.size(); ++i) {
                if (visitedList.get(i).getDistance() < minDist) {
                    minPos = i;
                    minDist = visitedList.get(i).getDistance();
                }
            }
            current = visitedList.get(minPos);
            visitedList.remove(current);
            ansList.add(current.getValue());

            for (int i = 0; i < edgesSize; ++i) {
                if (matrix[current.getCurrentVertexNumber()][i]) {
                    Edge<T> e = this.getEdgeByNumber(i);
                    int vertexNumber = e.getVertices().get(0);
                    if (vertexNumber == current.getCurrentVertexNumber()) {
                        vertexNumber = e.getVertices().get(1);
                    }

                    Vertex<T> v = this.getVertexByNumber(vertexNumber);

                    int newDistance = current.getDistance() + e.getEdgeWeight();
                    if (v.getDistance() == -1) {
                        v.setDistance(newDistance);
                        visitedList.add(v);
                    } else if (newDistance < v.getDistance()) {
                        v.setDistance(newDistance);
                    }
                }
            }
        }
        return ansList;
    }

    /**
     * Выдает все вершины.
     */
    @Override
    public HashMap<Integer, Vertex<T>> returnVertices() {
        return vertices;
    }

    /**
     * Выдает все ребра.
     */
    @Override
    public HashMap<Integer, Edge<T>> returnEdges() {
        return edges;
    }
}
