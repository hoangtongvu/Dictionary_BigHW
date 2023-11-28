package Game.MultiChoiceGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ChoiceQuesGenerator
{
    private final ChoiceGameCtrl choiceGameCtrl;

    public ChoiceQuesGenerator(ChoiceGameCtrl choiceGameCtrl)
    {
        this.choiceGameCtrl = choiceGameCtrl;
    }


    /**
     * Get random distinct multi-choice questions.
     * @param numberOfQuestions is number of questions you want to generate.
     * @return list of randomized questions.
     */
    public List<MultiChoiceQues> GetRandomQuestions(int numberOfQuestions)
    {
        List<MultiChoiceQues> randomQuestions = new ArrayList<>();
        List<MultiChoiceQues> questions = this.choiceGameCtrl.getChoiceQuesStorage().getQuestions();
        int length = questions.size();
        if (length == 0) return null;

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

    public void GenerateRandomQuestions(int numberOfQuestions)
    {

        List<MultiChoiceQues> questions = this.choiceGameCtrl.getChoiceGameManager().getQuestions();
        questions.clear();

        List<MultiChoiceQues> storedQuestions = this.choiceGameCtrl.getChoiceQuesStorage().getQuestions();
        int length = storedQuestions.size();
        if (length == 0) return;

        Random random = new Random();
        int randomIndex;

        for (int i = 0; i < numberOfQuestions; i++)
        {
            MultiChoiceQues ques;

            do
            {
                randomIndex = random.nextInt(length);
                ques = storedQuestions.get(randomIndex);
            } while (questions.contains(ques));

            questions.add(ques);

        }

    }



}
