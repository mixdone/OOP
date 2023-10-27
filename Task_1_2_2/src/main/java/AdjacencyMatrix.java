import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class AdjacencyMatrix<T> implements Graph<T> {

    private final HashMap<Integer, Vertex<T>> vertices;
    private final HashMap<Integer, Edge<T>> edges;
    private int[][] matrix;
    private int matrixSize;

    public AdjacencyMatrix() {
        vertices    = new HashMap<>();
        edges       = new HashMap<>();
        matrix      = new int[10][10];
        matrixSize  = 10;
    }

    /**
     * Увеличивает вместимость матрицы.
     */
    public void extendMatrix() {
        int[][] tmp = this.matrix;

        this.matrix = new int[matrixSize * 2][matrixSize * 2];

        for (int i = 0; i < matrixSize; ++i) {
            System.arraycopy(tmp[i], 0, matrix[i], 0, matrixSize);
        }

        this.matrixSize *= 2;
    }

    /**
     * Добавляет вершину.
     *
     * @param vertex - Добавляемая вершина.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.put(vertex.getCurrentVertexNumber(), vertex);

        while (vertex.getCurrentVertexNumber() >= matrixSize)
            this.extendMatrix();
    }

    /**
     * Удаляет вершину.
     *
     * @param vertex - Удаляемая вершинаю
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    @Override
    public void removeVertex(Vertex<T> vertex)  throws NoSuchElementException {
        if (vertices.containsKey(vertex.getCurrentVertexNumber())) {
            vertices.remove(vertex.getCurrentVertexNumber());

            int i = vertex.getCurrentVertexNumber();
            for (int j = 0; j < this.matrixSize; ++j) {
                matrix[i][j] = 0;
                matrix[j][i] = 0;
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
            vertices.remove(number);
            for (int j = 0; j < this.matrixSize; ++j) {
                this.matrix[number][j] = 0;
                this.matrix[j][number] = 0;
            }
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
        if (!vertices.containsKey(vertex.getCurrentVertexNumber())) {
            throw new NoSuchElementException();
        } else {
            vertex.changeVertexValue(newValue);
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
    public void changeVertexValueByNumber(int number, T newValue) {
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
    public Vertex<T> getVertexByNumber(int vertexNumber) throws NoSuchElementException{
        if(!vertices.containsKey(vertexNumber)) {
            throw new NoSuchElementException();
        }
        else {
            return vertices.get(vertexNumber);
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
        if (!vertices.containsKey(v1.getCurrentVertexNumber())) this.addVertex(v1);
        if (!vertices.containsKey(v2.getCurrentVertexNumber())) this.addVertex(v2);

        matrix[v1.getCurrentVertexNumber()][v2.getCurrentVertexNumber()] = weight;
        matrix[v2.getCurrentVertexNumber()][v1.getCurrentVertexNumber()] = weight;

        Edge<T> newEdge = new Edge<>(v1, v2, weight);
        edges.put(newEdge.getEdgeNumber(), newEdge);

        return newEdge;
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
            Edge<T> e = edges.get(number);
            int v1 = e.getVertices().get(0);
            int v2 = e.getVertices().get(1);

            edges.remove(number);

            matrix[v1][v2] = 0;
            matrix[v2][v1] = 0;
        } else {
            throw new NoSuchElementException();
        }
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
     * Перевешивает ребро.
     *
     * @param number - Порядковый номер ребра.
     * @param weight - Новый вес.
     * @throws NoSuchElementException - Если не существует ребра с данным порядковым номером.
     */
    @Override
    public void changeEdgeWeightByNumber(int number, int weight) throws NoSuchElementException {
        if (edges.containsKey(number)) {
            Edge<T> e = edges.get(number);
            int v1 = e.getVertices().get(0);
            int v2 = e.getVertices().get(1);

            e.changeEdgeWeight(weight);

            matrix[v1][v2] = weight;
            matrix[v2][v1] = weight;
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

            for (int i = 0; i < matrixSize; ++i) {
                if (matrix[current.getCurrentVertexNumber()][i] != 0) {
                    Vertex<T> v = this.getVertexByNumber(i);
                    int newDistance = current.getDistance() + matrix[current.getCurrentVertexNumber()][i];
                    if (v.getDistance() == -1) {
                        v.setDistance(newDistance);
                        visitedList.add(v);
                    }
                    else if (newDistance < v.getDistance()) {
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



