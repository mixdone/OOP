import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class TestMain {
    @Test
    public void testThe() {
        Main main = new Main();
        ArrayList<Integer> result = main.finder("src/test/resources/input.txt", "The");
        Assertions.assertTrue(result.contains(0) && result.contains(236) && result.contains(495));
    }

    @Test
    public void testDeadliest() {
        Main main = new Main();
        ArrayList<Integer> result = main.finder("src/test/resources/input.txt", "deadliest");
        Assertions.assertTrue(result.contains(4));
    }
}
