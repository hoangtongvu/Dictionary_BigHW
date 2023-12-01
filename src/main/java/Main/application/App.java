package Main.application;

import Main.FxmlFileManager;
import Main.SceneControllers.Account.LoginSceneController;
import Main.SceneControllers.Dictionary.HomeSceneController;
import javafx.application.Application;
import javafx.application.HostServices;
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

        LoginSceneController initSC = FxmlFileManager.getInstance().loginSC;
        FxmlFileManager.SwitchToInitScene(initSC);
        Scene scene = new Scene(FxmlFileManager.getInstance().loginSC.getRoot());

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