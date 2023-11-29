package Main.SceneControllers;

import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

import java.io.IOException;

public abstract class BaseSceneController
{
    private Parent root;


    public void setRoot(Parent root) {
        this.root = root;
    }

    public Parent getRoot() {
        return root;
    }

    public abstract void StartShow();
    public abstract void EndShow();

    protected void AddNavPane()
    {
        try {
            NavigationPaneSceneController navigationPaneSceneController = NavigationPaneSceneController.LoadInstance();
            navigationPaneSceneController.AddNavPaneComponentsToRoot((Pane) this.root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
