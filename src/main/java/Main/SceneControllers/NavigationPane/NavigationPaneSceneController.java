package Main.SceneControllers.NavigationPane;

import Main.FxmlFileManager;
import Main.SceneControllers.BaseSceneController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static Main.FxmlFileManager.SwitchScene;

public class NavigationPaneSceneController extends BaseSceneController implements Initializable
{

    @FXML
    private AnchorPane navPaneRoot;
    @FXML
    private AnchorPane drawerMenu;
    @FXML
    private Pane blurPane;
    @FXML
    private ImageView menuButton;
    @FXML
    private Button backButton;
    @FXML
    protected AnchorPane timerPlaceHolder;
    @FXML
    protected Button homeButton;
    @FXML
    protected Button dictionaryButton;
    @FXML
    protected Button gameButton;
    @FXML
    protected Button editSceneButton;
    @FXML
    protected Button chatButton;
    @FXML
    protected Button translateButton;
    @FXML
    protected Button settingButton;


    private TranslateTransition drawerTranslateTransition;
    private FadeTransition blurPaneFadeTransition;
    private static List<Node> nodes;

    public NavigationPaneSceneController()
    {
        nodes = new ArrayList<>();
    }

    public void setDisableStatus(boolean status) {
        homeButton.setDisable(status);
        dictionaryButton.setDisable(status);
        gameButton.setDisable(status);
        editSceneButton.setDisable(status);
        chatButton.setDisable(status);
        translateButton.setDisable(status);
        settingButton.setDisable(status);
    }

    //TODO: set all buttons on nav pane to disable until animation is done
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.drawerTranslateTransition = new TranslateTransition(Duration.seconds(0.5), this.drawerMenu);
        this.blurPaneFadeTransition = new FadeTransition(Duration.seconds(0.5),blurPane);
        nodes.addAll(this.navPaneRoot.getChildren());
//        try {
//            StudyTimerController.loadInstance().addToParent(timerPlaceHolder);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        setDisableStatus(true);
    }

    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

    }

    public static void AddNavPaneComponentsToRoot(Parent root)
    {
        ((Pane) root).getChildren().addAll(nodes);
    }


    @FXML
    private void onMenuButton()
    {
        this.drawerTranslateTransition.setOnFinished(event -> {
            setDisableStatus(false);
            drawerTranslateTransition.setOnFinished(null);
        });
        this.drawerTranslateTransition.setByX(drawerMenu.getWidth());
        this.drawerTranslateTransition.play();

        blurPane.setVisible(true);

        this.blurPaneFadeTransition.setFromValue(0);
        this.blurPaneFadeTransition.setToValue(1);
        this.blurPaneFadeTransition.play();
    }

    @FXML
    private void onMenuExit()
    {
        this.drawerTranslateTransition.setByX(-drawerMenu.getWidth());
        this.drawerTranslateTransition.play();
        setDisableStatus(true);
        this.blurPaneFadeTransition.setFromValue(1);
        this.blurPaneFadeTransition.setToValue(0);
        this.blurPaneFadeTransition.play();

        this.blurPaneFadeTransition.setOnFinished(event -> {
            blurPane.setVisible(false);
            blurPaneFadeTransition.setOnFinished(null);
        });
    }

    private void MoveToScene(BaseSceneController newScene)
    {
        this.onMenuExit();
        SwitchScene(newScene);
    }

    @FXML
    private void MoveToHomeScene()
    {
        this.MoveToScene(FxmlFileManager.getInstance().homeSC);
    }

    @FXML
    private void MoveToDictionaryScene()
    {
        this.MoveToScene(FxmlFileManager.getInstance().dictionarySC);
    }

    @FXML
    private void MoveToGamesScene()
    {
        MoveToScene(FxmlFileManager.getInstance().chooseGameSC);
    }

    @FXML
    private void MoveToDictionaryEditorScene()
    {
        MoveToScene(FxmlFileManager.getInstance().editWordSceneController);
    }

    @FXML
    private void MoveToAIChatBotScene()
    {
        MoveToScene(FxmlFileManager.getInstance().aiSC);
    }

    @FXML
    private void MoveToTranslateScene()
    {
        MoveToScene(FxmlFileManager.getInstance().translateSC);
    }

    @FXML
    private void MoveToSettingsScene()
    {
        MoveToScene(FxmlFileManager.getInstance().settingSC);
    }
}
