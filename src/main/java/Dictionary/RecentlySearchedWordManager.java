package Dictionary;

import Main.ProjectDirectory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RecentlySearchedWordManager
{

    private final RecentlySearchedWordLoader recentlySearchedWordLoader;
    private final RecentlySearchedWordSaver recentlySearchedWordSaver;

    private final String filePath = ProjectDirectory.resourcesPath + "/RecentlySearchedWords.txt";

    private File file;
    private final List<String> searchedWords;


    public RecentlySearchedWordManager()
    {
        this.recentlySearchedWordLoader = new RecentlySearchedWordLoader(this);
        this.recentlySearchedWordSaver = new RecentlySearchedWordSaver(this);
        this.searchedWords = new ArrayList<>();
        this.LoadFile();
    }

    public RecentlySearchedWordSaver getRecentlySearchedWordSaver() {
        return recentlySearchedWordSaver;
    }

    public RecentlySearchedWordLoader getRecentlySearchedWordLoader() {
        return recentlySearchedWordLoader;
    }

    public String getFilePath() {
        return filePath;
    }

    public List<String> getSearchedWords()
    {
        return this.searchedWords;
    }

    public File getFile() {
        return file;
    }

    private void LoadFile()
    {
        this.file = new File(this.filePath);
        if (!file.exists())
        {
            System.out.println("FILE NOT FOUND");
            return;
        }

    }
}
