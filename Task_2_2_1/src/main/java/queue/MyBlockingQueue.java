package queue;

/**
 * Blocking queue interface.
 *
 * @param <T> Generic type.
 */
public interface MyBlockingQueue<T> {
    /**
     * Are there any element in the queue?
     *
     * @return boolean
     */
    boolean isEmpty();

    /**
     * Add an element to the queue.
     *
     * @param element Added element.
     */
    void add(T element);

    /**
     * Takes an element out of the queue.
     *
     * @return element.
     */
    T get();
}
