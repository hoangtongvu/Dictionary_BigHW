package Main.SceneControllers.Settings;

import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingSceneController implements Initializable
{
    @FXML
    private AnchorPane root;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.AddNavPane();
    }

    private void AddNavPane()
    {
        try {
            NavigationPaneSceneController.LoadInstance().AddNavPaneComponentsToRoot(this.root);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
