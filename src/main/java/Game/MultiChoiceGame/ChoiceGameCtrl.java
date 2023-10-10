package Game.MultiChoiceGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class ChoiceGameCtrl
{
    private List<MultiChoiceQues> questions;
    private ChoiceQuesLoader choiceQuesLoader;


    public ChoiceGameCtrl()
    {
        this.choiceQuesLoader = new ChoiceQuesLoader(this);
        this.questions = new ArrayList<>();
    }
    public List<MultiChoiceQues> getQuestions()
    {
        return this.questions;
    }
    public ChoiceQuesLoader getChoiceQuesLoader() {
        return choiceQuesLoader;
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



    public static void main(String[] args) throws FileNotFoundException {
        ChoiceGameCtrl choiceGameCtrl = new ChoiceGameCtrl();
        choiceGameCtrl.getChoiceQuesLoader().LoadDefault();
        //System.out.println(choiceGameCtrl.GetInfoAllQuestions());
        System.out.println(choiceGameCtrl.GetInfoAtQuestion(0));

        boolean check = choiceGameCtrl.getQuestions().get(0).CheckAnswer(ChoiceCode.D);
        System.out.println(check);
    }


}
