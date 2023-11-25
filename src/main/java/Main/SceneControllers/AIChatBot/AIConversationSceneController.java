package Main.SceneControllers.AIChatBot;

import AIChatBot.AIChatBotCtrl;
import AIChatBot.AIChatBotManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
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
    private TextArea userTextArea;

    private AIChatBotCtrl aiChatBotCtrl;

    private List<MessageBlockSceneController> messageBlocks;

    private boolean isUserTextFieldDisabled = false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.aiChatBotCtrl = AIChatBotCtrl.getInstance();
        this.messageBlocks = new ArrayList<>();

        this.AddTextAreaKeyCombination();

    }

    private void AddTextAreaKeyCombination()
    {
        this.userTextArea.addEventFilter(KeyEvent.KEY_PRESSED, keyEvent ->
        {
            KeyCode keyCode = keyEvent.getCode();
            if(keyEvent.isShiftDown() && keyCode == KeyCode.ENTER)
            {
                userTextArea.appendText("\n");
                return;
            }
            if(keyCode == KeyCode.ENTER)
            {
                OnUserConfirmInstruction();
                keyEvent.consume();
            }

        });
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
        if (this.isUserTextFieldDisabled) return;
        String input = this.userTextArea.getText();
        if (input.isEmpty()) return;
        this.userTextArea.clear();


        MessageBlockSceneController userMessageBlock = this.CreateMessageBlock();
        userMessageBlock.setText(input);
        userMessageBlock.SetUserRole();


        Task<String> task = this.GetResponseProcessingTask(input);

        MessageBlockSceneController botMessageBlock = this.CreateMessageBlock();
        botMessageBlock.setText("...");

        task.messageProperty().addListener((observableValue, s, t1) -> botMessageBlock.setText(t1));

        Thread processingResponseThread = new Thread(task);

        processingResponseThread.setDaemon(true);
        processingResponseThread.start();

    }

    private Task<String> GetResponseProcessingTask(String input)
    {
        AIChatBotManager aiChatBotManager = this.aiChatBotCtrl.getAiChatBotManager();
        
        return new Task<>() {
            @Override
            protected String call() throws InterruptedException {

                //block textField from being used while waiting for response.
                ToggleTextField();

                //play waiting animation.
                double delaySecond = 0.2;
                KeyFrame oneDotFrame = new KeyFrame(Duration.seconds(1 * delaySecond), e -> updateMessage("."));

                KeyFrame twoDotFrame = new KeyFrame(Duration.seconds(2 * delaySecond), e -> updateMessage(".."));

                KeyFrame threeDotFrame = new KeyFrame(Duration.seconds(3 * delaySecond), e -> updateMessage("..."));
                Timeline timeline = new Timeline(oneDotFrame, twoDotFrame, threeDotFrame);
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();

                String response = aiChatBotManager.Chat(input);

                //stop animation on get response.
                timeline.stop();

                for (int i = 1; i < response.length(); i = i + 2)
                {
                    updateMessage(response.substring(0, i));
                    Thread.sleep(50);
                }

                updateMessage(response);
                ToggleTextField();
                return response;
            }
        };

    }

    private void ToggleTextField()
    {
        this.isUserTextFieldDisabled = !this.isUserTextFieldDisabled;
    }

    //todo using spelling API to spell Bot's responses.
    //todo try to word by word generation.
    //todo large language model downloading and choosing.
    //todo loading model in background.
    //todo scroll to latest message

}
