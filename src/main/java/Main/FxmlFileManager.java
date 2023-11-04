package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class FxmlFileManager
{

    private static FxmlFileManager instance;

    public static FxmlFileManager getInstance()
    {
        if (instance == null) instance = new FxmlFileManager();
        return instance;
    }


    public final Parent root;
    public final Parent homeScene;
    public final Parent multiChoiceWordGameScene;




    private FxmlFileManager()
    {
        try
        {
            this.root = FXMLLoader.load(getClass().getResource("/fxml/application/MainScene.fxml"));
            this.homeScene = FXMLLoader.load(getClass().getResource("/fxml/application/HomeScene.fxml"));
            this.multiChoiceWordGameScene = FXMLLoader.load(getClass().getResource("/fxml/application/GameScene.fxml"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }







}
