package game.task;

import game.task.gm.Game;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Fxml controller.
 */
public class Controller {

    Settings settings1 = new Settings();

    @FXML
    private Button play;

    @FXML
    private Button settings;

    @FXML
    private Slider amountOfFood;

    @FXML
    private Slider maxSize;


    /**
     * Play button.
     *
     * @param event event.
     *
     */
    public void onPlay(ActionEvent event) throws IOException {
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        var game = new Game(stage, settings1);
        game.run();
    }

    /**
     * Settings button.
     *
     * @param event event.
     *
     * @throws IOException exception.
     */
    public void onSettings(ActionEvent event) throws IOException {
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader(
                Controller.class.getResource("settings.fxml"));
        AnchorPane root = fxmlLoader.load();
        var scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Play on settings scene.
     *
     * @param event event.
     */
    public void onPlaySettings(ActionEvent event) throws IOException {
        settings1.setAmountOfFood((int) amountOfFood.getValue());
        settings1.setMaxSize((int) maxSize.getValue());
        var stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        var game = new Game(stage, settings1);
        game.run();
    }

}