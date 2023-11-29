package Main;

import Logger.LoggersCtrl;
import Main.SceneControllers.AIChatBot.AIConversationSceneController;
import Main.SceneControllers.Account.LoginSceneController;
import Main.SceneControllers.BaseSceneController;
import Main.SceneControllers.Dictionary.DictionarySceneController;
import Main.SceneControllers.Game.ChooseGameSceneController;
import Main.SceneControllers.Game.Wordle.WordleController;
import Main.SceneControllers.IHasNavPane;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import Main.SceneControllers.Settings.SettingSceneController;
import Main.SceneControllers.Translate.TranslateController;
import Main.application.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class FxmlFileManager
{

    public static final double stageHeight = 600;
    public static final double stageWidth = 1200;

    private static FxmlFileManager instance;

    public static FxmlFileManager getInstance()
    {
        if (instance == null) instance = new FxmlFileManager();
        return instance;
    }

    public static void switchScene(Parent newScene) {
        Stage primaryStage = App.getPrimaryStage();
        primaryStage.getScene().setRoot(newScene);
        primaryStage.show();
    }

    private static BaseSceneController currentSC;


    public final DictionarySceneController dictionarySC;
    public final Parent homeScene;


    public final ChooseGameSceneController chooseGameSC;


    public final Main.SceneControllers.Game.MultiChoiceGame.StartGameScreenSceneController multiChoiceGameStartSC;
    public final Main.SceneControllers.Game.MultiChoiceGame.GameSceneController multiChoiceGameSC;


    public final TranslateController translateSC;

    public final Parent editWordScene;
    public final Main.SceneControllers.Dictionary.EditWordSceneController editWordSceneController;


    public final Main.SceneControllers.Game.CreateWord4DirGame.StartGameScreenSceneController createWord4DirStartSC;
    public final Main.SceneControllers.Game.CreateWord4DirGame.GameSceneController createWord4DirSC;
    public final WordleController wordleSC;

    public final AIConversationSceneController aiSC;

    //public final Parent loginScreen;
    public final LoginSceneController loginSC;
    public final SettingSceneController settingSC;


    public static void SwitchScene(Parent newScene) {
        Stage primaryStage = App.getPrimaryStage();

        if (primaryStage.getScene().getRoot() == getInstance().editWordScene) {
            if (!editSceneExitHandler()) {
                return;
            }
        }

        primaryStage.getScene().setRoot(newScene);
        primaryStage.show();
    }

    public static void SwitchScene(BaseSceneController newSceneController)
    {
        if (currentSC == newSceneController) return;
        if (currentSC != null) currentSC.EndShow();
        currentSC = newSceneController;
        currentSC.StartShow();


        Stage primaryStage = App.getPrimaryStage();

        if (primaryStage.getScene().getRoot() == getInstance().editWordScene) {
            if (!editSceneExitHandler()) {
                return;
            }
        }

        primaryStage.getScene().setRoot(newSceneController.getRoot());
        primaryStage.show();

        //Check if SC implements IHasNavPane flag -> add navPane to that scene.
        if (newSceneController instanceof IHasNavPane) NavigationPaneSceneController.AddNavPaneComponentsToRoot1(newSceneController.getRoot());
        else LoggersCtrl.systemLogger.Log(newSceneController.getClass() + " does not have IHasNavPane flag");

    }

    private static boolean editSceneExitHandler() {
        try {
            return FxmlFileManager.getInstance().editWordSceneController.changeSceneSave();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private FxmlFileManager()
    {
        try
        {
            this.homeScene = FXMLLoader.load(getClass().getResource("/fxml/application/HomeScene.fxml"));

            FXMLLoader loader = null;

            this.dictionarySC = this.LoadSC("/application/DictionaryScene.fxml");


            this.chooseGameSC = this.LoadSC("/Game/ChooseGameScene.fxml");

            this.multiChoiceGameStartSC = this.LoadSC("/Game/MultiChoiceGame/StartGameScreenScene.fxml");
            this.multiChoiceGameSC = this.LoadSC("/Game/MultiChoiceGame/GameScene.fxml");


            this.createWord4DirStartSC = this.LoadSC("/Game/CreateWord4DirGame/StartGameScreenScene.fxml");
            this.createWord4DirSC = this.LoadSC("/Game/CreateWord4DirGame/GameScene.fxml");


            this.translateSC = this.LoadSC("/Translate/translateScene.fxml");

            loader = new FXMLLoader(getClass().getResource("/fxml/application/EditWord.fxml"));
            this.editWordScene = loader.load();
            this.editWordSceneController = loader.getController();


            this.aiSC = this.LoadSC("/AIChatBot/AIConversationScene.fxml");

            this.wordleSC = this.LoadSC("/Game/Wordle/WordleScene.fxml");

//            loader = new FXMLLoader(getClass().getResource("/fxml/application/LoginScreen.fxml"));
//            this.loginScreen = loader.load();
            this.loginSC = this.LoadSC("/application/LoginScreen.fxml");

            this.settingSC = this.LoadSC("/application/SettingScene.fxml");

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * SC stands for SceneController.
     * @param localPath is local path of fxml file in /fxml directory.
     * @param <T> is type of SceneController.
     * @return SceneController.
     */
    private <T extends BaseSceneController> T LoadSC(String localPath)
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml" + localPath));
        try {
            Parent root = loader.load();
            T sceneController = loader.getController();
            sceneController.setRoot(root);
            return sceneController;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
