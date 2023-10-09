package Word;
import java.util.List;
import java.util.ArrayList;

public class WordDescription 
{
    private String prefixSymbol = "\uD83D\uDF93 ";
    private String wordType;
    private List<WordDefinition> wordDefinitions;
    private List<WordPhrase> wordPhrases;

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

    public WordPhrase AddWordPhrase(WordPhrase wordPhrase)
    {
        if (this.wordPhrases == null) this.wordPhrases = new ArrayList<>();
        this.wordPhrases.add(wordPhrase);
        int lastPos = this.wordPhrases.size();
        return this.wordPhrases.get(lastPos - 1);
    }


    public String GetInfo()
    {
        String temp = this.prefixSymbol + this.wordType + "\n";
        for (WordDefinition wordDefinition : wordDefinitions) 
        {
            temp += wordDefinition.GetInfo("\t");
        }

        if (this.wordPhrases == null) return temp;
        for (WordPhrase wordPhrase : this.wordPhrases)
        {
            temp += wordPhrase.GetInfo("\t");
        }

        return temp;
    }

}
