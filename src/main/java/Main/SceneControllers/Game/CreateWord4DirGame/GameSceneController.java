package Main.SceneControllers.Game.CreateWord4DirGame;

import Game.CreateWord4DirGame.CreateWord4DirGameCtrl;
import Game.CreateWord4DirGame.CreateWord4DirGameManager;
import Game.GamesCtrl;
import Logger.LoggersCtrl;
import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import Main.application.App;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
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
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameSceneController implements Initializable
{

    @FXML
    private AnchorPane rootAnchorPane;

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
    private Button continueButton;

    private CreateWord4DirGameCtrl gameCtrl;

    private EventHandler<KeyEvent> keyPressedEventHandler;

    private final double fontSize = 30;
    private final double labelPrefWidth = 30;



    public GameSceneController()
    {
        System.out.println("HI");
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
            this.MoveBackToStartScreen();
        });
    }

    public void StartGame()
    {
        this.gameCtrl.getGameManager().Start();
        this.AddKeyBoardEvent();
        //this.AlignCenterNodes();

    }

    private void EndGame()
    {
        this.RemoveKeyBoardEvent();
    }

    private void SubEvent()
    {
        LoggersCtrl.systemLogger.Log("Sub Event.");
        CreateWord4DirGameManager gameManager = this.gameCtrl.getGameManager();
        gameManager.onChoiceCharsChangeEvent.AddListener(this::UpdateChoiceTexts);
        gameManager.onCreatingWordChangeEvent.AddListener(this::UpdateWordText);
        gameManager.onFinalPointChangeEvent.AddListener(this::UpdateFinalPointText);
        gameManager.onHintChangeEvent.AddListener(this::UpdateHintText);
        gameManager.onGameEndEvent.AddListener(this::ShowEndGameDialog);
        gameManager.onGameEndEvent.AddListener(e -> EndGame());
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

    private void SetButtonColor(Button button, double brightness, double contrast, double hue, double saturation) {
        ColorAdjust colorAdjust = (ColorAdjust) button.getEffect();
        colorAdjust.setBrightness(brightness);
        colorAdjust.setContrast(contrast);
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
    }

    private void SetButtonColorGreen(Button button) {
        this.SetButtonColor(button, 0, 0, 0.5, 1);
    }

    private void SetButtonColorRed(Button button) {
        this.SetButtonColor(button, 0, 0, 0, 1);
    }

    private void SetButtonColorDefault(Button button) {
        this.SetButtonColor(button, 0, 0, 0, 0);
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
        Font font = new Font(this.fontSize);
        for (int i = 0; i < text.length(); i++)
        {
            Label label = new Label(text.charAt(i) + "");
            label.fontProperty().setValue(font);
            label.setPrefWidth(this.labelPrefWidth);
            label.setAlignment(Pos.CENTER);
            this.wordHbox.getChildren().add(label);
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
        this.endGameVbox.setBackground(Background.fill(Paint.valueOf("GREY")));
        this.ToggleEndGameVbox();
        this.endGamePointText.setText("Your point is " + finalPoint + ".");
    }

    private void ToggleEndGameVbox()
    {
        this.endGameVbox.setDisable(!this.endGameVbox.isDisabled());
        this.endGameVbox.setVisible(!this.endGameVbox.isVisible());
    }

    private void MoveBackToStartScreen()
    {
        HomeSceneController.SwitchScene(FxmlFileManager.getInstance().createWord4DirGameStartScene);
    }

}
