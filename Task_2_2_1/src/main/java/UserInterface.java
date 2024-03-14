import order.PizzaOrder;
import queue.OrderQueue;


import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * User interface.
 */
public class UserInterface implements Runnable {
    private static final Logger logger = Logger.getLogger(String.valueOf(UserInterface.class));

    private int currentOrderNumber = 0;
    private OrderQueue<PizzaOrder> orders;

    /**
     * Set order queue.
     *
     * @param orders Queue.
     */
    public void setOrders(OrderQueue<PizzaOrder> orders) {
        this.orders = orders;
    }

    /**
     * Get order.
     *
     * @return orders number.
     */
    public int getOrder() {
        Random number = new Random();

        return number.nextInt() % 12;
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
        orders.add(order);
        logger.log(Level.INFO, order.toString());
        // Logging that order move into status:QUEUE
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while(!Thread.currentThread().isInterrupted()) {
            var num = getOrder();

            for (int i = 0; i < num; i++) {
                putOrder(generateOrder());
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
