module game.task {
    requires javafx.controls;
    requires javafx.fxml;


    opens game.task to javafx.fxml;
    exports game.task;
    exports game.task.gm;
    opens game.task.gm to javafx.fxml;
}