package Game;

import Game.CreateWord4DirGame.CreateWord4DirGameCtrl;
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
    private final CreateWord4DirGameCtrl createWord4DirGameCtrl;


    public List<GameCtrl> getGameCtrls() {
        return gameCtrls;
    }

    public ChoiceGameCtrl getChoiceGameCtrl() {
        return choiceGameCtrl;
    }
    public CreateWord4DirGameCtrl getCreateWord4DirGameCtrl() {
        return createWord4DirGameCtrl;
    }


    private GamesCtrl()
    {
        this.gameCtrls = new ArrayList<>();
        this.choiceGameCtrl = new ChoiceGameCtrl();
        this.createWord4DirGameCtrl = new CreateWord4DirGameCtrl();
        this.AddAllGameCtrls();
    }

    private void AddAllGameCtrls()
    {
        this.AddGameCtrl(this.choiceGameCtrl);
        this.AddGameCtrl(this.createWord4DirGameCtrl);
    }

    private void AddGameCtrl(GameCtrl gameCtrl)
    {
        this.gameCtrls.add(gameCtrl);
    }



}
