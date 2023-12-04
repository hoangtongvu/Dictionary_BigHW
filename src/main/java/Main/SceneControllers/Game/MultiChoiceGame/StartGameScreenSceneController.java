package Main.SceneControllers.Game.MultiChoiceGame;

import Interfaces.IHasBackButton;
import Main.FxmlFileManager;
import Main.SceneControllers.BaseSceneController;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class StartGameScreenSceneController extends BaseSceneController implements IHasBackButton
{

    @FXML
    private TextField maxTimeTextField;

    @FXML
    private TextField numOfQuesTextField;

    @FXML
    private Button startGameButton;


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

    @FXML
    private void MoveToGameScene()
    {
        int maxQues;
        int maxTimeSecond;
        try {
            maxQues = Integer.parseInt(numOfQuesTextField.getText());
            maxTimeSecond = Integer.parseInt(maxTimeTextField.getText());
        }
        catch (NumberFormatException e) {
            return;
        }

        GameSceneController gameSceneController = FxmlFileManager.getInstance().multiChoiceGameSC;
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().multiChoiceGameSC);
        gameSceneController.StartGame(maxQues, maxTimeSecond);
    }


}
