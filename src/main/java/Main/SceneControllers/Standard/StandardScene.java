package Main.SceneControllers.Standard;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

/** This class is the standard template for a scene, which include:
 * - Top navigation bar
 * - Drawer menu from the side
 * - Instruction included in StandardScene.fxml*/

/*<!--THIS SCENE IS NOT MEANT TO BE USED DIRECTLY
    This is the standard template to build other scene which includes:
    - Top bar and buttons
    - Drawer menu
    HOW TO USE:
    - Create new scene, copy and paste this file into the newly created FXML scene file
    - Create controller class that extends StandardScene.java
    - Example:
        + Created GameSceneController.java & GameScene.fxml
        + Copy StandardScene.fxml to GameScene.fxml
        + Change fx:controller in GameScene.fxml to GameSceneController.java !-->*/

abstract public class StandardScene {
    /**This part is dedicated to drawer menu.*/
    /**blurPane is a grey transparent pane used to create blur effect when menu is active
     * this function set blurPane to not be visible by default.*/
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
