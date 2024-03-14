package workers;

import order.PizzaOrder;
import queue.Stock;

public interface Worker {

    /**
     * Says current worker's name.
     *
     * @return String name.
     */
    String getName();

    /**
     * Stock for worker.
     *
     * @param stock Stock.
     */
    void setStock(Stock<PizzaOrder> stock);

    /**
     * Worker take order from queue.
     */
    void getOrder();

    /**
     * Waiting order.
     */
    void working();

}
