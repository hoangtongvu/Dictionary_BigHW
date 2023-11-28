package Game.MultiChoiceGame;

import java.util.ArrayList;
import java.util.List;

public class ChoiceQuesStorage
{
    private final List<MultiChoiceQues> questions;

    public List<MultiChoiceQues> getQuestions()
    {
        return this.questions;
    }

    public ChoiceQuesStorage()
    {
        this.questions = new ArrayList<>();
    }


}
