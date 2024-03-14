import order.PizzaOrder;
import queue.OrderQueue;
import queue.Stock;

/**
 * Initialises the whole system according to the data received from json.
 * Goes to sleep while the system is running(full-time work).
 * Wakes up and ensures correct shutdown of the system.
 *      1) Finalise orders.
 *      2) Wait for the bakers.
 *      3) Wait for the delivery men.
 */
public class Init {
    private OrderQueue<PizzaOrder> orders;
    private Stock<PizzaOrder> stock;


    public Init() {

    }
}
