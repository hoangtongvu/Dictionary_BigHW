package Main.SceneControllers.AIChatBot;

import AIChatBot.AIChatBotCtrl;
import AIChatBot.AIChatBotManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AIConversationSceneController implements Initializable
{

    @FXML
    private VBox conversationVbox;

    @FXML
    private TextField userTextField;

    private AIChatBotCtrl aiChatBotCtrl;

    private List<MessageBlockSceneController> messageBlocks;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.aiChatBotCtrl = AIChatBotCtrl.getInstance();
        this.messageBlocks = new ArrayList<>();
    }

    private MessageBlockSceneController CreateMessageBlock()
    {
        Pair<Parent, MessageBlockSceneController> pair = MessageBlockSceneController.CreateInstance();
        this.conversationVbox.getChildren().add(pair.getKey());
        this.messageBlocks.add(pair.getValue());

        return pair.getValue();
    }

    @FXML
    private void OnUserConfirmInstruction()
    {
        String input = this.userTextField.getText();
        if (input.isEmpty()) return;
        this.userTextField.clear();


        MessageBlockSceneController userMessageBlock = this.CreateMessageBlock();
        userMessageBlock.setText(input);
        userMessageBlock.SetUserRole();

        AIChatBotManager aiChatBotManager = this.aiChatBotCtrl.getAiChatBotManager();
        String response = aiChatBotManager.Chat(input);


        MessageBlockSceneController botMessageBlock = this.CreateMessageBlock();
        botMessageBlock.setText(response);

    }

}
