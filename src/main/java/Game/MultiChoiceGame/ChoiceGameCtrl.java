package Game.MultiChoiceGame;

import Game.GameCtrl;
import Game.GamesCtrl;
import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import java.io.FileNotFoundException;

public class ChoiceGameCtrl extends GameCtrl
{

    private final ChoiceGameManager choiceGameManager;
    private final ChoiceQuesLoader choiceQuesLoader;
    private final ChoiceQuesGenerator choiceQuesGenerator;
    private final ChoiceQuesStorage choiceQuesStorage;

    public ChoiceQuesLoader getChoiceQuesLoader() {
        return choiceQuesLoader;
    }
    public ChoiceGameManager getChoiceGameManager() {
        return choiceGameManager;
    }
    public ChoiceQuesGenerator getChoiceQuesGenerator() {
        return choiceQuesGenerator;
    }
    public ChoiceQuesStorage getChoiceQuesStorage() {
        return choiceQuesStorage;
    }



    public ChoiceGameCtrl()
    {
        super("Multi-Choice", actionEvent -> HomeSceneController.SwitchScene(FxmlFileManager.getInstance().multiChoiceGameStartScene));
        this.choiceGameManager = new ChoiceGameManager(this);
        this.choiceQuesLoader = new ChoiceQuesLoader(this);
        this.choiceQuesGenerator = new ChoiceQuesGenerator(this);
        this.choiceQuesStorage = new ChoiceQuesStorage();
    }


}
