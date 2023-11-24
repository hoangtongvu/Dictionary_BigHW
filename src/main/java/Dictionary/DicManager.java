package Dictionary;

import Word.WordBlock;

import java.sql.SQLException;
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


    private final Dictionary dictionary;
    private final DicWordLoader dicWordLoader;
    private final DicWordSearcher dicWordSearcher;
    private final RecentlySearchedWordManager recentlySearchedWordManager;


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

    public void addWordBlock(WordBlock wordBlock) {
        dictionary.AddWordBlock(wordBlock);
    }


    public String LookUpWord(String lookupString) throws SQLException {
        WordBlock dummy = new WordBlock(lookupString,"");
        int lookupPos = Collections.binarySearch(dictionary.getWordBlocks(), dummy);
        if (lookupPos < 0) return null;

        RecentlySearchedWordSaver recentlySearchedWordSaver = this.recentlySearchedWordManager.getRecentlySearchedWordSaver();
        recentlySearchedWordSaver.Save(lookupString);
        return dictionary.GetWordInfoAt(lookupPos);
    }



}
