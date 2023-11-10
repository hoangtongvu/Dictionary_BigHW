package Main.SceneControllers;

import Game.MultiChoiceGame.ChoiceCode;
import Game.MultiChoiceGame.ChoiceGameCtrl;
import Game.MultiChoiceGame.ChoiceGameTimerManager;
import Game.MultiChoiceGame.MultiChoiceQues;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable
{

    //region FXML
    @FXML
    private CheckBox answerA;

    @FXML
    private CheckBox answerB;

    @FXML
    private CheckBox answerC;

    @FXML
    private CheckBox answerD;

    @FXML
    private Button nextButton;

    @FXML
    private Button endGameButton;

    @FXML
    private Text question;

    @FXML
    private Text timerText;

    @FXML
    private ProgressBar answeredProgress;

    @FXML
    private GridPane quesGridPane;

    //endregion

    private CheckBox choseAnswer;

    private List<MultiChoiceQues> questions;

    private ChoiceGameCtrl choiceGameCtrl;
    private int maxQues = 20;
    private int currentQuesPos = 0;
    private ChoiceCode[] userAnswers;

    private ChoiceGameTimerManager timerManager;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.choiceGameCtrl = ChoiceGameCtrl.getInstance();
        this.timerManager = new ChoiceGameTimerManager(this.timerText);

        try {
            this.choiceGameCtrl.getChoiceQuesLoader().LoadDefault();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.LoadQuestions();
        this.choseAnswer = null;

        this.userAnswers = new ChoiceCode[this.maxQues];

        this.SetQuestion(this.questions.get(0).getQuestion());
        this.SetAnswers(this.questions.get(0).getAnswers());

        this.AddingButtonsToGridPane();
        this.SubTimerEvent();
        this.StartGame();

    }


    @FXML
    public void MoveToNextQuestion()
    {

//        //if (this.choseAnswer == null) return;
//        this.ResetChoseAnswer();
//        this.currentQuesPos++;
//        this.SetProgressBar(this.currentQuesPos, this.maxQues);
//
//        if (this.currentQuesPos >= this.maxQues) return;
//        this.SetQuestion(this.questions.get(this.currentQuesPos).getQuestion());
//        this.SetAnswers(this.questions.get(this.currentQuesPos).getAnswers());
//
//        for (ChoiceCode code : this.userAnswers)
//        {
//            System.out.print(code + " ");
//        }
//        System.out.println();
        this.MoveToQuestionAt(this.currentQuesPos + 1);
    }

    @FXML
    public void MoveToQuestionAt(int i)
    {

        if (i >= this.maxQues) return;
        this.currentQuesPos = i;
        this.ClearChoseAnswer();
        this.TickAnswerBasedOnUserAnswer();
        this.SetProgressBar(this.GetNumOfAnsweredQues(), this.maxQues);

        this.SetQuestion(this.questions.get(this.currentQuesPos).getQuestion());
        this.SetAnswers(this.questions.get(this.currentQuesPos).getAnswers());

        for (ChoiceCode code : this.userAnswers)
        {
            System.out.print(code + " ");
        }
        System.out.println();
    }

    private int GetNumOfAnsweredQues()
    {
        int count = 0;
        for (ChoiceCode userAnswer : this.userAnswers)
        {
            if (userAnswer != null) count++;
        }
        return count;
    }

    @FXML
    public void ChooseAnswerA() {this.ChooseAnswer(ChoiceCode.A);}

    @FXML
    public void ChooseAnswerB() {this.ChooseAnswer(ChoiceCode.B);}

    @FXML
    public void ChooseAnswerC() {this.ChooseAnswer(ChoiceCode.C);}

    @FXML
    public void ChooseAnswerD() {this.ChooseAnswer(ChoiceCode.D);}

    private void ChooseAnswer(ChoiceCode choiceCode)
    {
        if (this.choseAnswer != null) this.choseAnswer.setSelected(false);
        switch (choiceCode)
        {
            case A:
                this.choseAnswer = this.answerA;
                break;
            case B:
                this.choseAnswer = this.answerB;
                break;
            case C:
                this.choseAnswer = this.answerC;
                break;
            case D:
                this.choseAnswer = this.answerD;
                break;
            default:
                break;
        }

        this.userAnswers[this.currentQuesPos] = choiceCode;
        this.choseAnswer.setSelected(true);
        this.UpdateGridPaneButtonColor();

    }

    private void SetProgressBar(int current, int max)
    {
        this.answeredProgress.setProgress((double) current / max);
    }

    private void LoadQuestions()
    {
        this.questions = this.choiceGameCtrl.getChoiceQuesGenerator().GetRandomQuestions(this.maxQues);
    }

    private void SetQuestion(String ques)
    {
        this.question.setText((this.currentQuesPos + 1) + ". " + ques);
    }

    private void SetAnswers(String[] answers)
    {
        this.answerA.setText(answers[0]);
        this.answerB.setText(answers[1]);
        this.answerC.setText(answers[2]);
        this.answerD.setText(answers[3]);
    }

    private void ClearChoseAnswer()
    {
        if (this.choseAnswer == null) return;
        this.choseAnswer.setSelected(false);
        this.choseAnswer = null;
    }

    private void TickAnswerBasedOnUserAnswer()
    {
        ChoiceCode choseAnswerCode = this.userAnswers[this.currentQuesPos];
        if (choseAnswerCode == null) return;
        switch (choseAnswerCode)
        {
            case A:
                this.choseAnswer = this.answerA;
                break;
            case B:
                this.choseAnswer = this.answerB;
                break;
            case C:
                this.choseAnswer = this.answerC;
                break;
            case D:
                this.choseAnswer = this.answerD;
                break;
            default:
                break;
        }


        this.choseAnswer.setSelected(true);
    }

    private void AddingButtonsToGridPane()
    {
        int col = this.quesGridPane.getColumnCount();
        int row = this.quesGridPane.getRowCount();

        int quesCount = 1;
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                if (quesCount > this.maxQues) return;

                Button newBut = new Button(Integer.toString(quesCount));
                newBut.setMaxWidth(Integer.MAX_VALUE);
                newBut.setMaxHeight(Integer.MAX_VALUE);

                //add colorAdjust effect.
                ColorAdjust colorAdjust = new ColorAdjust();
                newBut.setEffect(colorAdjust);

                //add event to per button.
                int finalQuesCount = quesCount;
                newBut.addEventHandler(ActionEvent.ACTION, e -> this.MoveToQuestionAt(finalQuesCount - 1) );

                //add buttons to gridPane.
                this.quesGridPane.add(newBut, j, i);

                quesCount++;
            }

        }
    }

    private void UpdateGridPaneButtonColor()
    {
        List<Node> buttons = this.quesGridPane.getChildren();
        int count = 0;
        for (Node node : buttons)
        {
            Button button = (Button) node;
            if (this.userAnswers[count] != null)
            {
                this.SetButtonColorGreen(button);
            }
            count++;
        }
    }

    private void SetButtonColorGreen(Button button)
    {
        ColorAdjust colorAdjust = (ColorAdjust) button.getEffect();
        colorAdjust.setHue(0.5);
        colorAdjust.setSaturation(1);
    }

    private void SubTimerEvent()
    {
        this.timerManager.getCustomTimer().onStopEvent.AddListener(this::EndGame);
    }

    private void StartGame()
    {
        System.out.println("Start");
        this.timerManager.getCustomTimer().Start();
    }

    @FXML
    private void EndGameButton()
    {
        this.timerManager.getCustomTimer().Stop();
    }

    private void EndGame()
    {
        this.endGameButton.setDisable(true);
        this.ShowTimeOutScreen();
    }

    private void ShowTimeOutScreen()
    {
        System.out.println("Time out.");
    }

}
