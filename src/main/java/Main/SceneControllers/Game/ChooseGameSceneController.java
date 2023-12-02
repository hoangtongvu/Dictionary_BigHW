package Main.SceneControllers.Game;

import Game.GameCtrl;
import Game.GamesCtrl;
import Main.SceneControllers.BaseSceneController;

import animatefx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import Interfaces.IHasNavPane;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.layout.StackPane;

public class ChooseGameSceneController extends BaseSceneController implements Initializable, IHasNavPane
{

    @FXML private HBox leftCardHbox;
    @FXML private HBox rightCardHbox;
    @FXML private StackPane orderStackPane;

    private final List<GameChoosingCard> cards;
    private GameChoosingCard currentHoveringCard;


    public ChooseGameSceneController()
    {
        this.cards = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        this.CreateAllCards();
    }

    @Override
    public void StartShow() { }

    @Override
    public void EndShow() { }

    private void CreateAllCards()
    {
        GamesCtrl gamesCtrl = GamesCtrl.getInstance();
        for (GameCtrl gameCtrl : gamesCtrl.getGameCtrls())
        {
            this.CreateNewCard(gameCtrl);
        }
    }

    private void CreateNewCard(GameCtrl gameCtrl)
    {
        GameChoosingCard newCard = GameChoosingCard.CreateInstance();
        newCard.SetGameName(gameCtrl.getGameName());
        newCard.AddActionEventHandler(gameCtrl.getOnGameOpenEventHandler());
        newCard.SetTrailerGif(gameCtrl.getTrailerImage());

        this.AddCard(newCard);
        this.AddHoverListener(newCard);
    }

    private void AddCard(GameChoosingCard card)
    {
        this.cards.add(card);
        card.MoveParent(this.rightCardHbox);
    }

    private void AddHoverListener(GameChoosingCard card)
    {
        card.AddHoverListener(new ChangeListener<>()
        {

            @Override
            public void changed(ObservableValue<? extends Boolean> observableValue, Boolean prev, Boolean isHover)
            {
                if (!isHover) return;
                if (card.IsDisable()) return;
                if (currentHoveringCard == card) return;

                DisableAllCards();

                //after this, currentHoveringCard is null.
                if (currentHoveringCard != null) this.HandleOldCard();

                PlayAllCardsTranslateAnimation();

                this.HandleNewCard();

            }

            private void HandleOldCard()
            {
                CardSide cardSide = card.getCardSide();
                switch (cardSide)
                {
                    case Left -> currentHoveringCard.MoveToRightCardHBox(rightCardHbox);
                    case Right -> currentHoveringCard.MoveToLeftCardHBox(leftCardHbox);
                }
                currentHoveringCard.PlayNotHoveringAnimation();

            }

            private void HandleNewCard()
            {
                currentHoveringCard = card;
                currentHoveringCard.MoveParent(orderStackPane);
                card.PlayHoveringAnimation(e -> EnableAllCards());
            }


        });
    }

    private void DisableAllCards()
    {
        cards.forEach(c -> c.SetDisable(true));
    }

    private void EnableAllCards()
    {
        cards.forEach(c -> c.SetDisable(false));
    }

    private void PlayAllCardsTranslateAnimation()
    {
        this.rightCardHbox.getChildren().forEach(card -> new SlideInRight(card).play());
        this.leftCardHbox.getChildren().forEach(card -> new SlideInLeft(card).play());

    }


}
