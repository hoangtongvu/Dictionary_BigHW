package Main.SceneControllers.Dictionary;

import Main.FxmlFileManager;
import Main.application.App;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


    /**This part is for side menu*/
    @FXML
    public void initialize()
    {
        blurPane.setVisible(false);
        //this.SwitchToLookUpScene(); //dunno why this bug.
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
        this.SwitchScene(FxmlFileManager.getInstance().multiChoiceWordGameScene);
    }

    /**Activate drawer menu translateTransition for drawer menu, fadeTransition for blurPane.*/
    @FXML
    protected void onMenuButton() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),drawerMenu);
        translateTransition.setByX(+235);
        translateTransition.play();

        blurPane.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),blurPane);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    /**setOnFinished(lambdaExpression) to wait for the blur animation to finish before set blurPane invisible,
     * otherwise, blurPane will disappear immediately.*/
    @FXML
    protected void onMenuExit() {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.5),drawerMenu);
        translateTransition.setByX(-235);
        translateTransition.play();

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5),blurPane);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);
        fadeTransition.play();

        fadeTransition.setOnFinished(event -> {blurPane.setVisible(false);});
    }


    public void SwitchScene(Parent newScene)
    {
        Stage primaryStage = App.getPrimaryStage();
        primaryStage.getScene().setRoot(newScene);
        primaryStage.show();
    }

    public void SwitchToLookUpScene()
    {
        Parent root = FxmlFileManager.getInstance().root;
        this.SwitchScene(root);
    }

}
