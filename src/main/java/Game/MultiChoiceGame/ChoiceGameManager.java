package Game.MultiChoiceGame;


import CustomEventPackage.OneParameter.CustomEvent;

import java.util.ArrayList;
import java.util.List;

public class ChoiceGameManager
{

    private final ChoiceGameCtrl choiceGameCtrl;
    private final List<MultiChoiceQues> questions;


    public final CustomEvent<MultiChoiceQues> onQuesChangeEvent;

    public List<MultiChoiceQues> getQuestions() {
        return questions;
    }

    public ChoiceGameManager(ChoiceGameCtrl choiceGameCtrl)
    {
        this.choiceGameCtrl = choiceGameCtrl;
        this.questions = new ArrayList<>();
        this.choiceGameCtrl.getChoiceQuesLoader().LoadDefault();
        this.onQuesChangeEvent = new CustomEvent<>(this);
    }

    public void Start(int maxQues)
    {
        this.LoadQuestions(maxQues);
    }

    private void LoadQuestions(int maxQues)
    {
        this.choiceGameCtrl.getChoiceQuesGenerator().GenerateRandomQuestions(maxQues);
    }


}
