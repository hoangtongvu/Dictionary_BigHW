package Main.SceneControllers.AIChatBot;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageBlockSceneController implements Initializable
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
    private boolean isUserMessage = false;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
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
        this.isUserMessage = true;
    }

    private void SwapAvatarAndMessagePosition()
    {
        this.rootHbox.getChildren().setAll(this.messageOnlyHBox, this.avatarCircle);
        this.rootHbox.setAlignment(Pos.TOP_RIGHT);
    }

    public void PlayOnAppearAnimation()
    {
        this.PlayScaleAnimation();
        this.PlayTranslateAnimation();
    }

    private void PlayScaleAnimation()
    {
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(0.3), this.messageOnlyHBox);

        double startValue = 0.2;
        double topValue = 1.3;
        double finalValue = 1;

        scaleTransition.setInterpolator(Interpolator.EASE_OUT);
        scaleTransition.setFromX(startValue);
        scaleTransition.setFromY(startValue);
        scaleTransition.setToX(topValue);
        scaleTransition.setToY(topValue);


        ScaleTransition scaleTransition1 = new ScaleTransition(Duration.seconds(0.1), this.messageOnlyHBox);
        scaleTransition1.setInterpolator(Interpolator.EASE_BOTH);
        scaleTransition1.setFromX(topValue);
        scaleTransition1.setFromY(topValue);
        scaleTransition1.setToX(finalValue);
        scaleTransition1.setToY(finalValue);

        scaleTransition.setOnFinished(e -> scaleTransition1.play());


        scaleTransition.play();
    }

    private void PlayTranslateAnimation()
    {
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(0.3), this.messageOnlyHBox);

        double x = -50;
        if (this.isUserMessage) x *= -1;
        translateTransition.setInterpolator(Interpolator.EASE_OUT);
        translateTransition.setFromX(x);
        translateTransition.setToX(-x / 2);

        TranslateTransition translateTransition1 = new TranslateTransition(Duration.seconds(0.1), this.messageOnlyHBox);
        translateTransition1.setInterpolator(Interpolator.EASE_BOTH);
        translateTransition1.setFromX(-x / 2);
        translateTransition1.setToX(0);

        translateTransition.setOnFinished(e -> translateTransition1.play());

        translateTransition.play();

    }


}
