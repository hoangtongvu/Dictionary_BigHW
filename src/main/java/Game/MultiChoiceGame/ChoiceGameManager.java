package Game.MultiChoiceGame;


import CustomEventPackage.OneParameter.CustomEvent;

import java.util.ArrayList;
import java.util.List;

public class ChoiceGameManager
{

    private final ChoiceGameCtrl choiceGameCtrl;
    private final List<MultiChoiceQues> questions;
    private int maxQues = 20;
    private int currentQuesPos = 0;
    private ChoiceCode[] userAnswers;


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
        this.LoadQuestions();
        this.maxQues = maxQues;
        this.userAnswers = new ChoiceCode[this.maxQues];
    }

    public void End()
    {

    }

    private void LoadQuestions()
    {
        this.choiceGameCtrl.getChoiceQuesGenerator().GenerateRandomQuestions(this.maxQues);
    }

    private MultiChoiceQues GetCurrentQuestion()
    {
        return this.questions.get(this.currentQuesPos);
    }

//    private void MoveToQuestionAt(int i)
//    {
//        if (i >= this.maxQues) return;
//        this.currentQuesPos = i;
//        this.onQuesChangeEvent.Invoke(this, this.GetCurrentQuestion());
//        this.ClearChoseAnswer();
//        this.TickAnswerBasedOnUserAnswer();
//
//        this.SetQuestionText(this.questions.get(this.currentQuesPos).getQuestion());
//        this.SetAnswersCheckBox(this.questions.get(this.currentQuesPos).getAnswers());
//        this.SetAnswerResultVbox();
//    }

    private boolean AnswerIsCorrect(int i)
    {
        ChoiceCode userAnswer = this.userAnswers[i];
        return this.questions.get(i).CheckAnswer(userAnswer);
    }

}
