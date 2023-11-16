package Game.CreateWord4DirGame;

import Game.GameCtrl;
import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CreateWord4DirGameCtrl extends GameCtrl
{

    private final CreateWord4DirGameManager gameManager;

    public CreateWord4DirGameManager getGameManager() {
        return gameManager;
    }

    public CreateWord4DirGameCtrl()
    {
        super("4 Directions", actionEvent -> HomeSceneController.SwitchScene(FxmlFileManager.getInstance().createWord4DirGameStartScene));
        this.gameManager = new CreateWord4DirGameManager();
    }

}
