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

    public ChoiceQuesLoader getChoiceQuesLoader() {
        return choiceQuesLoader;
    }
    public ChoiceGameManager getChoiceGameManager() {
        return choiceGameManager;
    }
    public ChoiceQuesGenerator getChoiceQuesGenerator() {
        return choiceQuesGenerator;
    }



    public ChoiceGameCtrl()
    {
        super("Multi-Choice", actionEvent -> HomeSceneController.SwitchScene(FxmlFileManager.getInstance().multiChoiceGameStartScene));
        this.choiceGameManager = new ChoiceGameManager(this);
        this.choiceQuesLoader = new ChoiceQuesLoader(this);
        this.choiceQuesGenerator = new ChoiceQuesGenerator(this);
    }



    public static void main(String[] args) throws FileNotFoundException {
        ChoiceGameCtrl choiceGameCtrl = GamesCtrl.getInstance().getChoiceGameCtrl();
        choiceGameCtrl.getChoiceQuesLoader().LoadDefault();
        //System.out.println(choiceGameCtrl.GetInfoAllQuestions());
        System.out.println(choiceGameCtrl.getChoiceGameManager().GetInfoAtQuestion(0));

        boolean check = choiceGameCtrl.getChoiceGameManager().getQuestions().get(0).CheckAnswer(ChoiceCode.C);
        System.out.println(check);
    }

}
