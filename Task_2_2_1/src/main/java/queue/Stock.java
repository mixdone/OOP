package queue;

import java.util.ArrayDeque;

/**
 * Stock by my blocking queue.
 *
 * @param <T> Generic type.
 */
public class Stock<T> implements MyBlockingQueue<T> {
    private final ArrayDeque<T> deque;
    private final int size;

    public Stock(int size) {
        this.deque = new ArrayDeque<>(size);
        this.size = size;
    }

    /**
     * Are there any element in the queue?
     *
     * @return boolean
     */
    @Override
    public synchronized boolean isEmpty() {
        var ans = this.deque.isEmpty();
        notifyAll();
        return ans;
    }

    /**
     * Add an element to the queue.
     *
     * @param element Added element.
     */
    @Override
    public synchronized void add(T element) throws InterruptedException {
        while (deque.size() >= this.size) {
            wait();
        }
        deque.addLast(element);
        notifyAll();
    }

    /**
     * Takes an element out of the queue.
     *
     * @return element.
     */
    @Override
    public T get() throws InterruptedException {
        while (deque.isEmpty()) {
            wait();
        }

        var object = deque.removeFirst();
        notifyAll();
        return object;
    }
}
