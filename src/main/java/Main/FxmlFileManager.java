package Main;

import Interfaces.IHasBackButton;
import Logger.LoggersCtrl;
import Main.SceneControllers.AIChatBot.AIConversationSceneController;
import Main.SceneControllers.Account.EditProfilePic;
import Main.SceneControllers.Account.LoginSceneController;
import Main.SceneControllers.Account.TimePickerController;
import Main.SceneControllers.Account.UserProfileSceneController;
import Main.SceneControllers.BackButton.BackButton;
import Main.SceneControllers.BaseSceneController;
import Main.SceneControllers.Dictionary.DictionarySceneController;
import Main.SceneControllers.Dictionary.EditWordSceneController;
import Main.SceneControllers.Dictionary.HomeSceneController;
import Main.SceneControllers.Game.ChooseGameSceneController;
import Main.SceneControllers.Game.Wordle.WordleController;
import Interfaces.IHasNavPane;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import Main.SceneControllers.Settings.SettingSceneController;
import Main.SceneControllers.Thesaurus.ThesaurusController;
import Main.SceneControllers.Translate.TranslateController;
import Main.application.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

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

    private static BaseSceneController currentSC;

    //region SCs declaration
    public final DictionarySceneController dictionarySC;
    public final HomeSceneController homeSC;


    public final ChooseGameSceneController chooseGameSC;

    public final Main.SceneControllers.Game.MultiChoiceGame.StartGameScreenSceneController multiChoiceGameStartSC;
    public final Main.SceneControllers.Game.MultiChoiceGame.GameSceneController multiChoiceGameSC;

    public final Main.SceneControllers.Game.CreateWord4DirGame.StartGameScreenSceneController createWord4DirStartSC;
    public final Main.SceneControllers.Game.CreateWord4DirGame.GameSceneController createWord4DirSC;

    public final WordleController wordleSC;

    public final TranslateController translateSC;
    public final EditWordSceneController editWordSceneController;
    public final AIConversationSceneController aiSC;
    public final LoginSceneController loginSC;
    public final NavigationPaneSceneController navigationPaneSC;
    public final UserProfileSceneController profileSC;
    public final EditProfilePic editProfilePicSC;
    public final TimePickerController timePickerSC;
    public final ThesaurusController thesaurusSC;

    //endregion

    //region SwitchScene functions.
    public static void SwitchToInitScene(BaseSceneController initSC)
    {
        currentSC = initSC;
        TryAddNavPane(initSC);
    }


    public static void SwitchScene(BaseSceneController newSceneController)
    {
        if (currentSC == newSceneController) return;
        if (currentSC != null) currentSC.EndShow();
        newSceneController.setPrevSC(currentSC);
        currentSC = newSceneController;
        currentSC.StartShow();


        Stage primaryStage = App.getPrimaryStage();

        primaryStage.getScene().setRoot(newSceneController.getRoot());
        primaryStage.show();

        //Check if SC implements IHasNavPane flag -> add navPane to that scene.
        TryAddNavPane(newSceneController);
        TryAddBackButton(newSceneController);
    }

    public static void SwitchBack2PrevScene(BaseSceneController newSceneController)
    {
        if (currentSC == newSceneController) return;
        if (currentSC != null) currentSC.EndShow();
        currentSC = newSceneController;
        currentSC.StartShow();


        Stage primaryStage = App.getPrimaryStage();

        primaryStage.getScene().setRoot(newSceneController.getRoot());
        primaryStage.show();

        //Check if SC implements IHasNavPane flag -> add navPane to that scene.
        TryAddNavPane(newSceneController);
        TryAddBackButton(newSceneController);
    }

    private static void TryAddNavPane(BaseSceneController newSceneController)
    {
        if (newSceneController instanceof IHasNavPane) NavigationPaneSceneController.AddNavPaneComponentsToRoot(newSceneController.getRoot());
        else LoggersCtrl.systemLogger.Log(newSceneController.getClass() + " does not have IHasNavPane flag");
    }

    private static void TryAddBackButton(BaseSceneController newSC)
    {
        if (newSC instanceof IHasBackButton) BackButton.GetInstance().setParentSC(newSC);
        else LoggersCtrl.systemLogger.Log(newSC.getClass() + " does not have IHasBackButton flag");
    }


    //endregion


    private FxmlFileManager()
    {
        this.homeSC = this.LoadSC("/application/HomeScene.fxml");
        this.dictionarySC = this.LoadSC("/application/DictionaryScene.fxml");

        this.chooseGameSC = this.LoadSC("/Game/ChooseGameScene.fxml");

        this.multiChoiceGameStartSC = this.LoadSC("/Game/MultiChoiceGame/StartGameScreenScene.fxml");
        this.multiChoiceGameSC = this.LoadSC("/Game/MultiChoiceGame/GameScene.fxml");

        this.createWord4DirStartSC = this.LoadSC("/Game/CreateWord4DirGame/StartGameScreenScene.fxml");
        this.createWord4DirSC = this.LoadSC("/Game/CreateWord4DirGame/GameScene.fxml");

        this.translateSC = this.LoadSC("/Translate/translateScene.fxml");
        this.editWordSceneController = this.LoadSC("/application/EditWord.fxml");
        this.aiSC = this.LoadSC("/AIChatBot/AIConversationScene.fxml");
        this.wordleSC = this.LoadSC("/Game/Wordle/WordleScene.fxml");
        this.timePickerSC = this.LoadSC("/application/TimePicker.fxml");
        this.loginSC = this.LoadSC("/application/LoginScreen.fxml");

        this.profileSC = this.LoadSC("/application/UserProfileScene.fxml");
        this.editProfilePicSC = this.LoadSC("/application/ProfilePicScene.fxml");

        this.navigationPaneSC = this.LoadSC("/application/NavigationPaneScene.fxml");
        this.thesaurusSC = this.LoadSC("/application/thesaurusScene.fxml");

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
