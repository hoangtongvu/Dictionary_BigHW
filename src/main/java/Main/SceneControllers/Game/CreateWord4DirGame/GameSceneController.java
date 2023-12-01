package Main.SceneControllers.Game.CreateWord4DirGame;

import Game.CreateWord4DirGame.CreateWord4DirGameCtrl;
import Game.CreateWord4DirGame.CreateWord4DirGameManager;
import Game.CreateWord4DirGame.UI.OnFinishWordAnimator;
import Game.GamesCtrl;
import Main.FxmlFileManager;
import Main.SceneControllers.BaseSceneController;
import Main.application.App;
import User.User;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.css.SizeUnits;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameSceneController extends BaseSceneController implements Initializable
{

    @FXML
    private GridPane gameGridPane;

    @FXML
    private Button downButton;

    @FXML
    private Text downText;

    @FXML
    private Text hintText;

    @FXML
    private Button leftButton;

    @FXML
    private Text leftText;

    @FXML
    private Button rightButton;

    @FXML
    private Text rightText;

    @FXML
    private Button upButton;

    @FXML
    private Text upText;

    @FXML
    private Text finalPointText;

    @FXML
    private HBox wordHbox;

    @FXML
    private VBox endGameVbox;

    @FXML
    private Text endGamePointText;

    @FXML
    private Text timerText;

    @FXML
    private Button continueButton;

    @FXML
    private Pane blurPane;

    private CreateWord4DirGameCtrl gameCtrl;

    private EventHandler<KeyEvent> keyPressedEventHandler;

    private final double fontSize = 30;
    private final double labelPrefWidth = 30;

    private FadeTransition blurPaneFadeTransition;

    private final List<Label> wordLabels;
    private final OnFinishWordAnimator onFinishWordAnimator;



    public GameSceneController()
    {
        this.wordLabels = new ArrayList<>();
        this.onFinishWordAnimator = new OnFinishWordAnimator(this.wordLabels);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.gameCtrl = GamesCtrl.getInstance().getCreateWord4DirGameCtrl();
        this.SubEvent();

        this.keyPressedEventHandler = keyEvent ->
        {
            KeyCode keyCode = keyEvent.getCode();
            switch (keyCode)
            {
                case UP -> FireButton(upButton);
                case RIGHT -> FireButton(rightButton);
                case DOWN -> FireButton(downButton);
                case LEFT -> FireButton(leftButton);
            }
        };

        this.continueButton.addEventHandler(ActionEvent.ACTION, e -> {
            this.ToggleEndGameVbox();
            this.BlurPaneDisappear();
            this.MoveBackToStartScreen();
        });

        this.blurPaneFadeTransition = new FadeTransition(Duration.seconds(1), blurPane);
    }

    @Override
    public void StartShow()
    {
        //do some animation on show.
    }

    @Override
    public void EndShow()
    {
        //do some clean up things.
    }

    public void StartGame(int blockCount, int timeLimitSecond)
    {
        this.gameCtrl.getGameManager().Start(blockCount, timeLimitSecond);
        this.AddKeyBoardEvent();
    }

    private void EndGame()
    {
        this.RemoveKeyBoardEvent();
    }

    private void SubEvent()
    {
        CreateWord4DirGameManager gameManager = this.gameCtrl.getGameManager();
        gameManager.onChoiceCharsChangeEvent.AddListener(this::UpdateChoiceTexts);
        gameManager.onCreatingWordChangeEvent.AddListener(this::UpdateWordText);
        gameManager.onFinalPointChangeEvent.AddListener(this::UpdateFinalPointText);
        gameManager.onHintChangeEvent.AddListener(this::UpdateHintText);
        gameManager.onGameEndEvent.AddListener(this::ShowEndGameDialog);
        gameManager.onGameEndEvent.AddListener(e -> EndGame());
        gameManager.onGameEndEvent.AddListener(e -> BlurPaneAppear());
        gameManager.onFinishWord.AddListener(this.onFinishWordAnimator::PlayAnimation);
        gameManager.customTimer.onTickEvent.AddListener(this::UpdateTimerUI);
        gameManager.onToggleTimerEvent.AddListener(this::ToggleTimerText);
        gameManager.onChooseCharEvent.AddListener((isCorrect, index) -> {
            Button button = GetButtonIndex(index);
            SetButtonChose(button, isCorrect);
        });
    }

    private void AddKeyBoardEvent()
    {
        Scene scene = App.getPrimaryStage().getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
    }

    private void RemoveKeyBoardEvent()
    {
        Scene scene = App.getPrimaryStage().getScene();
        scene.removeEventFilter(KeyEvent.KEY_PRESSED, this.keyPressedEventHandler);
    }

    private void FireButton(Button button)
    {
        button.fire();
    }

    private void SetButtonChose(Button button, boolean isCorrect)
    {
        ColorAdjust colorAdjust = (ColorAdjust) button.getEffect();
        ButtonColorTransition(colorAdjust, isCorrect);
    }

    private void ButtonColorTransition(ColorAdjust colorAdjust, boolean isCorrect)
    {

        KeyValue startSaturationValue = new KeyValue(colorAdjust.saturationProperty(), 0);
        KeyValue endSaturationValue = new KeyValue(colorAdjust.saturationProperty(), 1);

        KeyValue startHueValue = new KeyValue(colorAdjust.hueProperty(), 0);
        KeyValue endHueValue = new KeyValue(colorAdjust.hueProperty(), isCorrect? 0.5 : 0);

        KeyFrame startKeyFrame = new KeyFrame(Duration.seconds(0), startSaturationValue, startHueValue);
        KeyFrame endKeyFrame = new KeyFrame(Duration.seconds(0.3), endSaturationValue, endHueValue);


        Timeline timeline = new Timeline(startKeyFrame, endKeyFrame);
        timeline.setAutoReverse(true);
        timeline.setCycleCount(2);
        timeline.play();
    }

    @FXML
    private void ChooseUpChar()
    {
        this.ChooseChar(0);
    }

    @FXML
    private void ChooseRightChar()
    {
        this.ChooseChar(1);
    }

    @FXML
    private void ChooseDownChar()
    {
        this.ChooseChar(2);
    }

    @FXML
    private void ChooseLeftChar()
    {
        this.ChooseChar(3);
    }

    private void ChooseChar(int index)
    {
        CreateWord4DirGameManager gameManager = this.gameCtrl.getGameManager();
        gameManager.ChooseChar(index);
    }

    private void UpdateWordText(String text)
    {
        this.wordHbox.getChildren().clear();
        this.wordLabels.clear();
        Font font = new Font(this.fontSize);
        for (int i = 0; i < text.length(); i++)
        {
            Label label = new Label(text.charAt(i) + "");
            label.fontProperty().setValue(font);
            label.setPrefWidth(this.labelPrefWidth);
            label.setAlignment(Pos.CENTER);
            this.wordHbox.getChildren().add(label);
            this.wordLabels.add(label);
        }

        int underScorePos = text.indexOf("_");
        if (underScorePos <= 0) return;

        DoubleProperty opacityProperty = this.wordHbox.getChildren().get(underScorePos - 1).opacityProperty();
        KeyValue startKeyValue = new KeyValue(opacityProperty, 0);
        KeyValue endKeyValue = new KeyValue(opacityProperty, 1);

        KeyFrame startKf = new KeyFrame(Duration.seconds(0), startKeyValue);
        KeyFrame endKf = new KeyFrame(Duration.seconds(0.7), endKeyValue);

        Timeline timeline = new Timeline(startKf, endKf);
        timeline.setCycleCount(1);
        timeline.play();
    }

    private void UpdateChoiceTexts(Character[] characters)
    {
        this.upText.setText(characters[0] + "");
        this.rightText.setText(characters[1] + "");
        this.downText.setText(characters[2] + "");
        this.leftText.setText(characters[3] + "");
    }

    private void UpdateFinalPointText(int finalPoint)
    {
        this.finalPointText.setText("Point: " + finalPoint);
    }

    private void UpdateHintText(String text)
    {
        this.hintText.setText(text);
    }

    private Button GetButtonIndex(int index)
    {
        Button button = null;
        switch (index)
        {
            case 0 -> button = this.upButton;
            case 1 -> button = this.rightButton;
            case 2 -> button = this.downButton;
            case 3 -> button = this.leftButton;
        }
        return button;
    }

    private void ShowEndGameDialog(int finalPoint)
    {
        if (User.getCurrentUser().isOnline()) {
            int tempPoint = User.getCurrentUser().getScore();
            tempPoint += finalPoint;
            if (tempPoint <= 0) {
                tempPoint = 0;
            }
            User.getCurrentUser().setScore(tempPoint);
            User.getCurrentUser().updateScore();
        }
        this.ToggleEndGameVbox();
        this.endGamePointText.setText("Your point is " + finalPoint + ".");
    }

    private void ToggleEndGameVbox()
    {
        this.endGameVbox.setDisable(!this.endGameVbox.isDisabled());
        this.endGameVbox.setVisible(!this.endGameVbox.isVisible());
    }

    private void ToggleBlurPane(double fromValue, double toValue)
    {
        this.blurPaneFadeTransition.setFromValue(fromValue);
        this.blurPaneFadeTransition.setFromValue(toValue);
        this.blurPaneFadeTransition.play();
    }

    private void BlurPaneAppear()
    {
        this.blurPane.setVisible(true);
        //this.ToggleBlurPane(0, 1);
        this.blurPaneFadeTransition.setFromValue(0);
        this.blurPaneFadeTransition.setFromValue(1);
        this.blurPaneFadeTransition.play();
    }

    private void BlurPaneDisappear()
    {
        this.blurPaneFadeTransition.setOnFinished(event -> {
            this.blurPane.setVisible(false);
            this.blurPaneFadeTransition.setOnFinished(null);
        });
        this.ToggleBlurPane(1, 0);
    }

    private void MoveBackToStartScreen()
    {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().createWord4DirStartSC);
    }

    private void ToggleTimerText(boolean useTimer)
    {
        this.timerText.setVisible(useTimer);
    }

    private void UpdateTimerUI(int second, int maxTimeSecond)
    {
        this.timerText.setText("Time left: " + (maxTimeSecond - second));
    }


}
