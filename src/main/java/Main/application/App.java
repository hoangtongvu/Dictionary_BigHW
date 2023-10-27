package Main.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
        try {
            root = FXMLLoader.load(getClass().getResource("/fxml/application/MainScene.fxml"));
            Scene scene = new Scene(root);

            //InputManager inputManager = new InputManager(scene);
            primaryStage.setMinWidth(854);
            primaryStage.setMinHeight(494);
            primaryStage.setTitle("Hello World!");
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}