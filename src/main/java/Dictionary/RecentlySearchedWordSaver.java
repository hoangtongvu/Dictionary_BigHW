package Dictionary;


import java.io.*;
import java.util.List;

public class RecentlySearchedWordSaver
{
    private final RecentlySearchedWordManager recentlySearchedWordManager;




    public RecentlySearchedWordSaver(RecentlySearchedWordManager recentlySearchedWordManager)
    {
        this.recentlySearchedWordManager = recentlySearchedWordManager;
    }


    public void Save(String newWord)
    {

        List<String> searchedWords = this.recentlySearchedWordManager.getSearchedWords();

        //restructure list, if list contain newWord, sink newWord down, if not, addLast.
        this.RestructureList(searchedWords, newWord);

        //System.out.println(searchedWords);

        this.Save(searchedWords);
    }

    private void Save(List<String> searchedWords)
    {
        File file = this.recentlySearchedWordManager.getFile();
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            int size = searchedWords.size();
            for (int i = 0; i < size; i++)
            {
                String s = searchedWords.get(i);
                writer.write(s);

                //if reach last line then no newLine.
                if (i == size - 1) break;
                writer.newLine();
            }
            writer.close();


        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


    private void RestructureList(List<String> words, String word)
    {
        words.remove(word);
        words.add(word);
    }

    public void clearHistory() {
        File file = this.recentlySearchedWordManager.getFile();
        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file,false));
            writer.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
}
