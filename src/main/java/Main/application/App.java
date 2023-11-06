package Main.application;

import Main.FxmlFileManager;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class App extends Application
{

    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Parent root;
        //root = FXMLLoader.load(getClass().getResource("/fxml/application/MainScene.fxml"));
        root = FxmlFileManager.getInstance().homeScene;
        Scene scene = new Scene(root);

        //InputManager inputManager = new InputManager(scene);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("MyBigDic");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}