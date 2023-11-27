package Game.CreateWord4DirGame;


import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.List;
import java.util.Random;

public class GameAutoCompletion
{

    private final CreateWord4DirGameCtrl createWord4DirGameCtrl;
    private final Timeline timeline;

    public GameAutoCompletion(CreateWord4DirGameCtrl createWord4DirGameCtrl)
    {
        this.timeline = new Timeline();
        this.createWord4DirGameCtrl = createWord4DirGameCtrl;
        this.createWord4DirGameCtrl.getGameManager().onGameEndEvent.AddListener(para1 -> StopTimeLine(timeline));
    }


    public void AutoCompletion()
    {
        CreateWord4DirGameManager gameManager = this.createWord4DirGameCtrl.getGameManager();
        List<CreatingWord> creatingWords = gameManager.getCreatingWords();
        int currentWordIndex = gameManager.getCurrentWordIndex();

        KeyFrame chooseCharKf = new KeyFrame(Duration.seconds(0.5), ev -> {
//            CreatingWord creatingWord = creatingWords.get(currentWordIndex);
//            Character[] chars = creatingWord.GetChoiceCharacters();
//            char nextChar = creatingWord.GetNextChar();
//
//            if (nextChar == '\0') return;
//            int index = 0;
//            for (int i = 0; i < chars.length; i++)
//            {
//                if (nextChar == chars[i]) index = i;
//            }
//
//            int finalIndex = index;
//            gameManager.ChooseChar(finalIndex);
            gameManager.ChooseChar(new Random().nextInt(0, 4));
        });

        this.timeline.getKeyFrames().add(chooseCharKf);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }


    private void StopTimeLine(Timeline timeline)
    {
        timeline.stop();
        System.out.println("Stop timeline.");
    }


}
