package Dictionary;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class RecentlySearchedWordLoader
{

    private final RecentlySearchedWordManager recentlySearchedWordManager;

    public RecentlySearchedWordLoader(RecentlySearchedWordManager recentlySearchedWordManager)
    {
        this.recentlySearchedWordManager = recentlySearchedWordManager;
    }


    public void Load() throws Exception
    {

        File file = this.recentlySearchedWordManager.getFile();

        List<String> searchedWords = this.recentlySearchedWordManager.getSearchedWords();
        if (!searchedWords.isEmpty()) searchedWords.clear();

        Scanner scanner = new Scanner(file, "UTF-8");

        String line;
        while (scanner.hasNextLine())
        {
            line = scanner.nextLine().strip();
            searchedWords.add(line);
        }
    }



}
