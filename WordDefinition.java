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

    public void PrintOut()
    {
        System.out.println("definition: " + this.definition);
        if (this.wordExamples == null) return;
        for (WordExample wordExample : wordExamples) 
        {
            wordExample.PrintOut();
        }
    }

}