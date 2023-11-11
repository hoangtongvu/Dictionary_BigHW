package Main;

import Main.SceneControllers.Game.MultiChoiceGame.GameSceneController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;

public class FxmlFileManager
{

    private static FxmlFileManager instance;

    public static FxmlFileManager getInstance()
    {
        if (instance == null) instance = new FxmlFileManager();
        return instance;
    }


    public final Parent root;
    public final Parent homeScene;
    public final Parent multiChoiceWordGameScene;
    public final GameSceneController multiChoiceGameSceneController;

    public final Parent multiChoiceGameStartScene;




    private FxmlFileManager()
    {
        try
        {
            this.root = FXMLLoader.load(getClass().getResource("/fxml/application/MainScene.fxml"));
            this.homeScene = FXMLLoader.load(getClass().getResource("/fxml/application/HomeScene.fxml"));


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Game/MultiChoiceGame/GameScene.fxml"));
            this.multiChoiceWordGameScene = loader.load();
            this.multiChoiceGameSceneController = loader.getController();

            loader = new FXMLLoader(getClass().getResource("/fxml/Game/MultiChoiceGame/StartGameScreenScene.fxml"));
            this.multiChoiceGameStartScene = loader.load();
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }







}
