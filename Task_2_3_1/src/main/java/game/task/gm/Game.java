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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;


/**
 * Game class.
 */
public class Game implements Runnable {

    private final Stage stage;
    private Field field;
    private final Settings settings;
    private Snake snake;
    private final Drawer drawer;
    private Thread game;
    private Thread food;

    private final Random random          = new Random();
    private final AtomicBoolean gameOver = new AtomicBoolean(false);
    private final AtomicInteger eat      = new AtomicInteger(0);

    private boolean win = false;
    private long speedUpdate = 250;

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
        eat.set(settings.getAmountOfFood());

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
            while (true) {
                while (!gameOver.get() && !Thread.currentThread().isInterrupted()) {
                    var currentTime = System.currentTimeMillis();
                    if (currentTime - time > speedUpdate - diff) {
                        update();
                        time = currentTime;
                        diff = snake.getLenght() * 10;
                    }
                }
                drawer.drawGameOver(win);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                gameOver.set(false);
                field = new Field(settings.getRows(), settings.getRows(), settings);
                snake = new Snake(field);
                drawer.drawField();
                eat.set(settings.getAmountOfFood());
                speedUpdate = 250;
            }
        });

        /*
         * Food generator.
         */
        food = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                if (eat.get() > 0) {

                    var x = Math.abs(random.nextInt()) % 50;
                    var y = Math.abs(random.nextInt()) % 50;

                    while (field.getCell(x, y) != CellType.GRASS) {
                        x = Math.abs(random.nextInt()) % 50;
                        y = Math.abs(random.nextInt()) % 50;
                    }

                    var f = new Cell(x, y, CellType.FOOD);

                    field.setCell(f);

                    drawer.drawFood(f);
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
            win = true;
        }

        if (!snake.nextState()) {
            gameOver.set(true);
        }

        if (snake.getLenght() > length) {
            eat.set(eat.get() + 1);
        }

        drawer.drawGrass(snake.getChangedCell());
        drawer.drawSnake(snake.getHead());
    }

}