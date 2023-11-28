package Main.SceneControllers.Game.CreateWord4DirGame;

import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class StartGameScreenSceneController
{

    @FXML
    private TextField blockCountTextField;

    @FXML
    private HBox timeLimitHbox;

    @FXML
    private TextField timeLimitTextField;

    @FXML
    private CheckBox useTimerCheckBox;

    @FXML
    private void MoveToGameScene()
    {
        if (this.blockCountTextField.getText().isEmpty()) return;
        int blockCount;
        int timeLimitSecond;

        try {
            blockCount = Integer.parseInt(this.blockCountTextField.getText());
            timeLimitSecond = this.useTimerCheckBox.isSelected()? Integer.parseInt(this.timeLimitTextField.getText()) : -1;
        } catch (NumberFormatException e) {
            return;
        }

        GameSceneController gameSceneController = FxmlFileManager.getInstance().createWord4DirGameSceneController;

        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().createWord4DirGameScene);

        gameSceneController.StartGame(blockCount, timeLimitSecond);
    }

    @FXML
    private void ToggleTimeLimitHBox()
    {
        this.timeLimitHbox.setVisible(!this.timeLimitHbox.isVisible());
    }
    //todo add Timer.
}
