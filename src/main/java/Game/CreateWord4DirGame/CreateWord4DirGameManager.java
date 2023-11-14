package Game.CreateWord4DirGame;

import Dictionary.DicManager;
import Word.WordBlock;

import java.util.ArrayList;
import java.util.List;

public class CreateWord4DirGameManager
{
    private final List<CreatingWord> creatingWords;
    private final int numberOfDicWords;
    private int currentWordIndex;

    private int finalPoint;

    private final int defaultAddPoint;
    private final int defaultDeductPoint;


    public final CustomEventPackage.OneParameter.CustomEvent<String> onCreatingWordChangeEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<String> onHintChangeEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<Character[]> onChoiceCharsChangeEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<Integer> onFinalPointChangeEvent;



    private void setFinalPoint(int finalPoint) {
        //trigger event.
        this.finalPoint = finalPoint;
        this.onFinalPointChangeEvent.Invoke(this, this.finalPoint);
    }

    public CreateWord4DirGameManager()
    {
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

    }

    public void Start()
    {
        this.InitCreatingWords(5);
        this.ResetPoint();
        this.currentWordIndex = 0;
        this.MoveToCreatingWordAt(0);
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

    public void MoveToNextCreatingWord()
    {
        this.MoveToCreatingWordAt(this.currentWordIndex + 1);
    }

    private void MoveToCreatingWordAt(int i)
    {
        this.currentWordIndex = i;
        CreatingWord creatingWord = this.creatingWords.get(this.currentWordIndex);
        System.out.println("[WORD] " + creatingWord.getResult());

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
        CreatingWord creatingWord = this.creatingWords.get(this.currentWordIndex);
        creatingWord.ChooseChar(charIndex);
    }



}
