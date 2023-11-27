package Game.CreateWord4DirGame;

import Dictionary.DicManager;
import Logger.LoggersCtrl;
import Word.WordBlock;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;


public class CreateWord4DirGameManager
{
    private final CreateWord4DirGameCtrl gameCtrl;

    private final List<CreatingWord> creatingWords;
    private final int numberOfDicWords;
    private int currentWordIndex;

    private int finalPoint;

    private final int defaultAddPoint;
    private final int defaultDeductPoint;

    private boolean gameIsEnd;
    private final double delayOnFinishWordSecond = 1.5;


    public final CustomEventPackage.OneParameter.CustomEvent<String> onCreatingWordChangeEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<String> onHintChangeEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<Character[]> onChoiceCharsChangeEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<Integer> onFinalPointChangeEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<Integer> onGameEndEvent;
    public final CustomEventPackage.TwoParameters.CustomEvent<Boolean, Integer> onChooseCharEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<Double> onFinishWord;



    private void setFinalPoint(int finalPoint) {
        this.finalPoint = finalPoint;
        this.onFinalPointChangeEvent.Invoke(this, this.finalPoint);
    }

    public List<CreatingWord> getCreatingWords()
    {
        return this.creatingWords;
    }

    public int getCurrentWordIndex()
    {
        return this.currentWordIndex;
    }

    public CreateWord4DirGameManager(CreateWord4DirGameCtrl gameCtrl)
    {
        this.gameCtrl = gameCtrl;
        this.creatingWords = new ArrayList<>();

        List<WordBlock> wordBlocks = DicManager.getInstance().getDictionary().getWordBlocks();
        this.numberOfDicWords = wordBlocks.size();
        this.finalPoint = 0;
        this.defaultAddPoint = 7;
        this.defaultDeductPoint = 10;


        this.onCreatingWordChangeEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        this.onHintChangeEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        this.onChoiceCharsChangeEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        this.onFinalPointChangeEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        this.onGameEndEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        this.onChooseCharEvent = new CustomEventPackage.TwoParameters.CustomEvent<>(this);
        this.onFinishWord = new CustomEventPackage.OneParameter.CustomEvent<>(this);

    }

    public void Start()
    {
        this.InitCreatingWords(1);
        this.ResetPoint();
        this.currentWordIndex = 0;
        this.MoveToCreatingWordAt(0);
        this.gameIsEnd = false;
        //this.gameCtrl.getGameAutoCompletion().AutoCompletion();
    }

    public void End()
    {
        this.onGameEndEvent.Invoke(this, this.finalPoint);
        this.gameIsEnd = true;
    }

    private void ResetPoint()
    {
        this.setFinalPoint(0);
    }

    public void AddPoint()
    {
        this.AddPoint(this.defaultAddPoint);
    }

    public void DeductPoint()
    {
        this.DeductPoint(this.defaultDeductPoint);
    }

    private void AddPoint(int addAmount)
    {
        this.setFinalPoint(this.finalPoint + addAmount);
    }

    private void DeductPoint(int deductAmount)
    {
        this.setFinalPoint(this.finalPoint - deductAmount);
    }

    public void OnFinishWord()
    {
        //play some animation.
        this.onFinishWord.Invoke(this, this.delayOnFinishWordSecond);

        double delaySecond = 1;
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(delaySecond), actionEvent -> this.MoveToNextCreatingWord());
        new Timeline(keyFrame).play();
    }

    private void MoveToNextCreatingWord()
    {
        this.MoveToCreatingWordAt(this.currentWordIndex + 1);
    }

    private void MoveToCreatingWordAt(int i)
    {
        if (i >= this.creatingWords.size())
        {
            this.End();
            return;
        }
        this.currentWordIndex = i;
        CreatingWord creatingWord = this.creatingWords.get(this.currentWordIndex);
        LoggersCtrl.gameLogger.Log("WORD", creatingWord.getResult());

        this.onCreatingWordChangeEvent.Invoke(this, creatingWord.getCurrentCreatingWord());
        this.onHintChangeEvent.Invoke(this, creatingWord.getHint());
        this.onChoiceCharsChangeEvent.Invoke(this, creatingWord.GetChoiceCharacters());
    }

    public void InvokeOnCreatingWordChangeEvent(String string)
    {
        this.onCreatingWordChangeEvent.Invoke(this, string);
    }

    public void InvokeOnChoiceCharsChangeEvent(Character[] characters)
    {
        this.onChoiceCharsChangeEvent.Invoke(this, characters);
    }

    public void InvokeOnChooseCharEvent(boolean isCorrect, int index)
    {
        this.onChooseCharEvent.Invoke(this, isCorrect, index);
    }

    private void InitCreatingWords(int amount)
    {
        List<WordBlock> wordBlocks = DicManager.getInstance().getDictionary().getWordBlocks();
        this.creatingWords.clear();

        for (int i = 0; i < amount; i++)
        {
            CreatingWord creatingWord = new CreatingWord(this, wordBlocks, this.numberOfDicWords);
            this.creatingWords.add(creatingWord);

        }
    }

    public void ChooseChar(int charIndex)
    {
        if (this.gameIsEnd) return;
        CreatingWord creatingWord = this.creatingWords.get(this.currentWordIndex);
        creatingWord.ChooseChar(charIndex);
    }



}
