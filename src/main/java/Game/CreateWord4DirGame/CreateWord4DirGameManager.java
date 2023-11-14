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


    public final CustomEventPackage.OneParameter.CustomEvent<String> onCreatingWordChangeEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<Character[]> onChoiceCharsChangeEvent;
    //public final CustomEvent onChoosingCorrectChar;
    //public final CustomEvent onChoosingIncorrectChar;


    public CreateWord4DirGameManager()
    {
        this.creatingWords = new ArrayList<>();

        List<WordBlock> wordBlocks = DicManager.getInstance().getDictionary().getWordBlocks();
        this.numberOfDicWords = wordBlocks.size();


        this.onCreatingWordChangeEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        this.onChoiceCharsChangeEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        //this.onChoosingCorrectChar = new CustomEvent(this);
        //this.onChoosingIncorrectChar = new CustomEvent(this);
    }

    public void Start()
    {
        this.InitCreatingWords(3);
        this.currentWordIndex = 0;
        this.MoveToCreatingWordAt(0);
    }

    public void MoveToNextCreatingWord()
    {
        this.MoveToCreatingWordAt(this.currentWordIndex + 1);
    }

    private void MoveToCreatingWordAt(int i)
    {
        this.currentWordIndex = i;
        CreatingWord creatingWord = this.creatingWords.get(this.currentWordIndex);
        System.out.println(creatingWord.getResult());

        this.onCreatingWordChangeEvent.Invoke(this, creatingWord.getCurrentCreatingWord());
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
