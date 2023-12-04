module MainGroup.application {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires javafx.web;
    requires java.sql;
    requires scenebuilderextended.components.choicegameextendedcomponents;
    requires com.google.gson;

    opens Main.application to javafx.fxml;
    exports Main.application;

    exports Main.SceneControllers.Dictionary;
    opens Main.SceneControllers.Dictionary to javafx.fxml;

    exports Main.SceneControllers.Game.MultiChoiceGame;
    opens Main.SceneControllers.Game.MultiChoiceGame to javafx.fxml;

    exports Main.SceneControllers.Game;
    opens Main.SceneControllers.Game to javafx.fxml;

    exports Main.SceneControllers.Game.CreateWord4DirGame;
    opens Main.SceneControllers.Game.CreateWord4DirGame to javafx.fxml;

    exports Main.SceneControllers.Game.Wordle;
    opens Main.SceneControllers.Game.Wordle to javafx.fxml;



    exports Main.SceneControllers.NavigationPane;
    opens Main.SceneControllers.NavigationPane to javafx.fxml;

    exports Main.SceneControllers.BackButton;
    opens Main.SceneControllers.BackButton to javafx.fxml;

    exports Main.SceneControllers.AIChatBot;
    opens Main.SceneControllers.AIChatBot to javafx.fxml;

    exports TodoList.UI;
    opens TodoList.UI to javafx.fxml;


    exports CustomEventPackage.OneParameter;
    opens CustomEventPackage.OneParameter to javafx.fxml;
    exports CustomEventPackage.ZeroParameter;
    opens CustomEventPackage.ZeroParameter to javafx.fxml;
    exports CustomEventPackage.TwoParameters;
    opens CustomEventPackage.TwoParameters to javafx.fxml;

    opens Main.SceneControllers.Translate to javafx.fxml;
    opens WordEditing to com.google.gson;

    exports Main.SceneControllers.Account to javafx.fxml;
    opens Main.SceneControllers.Account to javafx.fxml;

    exports AIChatBot.gpt4all;
    exports AIChatBot;
    exports AIChatBot.ModelList;

    requires jdk.jsobject;

    requires org.jnrproject.ffi;
    requires org.slf4j;

    requires javafx.media;
    requires org.json;
    requires jlayer;
    requires AnimateFX;
    requires java.desktop;
    requires org.apache.commons.text;

    exports Main.SceneControllers.Widget;
    opens Main.SceneControllers.Widget to javafx.fxml;

    exports Main.SceneControllers.Settings;
    opens Main.SceneControllers.Settings to javafx.fxml;
    exports AIChatBot.Voice;

    exports Main.SceneControllers.Thesaurus;
    opens Main.SceneControllers.Thesaurus to javafx.fxml;
}