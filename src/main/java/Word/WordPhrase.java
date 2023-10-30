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
    private List<WordDefinition> definitionList;

    public WordPhrase(String phrase) {
        this.phrase = phrase;
    }

    public WordDefinition AddDefinition(WordDefinition definition) {
        if (definitionList == null) definitionList = new ArrayList<>();
        definitionList.add(definition);
        int lastPos = definitionList.size();
        return definitionList.get(lastPos - 1);
    }

    public String GetInfo(String prefixSpace) {
        String temp = "<h2>" + prefixSpace + this.prefixSymbol + this.phrase + "\n" + "</h2>";

        if (definitionList == null) {
            return temp;
        }

        for (WordDefinition wordDefinition : definitionList) {
            temp += wordDefinition.GetInfo(prefixSpace + "\t");
        }

        return temp;
    }

}
