package Game.Wordle;

import Game.GameCtrl;
import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;


public class WordleCtrl extends GameCtrl {

    public WordleCtrl() {
        super("Wordle", actionEvent -> FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().wordleScene));
    }
}
