import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * Граф.
 * @param <T> - параметр вершины.
 */
public interface Graph<T> {

    /**
     * Добавляет вершину.
     *
     * @param vertex - Добавляемая вершина.
     */
    void addVertex(Vertex<T> vertex);

    /**
     * Удаляет вершину.
     *
     * @param vertex - Удаляемая вершинаю
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    void removeVertex(Vertex<T> vertex) throws NoSuchElementException;

    /**
     * Удаляет вершину по ее порядковому номеру.
     *
     * @param number - Номер вершины.
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    void removeVertexByNumber(int number) throws NoSuchElementException;

    /**
     * Изменяет значение переданнной вершины.
     *
     * @param vertex    - Изменяемая вершина.
     * @param newValue  - Новое значение.
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    void changeVertexValue(Vertex<T> vertex, T newValue) throws NoSuchElementException;

    /**
     * Изменяет значение вершины по ее порядковому номеру.
     *
     * @param number    - Номер вершины.
     * @param newValue  - Новое значение.
     * @throws NoSuchElementException - Если такой вершины нет.
     */
    void changeVertexValueByNumber(int number, T newValue) throws NoSuchElementException;


    /**
     * Возвращает вершину по ее порядковому номеру.
     *
     * @param vertexNumber  - Номер вершины.
     * @return vertex       - Нужная вершина.
     * @throws NoSuchElementException   - Если такой вершины нет.
     */
    Vertex<T> getVertexByNumber(int vertexNumber) throws NoSuchElementException;


    /**
     * Добавляет ребро между вершинами и добавляет их при необходимости.
     *
     * @param v1     - Вершина 1.
     * @param v2     - Вершина 2.
     * @param weight - Вес ребра.
     */
    Edge<T> addEdge(Vertex<T> v1, Vertex<T> v2, int weight);

    /**
     * Удаляет ребро по номеру.
     *
     * @param number - Порядковый номер ребра.
     * @throws NoSuchElementException - Если не существует ребра с данным порядковым номером.
     */
    void removeEdgeByNumber(int number) throws NoSuchElementException;

    /**
     * Возвращает ребро по его номеру.
     *
     * @param number    - Номер ребра.
     * @return edge     - Искомое.
     * @throws NoSuchElementException - Если не существует ребра с данным порядковым номером.
     */
    Edge<T> getEdgeByNumber(int number) throws NoSuchElementException;

    /**
     * Перевешивает ребро.
     *
     * @param number - Порядковый номер ребра.
     * @param weight - Новый вес.
     * @throws NoSuchElementException - Если не существует ребра с данным порядковым номером.
     */
    void changeEdgeWeightByNumber(int number, int weight) throws NoSuchElementException;

    /**
     * Алгоритм Дейкстры.
     *
     * @param startVertex - Стартовая вершина.
     * @return Список вершин по удаленности от стартовой.
     */
    ArrayList<T> dijkstra(int startVertex);

    /**
     * Выдает все вершины.
     */
    HashMap<Integer, Vertex<T>> returnVertices();

    /**
     * Выдает все ребра.
     */
    HashMap<Integer, Edge<T>> returnEdges();

}
