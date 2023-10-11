package Game.MultiChoiceGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ChoiceGameManager
{

    private ChoiceGameCtrl choiceGameCtrl;
    private List<MultiChoiceQues> questions;

    public List<MultiChoiceQues> getQuestions()
    {
        return this.questions;
    }

    public ChoiceGameManager(ChoiceGameCtrl choiceGameCtrl)
    {
        this.choiceGameCtrl = choiceGameCtrl;
        this.questions = new ArrayList<>();
    }



    public String GetInfoAllQuestions()
    {
        int length = this.questions.size();
        String s = "";
        for (int i = 0; i < length; i++)
        {
            s += (i + 1) + ". " + this.questions.get(i).GetInfo() + "\n";
        }
        return s;
    }


    public String GetInfoAtQuestion(int index)
    {
        int length = this.questions.size();
        if (index >= length) return "Question not found";

        return (index + 1) + ". " + this.questions.get(index).GetInfo() + "\n";
    }




}
