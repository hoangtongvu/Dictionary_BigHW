package Main.SceneControllers.Game.CreateWord4DirGame;

import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import javafx.fxml.FXML;


public class StartGameScreenSceneController
{

    @FXML
    private void MoveToGameScene()
    {

        GameSceneController gameSceneController = FxmlFileManager.getInstance().createWord4DirGameSceneController;

        HomeSceneController.SwitchScene(FxmlFileManager.getInstance().createWord4DirGameScene);
        gameSceneController.StartGame();
    }

}
