package Main.SceneControllers.Dictionary;

import Main.FxmlFileManager;
import Main.SceneControllers.Widget.StudyTimerController;
import Main.application.App;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.util.Objects;

public class HomeSceneController {
    @FXML
    protected AnchorPane drawerMenu;

    @FXML
    protected Pane blurPane;

    @FXML
    protected TextField timerTextField;

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
    }

    /**Switching scene*/
    @FXML
    public void onDictionaryButton(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/application/DictionaryScene.fxml")));
//        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//        stage.getScene().setRoot(root);
//        stage.show();
        this.SwitchToLookUpScene();
    }

    @FXML
    public void onGameButton(ActionEvent event) throws IOException
    {
        this.SwitchScene(FxmlFileManager.getInstance().chooseGameScene);
    }

    @FXML
    public void onTranslateButton(ActionEvent event) throws IOException {
        this.SwitchScene(FxmlFileManager.getInstance().translateScene);

    }

    public static void SwitchScene(Parent newScene) {
        Stage primaryStage = App.getPrimaryStage();
        primaryStage.getScene().setRoot(newScene);
        primaryStage.show();
    }

    public void SwitchToLookUpScene() {
        Parent root = FxmlFileManager.getInstance().root;
        this.SwitchScene(root);
    }
}
