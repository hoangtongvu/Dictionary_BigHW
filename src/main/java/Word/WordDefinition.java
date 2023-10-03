package Word;
import java.util.ArrayList;
import java.util.List;

public class WordDefinition 
{
    private String definition;
    private List<WordExample> wordExamples;//not mandatory

    public void setDefinition(String definition) {
        this.definition = definition;
    }
    
    public WordExample AddWordExample(WordExample wordExample)
    {
        if (this.wordExamples == null) this.wordExamples = new ArrayList<WordExample>();
        this.wordExamples.add(wordExample);
        int lastPos = this.wordExamples.size();
        return this.wordExamples.get(lastPos - 1);
    }


    public String GetInfo()
    {
        String temp = "  => " + this.definition + "\n";
            
        if (this.wordExamples == null) return temp;
        for (WordExample wordExample : wordExamples) 
        {
            temp += wordExample.GetInfo() + "\n";
        }
        return temp;
    }

}