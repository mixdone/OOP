public class Vertex<T> {
    private T value;
    private final int vertexNumber;

    private int distance;
    private static int vertexCounter = 0;

    /**
     * Конструктор.
     */
    public Vertex(T value) {
        this.value          = value;
        this.vertexNumber   = vertexCounter;
        this.distance       = -1;
        ++vertexCounter;
    }

    /**
     * Возвращает удаленность данной вершины от стартовой в алгоритме Дейкстры.
     */
    public int getDistance() {
        return this.distance;
    }

    /**
     * Релаксация.
     */
    public void setDistance(int newDistance) {
        this.distance = newDistance;
    }

    /**
     * Изменяет значение вершины.
     *
     * @param newValue - новое значение вершины.
     *
     */
    public void changeVertexValue(T newValue) {
        this.value = newValue;
    }

    /**
     * Возвращает значение вершины.
     */
    public T getValue() {
        return this.value;
    }

    /**
     * Возвращает номер вершины.
     */
    public int getCurrentVertexNumber() {
        return this.vertexNumber;
    }
}
