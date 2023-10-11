package Game.MultiChoiceGame;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoiceQuesGenerator
{
    private ChoiceGameCtrl choiceGameCtrl;

    public ChoiceQuesGenerator(ChoiceGameCtrl choiceGameCtrl)
    {
        this.choiceGameCtrl = choiceGameCtrl;
    }

    /**
     * Get random distinct multi-choice questions.
     * @numberOfQuestions is number of questions you want to generate.
     */
    public List<MultiChoiceQues> GetRandomQuestions(int numberOfQuestions)
    {
        List<MultiChoiceQues> randomQuestions = new ArrayList<>();
        List<MultiChoiceQues> questions = this.choiceGameCtrl.getChoiceGameManager().getQuestions();
        int length = questions.size();
        if (length <= 0) return null;

        Random random = new Random();
        int randomIndex;

        for (int i = 0; i < numberOfQuestions; i++)
        {
            MultiChoiceQues ques;

            do
            {
                randomIndex = random.nextInt(length);
                ques = questions.get(randomIndex);
            } while (randomQuestions.contains(ques));

            randomQuestions.add(ques);

        }

        return randomQuestions;
    }



}
