module towerDefenseGame {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    exports App to javafx.graphics;
    opens App.Controller to javafx.fxml;
}