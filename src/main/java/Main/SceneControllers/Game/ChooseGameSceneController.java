package Main.SceneControllers.Game;

import Game.GameCtrl;
import Game.GamesCtrl;
import Main.SceneControllers.NavigationPane.NavigationPaneSceneController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.Button;

public class ChooseGameSceneController implements Initializable
{

    @FXML private HBox chooseGameButtonsHbox;
    @FXML private AnchorPane rootAnchorPane;

    private List<Button> chooseGameButtons;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.chooseGameButtons = new ArrayList<>();
        this.CreateAllButtons();
        this.AddNavPane();
    }

    private void AddNavPane()
    {
        try {
            NavigationPaneSceneController.LoadInstance().AddNavPaneComponentsToRoot(this.rootAnchorPane);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private void CreateAllButtons()
    {
        GamesCtrl gamesCtrl = GamesCtrl.getInstance();
        for (GameCtrl gameCtrl : gamesCtrl.getGameCtrls())
        {
            this.CreateNewButton(gameCtrl.getGameName(), gameCtrl.getOnGameOpenEventHandler());
        }
    }

    private void CreateNewButton(String gameName, EventHandler<ActionEvent> eventHandler)
    {
        Button newBut = new Button(gameName);
        newBut.addEventHandler(ActionEvent.ACTION, eventHandler);
        this.FillHeightButton(newBut);
        this.AddChooseGameButton(newBut);
    }

    private void AddChooseGameButton(Button button)
    {
        this.chooseGameButtons.add(button);
        this.chooseGameButtonsHbox.getChildren().add(button);
    }

    private void FillHeightButton(Button button)
    {
        button.setPrefHeight(Integer.MAX_VALUE);
    }


}
