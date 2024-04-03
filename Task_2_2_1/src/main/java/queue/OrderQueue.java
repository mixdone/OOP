package queue;

import java.util.ArrayDeque;

/**
 * Order queue by my blocking queue.
 *
 * @param <T> Generic type.
 */
public class OrderQueue<T> implements MyBlockingQueue<T>{
    private final ArrayDeque<T> deque = new ArrayDeque<>();

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
    public synchronized void add(T element) {
        deque.addLast(element);
        notifyAll();
    }

    /**
     * Takes an element out of the queue.
     *
     * @return element.
     */
    @Override
    public synchronized T get() {
        while (deque.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                return null;
            }
        }

        var object = deque.removeFirst();
        notifyAll();
        return object;
    }
}