package workers;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import order.PizzaOrder;
import order.Status;
import queue.Stock;

/**
 * Delivery.
 */
public class Delivery implements Runnable, Worker {
    private static final Logger logger = Logger.getLogger(String.valueOf(Delivery.class));

    private final String name;
    private final int amount;
    private Stock<PizzaOrder> stock;
    private boolean workdayIsOver = false;
    private final ArrayList<PizzaOrder> trunk;

    /**
     * Deliverymen constructor.
     *
     * @param name Name.
     */
    public Delivery(String name, int amount) {
        this.name = name;
        this.amount = amount;
        this.trunk = new ArrayList<>(amount);
    }

    /**
     * Runs this operation.
     */
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < amount; i++) {
                getOrder();
            }

            if (workdayIsOver) {
                break;
            }

            working();
        }
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
        var currentOrder = stock.get();
        if (currentOrder.getStatus() == Status.DONE) {
            workdayIsOver = true;
            return;
        }
        currentOrder.setStatus(Status.DELIVERING);
        trunk.add(currentOrder);
        logger.log(Level.INFO, this.toString());
        // Logging that deliveryman move the order into status:DELIVERING
    }

    /**
     * Waiting order.
     */
    @Override
    public void working() {
        Random workingTime = new Random();
        try {
            for (var i : trunk) {
                Thread.sleep(Math.abs(workingTime.nextInt() * 100L % 10000));
                i.setStatus(Status.DONE);
                logger.log(Level.INFO, "Deliveryman " + name + " move " + i);
                // Logging that Deliveryman move the order into status:DONE
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}