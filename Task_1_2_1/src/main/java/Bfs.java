import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;


/**
 * Итератор bfs.
 */
public class Bfs<T> implements Iterator<T> {
    Queue<Tree<T>> queue;
    Tree<T> root;
    int expectedModCount;

    /**
     * Создает очередь.
     */
    public Bfs(Tree<T> node) {
        this.expectedModCount = node.modCount;
        this.queue = new ArrayDeque<>();
        this.queue.add(node);
        this.root = node;
    }

    /**
     * Проверяет наличие следующего элемента.
     *
     * @return flag not empty
     */
    @Override
    public boolean hasNext() throws ConcurrentModificationException{
        if (root.modCount != this.expectedModCount) {
            throw new ConcurrentModificationException();
        } else {
            return !this.queue.isEmpty();
        }
    }

    /**
     * Возвращает следующий элемент.
     *
     * @return next value or thow exception
     */
    @Override
    public T next() throws NoSuchElementException {

        if (hasNext()) {
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
