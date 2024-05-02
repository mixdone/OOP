module game.task {
    requires javafx.controls;
    requires javafx.fxml;


    opens game.task to javafx.fxml;
    exports game.task;
    exports game.task.game;
    opens game.task.game to javafx.fxml;
}