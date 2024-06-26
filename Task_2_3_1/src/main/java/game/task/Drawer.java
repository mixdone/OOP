package game.task;

import game.task.enums.CellType;
import game.task.gm.Cell;
import game.task.gm.Field;
import game.task.gm.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Class drawer.
 */
public class Drawer {
    GraphicsContext graphicsContext;
    Settings settings;
    Field field;

    /**
     * Class constructor.
     *
     * @param graphicsContext graphics context.
     * @param field game field.
     * @param settings custom settings(maybe default).
     */
    public Drawer(GraphicsContext graphicsContext, Field field, Settings settings) {
        this.graphicsContext = graphicsContext;
        this.settings = settings;
        this.field = field;
    }

    /**
     * Start function. Draw field.
     */
    public void drawField() {
        for (int i = 0; i < field.getxSize(); i++) {
            for (int j = 0; j < field.getySize(); j++) {
                drawGrass(new Cell(i, j, CellType.GRASS));
            }
        }
    }

    /**
     * Draw one grass cell.
     *
     * @param grass necessary cell.
     */
    public synchronized void drawGrass(Cell grass) {

        if (grass == null) {
            return;
        }

        if ((grass.getX() + grass.getY()) % 2 == 0) {
            graphicsContext.setFill(Color.web("228B22"));
        } else {
            graphicsContext.setFill(Color.web("008000"));
        }
        graphicsContext.fillRect(grass.getX() * settings.getCellSize(),
                grass.getY() * settings.getCellSize(),
                settings.getCellSize(), settings.getCellSize());

        notifyAll();
    }

    /**
     * Draw one snake cell.
     *
     * @param snake necessary cell.
     */
    public synchronized void drawSnake(Cell snake) {
        graphicsContext.setFill(Color.web("C71585"));
        graphicsContext.fillRoundRect(snake.getX() * settings.getCellSize(),
                snake.getY() * settings.getCellSize(),
                settings.getCellSize(), settings.getCellSize(), 20, 20);

        notifyAll();
    }

    /**
     * Draw one food cell.
     *
     * @param food necessary cell.
     */
    public synchronized void drawFood(Cell food) {
        graphicsContext.setFill(Color.web("FFDAB9"));
        graphicsContext.fillRoundRect(food.getX() * settings.getCellSize(),
                food.getY() * settings.getCellSize(),
                settings.getCellSize(), settings.getCellSize(), 20, 20);
        notifyAll();
    }

    public void drawGameOver(boolean win) {
        if (win) {
            graphicsContext.setFill(Color.YELLOWGREEN);
            graphicsContext.setFont(new Font("Digital-7", 90));
            graphicsContext.fillText("YOU WIN", 250, 500);
        } else {
            graphicsContext.setFill(Color.RED);
            graphicsContext.setFont(new Font("Digital-7", 90));
            graphicsContext.fillText("YOU LOSE", 250, 500);
        }
    }
}