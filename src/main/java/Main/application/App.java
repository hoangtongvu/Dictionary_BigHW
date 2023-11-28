package Main.application;

import Main.FxmlFileManager;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage primaryStage;
    private static HostServices myHostServices;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        myHostServices = getHostServices();
        Parent root;

        //root = FXMLLoader.load(getClass().getResource("/fxml/application/DictionaryScene.fxml"));
        root = FxmlFileManager.getInstance().loginScreen;
        Scene scene = new Scene(root);

        //InputManager inputManager = new InputManager(scene);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);
        primaryStage.setTitle("MyBigDic");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static HostServices getMyHostServices() {
        return myHostServices;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}