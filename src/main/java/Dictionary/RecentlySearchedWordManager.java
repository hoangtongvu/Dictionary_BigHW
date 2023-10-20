package Dictionary;

import java.util.ArrayList;
import java.util.List;

public class RecentlySearchedWordManager
{

    private final RecentlySearchedWordLoader recentlySearchedWordLoader;

    private final List<String> searchedWords;


    public RecentlySearchedWordManager()
    {
        this.recentlySearchedWordLoader = new RecentlySearchedWordLoader(this);
        this.searchedWords = new ArrayList<>();
    }

    public void AddSearchedWord(String word)
    {
        this.searchedWords.add(word);
    }

    public List<String> getSearchedWords()
    {
        return this.searchedWords;
    }

    public RecentlySearchedWordLoader getRecentlySearchedWordLoader() {
        return recentlySearchedWordLoader;
    }
}
