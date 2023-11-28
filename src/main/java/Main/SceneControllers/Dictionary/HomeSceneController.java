package Main.SceneControllers.Dictionary;

import Main.FxmlFileManager;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import Main.SceneControllers.Widget.StudyTimerController;
import Main.application.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

import static Main.FxmlFileManager.SwitchScene;

public class HomeSceneController {
    @FXML
    protected AnchorPane drawerMenu;

    @FXML
    protected Pane blurPane;
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
        blurPane.setVisible(false);
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

    /**Switching scene*/
    @FXML
    public void onDictionaryButton() throws IOException {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().dictionaryScene);
    }

    @FXML
    public void onGameButton(ActionEvent event) throws IOException
    {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().chooseGameScene);
    }

    @FXML
    public void onTranslateButton(ActionEvent event) throws IOException {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().translateScene);

    }
}
