package game.task;

/**
 * Custom settings.
 */
public class Settings {
    private int amountOfFood = 1;
    private int maxSize = 20;

    private final int rows = 50;
    private final int cellSize = 20;
    private final int windowSize = 1000;

    /**
     * Get window size.
     *
     * @return window size.
     */
    public int getWindowSize() {
        return windowSize;
    }

    /**
     * Get field matrix size.
     *
     * @return matrix size.
     */
    public int getRows() {
        return rows;
    }

    /**
     * Get cell size.
     *
     * @return cell size
     */
    public int getCellSize() {
        return cellSize;
    }

    /**
     * Set amount of food.
     *
     * @param amountOfFood int.
     */
    public void setAmountOfFood(int amountOfFood) {
        this.amountOfFood = amountOfFood;
    }

    /**
     * Get amount of food.
     *
     * @return number.
     */
    public int getAmountOfFood() {
        return amountOfFood;
    }

    /**
     * Set win size.
     *
     * @param maxSize int.
     */
    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * Get win size.
     *
     * @return number.
     */
    public int getMaxSize() {
        return maxSize;
    }
}