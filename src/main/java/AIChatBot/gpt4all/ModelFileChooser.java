package AIChatBot.gpt4all;

import AIChatBot.AIChatBotManager;
import Main.application.App;
import javafx.stage.FileChooser;

import java.io.File;

public class ModelFileChooser
{
    
    public File GetFileFromFileExplorer()
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Model File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Model files (*.gguf)", "*.gguf");
        fileChooser.getExtensionFilters().add(extFilter);
        return fileChooser.showOpenDialog(App.getPrimaryStage());
    }

}
