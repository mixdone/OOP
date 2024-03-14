import java.util.Scanner;
import order.PizzaOrder;
import queue.OrderQueue;

/**
 * User interface.
 */
public class UserInterface implements Runnable {
    private int currentOrderNumber = 0;
    private OrderQueue<PizzaOrder> orders;

    /**
     * Get order.
     *
     * @return orders number.
     */
    public int getOrder() {
        var stdin = new Scanner(System.in);
        System.out.println("Enter number of pizza:");
        return stdin.nextInt();
    }

    /**
     * Create a PizzaOrder object.
     *
     * @return PizzaOrder.
     */
    public PizzaOrder generateOrder() {
        return new PizzaOrder(currentOrderNumber++);
    }

    /**
     * Put order in queue.
     */
    public void putOrder(PizzaOrder order) {
        try {
            orders.add(order);
            // Logging that order move into status:QUEUE
        } catch (InterruptedException e) {
            putOrder(order);
        }
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while(true) {
            var num = getOrder();

            for (int i = 0; i < num; i++) {
                putOrder(generateOrder());
            }
        }
    }
}
