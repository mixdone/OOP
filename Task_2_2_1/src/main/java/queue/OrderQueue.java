package queue;

import java.util.ArrayDeque;

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
     * @param element - Added element.
     */
    @Override
    public synchronized void add(T element) throws InterruptedException {
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
