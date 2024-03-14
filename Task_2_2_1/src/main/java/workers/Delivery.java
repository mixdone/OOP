package workers;

import order.PizzaOrder;
import order.Status;
import queue.Stock;

import java.util.Random;

public class Delivery implements Runnable, Worker {
    private final String name;
    private final int amount;
    private Stock<PizzaOrder> stock;
    private boolean workdayIsOver = false;
    private PizzaOrder currentOrder;

    /**
     * Deliverymen constructor.
     *
     * @param name Name.
     */
    public Delivery(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while(true) {
            getOrder();

            if (workdayIsOver) {
                break;
            }

            working();
        }
    }

    /**
     * Stock for worker.
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
        try {
            currentOrder = stock.get();
            if (currentOrder.getStatus() == Status.DONE) {
                workdayIsOver = true;
                return;
            }
            currentOrder.setStatus(Status.DELIVERING);
            // Logging that deliveryman move the order into status:DELIVERING
        } catch (InterruptedException e) {
            getOrder();
        }
    }

    /**
     * Waiting order.
     */
    @Override
    public void working() {
        Random workingTime = new Random();
        try {
            Thread.sleep(workingTime.nextInt() * 100L % 10000);
            currentOrder.setStatus(Status.DONE);
            // Logging that Deliveryman move the order into status:DONE
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
