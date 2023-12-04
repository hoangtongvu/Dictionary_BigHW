package Main.SceneControllers.BackButton;

import Main.FxmlFileManager;
import Main.SceneControllers.BaseSceneController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class BackButton
{
    @FXML private Button button;

    private static BackButton instance;
    private BaseSceneController parentSC;


    public static BackButton GetInstance()
    {
        if (instance == null) instance = CreateInstance();
        return instance;
    }

    @FXML
    private void BackToPreviousScene()
    {
        BaseSceneController prevSC = this.parentSC.getPrevSC();
        if (prevSC == null) return;
        FxmlFileManager.SwitchBack2PrevScene(prevSC);
    }

    public void setParentSC(BaseSceneController parentSC)
    {
        if (parentSC == this.parentSC) return;
        this.parentSC = parentSC;
        this.ChangeParentPane((Pane) parentSC.getRoot());
    }

    private static BackButton CreateInstance()
    {
        FXMLLoader loader = new FXMLLoader(BackButton.class.getResource("/fxml/application/BackButton.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.getController();
    }

    private void ChangeParentPane(Pane newPane)
    {
        newPane.getChildren().add(this.button);
    }

}
