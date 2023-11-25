package AIChatBot.gpt4all;

import AIChatBot.AIChatBotManager;
import Main.application.App;
import javafx.stage.FileChooser;

import java.io.File;

public class ModelFileChooser
{

    public void ChooseModel(AIChatBotManager aiChatBotManager)
    {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose Model File");

        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Model files (*.gguf)", "*.gguf");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(App.getPrimaryStage());
        if (file == null) return;
        aiChatBotManager.InitModel(file.toPath());
    }

}
