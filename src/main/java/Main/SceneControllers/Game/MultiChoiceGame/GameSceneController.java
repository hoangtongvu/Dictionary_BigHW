package Main.SceneControllers.Game.MultiChoiceGame;

import Game.GamesCtrl;
import Game.MultiChoiceGame.*;
import Main.FxmlFileManager;
import Main.SceneControllers.BaseSceneController;
import User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import scenebuilderextended.components.choicegameextendedcomponents.ChoiceGameButton;
import scenebuilderextended.components.choicegameextendedcomponents.ChoiceGameGridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


public class GameSceneController extends BaseSceneController implements Initializable
{

    //region FXML

    @FXML private CheckBox answerA;
    @FXML private CheckBox answerB;
    @FXML private CheckBox answerC;
    @FXML private CheckBox answerD;
    @FXML private Button nextButton;
    @FXML private Button endGameButton;
    @FXML private Button endReviewButton;
    @FXML private Text questionText;
    @FXML private Text timerText;
    @FXML private ProgressBar answeredProgress;
    @FXML private ChoiceGameGridPane quesGridPane;
    @FXML private VBox answerResultVbox;
    @FXML private Text finalQuestionStateText;
    @FXML private Text rightAnswerIfIncorrectText;
    @FXML private Text finalPointText;

    //endregion

    private CheckBox choseAnswerCheckBox;
    private final List<MultiChoiceQues> questions;
    private final ChoiceGameCtrl choiceGameCtrl;
    private int maxQues = 20;
    private int currentQuesPos = 0;
    private ChoiceCode[] userAnswers;
    private ChoiceGameTimerManager timerManager;


    public GameSceneController()
    {
        this.choiceGameCtrl = GamesCtrl.getInstance().getChoiceGameCtrl();
        this.questions = this.choiceGameCtrl.getChoiceGameManager().getQuestions();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.timerManager = new ChoiceGameTimerManager(this.timerText);
        this.SubTimerEvent();
    }

    @Override
    public void StartShow()
    {

    }

    @Override
    public void EndShow()
    {

    }

    @Override
    public void update() {

    }

    public void StartGame(int maxQues, int maxTimeSecond)
    {
        ChoiceGameManager choiceGameManager = this.choiceGameCtrl.getChoiceGameManager();


        this.maxQues = maxQues;
        choiceGameManager.Start(maxQues);

        this.timerManager.getCustomTimer().setMaxTimeSecond(maxTimeSecond);
        this.timerManager.getCustomTimer().Start();

        this.choseAnswerCheckBox = null;

        this.userAnswers = new ChoiceCode[this.maxQues];//

        this.SetQuestionText(this.questions.get(0).getQuestion());
        this.SetAnswersCheckBox(this.questions.get(0).getAnswers());

        this.quesGridPane.AddingButtonsToGridPane(this.maxQues);

        this.AddEventToGridPaneButton();
        this.MoveToQuestionAt(0);

    }

    private void EndGame()
    {
        this.MoveToQuestionAt(0);
        this.CheckAnswers();
        this.ToggleAnswerResultVbox();
        this.ToggleFinalPointText();
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

        this.SetQuestionText(this.questions.get(this.currentQuesPos).getQuestion());
        this.SetAnswersCheckBox(this.questions.get(this.currentQuesPos).getAnswers());
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
        if (this.choseAnswerCheckBox != null) this.choseAnswerCheckBox.setSelected(false);
        this.choseAnswerCheckBox = this.GetCheckBoxByChoiceCode(choiceCode);

        this.userAnswers[this.currentQuesPos] = choiceCode;
        this.choseAnswerCheckBox.setSelected(true);

        this.quesGridPane.getChoiceGameButtons().get(this.currentQuesPos).SetDone();


        this.SetProgressBar(this.GetNumOfAnsweredQues(), this.maxQues);

    }

    private CheckBox GetCheckBoxByChoiceCode(ChoiceCode choiceCode)
    {
        CheckBox checkBox = null;
        switch (choiceCode)
        {
            case A -> checkBox = this.answerA;
            case B -> checkBox = this.answerB;
            case C -> checkBox = this.answerC;
            case D -> checkBox = this.answerD;
        }
        return checkBox;
    }

    private void SetProgressBar(int current, int max)
    {
        this.answeredProgress.setProgress((double) current / max);
    }

    private void SetQuestionText(String ques)
    {
        this.questionText.setText((this.currentQuesPos + 1) + ". " + ques);
    }

    private void SetAnswersCheckBox(String[] answers)
    {
        this.answerA.setText(answers[0]);
        this.answerB.setText(answers[1]);
        this.answerC.setText(answers[2]);
        this.answerD.setText(answers[3]);
    }

    private void ClearChoseAnswer()
    {
        if (this.choseAnswerCheckBox == null) return;
        this.choseAnswerCheckBox.setSelected(false);
        this.choseAnswerCheckBox = null;
    }

    private void TickAnswerBasedOnUserAnswer()
    {
        ChoiceCode choseAnswerCode = this.userAnswers[this.currentQuesPos];
        if (choseAnswerCode == null) return;
        this.choseAnswerCheckBox = this.GetCheckBoxByChoiceCode(choseAnswerCode);
        this.choseAnswerCheckBox.setSelected(true);

    }

    private void AddEventToGridPaneButton()
    {
        List<ChoiceGameButton> buttons = this.quesGridPane.getChoiceGameButtons();
        int quesCount = 1;
        for (ChoiceGameButton button : buttons)
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

    private void CheckAnswers()
    {
        double finalPoint;
        int correctAnswerAmount = 0;
        int incorrectAnswerAmount = 0;


        List<ChoiceGameButton> buttons = this.quesGridPane.getChoiceGameButtons();
        for (int i = 0; i < this.maxQues; i++)
        {
            if (this.AnswerIsCorrect(i))
            {
                correctAnswerAmount++;
                buttons.get(i).SetCorrect();
                continue;
            }

            incorrectAnswerAmount++;
            buttons.get(i).SetIncorrect();
        }


        finalPoint = (double) correctAnswerAmount / this.maxQues * 10;
        finalPoint = Math.round(finalPoint * 100.0) / 100.0;

        if (User.getCurrentUser().isOnline()) {
            User.getCurrentUser().setScore(User.getCurrentUser().getScore() + (int) finalPoint);
            User.getCurrentUser().updateScore();
        }
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
        this.ToggleEndGameButton();
        this.ToggleEndReviewButton();
        this.ToggleFinalPointText();
        this.ToggleAnswerResultVbox();
        this.ToggleCheckBoxes();
        this.ClearChoseAnswer();
        this.SetProgressBar(0, this.maxQues);
        this.SwitchBackToStartScreen();
    }

    private void SwitchBackToStartScreen()
    {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().multiChoiceGameStartSC);
    }
    

}
