import java.util.*;

public class Dfs<T> implements Iterator<T>{
    Stack<Tree<T>> stack;
    public Dfs(Tree<T> node) {
        this.stack = new Stack<>();
        this.stack.push(node);
    }

    /**
     * Проверяет наличие следующего элемента.
     * @return flag not empty
     */
    @Override
    public boolean hasNext() {
        return !this.stack.empty();
    }

    /**
     * Возвращает следующий элемент.
     * @return  next value or thow exception
     */
    @Override
    public T next() throws NoSuchElementException{
        if (hasNext() != false) {
            Tree<T> current = this.stack.pop();
            for(int i = 0; i < current.children.size(); ++i) {
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
