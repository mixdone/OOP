package workers;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.PizzaOrder;
import order.Status;
import queue.OrderQueue;
import queue.Stock;


/**
 * workers.Baker. Has the speed of work.
 * Takes orders to make pizzas. Reports that he has taken the order.
 * Falls asleep while working (making pizza).
 * The baker is putting pizza in the stock.
 */
public class Baker implements Runnable, Worker {
    private static final Logger logger = Logger.getLogger(String.valueOf(Baker.class));

    private final String name;
    private final int time;

    private OrderQueue<PizzaOrder> orders;
    private Stock<PizzaOrder> stock;
    private boolean workdayIsOver = false;

    private PizzaOrder currentOrder;

    /**
     * workers.Baker class constructor.
     *
     * @param name workers.Baker's name.
     * @param time Time to make a pizza.
     */
    public Baker(String name, int time) {
        this.name = name;
        this.time = time;
    }

    /**
     * Orders for baker.
     *
     * @param orders Orders.
     */
    public void setOrders(OrderQueue<PizzaOrder> orders) {
        this.orders = orders;
    }

    /**
     * Says current worker's name.
     *
     * @return String name.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Stock for backer.
     *
     * @param stock Stock.
     */
    @Override
    public void setStock(Stock<PizzaOrder> stock) {
        this.stock = stock;
    }


    /**
     * Worker take order from queue.
     */
    @Override
    public void getOrder() {
        currentOrder = null;
        try {
            currentOrder = orders.get();
            if (currentOrder.getStatus() == Status.DONE) {
                workdayIsOver = true;
                return;
            }
            currentOrder.setStatus(Status.COOCKING);
            logger.log(Level.INFO, this.toString());
            // Logging that baker move the order into status:COOKING
        } catch (InterruptedException e) {
            getOrder();
        }
    }

    /**
     * Worker stock the pizza.
     */
    public void stocking() {
        try {
            currentOrder.setStatus(Status.STOCK);
            stock.add(currentOrder);
            logger.log(Level.INFO, this.toString());
            // Logging that baker move the order into status:STOCK
        } catch (InterruptedException e) {
            stocking();
        }
    }

    /**
     * Waiting order.
     */
    @Override
    public void working() {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * String representation.
     *
     * @return string.
     */
    @Override
    public String toString() {
        return "Baker" + name + "move" + currentOrder.toString();
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while (true) {
            getOrder();

            if (workdayIsOver) {
                break;
            }

            working();

            stocking();
        }
    }
}
