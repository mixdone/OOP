package game.task.game;

import game.task.enums.CellType;
import game.task.enums.Direction;

import java.util.LinkedList;

/**
 * Class snake
 */
public class Snake {
    private final LinkedList<Cell> tail;
    private Cell head;
    private int lenght;
    private int growUp = 0;
    private Cell changedCell;
    private Direction direction;
    private final Field field;

    /**
     * Class constructor.
     */
    public Snake(Field field) {
        this.head       = new Cell(10, 10, CellType.SNAKE);
        this.tail       = new LinkedList<>();
        this.direction  = Direction.RIGHT;
        this.field      = field;
        this.lenght     = 1;
    }

    /**
     * Set snake head.
     *
     * @param head Snake head.
     */
    private void setHead(Cell head) {
        this.head = head;
    }


    /**
     * Move head.
     */
    private boolean moveHead() {
        var x = head.getX();
        var y = head.getY();

        if (direction == Direction.UP) {
            --y;
        } else if (direction == Direction.DOWN) {
            ++y;
        } else if (direction == Direction.LEFT) {
            --x;
        } else {
            ++x;
        }

        if (x < 0 || y < 0 || y >= 50 || x >= 50) {
            return false;
        }

        var cellType = field.getCell(x, y);
        if (cellType == CellType.SNAKE) {
            return false;
        }

        if (cellType == CellType.FOOD) {
            growUp++;
        }

        setHead(new Cell(x, y, CellType.SNAKE));


        field.setCell(head);

        return true;
    }



    /**
     * Set snake direction.
     *
     * @param direction Snake direction.
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * Get snake length.
     *
     * @return snake length.
     */
    public int getLenght() {
        return lenght;
    }

    /**
     * Get snake direction.
     *
     * @return snake direction.
     */
    public Direction getDirection() {
        return direction;
    }


    /**
     * Get snake head.
     *
     * @return snake head.
     */
    public Cell getHead() {
        return head;
    }

    /**
     * Get changed cell.
     *
     * @return cell.
     */
    public Cell getChangedCell() {
        return changedCell;
    }

    /**
     * Move snake.
     */
    public boolean nextState() {
        if (growUp > 0) {
            growUp--;
            lenght++;
            changedCell = null;
            tail.addFirst(new Cell(head.getX(), head.getY(), head.getType()));
        } else if (lenght == 1) {
            changedCell = head;
            changedCell.setType(CellType.GRASS);
            field.setCell(changedCell);
        }  else {
            changedCell = tail.removeLast();
            tail.addFirst(head);
            changedCell.setType(CellType.GRASS);
            field.setCell(changedCell);
        }

        return moveHead();
    }


}