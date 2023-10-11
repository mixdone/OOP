import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.Stack;
import java.util.NoSuchElementException;

/**
 * Итератор dfs.
 */
public class Dfs<T> implements Iterator<T> {
    Stack<Tree<T>> stack;

    /**
     * Создает стек.
     */
    public Dfs(Tree<T> node) {
        this.stack = new Stack<>();
        this.stack.push(node);
    }

    /**
     * Проверяет наличие следующего элемента.
     *
     * @return flag not empty
     */
    @Override
    public boolean hasNext() {
        return !this.stack.empty();
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
