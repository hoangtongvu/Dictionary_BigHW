package Game.MultiChoiceGame;

import java.io.FileNotFoundException;

public class ChoiceGameCtrl
{
    private static ChoiceGameCtrl instance;
    public static ChoiceGameCtrl getInstance() {
        if (instance == null) instance = new ChoiceGameCtrl();
        return instance;
    }

    private ChoiceGameManager choiceGameManager;
    private ChoiceQuesLoader choiceQuesLoader;
    private ChoiceQuesGenerator choiceQuesGenerator;

    public ChoiceQuesLoader getChoiceQuesLoader() {
        return choiceQuesLoader;
    }
    public ChoiceGameManager getChoiceGameManager() {
        return choiceGameManager;
    }
    public ChoiceQuesGenerator getChoiceQuesGenerator() {
        return choiceQuesGenerator;
    }



    private ChoiceGameCtrl()
    {
        this.choiceGameManager = new ChoiceGameManager(this);
        this.choiceQuesLoader = new ChoiceQuesLoader(this);
    }



    public static void main(String[] args) throws FileNotFoundException {
        ChoiceGameCtrl choiceGameCtrl = ChoiceGameCtrl.getInstance();
        choiceGameCtrl.getChoiceQuesLoader().LoadDefault();
        //System.out.println(choiceGameCtrl.GetInfoAllQuestions());
        System.out.println(choiceGameCtrl.getChoiceGameManager().GetInfoAtQuestion(0));

        boolean check = choiceGameCtrl.getChoiceGameManager().getQuestions().get(0).CheckAnswer(ChoiceCode.C);
        System.out.println(check);
    }

}
