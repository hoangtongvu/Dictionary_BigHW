package Main.SceneControllers.Game.MultiChoiceGame;

import Game.GamesCtrl;
import Game.MultiChoiceGame.*;
import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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
    private Button endReviewButton;

    @FXML
    private Text question;

    @FXML
    private Text timerText;

    @FXML
    private ProgressBar answeredProgress;

    @FXML
    private GridPane quesGridPane;

    @FXML
    private VBox answerResultVbox;

    @FXML
    private Text finalQuestionStateText;

    @FXML
    private Text rightAnswerIfIncorrectText;

    @FXML
    private Text finalPointText;

    //endregion

    private CheckBox choseAnswer;
    private List<MultiChoiceQues> questions;
    private ChoiceGameCtrl choiceGameCtrl;
    private int maxQues = 20;
    private int currentQuesPos = 0;
    private ChoiceCode[] userAnswers;
    private ChoiceGameTimerManager timerManager;
    private QuesGridPaneManager quesGridPaneManager;


    public void setMaxQues(int maxQues) { this.maxQues = maxQues; }
    public ChoiceGameTimerManager getTimerManager() { return this.timerManager; }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.choiceGameCtrl = GamesCtrl.getInstance().getChoiceGameCtrl();
        this.timerManager = new ChoiceGameTimerManager(this.timerText);
        this.quesGridPaneManager = new QuesGridPaneManager(this.quesGridPane);

        try {
            this.choiceGameCtrl.getChoiceQuesLoader().LoadDefault();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        this.SubTimerEvent();

    }

    public void StartGame()
    {
        this.timerManager.getCustomTimer().Start();

        this.LoadQuestions();
        this.choseAnswer = null;

        this.userAnswers = new ChoiceCode[this.maxQues];

        this.SetQuestion(this.questions.get(0).getQuestion());
        this.SetAnswers(this.questions.get(0).getAnswers());

        this.quesGridPaneManager.AddingButtonsToGridPane(this.maxQues);
        this.AddEventToGridPaneButton();
        this.MoveToQuestionAt(0);

    }

    private void EndGame()
    {
        //show point.
        //show number of correct and incorrect answers.
        //set color of correct and incorrect answers button.
        //show right answer if user's answer is incorrect.
        this.MoveToQuestionAt(0);
        this.CheckAnswers();
        this.ToggleAnswerResultVbox();
        this.ToggleFinalPointText();
        this.ShowTimeOutScreen();
        this.ToggleCheckBoxes();
        this.ToggleEndGameButton();
        this.ToggleEndReviewButton();
    }

    @FXML
    private void MoveToNextQuestion()
    {
        this.MoveToQuestionAt(this.currentQuesPos + 1);
    }

    @FXML
    private void MoveToQuestionAt(int i)
    {
        if (i >= this.maxQues) return;
        this.currentQuesPos = i;
        this.ClearChoseAnswer();
        this.TickAnswerBasedOnUserAnswer();

        this.SetQuestion(this.questions.get(this.currentQuesPos).getQuestion());
        this.SetAnswers(this.questions.get(this.currentQuesPos).getAnswers());
        this.SetAnswerResultVbox();
    }

    private int GetNumOfAnsweredQues()
    {
        int count = 0;
        for (ChoiceCode userAnswer : this.userAnswers) if (userAnswer != null) count++;
        return count;
    }

    @FXML
    private void ChooseAnswerA() {this.ChooseAnswer(ChoiceCode.A);}

    @FXML
    private void ChooseAnswerB() {this.ChooseAnswer(ChoiceCode.B);}

    @FXML
    private void ChooseAnswerC() {this.ChooseAnswer(ChoiceCode.C);}

    @FXML
    private void ChooseAnswerD() {this.ChooseAnswer(ChoiceCode.D);}

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

        this.quesGridPaneManager.SetButDone(this.currentQuesPos);


        this.SetProgressBar(this.GetNumOfAnsweredQues(), this.maxQues);

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

    private void AddEventToGridPaneButton()
    {
        List<Button> buttons = this.quesGridPaneManager.getButtons();
        int quesCount = 1;
        for (Button button : buttons)
        {
            if (quesCount > this.maxQues) return;
            int finalQuesCount = quesCount;
            button.addEventHandler(ActionEvent.ACTION, e -> this.MoveToQuestionAt(finalQuesCount - 1) );
            quesCount++;
        }
    }

    private void SubTimerEvent()
    {
        this.timerManager.getCustomTimer().onStopEvent.AddListener(this::EndGame);
    }

    @FXML
    private void EndGameButton()
    {
        this.timerManager.getCustomTimer().Stop();
    }

    private void ShowTimeOutScreen()
    {
        System.out.println("Time out.");
    }

    private void CheckAnswers()
    {
        double finalPoint;
        int correctAnswerAmount = 0;
        int incorrectAnswerAmount = 0;

        for (int i = 0; i < this.maxQues; i++)
        {
            if (this.AnswerIsCorrect(i))
            {
                correctAnswerAmount++;
                this.quesGridPaneManager.SetButCorrect(i);
                continue;
            }

            incorrectAnswerAmount++;
            this.quesGridPaneManager.SetButIncorrect(i);
        }


        finalPoint = (double) correctAnswerAmount / this.maxQues * 10;
        finalPoint = Math.round(finalPoint * 100.0) / 100.0;

        this.finalPointText.setText("Point: " + finalPoint);

    }

    private boolean AnswerIsCorrect(int i)
    {
        ChoiceCode userAnswer = this.userAnswers[i];
        return this.questions.get(i).CheckAnswer(userAnswer);
    }

    private void SetAnswerResultVbox()
    {
        String quesState;
        String message;
        Paint paint;
        String colorCode;
        if (this.AnswerIsCorrect(this.currentQuesPos))
        {
            colorCode = "GREEN";
            quesState = "Correct";
            message = "";
        }
        else
        {
            colorCode = "RED";
            quesState = "Incorrect";
            message = "Correct answer is " + this.questions.get(this.currentQuesPos).getRightAnswerCode() + ".";
        }

        paint = Paint.valueOf(colorCode);
        this.finalQuestionStateText.setFill(paint);
        this.finalQuestionStateText.setText(quesState);
        this.rightAnswerIfIncorrectText.setText(message);

    }

    private void ToggleCheckBoxes()
    {
        this.answerA.setDisable(!this.answerA.isDisable());
        this.answerB.setDisable(!this.answerB.isDisable());
        this.answerC.setDisable(!this.answerC.isDisable());
        this.answerD.setDisable(!this.answerD.isDisable());
    }

    private void ToggleEndGameButton()
    {
        this.endGameButton.setDisable(!this.endGameButton.isDisable());
        this.endGameButton.setVisible(!this.endGameButton.isVisible());
    }

    private void ToggleEndReviewButton()
    {
        this.endReviewButton.setDisable(!this.endGameButton.isDisable());
        this.endReviewButton.setVisible(!this.endGameButton.isVisible());
    }

    private void ToggleFinalPointText()
    {
        this.finalPointText.setVisible(!this.finalPointText.isVisible());
    }

    private void ToggleAnswerResultVbox()
    {
        this.answerResultVbox.setVisible(!this.answerResultVbox.isVisible());
    }

    @FXML
    private void EndReview()
    {

        this.SwitchBackToStartScreen();
    }

    private void SwitchBackToStartScreen()
    {
        this.ToggleEndGameButton();
        this.ToggleEndReviewButton();
        this.ToggleFinalPointText();
        this.ToggleAnswerResultVbox();
        this.ToggleCheckBoxes();
        this.ClearChoseAnswer();
        this.SetProgressBar(0, this.maxQues);
        HomeSceneController.SwitchScene(FxmlFileManager.getInstance().multiChoiceGameStartScene);

    }
    

}
