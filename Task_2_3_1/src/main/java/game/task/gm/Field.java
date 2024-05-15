package game.task.gm;

import game.task.Settings;
import game.task.enums.CellType;
import java.util.Random;

/**
 * Class field.
 */
public class Field {
    private final int sizeX;
    private final int sizeY;

    private final Settings settings;
    private final CellType[][] matrix;

    private final Random random = new Random();


    /**
     * Class constructor.
     *
     * @param sizeX columns number.
     * @param sizeY rows number.
     * @param settings settings.
     */
    public Field(int sizeX, int sizeY, Settings settings) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.settings = settings;
        this.matrix = new CellType[sizeX][sizeY];
        fillInField();
    }

    /**
     * Fill in field. Grass and food.
     */
    private void fillInField() {
        for (int i = 0; i < sizeX; i++) {
            for (int j = 0; j < sizeY; j++) {
                this.matrix[i][j] = CellType.GRASS;
            }
        }
    }

    /**
     * Get width.
     *
     * @return x size.
     */
    public int getxSize() {
        return sizeX;
    }

    /**
     * Get height.
     *
     * @return y size.
     */
    public int getySize() {
        return sizeY;
    }

    /**
     * Get cell type by coords.
     *
     * @param x x coord.
     * @param y y coord.
     *
     * @return cell type.
     */
    public CellType getCell(int x, int y) {
        return matrix[x][y];
    }

    /**
     * Set specific cell type.
     *
     * @param cell cell.
     */
    public void setCell(Cell cell) {
        matrix[cell.getX()][cell.getY()] = cell.getType();
    }

}