package Main.SceneControllers.Dictionary;

import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import Main.SceneControllers.Widget.StudyTimerController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;


public class HomeSceneController {
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

        try {
            NavigationPaneSceneController.LoadInstance().AddNavPaneComponentsToRoot(this.root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
