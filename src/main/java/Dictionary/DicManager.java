package Dictionary;

import Word.WordBlock;

import java.util.Collections;


public class DicManager 
{
    private static DicManager instance;
    

    public static DicManager getInstance() {
        if (instance == null) {
            instance = new DicManager();
        }
        return instance;
    }


    private Dictionary dictionary;
    private DicWordLoader dicWordLoader;
    private DicWordSearcher dicWordSearcher;
    private RecentlySearchedWordManager recentlySearchedWordManager;


    public Dictionary getDictionary() {
        return dictionary;
    }
    public DicWordLoader getDicWordLoader() {
        return dicWordLoader;
    }
    public DicWordSearcher getDicWordSearcher() {
        return dicWordSearcher;
    }
    public RecentlySearchedWordManager getRecentlySearchedWordManager() {
        return recentlySearchedWordManager;
    }

    public DicManager()
    {
        this.dictionary = new Dictionary();
        this.dicWordLoader = new DicWordLoader(this);
        this.dicWordSearcher = new DicWordSearcher(this);
        this.recentlySearchedWordManager = new RecentlySearchedWordManager();
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
