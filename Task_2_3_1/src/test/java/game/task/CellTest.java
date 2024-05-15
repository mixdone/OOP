package game.task;

import game.task.enums.CellType;
import game.task.gm.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CellTest {
    @Test
    public void testX() {
        Cell cell = new Cell(1, 2, CellType.FOOD);

        Assertions.assertEquals(cell.getX(), 1);
    }

    @Test
    public void testY() {
        Cell cell = new Cell(1, 2, CellType.FOOD);

        Assertions.assertEquals(cell.getY(), 2);
    }

    @Test
    public void testType() {
        Cell cell = new Cell(1, 2, CellType.FOOD);

        Assertions.assertEquals(cell.getType(), CellType.FOOD);
    }
}