package Main.SceneControllers.Dictionary;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

public class HomeSceneController {
    /**This part is for side menu*/
    @FXML
    public void initialize() {
        blurPane.setVisible(false);
    }

    @FXML
    protected AnchorPane drawerMenu;
    @FXML
    protected Pane blurPane;

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
}
