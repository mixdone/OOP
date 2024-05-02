package game.task.gm;

import game.task.Drawer;
import game.task.Settings;
import game.task.enums.CellType;
import game.task.enums.Direction;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

/**
 * Game class.
 */
public class Game implements Runnable {

    private final Stage stage;
    private final Field field;
    private final Settings settings;
    private final Snake snake;
    private final Drawer drawer;
    private Thread game;
    private Thread food;

    private final long speedUpdate          = 250;
    private final Random random             = new Random();
    private final AtomicBoolean gameOver    = new AtomicBoolean(false);
    private final AtomicInteger eat         = new AtomicInteger(0);

    /**
     * Keyboard action handler.
     */
    public class KeyStoneHandler implements EventHandler<KeyEvent> {

        /**
         * Key handler.
         *
         * @param keyEvent event.
         */
        @Override
        public void handle(KeyEvent keyEvent) {
            var keyEventCode = keyEvent.getCode();
            if (keyEventCode == KeyCode.A) {
                if (snake.getDirection() != Direction.RIGHT) {
                    snake.setDirection(Direction.LEFT);
                }
            } else if (keyEventCode == KeyCode.W) {
                if (snake.getDirection() != Direction.DOWN) {
                    snake.setDirection(Direction.UP);
                }
            } else if (keyEventCode == KeyCode.S) {
                if (snake.getDirection() != Direction.UP) {
                    snake.setDirection(Direction.DOWN);
                }
            } else if (keyEventCode == KeyCode.D) {
                if (snake.getDirection() != Direction.LEFT) {
                    snake.setDirection(Direction.RIGHT);
                }
            }
        }


    }


    /**
     * Class constructor.
     *
     * @param primaryStage stage.
     * @param settings settings.
     */
    public Game(Stage primaryStage, Settings settings) {
        this.field = new Field(settings.getRows(), settings.getRows(), settings);
        this.settings = settings;
        this.snake = new Snake(this.field);
        this.stage = primaryStage;
        var root = new Group();
        Canvas canvas = new Canvas(settings.getWindowSize(), settings.getWindowSize());
        root.getChildren().add(canvas);

        var scene = new Scene(root);
        scene.setOnKeyPressed(new KeyStoneHandler());
        stage.setOnCloseRequest(t -> System.exit(0));
        stage.setScene(scene);

        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        this.drawer = new Drawer(graphicsContext, field, settings);

        stage.show();

    }

    /**
     * Run.
     */
    @Override
    public void run() {
        drawer.drawField();

        /*
         * Game loop.
         */
        game = new Thread(() -> {
            var time = System.currentTimeMillis();
            var diff = snake.getLenght() * 10;
            while (!gameOver.get() && !Thread.currentThread().isInterrupted()) {
                var currentTime = System.currentTimeMillis();
                if (currentTime - time > speedUpdate - diff) {
                    update();
                    time = currentTime;
                    diff = snake.getLenght() * 10;
                }
            }

        });

        /*
         * Food generator.
         */
        food = new Thread(() -> {
            while (!gameOver.get() && !Thread.currentThread().isInterrupted()) {
                if (eat.get() > 0) {

                    var x = Math.abs(random.nextInt()) % 50;
                    var y = Math.abs(random.nextInt()) % 50;

                    while(field.getCell(x, y) != CellType.GRASS) {
                        x = Math.abs(random.nextInt()) % 50;
                        y = Math.abs(random.nextInt()) % 50;
                    }

                    var f = new Cell(x, y, CellType.FOOD);

                    field.setCell(f);
                    try {
                        drawer.drawFood(f);
                    } catch (InternalError e) {
                        drawer.drawField();
                        continue;
                    }
                    eat.set(eat.get() - 1);
                }
            }
        });

        game.start();
        food.start();
    }


    /**
     * Updater. In game loop.
     */
    private void update() {
        var length = snake.getLenght();

        if (length == settings.getMaxSize()) {
            gameOver.set(true);
        }

        if (!snake.nextState()) {
            gameOver.set(true);
        }

        if (snake.getLenght() > length) {
            eat.set(eat.get() + 1);
        }
        try {
            drawer.drawGrass(snake.getChangedCell());
            drawer.drawSnake(snake.getHead());
        } catch (InternalError e) {
            drawer.drawField();
            drawer.drawGrass(snake.getChangedCell());
            drawer.drawSnake(snake.getHead());
        }
    }

}