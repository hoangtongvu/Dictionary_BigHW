package Main.SceneControllers.Game.CreateWord4DirGame;

import Game.CreateWord4DirGame.CreateWord4DirGameCtrl;
import Game.CreateWord4DirGame.CreateWord4DirGameManager;
import Game.GamesCtrl;
import Main.application.App;
import UnsortedScript.NodeAligner;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

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
        this.AlignCenterNodes();
    }

    private void EndGame()
    {

    }

    private void AlignCenterNodes()
    {
        NodeAligner.AlignCenterWidth(this.rootAnchorPane, this.gameGridPane);
        NodeAligner.AlignCenterWidth(this.rootAnchorPane, this.wordText);
        NodeAligner.AlignCenterWidth(this.rootAnchorPane, this.hintText);
        this.AlignCenterGridPane();


    }

    private void AlignCenterGridPane()
    {
        Pair<Double, Double> rootSize = NodeAligner.GetSizeOfNode(this.rootAnchorPane);
        double width = this.gameGridPane.getPrefWidth();
        double height = this.gameGridPane.getPrefHeight();
        this.gameGridPane.setLayoutX((rootSize.getKey() - width) / 2);
        this.gameGridPane.setLayoutY((rootSize.getValue() - height) / 2);
    }

    private void SubEvent()
    {
        CreateWord4DirGameManager gameManager = this.gameCtrl.getGameManager();
        gameManager.onChoiceCharsChangeEvent.AddListener(this::UpdateChoiceTexts);
        gameManager.onCreatingWordChangeEvent.AddListener(this::UpdateWordText);
        gameManager.onFinalPointChangeEvent.AddListener(this::UpdateFinalPointText);
        gameManager.onHintChangeEvent.AddListener(this::UpdateHintText);
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
        this.SetButtonChose(button);
    }

    private void SetButtonChose(Button button)
    {
        this.SetButtonColorGreen(button);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                SetButtonColorDefault(button);
            }
        }, 200);
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
}
