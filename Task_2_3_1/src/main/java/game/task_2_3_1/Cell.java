package game.task_2_3_1;

import game.task_2_3_1.enums.CellType;

/**
 * Class cell.
 */
public class Cell {
    private final int x;
    private final int y;
    private CellType type;

    public Cell(int x, int y, CellType type) {
        this.x      = x;
        this.y      = y;
        this.type   = type;
    }

    /**
     * Set cell type.
     *
     * @param type cell type.
     */
    public void setType(CellType type) {
        this.type = type;
    }

    /**
     * Get cell type.
     *
     * @return cell type.
     */
    public CellType getType() {
        return type;
    }

    /**
     * Get x.
     *
     * @return coord.
     */
    public int getX() {
        return x;
    }

    /**
     * Get y.
     *
     * @return coord.
     */
    public int getY() {
        return y;
    }

}
