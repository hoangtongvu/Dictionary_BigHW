package Main.SceneControllers;

import javafx.scene.Parent;

public abstract class BaseSceneController
{
    private Parent root;
    private BaseSceneController prevSC;


    public void setRoot(Parent root) {
        this.root = root;
    }

    public Parent getRoot() {
        return root;
    }

    public void setPrevSC(BaseSceneController prevSC) {
        this.prevSC = prevSC;
    }

    public BaseSceneController getPrevSC() {
        return prevSC;
    }

    public abstract void StartShow();

    public abstract void EndShow();

    public abstract void update();

}
