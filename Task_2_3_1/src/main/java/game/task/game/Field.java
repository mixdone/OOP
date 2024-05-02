package game.task.game;

import game.task.Settings;
import game.task.enums.CellType;

import java.util.Random;

/**
 * Class field.
 */
public class Field {
    private final int xSize;
    private final int ySize;

    private final Settings settings;
    private final CellType[][] matrix;

    private final Random random = new Random();


    /**
     * Class constructor.
     *
     * @param xSize columns number.
     * @param ySize rows number.
     * @param settings settings.
     */
    public Field(int xSize, int ySize, Settings settings) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.settings = settings;
        this.matrix = new CellType[xSize][ySize];
        fillInField();
    }

    /**
     * Fill in field. Grass and food.
     */
    private void fillInField() {
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                this.matrix[i][j] = CellType.GRASS;
            }
        }

        for (int i = 0; i < settings.getAmountOfFood(); i++) {

            var x = Math.abs(random.nextInt()) % 50;
            var y = Math.abs(random.nextInt()) % 50;

            while(getCell(x, y) != CellType.GRASS) {
                x = Math.abs(random.nextInt()) % 50;
                y = Math.abs(random.nextInt()) % 50;
            }

            matrix[x][y] = CellType.FOOD;
        }
    }

    /**
     * Get width.
     *
     * @return x size.
     */
    public int getxSize() {
        return xSize;
    }

    /**
     * Get height.
     *
     * @return y size.
     */
    public int getySize() {
        return ySize;
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