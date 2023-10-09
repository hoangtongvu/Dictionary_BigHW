package Word;
import java.util.ArrayList;
import java.util.List;

public class WordBlock implements Comparable<WordBlock>
{
    private String word;
    private String spelling;
    private List<WordDescription> wordDescriptions;

    public String getWord() {
        return word;
    }

    public String getSpelling() {
        return spelling;
    }

    public void SetWordAndSpelling(String w, String s)
    {
        this.word = w;
        this.spelling = s;
    }

    public WordDescription AddWordDescription(WordDescription wd)
    {
        this.wordDescriptions.add(wd);
        int lastPos = this.wordDescriptions.size();
        return this.wordDescriptions.get(lastPos - 1);
    }

    public WordBlock()
    {
        this.wordDescriptions = new ArrayList<>();
    }


    public String GetInfo()
    {
        String temp = this.word + "\n" + this.spelling + "\n";

        for (WordDescription wordDescription : wordDescriptions) 
        {
            temp += wordDescription.GetInfo() ;
        }
        return temp;
    }

    @Override
    public int compareTo(WordBlock o) 
    {
        //throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
        return this.word.compareToIgnoreCase(o.getWord());
    }


}
