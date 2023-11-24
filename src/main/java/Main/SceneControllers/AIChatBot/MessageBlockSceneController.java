package Main.SceneControllers.AIChatBot;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.io.IOException;

public class MessageBlockSceneController
{

    @FXML
    private HBox rootHbox;

    @FXML
    private Circle avatarCircle;

    @FXML
    private Text messageText;

    @FXML
    private HBox messageOnlyHBox;


    private final String radiusStyle = "-fx-background-radius: 15;";
    private final double maxWrappingWidth = 500;

    public Circle getAvatarCircle() {
        return avatarCircle;
    }

    public void setText(String content) {
        this.messageText.setWrappingWidth(0);
        this.messageText.setText(content);
        double width = this.messageText.getBoundsInLocal().getWidth();
        if (width >= this.maxWrappingWidth)
            this.messageText.setWrappingWidth(this.maxWrappingWidth);
    }

    public static Pair<Parent, MessageBlockSceneController> CreateInstance()
    {
        FXMLLoader loader = new FXMLLoader(MessageBlockSceneController.class.getResource("/fxml/AIChatBot/MessageBlock.fxml"));
        MessageBlockSceneController messageBlockSceneController;
        Parent parent;
        try {
            parent = loader.load();
            messageBlockSceneController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return new Pair<>(parent, messageBlockSceneController);
    }

    public void SetUserRole()
    {
        String backgroundColorStyle = "-fx-background-color: #3d5c95;";
        this.messageOnlyHBox.setStyle(backgroundColorStyle + this.radiusStyle);// #dfe3ee
        this.SwapAvatarAndMessagePosition();
    }

    private void SwapAvatarAndMessagePosition()
    {
        this.rootHbox.getChildren().setAll(this.messageOnlyHBox, this.avatarCircle);
        this.rootHbox.setAlignment(Pos.TOP_RIGHT);
    }

}
