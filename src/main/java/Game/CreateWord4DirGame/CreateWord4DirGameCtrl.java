package Game.CreateWord4DirGame;

import Game.GameCtrl;
import Main.FxmlFileManager;

public class CreateWord4DirGameCtrl extends GameCtrl
{

    private final CreateWord4DirGameManager gameManager;
    private final GameAutoCompletion gameAutoCompletion;

    public CreateWord4DirGameManager getGameManager() {
        return gameManager;
    }

    public GameAutoCompletion getGameAutoCompletion() {
        return gameAutoCompletion;
    }

    public CreateWord4DirGameCtrl()
    {
        super("4 Directions", actionEvent -> FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().createWord4DirStartSC));
        this.gameManager = new CreateWord4DirGameManager(this);
        this.gameAutoCompletion = new GameAutoCompletion(this);
    }

    @Override
    public String getTrailerImagePath()
    {
        return "/gif/GameTrailers/4Directions.gif";
    }
}
