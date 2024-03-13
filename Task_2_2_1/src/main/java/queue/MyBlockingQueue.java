package queue;

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
     * @param element - Added element.
     */
    void add(T element) throws InterruptedException;

    /**
     * Takes an element out of the queue.
     *
     * @return element.
     */
    T get() throws InterruptedException;
}
