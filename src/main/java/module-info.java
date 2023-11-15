module MainGroup.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.web;
    requires java.sql;
    requires scenebuilderextended.components.choicegameextendedcomponents;

    opens Main.application to javafx.fxml;
    exports Main.application;

    exports Main.SceneControllers.Dictionary;
    opens Main.SceneControllers.Dictionary to javafx.fxml;

    exports Main.SceneControllers.Game.MultiChoiceGame;
    opens Main.SceneControllers.Game.MultiChoiceGame to javafx.fxml;
    exports Main.SceneControllers.Game;
    opens Main.SceneControllers.Game to javafx.fxml;

    exports CustomEventPackage.OneParameter;
    opens CustomEventPackage.OneParameter to javafx.fxml;
    exports CustomEventPackage.ZeroParameter;
    opens CustomEventPackage.ZeroParameter to javafx.fxml;
    exports CustomEventPackage.TwoParameters;
    opens CustomEventPackage.TwoParameters to javafx.fxml;

    opens Main.SceneControllers.Translate to javafx.fxml;
}