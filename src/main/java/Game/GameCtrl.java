package Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

import java.net.URL;

public abstract class GameCtrl
{

    private final String gameName;
    private final EventHandler<ActionEvent> onGameOpenEventHandler;
    private Image trailerImage;

    public String getGameName() {
        return gameName;
    }

    public EventHandler<ActionEvent> getOnGameOpenEventHandler() {
        return onGameOpenEventHandler;
    }

    public Image getTrailerImage() {
        return trailerImage;
    }


    public GameCtrl(String gameName, EventHandler<ActionEvent> onGameOpenEventHandler)
    {
        this.gameName = gameName;
        this.onGameOpenEventHandler = onGameOpenEventHandler;
        this.InitTrailerImage();
    }

    private void InitTrailerImage()
    {
        String path = this.getTrailerImagePath();
        if (path == null || path.isEmpty())
        {
            this.trailerImage = null;
            return;
        }
        URL url = getClass().getResource(this.getTrailerImagePath());
        if (url == null)
        {
            this.trailerImage = null;
            return;
        }
        this.trailerImage = new Image(url.toExternalForm());
    }

    protected abstract String getTrailerImagePath();
}
