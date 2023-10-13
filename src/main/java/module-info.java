module MainGroup.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens Main.application to javafx.fxml;
    exports Main.application;
    opens Main.SceneControllers to javafx.fxml;
    exports Main.SceneControllers;
}