import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

public class IncidenceList<T> implements Graph<T>{
    private final HashMap<Integer, Vertex<T>> vertices;
    private final HashMap<Integer, Edge<T>> edges;
    private final HashMap<Vertex<T>, ArrayList<Edge<T>>> incidenceList;
    public IncidenceList() {
        vertices        = new HashMap<>();
        edges           = new HashMap<>();
        incidenceList   = new HashMap<>();
    }

    /**
     * Добавляет вершину.
     *
     * @param vertex - Добавляемая вершина.
     */
    @Override
    public void addVertex(Vertex<T> vertex) {
        vertices.put(vertex.getCurrentVertexNumber(), vertex);
        incidenceList.put(vertex, new ArrayList<>());
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
            incidenceList.remove(vertex);
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
            Vertex<T> vertex = this.getVertexByNumber(number);
            incidenceList.remove(vertex);
            vertices.remove(number);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Изменяет значение переданнной вершины.
     *
     * @param vertex   - Изменяемая вершина.
     * @param newValue - Новое значение.
     * @throws NoSuchElementException - Если такой вершины нет.
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
     * @param number   - Номер вершины.
     * @param newValue - Новое значение.
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    @Override
    public void changeVertexValueByNumber(int number, T newValue) throws NoSuchElementException {
        if (vertices.containsKey(number)) {
            Vertex<T> vertex = this.getVertexByNumber(number);
            vertex.changeVertexValue(newValue);
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Возвращает вершину по ее порядковому номеру.
     *
     * @param vertexNumber - Номер вершины.
     * @return vertex       - Нужная вершина.
     * @throws NoSuchElementException - Если такой вершины нет.
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
        if (!vertices.containsKey(v1.getCurrentVertexNumber())) this.addVertex(v1);
        if (!vertices.containsKey(v2.getCurrentVertexNumber())) this.addVertex(v2);

        Edge<T> edge = new Edge<>(v1, v2, weight);
        edges.put(edge.getEdgeNumber(), edge);

        incidenceList.get(v1).add(edge);
        incidenceList.get(v2).add(edge);

        return edge;
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
            Edge<T> edge = this.getEdgeByNumber(number);

            Vertex<T> v1 = this.getVertexByNumber(edge.getVertices().get(0));
            Vertex<T> v2 = this.getVertexByNumber(edge.getVertices().get(1));

            incidenceList.get(v1).remove(edge);
            incidenceList.get(v2).remove(edge);

            edges.remove(number);
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
            this.getEdgeByNumber(number).changeEdgeWeight(weight);
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

            ArrayList<Edge<T>> listForCurrentVertex = incidenceList.get(current);
            for (Edge<T> e : listForCurrentVertex) {
                int vNumber = e.getVertices().get(0);
                if (vNumber == current.getCurrentVertexNumber())
                    vNumber = e.getVertices().get(1);

                Vertex<T> v = this.getVertexByNumber(vNumber);
                int newDistance = current.getDistance() + e.getEdgeWeight();
                if (v.getDistance() == -1) {
                    v.setDistance(newDistance);
                    visitedList.add(v);
                } else if (newDistance < v.getDistance()) {
                    v.setDistance(newDistance);
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
