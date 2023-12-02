package Main.SceneControllers;

import javafx.scene.Parent;

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

    public abstract void update();

}
