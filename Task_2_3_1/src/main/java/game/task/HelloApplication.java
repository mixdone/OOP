package game.task;

import java.io.IOException;

import game.task.gm.Snake;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class.
 */
public class SnakeApplication extends Application {

    /**
     * Start.
     *
     * @param stage the primary stage for this application, onto which
     *    the application scene can be set.
     *    Applications may create other stages, if needed, but they will not be
     *    primary stages.
     * @throws IOException exception.
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SnakeApplication.class.getResource("start.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Snake Game");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main.
     *
     * @param args args.
     */
    public static void main(String[] args) {
        launch();
    }
}