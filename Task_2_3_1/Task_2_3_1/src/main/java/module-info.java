module game.task_2_3_1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens game.task_2_3_1 to javafx.fxml;
    exports game.task_2_3_1;
}