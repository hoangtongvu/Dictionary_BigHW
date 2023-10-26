package Word;

import java.util.ArrayList;
import java.util.List;

/**
 * 1 phrase has 1 to many definitions, each definition has it own 0 to many example.
 */
public class WordPhrase
{
    private String prefixSymbol = "➤ ";
    private String phrase;
    private List<WordDefinition> definitions;

    public WordPhrase(String phrase)
    {
        this.phrase = phrase;

    }

    public WordDefinition AddDefinition(WordDefinition definition)
    {
        if (this.definitions == null) this.definitions = new ArrayList<>();
        this.definitions.add(definition);
        int lastPos = this.definitions.size();
        return this.definitions.get(lastPos - 1);
    }

    public String GetInfo(String prefixSpace)
    {
        String temp = "<h2>" + prefixSpace + this.prefixSymbol + this.phrase + "\n" + "</h2>";
        if (this.definitions == null) return temp;

        for (WordDefinition wordDefinition : this.definitions)
        {
            temp += wordDefinition.GetInfo(prefixSpace + "\t");
        }


        return temp;
    }

}
