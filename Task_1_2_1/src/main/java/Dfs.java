import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Итератор dfs.
 */
public class Dfs<T> implements Iterator<T> {
    Stack<Tree<T>> stack;

    Tree<T> root;
    int expectedModCount;

    /**
     * Создает стек.
     */
    public Dfs(Tree<T> node) {
        this.expectedModCount = node.modCount;
        this.stack = new Stack<>();
        this.stack.push(node);
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
            return !this.stack.empty();
        }
    }

    /**
     * Возвращает следующий элемент.
     *
     * @return  next value or thow exception
     */
    @Override
    public T next() throws NoSuchElementException {

        if (hasNext()) {
            Tree<T> current = this.stack.pop();

            for (int i = 0; i < current.children.size(); ++i) {
                this.stack.push(current.children.get(i));
            }

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
