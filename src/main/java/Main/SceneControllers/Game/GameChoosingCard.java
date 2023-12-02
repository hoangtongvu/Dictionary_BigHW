package Main.SceneControllers.Game;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameChoosingCard implements Initializable
{

    @FXML private Button rootButton;
    @FXML private ImageView trailerImageView;

    private boolean isDisabled = false;
    private CardSide cardSide;
    private Duration animationDuration;
    private Image defaultTrailerGif;


    public CardSide getCardSide() {
        return cardSide;
    }



    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.cardSide = CardSide.Right;
        this.animationDuration = Duration.seconds(0.4);
        this.defaultTrailerGif = new Image(getClass().getResource("/gif/GameTrailers/DLAN.gif").toExternalForm());
    }

    public static GameChoosingCard CreateInstance()
    {
        FXMLLoader loader = new FXMLLoader(GameChoosingCard.class.getResource("/fxml/Game/GameChoosingCard.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.getController();
    }

    public void MoveToLeftCardHBox(HBox hBox)
    {
        hBox.getChildren().add(this.rootButton);
        this.cardSide = CardSide.Left;
    }

    public void MoveToRightCardHBox(HBox hBox)
    {
        hBox.getChildren().add(0, this.rootButton);
        this.cardSide = CardSide.Right;
    }

    public void MoveParent(Pane parent)
    {
        parent.getChildren().add(this.rootButton);
    }

    public void SetGameName(String name)
    {
        this.rootButton.setText(name);
    }

    public void AddActionEventHandler(EventHandler<ActionEvent> eventHandler)
    {
        this.rootButton.addEventHandler(ActionEvent.ACTION, eventHandler);
    }

    public void AddHoverListener(ChangeListener<Boolean> listener)
    {
        this.rootButton.hoverProperty().addListener(listener);
    }


    //region Animation

    public void PlayHoveringAnimation(EventHandler<ActionEvent> eventHandler)
    {
        double hoveringScale = 1.5;
        ScaleTransition scaleTransition = this.GetScaleTransition(hoveringScale);
        scaleTransition.setOnFinished(eventHandler);
        scaleTransition.play();

        double hoveringFade = 1;
        FadeTransition fadeTransition = this.GetFadeTransition(hoveringFade);
        fadeTransition.play();
    }

    public void PlayNotHoveringAnimation()
    {
        double notHoveringScale = 1;
        ScaleTransition scaleTransition = this.GetScaleTransition(notHoveringScale);
        scaleTransition.play();

        double notHoveringFade = 0.6;
        FadeTransition fadeTransition = this.GetFadeTransition(notHoveringFade);
        fadeTransition.play();
    }

    private ScaleTransition GetScaleTransition(double scaleTo)
    {
        ScaleTransition scaleTransition = new ScaleTransition(this.animationDuration, this.rootButton);
        scaleTransition.setInterpolator(Interpolator.EASE_BOTH);

        scaleTransition.setFromX(this.rootButton.scaleXProperty().get());
        scaleTransition.setFromY(this.rootButton.scaleYProperty().get());

        scaleTransition.setToX(scaleTo);
        scaleTransition.setToY(scaleTo);

        return scaleTransition;
    }

    private FadeTransition GetFadeTransition(double toValue)
    {
        FadeTransition fadeTransition = new FadeTransition(this.animationDuration, this.rootButton);
        fadeTransition.setInterpolator(Interpolator.EASE_BOTH);

        fadeTransition.setFromValue(this.rootButton.getOpacity());
        fadeTransition.setToValue(toValue);

        return fadeTransition;
    }

//endregion


    public void SetDisable(boolean isDisable) { this.isDisabled = isDisable; }

    public boolean IsDisable() { return this.isDisabled; }

    public void SetTrailerGif(Image image)
    {
        if (image == null) image = this.defaultTrailerGif;
        this.trailerImageView.setImage(image);
    }

}
