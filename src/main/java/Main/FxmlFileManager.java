package Main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

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




    private FxmlFileManager()
    {
        try
        {
            this.root = FXMLLoader.load(getClass().getResource("/fxml/application/MainScene.fxml"));
            this.homeScene = FXMLLoader.load(getClass().getResource("/fxml/application/HomeScene.fxml"));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }







}
