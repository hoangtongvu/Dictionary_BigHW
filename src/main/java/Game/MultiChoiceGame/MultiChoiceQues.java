package Game.MultiChoiceGame;

public class MultiChoiceQues
{
    private String question;
    private String[] answers;
    private ChoiceCode rightAnswerCode;
    public MultiChoiceQues(String ques, String[] ans)
    {
        this.question = ques;
        this.answers = ans;
    }

    public String GetInfo()
    {

        String s = "Question: " + this.question + "\n";
        for (int j = 0; j < 4; j++)
        {
            s += this.answers[j] + "\n";
        }
        s += "Right answer: " + this.rightAnswerCode + "\n";

        return s;
    }

    public String[] getAnswers()
    {
        return this.answers;
    }

    public String getQuestion()
    {
        return this.question;
    }

    public ChoiceCode getRightAnswerCode() {
        return rightAnswerCode;
    }

    public void setRightAnswerCode(String rightAnswer) {
        this.rightAnswerCode = ChoiceCode.valueOf(rightAnswer);
    }

    public boolean CheckAnswer(ChoiceCode choiceCode)
    {
        return this.rightAnswerCode == choiceCode;
    }

}
