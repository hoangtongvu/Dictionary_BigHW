module MainGroup.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens Main.application to javafx.fxml;
    exports Main.application;
    exports Main.SceneControllers.Dictionary;
    opens Main.SceneControllers.Dictionary to javafx.fxml;

}