package Word;
import java.util.List;
import java.util.ArrayList;

public class WordDescription 
{
    private String wordType;
    private List<WordDefinition> wordDefinitions;

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }


    public WordDescription()
    {
        this.wordDefinitions = new ArrayList<WordDefinition>();
    }

    public WordDefinition AddWordDefinition(WordDefinition wordDefinition)
    {
        this.wordDefinitions.add(wordDefinition);
        int lastPos = this.wordDefinitions.size();
        return this.wordDefinitions.get(lastPos - 1);
    }

    public void PrintOut()
    {
        System.out.println("* " + this.wordType);
        for (WordDefinition wordDefinition : wordDefinitions) 
        {
            wordDefinition.PrintOut();
            
        }
    }
}
