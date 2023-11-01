package Main.application;

import Main.FxmlFileManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class App extends Application
{
    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        Parent root;
        //root = FXMLLoader.load(getClass().getResource("/fxml/application/MainScene.fxml"));
        root = FxmlFileManager.getInstance().root;
        Scene scene = new Scene(root);

        //InputManager inputManager = new InputManager(scene);
        primaryStage.setMinWidth(854);
        primaryStage.setMinHeight(494);
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}