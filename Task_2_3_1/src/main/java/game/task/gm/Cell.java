package game.task.gm;

import game.task.enums.CellType;

/**
 * Class cell.
 */
public class Cell {
    private final int coordX;
    private final int coordY;
    private CellType type;

    /**
     * Constructor.
     *
     * @param x x coord.
     * @param y y coord
     * @param type cell type.
     */
    public Cell(int x, int y, CellType type) {
        this.coordX = x;
        this.coordY = y;
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
        return coordX;
    }

    /**
     * Get y.
     *
     * @return coord.
     */
    public int getY() {
        return coordY;
    }

}