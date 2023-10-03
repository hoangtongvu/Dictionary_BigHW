module MainGroup.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens MainGroup.application to javafx.fxml;
    exports MainGroup.application;
    opens MainGroup.SceneControllers to javafx.fxml;
    exports MainGroup.SceneControllers;
}