package Main.SceneControllers.AIChatBot;

import AIChatBot.AIChatBotCtrl;
import AIChatBot.AIChatBotManager;
import AIChatBot.ModelList.ModelListManager;
import AIChatBot.gpt4all.ModelFileChooser;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import Main.application.App;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AIConversationSceneController implements Initializable
{

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private VBox conversationVbox;

    @FXML
    private TextArea userTextArea;

    @FXML
    private ScrollPane messageScrollPane;

    @FXML
    private ComboBox<String> modelComboBox;

    private final AIChatBotCtrl aiChatBotCtrl;

    private final List<MessageBlockSceneController> messageBlocks;

    private boolean isUserTextFieldDisabled = false;
    private final ModelFileChooser modelFileChooser;
    private static final String chooseModelFileComboBoxItem = "Choose model File...";
    private static final String naComboBoxItem = "<N/A>";
    private static final String downloadFromGpt4AllComboBoxItem = "Download model File from Gpt4all...";

    private static final String gpt4AllLink = "https://gpt4all.io";



    public AIConversationSceneController()
    {
        this.aiChatBotCtrl = AIChatBotCtrl.getInstance();
        this.messageBlocks = new ArrayList<>();
        this.modelFileChooser = new ModelFileChooser();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.AddNavPane();
        this.AddTextAreaKeyCombination();
        this.UpdateModelComboBox();
    }

    private void AddNavPane()
    {
        try {
            NavigationPaneSceneController navigationPaneSceneController = NavigationPaneSceneController.LoadInstance();
            navigationPaneSceneController.AddNavPaneComponentsToRoot(this.rootAnchorPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private void UpdateModelComboBox()//note only update on initialize().
    {
        ObservableList<Pair<String, String>> modelNamePathPairs = this.aiChatBotCtrl.getModelListManager().getModelNameAndPaths();
        List<String> items = this.modelComboBox.getItems();
        items.clear();

        items.add(naComboBoxItem);
        modelNamePathPairs.forEach(pair -> items.add(pair.getKey()));
        items.add(chooseModelFileComboBoxItem);
        items.add(downloadFromGpt4AllComboBoxItem);

        this.modelComboBox.getSelectionModel().select(naComboBoxItem);
    }

    @FXML
    private void OnModelComboBoxChoose()
    {
        if (this.modelComboBox.getValue() == null) return;
        SelectionModel<String> selectionModel = this.modelComboBox.getSelectionModel();

        String selectedItem = selectionModel.getSelectedItem();

        switch (selectedItem)
        {
            case naComboBoxItem -> {}
            case chooseModelFileComboBoxItem -> this.OnSelectChooseFileItem();
            case downloadFromGpt4AllComboBoxItem -> this.OnSelectDownloadItem();
            default -> this.OnSelectExistModelItem();
        }

    }

    private void OnSelectChooseFileItem()
    {
        SelectionModel<String> selectionModel = this.modelComboBox.getSelectionModel();
        File file = this.modelFileChooser.GetFileFromFileExplorer();
        if (file == null)
        {
            //todo select previous item instead of NA.
            selectionModel.select(naComboBoxItem);
            return;
        }
        ModelListManager modelListManager = this.aiChatBotCtrl.getModelListManager();

        //if successful adding into list.
        if (modelListManager.AddFileIntoList(file))
        {
            String firstItem = modelListManager.getModelNameAndPaths().get(0).getKey();
            this.modelComboBox.getItems().add(1, firstItem);
            selectionModel.select(1);
        }
        selectionModel.select(ModelListManager.GetFileNameAfterRemoveExtension(file));
    }

    private void OnSelectDownloadItem()
    {
        this.modelComboBox.getSelectionModel().select(naComboBoxItem);
        App.getMyHostServices().showDocument(gpt4AllLink);
    }

    private void OnSelectExistModelItem()
    {
        int index = this.modelComboBox.getSelectionModel().getSelectedIndex();
        List<Pair<String, String>> modelNamePathPairs = this.aiChatBotCtrl.getModelListManager().getModelNameAndPaths();
        Pair<String, String> pair = modelNamePathPairs.get(index - 1);
        this.aiChatBotCtrl.getAiChatBotManager().InitModel(Path.of(pair.getValue()));
    }

    private MessageBlockSceneController CreateMessageBlock()
    {
        Pair<Parent, MessageBlockSceneController> pair = MessageBlockSceneController.CreateInstance();
        this.conversationVbox.getChildren().add(pair.getKey());
        this.messageBlocks.add(pair.getValue());
        this.ScrollToLatestMessage();
        return pair.getValue();
    }

    @FXML
    private void OnUserConfirmInstruction()
    {
        if (this.isUserTextFieldDisabled) return;
        String input = this.userTextArea.getText();
        if (input.isEmpty()) return;
        this.userTextArea.clear();

        if (!this.aiChatBotCtrl.getAiChatBotManager().ModelExist()) return;//todo why intellij suggests safe del this function? why it is not working?


        MessageBlockSceneController userMessageBlock = this.CreateMessageBlock();
        userMessageBlock.setText(input);
        userMessageBlock.SetUserRole();
        userMessageBlock.PlayOnAppearAnimation();


        Task<String> task = this.GetResponseProcessingTask(input);

        MessageBlockSceneController botMessageBlock = this.CreateMessageBlock();
        botMessageBlock.setText("...");
        botMessageBlock.PlayOnAppearAnimation();

        task.messageProperty().addListener((observableValue, s, t1) -> botMessageBlock.setText(t1));
        task.messageProperty().addListener((observableValue, s, t1) -> this.ScrollToLatestMessage());

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

    private void ScrollToLatestMessage()
    {
        this.messageScrollPane.setVvalue(1);
    }


    //todo switch to this scene from another scene.
    //todo using spelling API to spell AI's responses.
    //todo loading model in background. x
    //todo try to word by word generation. x

}
