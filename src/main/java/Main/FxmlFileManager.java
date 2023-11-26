package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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


    public final Parent root;
    public final Parent homeScene;


    public final Parent chooseGameScene;


    public final Parent multiChoiceGameStartScene;
    public final Parent multiChoiceWordGameScene;

  

    public final Parent translateScene;

    public final Parent editWordScene;

    public final Main.SceneControllers.Game.MultiChoiceGame.GameSceneController multiChoiceGameSceneController;


    public final Parent createWord4DirGameStartScene;
    public final Parent createWord4DirGameScene;
    public final Main.SceneControllers.Game.CreateWord4DirGame.GameSceneController createWord4DirGameSceneController;
    public final Parent wordleScene;




    private FxmlFileManager()
    {
        try
        {
            this.root = FXMLLoader.load(getClass().getResource("/fxml/application/DictionaryScene.fxml"));
            this.homeScene = FXMLLoader.load(getClass().getResource("/fxml/application/HomeScene.fxml"));

            FXMLLoader loader = null;

            loader = new FXMLLoader(getClass().getResource("/fxml/Game/MultiChoiceGame/GameScene.fxml"));
            this.multiChoiceWordGameScene = loader.load();
            this.multiChoiceGameSceneController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/fxml/Game/MultiChoiceGame/StartGameScreenScene.fxml"));
            this.multiChoiceGameStartScene = loader.load();

            loader = new FXMLLoader(getClass().getResource("/fxml/Game/CreateWord4DirGame/GameScene.fxml"));
            this.createWord4DirGameScene = loader.load();
            this.createWord4DirGameSceneController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/fxml/Game/CreateWord4DirGame/StartGameScreenScene.fxml"));
            this.createWord4DirGameStartScene = loader.load();

            loader = new FXMLLoader(getClass().getResource("/fxml/Game/ChooseGameScene.fxml"));
            this.chooseGameScene = loader.load();

            loader = new FXMLLoader(getClass().getResource("/fxml/Translate/translateScene.fxml"));
            this.translateScene = loader.load();

            loader = new FXMLLoader(getClass().getResource("/fxml/application/EditWord.fxml"));
            this.editWordScene = loader.load();

            loader = new FXMLLoader(getClass().getResource("/fxml/Game/Wordle/WordleScene.fxml"));
            this.wordleScene = loader.load();

        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }







}
