package Main.SceneControllers.Game.CreateWord4DirGame;

import Game.CreateWord4DirGame.CreateWord4DirGameCtrl;
import Game.CreateWord4DirGame.CreateWord4DirGameManager;
import Game.GamesCtrl;
import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import Main.application.App;
import UnsortedScript.NodeAligner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

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
    private Text wordText;

    @FXML
    private Text finalPointText;

    private CreateWord4DirGameCtrl gameCtrl;


    public GameSceneController()
    {
        System.out.println("HI");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.gameCtrl = GamesCtrl.getInstance().getCreateWord4DirGameCtrl();
        this.SubEvent();
    }

    public void StartGame()
    {
        this.gameCtrl.getGameManager().Start();
        this.SetKeyBoardEvent();
        //this.AlignCenterNodes();

    }

    private void EndGame()
    {

    }

//    private void AlignCenterNodes()
//    {
//        NodeAligner.AlignCenterWidth(this.rootAnchorPane, this.gameGridPane);
//        NodeAligner.AlignCenterWidth(this.rootAnchorPane, this.wordText);
//        NodeAligner.AlignCenterWidth(this.rootAnchorPane, this.hintText);
//        this.AlignCenterGridPane();
//
//
//    }

//    private void AlignCenterGridPane()
//    {
//        Pair<Double, Double> rootSize = NodeAligner.GetSizeOfNode(this.rootAnchorPane);
//        double width = this.gameGridPane.getPrefWidth();
//        double height = this.gameGridPane.getPrefHeight();
//        this.gameGridPane.setLayoutX((rootSize.getKey() - width) / 2);
//        this.gameGridPane.setLayoutY((rootSize.getValue() - height) / 2);
//    }

    private void SubEvent()
    {
        CreateWord4DirGameManager gameManager = this.gameCtrl.getGameManager();
        gameManager.onChoiceCharsChangeEvent.AddListener(this::UpdateChoiceTexts);
        gameManager.onCreatingWordChangeEvent.AddListener(this::UpdateWordText);
        gameManager.onFinalPointChangeEvent.AddListener(this::UpdateFinalPointText);
        gameManager.onHintChangeEvent.AddListener(this::UpdateHintText);
        gameManager.onGameEndEvent.AddListener(this::ShowEndGameDialog);
        gameManager.onChooseCharEvent.AddListener((isCorrect, index) -> {
            Button button = GetButtonIndex(index);
            SetButtonChose(button, isCorrect);
        });
    }

    private void SetKeyBoardEvent()
    {
        Scene scene = App.getPrimaryStage().getScene();
        scene.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent ->
        {
            KeyCode keyCode = keyEvent.getCode();
            switch (keyCode)
            {
                case UP -> FireButton(upButton);
                case RIGHT -> FireButton(rightButton);
                case DOWN -> FireButton(downButton);
                case LEFT -> FireButton(leftButton);
            }
        });
    }

    private void FireButton(Button button)
    {
        button.fire();
    }

    private void SetButtonChose(Button button, boolean isCorrect)
    {

        KeyFrame startKeyFrame = new KeyFrame(Duration.seconds(0), ev -> {
            if (isCorrect) SetButtonColorGreen(button);
            else SetButtonColorRed(button);
        });
        KeyFrame endKeyFrame = new KeyFrame(Duration.seconds(0.1), ev -> SetButtonColorDefault(button));
        Timeline timeline = new Timeline(startKeyFrame, endKeyFrame);
        timeline.setCycleCount(1);
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
        this.wordText.setText(text);
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
        System.out.println("show dialog");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Game End");
        //alert.setHeaderText("header");
        alert.setContentText("Your point is: " + finalPoint);

        ButtonType buttonTypeYes = new ButtonType("YES", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("NO", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);
        alert.setOnHidden(e -> MoveBackToStartScreen());
        alert.show();

    }

    private void MoveBackToStartScreen()
    {
        HomeSceneController.SwitchScene(FxmlFileManager.getInstance().createWord4DirGameStartScene);
    }

}
