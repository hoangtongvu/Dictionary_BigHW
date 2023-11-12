package Game;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public abstract class GameCtrl
{

    private final String gameName;
    private final EventHandler<ActionEvent> onGameOpenEventHandler;

    public String getGameName() {
        return gameName;
    }

    public EventHandler<ActionEvent> getOnGameOpenEventHandler() {
        return onGameOpenEventHandler;
    }


    public GameCtrl(String gameName, EventHandler<ActionEvent> onGameOpenEventHandler)
    {
        this.gameName = gameName;
        this.onGameOpenEventHandler = onGameOpenEventHandler;

    }
}
