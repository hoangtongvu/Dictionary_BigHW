package Main.SceneControllers.Dictionary;

import Main.SceneControllers.BaseSceneController;
import Main.SceneControllers.IHasNavPane;
import Main.SceneControllers.Widget.StudyTimerController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class HomeSceneController extends BaseSceneController implements IHasNavPane {
    @FXML
    protected AnchorPane drawerMenu;
    @FXML
    protected AnchorPane root;
    @FXML
    protected TextField timerTextField;
    @FXML
    protected AnchorPane spotifyPlaceHolder;
    @FXML
    AnchorPane timerPlaceHolder;

    /**This part is for side menu*/
    @FXML
    public void initialize() {
        //this.SwitchToLookUpScene(); //dunno why this bug.
        try {
            StudyTimerController.loadInstance().addToParent(timerPlaceHolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

    }
}
