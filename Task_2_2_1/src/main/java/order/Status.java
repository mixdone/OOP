package order;

/**
 * Status enum.
 */
public enum Status {
    QUEUE(0),
    COOCKING(1),
    STOCK(2),
    DELIVERING(3),
    DONE(4);
    private int value;

    /**
     * Status constructor.
     *
     * @param i status value.
     */
    Status(int i) {
        value = i;
    }

    /**
     * Set status value.
     *
     * @param value new value.
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * String representation.
     *
     * @return string.
     */
    @Override
    public String toString() {
        return super.toString();
    }
}