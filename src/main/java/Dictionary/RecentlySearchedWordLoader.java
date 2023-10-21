package Dictionary;

import java.io.File;
import java.net.URL;
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

        URL url = this.getClass().getClassLoader().getResource("RecentlySearchedWords.txt");
        if (url == null) throw new Exception("URL is NULL");

        File file = new File(url.getFile());

        if (!file.exists())
        {
            System.out.println("FILE NOT FOUND");
            return;
        }

        List<String> searchedWords = this.recentlySearchedWordManager.getSearchedWords();
        if (!searchedWords.isEmpty()) searchedWords.clear();

        Scanner scanner;
        scanner = new Scanner(file, "UTF-8");

        String line;
        while (scanner.hasNextLine())
        {
            line = scanner.nextLine().strip();
            searchedWords.add(line);


        }

    }



}
