package game.task_2_3_1;

import game.task_2_3_1.enums.CellType;
import game.task_2_3_1.Cell;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestCell {
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
