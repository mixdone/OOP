import java.util.*;
public class Tree<T> implements Iterable<T>{
    T value;
    Tree<T> parent;
    ArrayList<Tree<T>> children;

    public Tree(T value) {
        this.value = value;
        this.parent = null;
        this.children = new ArrayList<>();
    }
    public Tree(T value, Tree<T> parent) {
        this.value = value;
        this.parent = parent;
        this.children = new ArrayList<>();
    }

    /**
     * Добавление элемента.
     * @param value
     * @return new node
     */
    public Tree<T> addChild(T value) {
        Tree<T> node = new Tree<>(value, this);
        this.children.add(node);
        return node;
    }

    /**
     * Присоединяет поддерево.
     * @param subtree
     * @return subtree
     */
    public Tree<T> addChild(Tree<T> subtree) {
        this.children.add(subtree);
        return subtree;
    }

    /**
     * Удаление элемента.
     */
    public void remove() {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
    }

    /**
     * Реализация итератора.
     * @return dfs
     */
    @Override
    public Iterator<T> iterator() {
        return new Dfs<>(this);
    }

    /**
     * Реализация итератора.
     * @return bfs
     */
    public  Iterator<T> iteratorbfs() {
        return new Bfs<>(this);
    }

    /**
     * Сравнивает два дерева.
     * @param obj - объект сравнения
     * @return is_equal
     */
    @Override
    public boolean equals(Object obj) {
        boolean isEqual = true;
        if ((obj instanceof Tree)) {
            Tree<T> tree = (Tree<T>) obj;
            Iterator<T> iterator1 = this.iterator();
            Iterator<T> iterator2 = tree.iterator();

            while (iterator1.hasNext() && iterator2.hasNext()) {
                if (iterator1.next() != iterator2.next()) {
                    isEqual = false;
                    break;
                }
            }

            if (iterator1.hasNext() != iterator2.hasNext()) {
                isEqual = false;
            }
        }
        else {
            isEqual = false;
        }
        return isEqual;
    }

}
