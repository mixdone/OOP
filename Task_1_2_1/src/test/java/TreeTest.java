import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;



public class TreeTest {
    @ParameterizedTest
    @MethodSource("getTestAddChildString")
    public void addChildStringTest(String expected, String value) {
        Assertions.assertEquals(expected, value);
    }

    @ParameterizedTest
    @MethodSource("getTestAddChildInteger")
    public void addChildIntegerTest(Integer expected, Integer value) {
        Assertions.assertEquals(expected, value);
    }

    @Test
    public void removeStringTest() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        var b = a.addChild("B");
        b.remove();
        Assertions.assertFalse(a.children.contains(b));
    }

    @Test
    public void removeIntegerTest() {
        Tree<Integer> tree = new Tree<>(0);
        var a = tree.addChild(1);
        var b = a.addChild(11);
        a.remove();
        Assertions.assertFalse(tree.children.contains(a));
    }

    @ParameterizedTest
    @MethodSource("getTestEquals")
    public void equalsTest(boolean expected, Tree<Integer> tree, Object obj) {
        Assertions.assertEquals(expected, tree.equals(obj));
    }

    @ParameterizedTest
    @MethodSource("getBfsTest")
    public void bfsTest(Integer expected, Integer value) {
        Assertions.assertEquals(expected, value);
    }

    @ParameterizedTest
    @MethodSource("getDfsTest")
    public void dfsTest(Integer expected, Integer value) {
        Assertions.assertEquals(expected, value);
    }

    static Stream<Arguments> getDfsTest() {
        Tree<Integer> tree = new Tree<>(0);
        var a = tree.addChild(1);
        a.addChild(11);
        Tree<Integer> subtree = new Tree<>(2);
        subtree.addChild(21);
        subtree.addChild(22);
        tree.addChild(subtree);

        var i = tree.iterator();

        return Stream.of(
                Arguments.of(0, i.next()),
                Arguments.of(2, i.next()),
                Arguments.of(22, i.next()),
                Arguments.of(21, i.next()),
                Arguments.of(1, i.next()),
                Arguments.of(11, i.next()));
    }

    static Stream<Arguments> getBfsTest() {
        Tree<Integer> tree = new Tree<>(0);
        var a = tree.addChild(1);
        a.addChild(11);
        Tree<Integer> subtree = new Tree<>(2);
        subtree.addChild(21);
        subtree.addChild(22);
        tree.addChild(subtree);

        var i = tree.iteratorbfs();

        return Stream.of(
                Arguments.of(0, i.next()),
                Arguments.of(1, i.next()),
                Arguments.of(2, i.next()),
                Arguments.of(11, i.next()),
                Arguments.of(21, i.next()),
                Arguments.of(22, i.next()));
    }

    static Stream<Arguments> getTestEquals() {
        Tree<Integer> tree1 = new Tree<>(0);
        var a = tree1.addChild(1);
        a.addChild(3);

        Tree<Integer> tree2 = new Tree<>(0);
        var b = tree2.addChild(1);
        b.addChild(3);

        Tree<Integer> tree3 = new Tree<>(0);
        var c = tree3.addChild(1);
        c.addChild(3);
        c.addChild(4);

        return Stream.of(
                Arguments.of(false, tree1, "tree1"),
                Arguments.of(true, tree1, tree2),
                Arguments.of(false, tree1, tree3));
    }

    static Stream<Arguments> getTestAddChildInteger() {
        Tree<Integer> tree = new Tree<>(0);
        var a = tree.addChild(1);
        a.addChild(11);
        Tree<Integer> subtree = new Tree<>(2);
        subtree.addChild(21);
        subtree.addChild(22);
        tree.addChild(subtree);

        return Stream.of(
                Arguments.of(0, tree.value),
                Arguments.of(1, tree.children.get(0).value),
                Arguments.of(11, tree.children.get(0).children.get(0).value),
                Arguments.of(2, tree.children.get(1).value),
                Arguments.of(21, tree.children.get(1).children.get(0).value),
                Arguments.of(22, tree.children.get(1).children.get(1).value));
    }

    static Stream<Arguments> getTestAddChildString() {
        Tree<String> tree = new Tree<>("R1");
        var a = tree.addChild("A");
        a.addChild("B");
        Tree<String> subtree = new Tree<>("R2");
        subtree.addChild("C");
        subtree.addChild("D");
        tree.addChild(subtree);

        return Stream.of(
                Arguments.of("R1", tree.value),
                Arguments.of("A", tree.children.get(0).value),
                Arguments.of("B", tree.children.get(0).children.get(0).value),
                Arguments.of("R2", tree.children.get(1).value),
                Arguments.of("C", tree.children.get(1).children.get(0).value),
                Arguments.of("D", tree.children.get(1).children.get(1).value));
    }
}
