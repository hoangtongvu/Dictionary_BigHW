package Game;

import Game.MultiChoiceGame.ChoiceGameCtrl;
import java.util.ArrayList;
import java.util.List;

public class GamesCtrl
{

    private static GamesCtrl instance;
    public static GamesCtrl getInstance() {
        if (instance == null) instance = new GamesCtrl();
        return instance;
    }

    private final List<GameCtrl> gameCtrls;
    private final ChoiceGameCtrl choiceGameCtrl;


    public List<GameCtrl> getGameCtrls() {
        return gameCtrls;
    }

    public ChoiceGameCtrl getChoiceGameCtrl() {
        return choiceGameCtrl;
    }


    private GamesCtrl()
    {
        this.gameCtrls = new ArrayList<>();
        this.choiceGameCtrl = new ChoiceGameCtrl();
        this.AddAllGameCtrls();
    }

    private void AddAllGameCtrls()
    {
        this.AddGameCtrl(this.choiceGameCtrl);
    }

    private void AddGameCtrl(GameCtrl gameCtrl)
    {
        this.gameCtrls.add(gameCtrl);
    }



}
