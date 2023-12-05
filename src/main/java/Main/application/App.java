package Main.application;

import Main.FxmlFileManager;
import Main.SceneControllers.Account.LoginSceneController;
import Main.SceneControllers.Dictionary.HomeSceneController;
import Timer.SessionTime;
import User.User;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class App extends Application {
    private static Stage primaryStage;
    private static HostServices myHostServices;

    public static void main(String[] args) {
        launch(args);
        exitApplicationHandler();
    }

    public static void exitApplicationHandler() {
        System.out.println("Program exited");
        if (User.getCurrentUser().isOnline()) {
            User.getCurrentUser().logoutHandler();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        myHostServices = getHostServices();

        HomeSceneController initSC = FxmlFileManager.getInstance().homeSC;
        FxmlFileManager.SwitchToInitScene(initSC);
        Scene scene = new Scene(FxmlFileManager.getInstance().homeSC.getRoot());
        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/png/nerd.png"))));
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(768);
        primaryStage.setResizable(false);
            primaryStage.setTitle("Dictionary");
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