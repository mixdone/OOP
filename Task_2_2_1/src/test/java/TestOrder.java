import java.util.stream.Stream;
import order.PizzaOrder;
import order.Status;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test pizza order and status.
 */
public class TestOrder {

    @Test
    public void testToString() {
        var order = new PizzaOrder(0);
        Assertions.assertEquals("the order 0 is into status:QUEUE", order.toString());
    }

    /**
     * Test set status.
     *
     * @param expected expected answer.
     * @param i order number.
     * @param status order status.
     */
    @ParameterizedTest
    @MethodSource("getTestsSetStatus")
    public void testSetStatus(String expected, int i, Status status) {
        var order = new PizzaOrder(i);
        order.setStatus(status);
        Assertions.assertEquals(expected, order.toString());
    }

    static Stream<Arguments> getTestsSetStatus() {
        return Stream.of(
                Arguments.of("the order 3 is into status:COOCKING", 3, Status.COOCKING),
                Arguments.of("the order 7 is into status:QUEUE", 7, Status.QUEUE),
                Arguments.of("the order 2 is into status:STOCK", 2, Status.STOCK),
                Arguments.of("the order 1 is into status:DELIVERING", 1, Status.DELIVERING),
                Arguments.of("the order 10 is into status:DONE", 10, Status.DONE));
    }
}