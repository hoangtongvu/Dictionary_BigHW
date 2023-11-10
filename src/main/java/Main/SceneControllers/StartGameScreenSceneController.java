package Main.SceneControllers;

import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartGameScreenSceneController
{

    @FXML
    private TextField maxTimeTextField;

    @FXML
    private TextField numOfQuesTextField;

    @FXML
    private Button startGameButton;

    @FXML
    private void MoveToGameScene()
    {
        int maxQues;
        int maxTimeSecond;
        try
        {
            maxQues = Integer.parseInt(numOfQuesTextField.getText());
            maxTimeSecond = Integer.parseInt(maxTimeTextField.getText());
        }
        catch (NumberFormatException e)
        {
            return;
        }

        GameSceneController gameSceneController = FxmlFileManager.getInstance().multiChoiceGameSceneController;


        gameSceneController.setMaxQues(maxQues);
        gameSceneController.getTimerManager().getCustomTimer().setMaxTimeSecond(maxTimeSecond);

        gameSceneController.StartGame();
        HomeSceneController.SwitchScene(FxmlFileManager.getInstance().multiChoiceWordGameScene);
    }

}
