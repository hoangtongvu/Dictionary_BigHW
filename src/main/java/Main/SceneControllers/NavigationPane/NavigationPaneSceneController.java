package Main.SceneControllers.NavigationPane;

import Main.FxmlFileManager;
import Main.SceneControllers.Dictionary.HomeSceneController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class NavigationPaneSceneController implements Initializable
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


    private static NavigationPaneSceneController instance;

    private List<Node> nodes;
    private static List<Node> testNodes = new ArrayList<>(Arrays.asList(new Button("Asdf"), new Button("qwrrqerqr"), new Button("q")));

    private TranslateTransition drawerTranslateTransition;
    private FadeTransition blurPaneFadeTransition;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.drawerTranslateTransition = new TranslateTransition(Duration.seconds(0.5), this.drawerMenu);
        this.blurPaneFadeTransition = new FadeTransition(Duration.seconds(0.5),blurPane);
        //this.nodes = this.navPaneRoot.getChildren();
        this.nodes = new ArrayList<>(this.navPaneRoot.getChildren());
    }


    public static void TestLoad(Pane root)
    {
        System.out.println("testList size before adding: " + testNodes.size());
        root.getChildren().addAll(testNodes);
        System.out.println("testList size after adding: " + testNodes.size());
    }
    public static void LoadNavPane(Pane root) throws IOException
    {
        if (instance == null) instance = LoadInstance();
        System.out.println("size of nodes = " + instance.nodes.size());
        instance.AddNavPaneComponentsToRoot(root);
        instance.nodes.addAll(instance.navPaneRoot.getChildren());
        System.out.println("size of nodes = " + instance.nodes.size());
    }

    public static NavigationPaneSceneController LoadInstance() throws IOException
    {
        String absolutePath = "D:\\Java\\Dictionary_BigHW\\src\\main\\resources\\fxml\\application\\NavigationPaneScene.fxml";
        URL fxmlURL = Paths.get(absolutePath).toUri().toURL();
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        loader.load();

        return loader.getController();
    }

    public void AddNavPaneComponentsToRoot(Pane root)
    {
        List<Node> nodes = navPaneRoot.getChildren();
        root.getChildren().addAll(nodes);
    }


    @FXML
    private void onMenuButton()
    {
        this.drawerTranslateTransition.setByX(235);
        this.drawerTranslateTransition.play();

        blurPane.setVisible(true);

        this.blurPaneFadeTransition.setFromValue(0);
        this.blurPaneFadeTransition.setToValue(1);
        this.blurPaneFadeTransition.play();
    }

    @FXML
    private void onMenuExit()
    {
        this.drawerTranslateTransition.setByX(-235);
        this.drawerTranslateTransition.play();

        this.blurPaneFadeTransition.setFromValue(1);
        this.blurPaneFadeTransition.setToValue(0);
        this.blurPaneFadeTransition.play();

        this.blurPaneFadeTransition.setOnFinished(event -> {
            blurPane.setVisible(false);
            blurPaneFadeTransition.setOnFinished(null);
        });
    }

    private void MoveToScene(Parent newScene)
    {
        this.onMenuExit();
        HomeSceneController.SwitchScene(newScene);
    }

    @FXML
    private void MoveToDictionaryScene()
    {
        this.MoveToScene(FxmlFileManager.getInstance().root);
    }

    @FXML
    private void MoveToGamesScene()
    {
        MoveToScene(FxmlFileManager.getInstance().chooseGameScene);
    }


}
