package Game;

import Game.CreateWord4DirGame.CreateWord4DirGameCtrl;
import Game.MultiChoiceGame.ChoiceGameCtrl;
import Game.Wordle.WordleCtrl;

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
    private final WordleCtrl wordleCtrl;


    public List<GameCtrl> getGameCtrls() {
        return gameCtrls;
    }

    public ChoiceGameCtrl getChoiceGameCtrl() {
        return choiceGameCtrl;
    }
    public CreateWord4DirGameCtrl getCreateWord4DirGameCtrl() {
        return createWord4DirGameCtrl;
    }

    public WordleCtrl getWordleCtrl() { return wordleCtrl;}


    private GamesCtrl()
    {
        this.gameCtrls = new ArrayList<>();
        this.choiceGameCtrl = new ChoiceGameCtrl();
        this.createWord4DirGameCtrl = new CreateWord4DirGameCtrl();
        this.wordleCtrl = new WordleCtrl();
        this.AddAllGameCtrls();
    }

    private void AddAllGameCtrls()
    {
        this.AddGameCtrl(this.choiceGameCtrl);
        this.AddGameCtrl(this.createWord4DirGameCtrl);
        this.AddGameCtrl(this.wordleCtrl);
    }

    private void AddGameCtrl(GameCtrl gameCtrl)
    {
        this.gameCtrls.add(gameCtrl);
    }



}
