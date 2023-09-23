package Dictionary;
import java.util.Collections;
import Word.WordBlock;


public class DicManager 
{
    
    private Dictionary dictionary;
    private DicWordLoader dicWordLoader;
    private DicWordSearcher dicWordSearcher;

    
    public DicWordSearcher getDicWordSearcher() {
        return dicWordSearcher;
    }

    public Dictionary getDictionary() {
        return dictionary;
    }

    public DicWordLoader getDicWordLoader() {
        return dicWordLoader;
    }


    public DicManager()
    {
        this.dictionary = new Dictionary();
        this.dicWordLoader = new DicWordLoader(this);
        this.dicWordSearcher = new DicWordSearcher(this);
    }

    public WordBlock AddNewWord(WordBlock wordBlock)
    {
        return this.dictionary.AddWordBlock(wordBlock);
    }


    public String LookUpWord(String lookupString)
    {
        WordBlock dummy = new WordBlock();
        dummy.SetWordAndSpelling(lookupString, "");
        int lookupPos = Collections.binarySearch(this.dictionary.getWordBlocks(), dummy);
        if (lookupPos < 0) return "Can't find your word.";
        return this.dictionary.GetWordInfoAt(lookupPos);
    }

    


}
