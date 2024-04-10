package order;

/**
 * Pizza order.
 */
public class PizzaOrder {
    int number;
    Status status;


    /**
     * Constructror.
     *
     * @param i order number.
     */
    public PizzaOrder(int i) {
        number = i;
        status = Status.QUEUE;
    }

    /**
     * Return current order status.
     *
     * @return status.
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Set new order status.
     *
     * @param status new status.
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * String representation.
     *
     * @return string.
     */
    @Override
    public String toString() {
        return "the order " + number + " is into status:" + status.toString();
    }
}