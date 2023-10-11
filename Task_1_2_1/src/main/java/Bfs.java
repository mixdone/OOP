import java.util.*;

public class Bfs<T> implements Iterator<T> {
    Queue<Tree<T>> queue;

    public Bfs(Tree<T> node) {
        this.queue = new ArrayDeque<>();
        this.queue.add(node);
    }

    /**
     * Проверяет наличие следующего элемента.
     * @return flag not empty
     */
    @Override
    public boolean hasNext() {
        return !this.queue.isEmpty();
    }

    /**
     * Возвращает следующий элемент.
     * @return next value or thow exception
     */
    @Override
    public T next() throws NoSuchElementException {
        if(hasNext()) {
            Tree<T> current = this.queue.remove();
            this.queue.addAll(current.children);
            return current.value;
        }
        throw new NoSuchElementException();
    }

    /**
     *  Обработка ошибки.
     */
    @Override
    public void remove() throws ConcurrentModificationException {
        throw new ConcurrentModificationException();
    }
}
