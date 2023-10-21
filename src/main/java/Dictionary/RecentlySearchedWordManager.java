package Dictionary;

import java.util.ArrayList;
import java.util.List;

public class RecentlySearchedWordManager
{

    private final RecentlySearchedWordLoader recentlySearchedWordLoader;
    private final RecentlySearchedWordSaver recentlySearchedWordSaver;

    private final List<String> searchedWords;


    public RecentlySearchedWordManager()
    {
        this.recentlySearchedWordLoader = new RecentlySearchedWordLoader(this);
        this.recentlySearchedWordSaver = new RecentlySearchedWordSaver(this);
        this.searchedWords = new ArrayList<>();
    }

    public RecentlySearchedWordSaver getRecentlySearchedWordSaver() {
        return recentlySearchedWordSaver;
    }

    public RecentlySearchedWordLoader getRecentlySearchedWordLoader() {
        return recentlySearchedWordLoader;
    }

    public List<String> getSearchedWords()
    {
        return this.searchedWords;
    }






}
