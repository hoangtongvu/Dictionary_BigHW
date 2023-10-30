package Word;
import java.util.List;
import java.util.ArrayList;

public class WordDescription 
{
    private String prefixSymbol = "\uD83D\uDF93 ";
    private String wordType;
    private List<WordDefinition> definitionList;
    private List<WordPhrase> phraseList;

    public void setWordType(String wordType) {
        this.wordType = wordType;
    }


    public WordDescription() {
        definitionList = new ArrayList<>();
    }

    public WordDefinition AddWordDefinition(WordDefinition wordDefinition)
    {
        definitionList.add(wordDefinition);
        int lastPos = definitionList.size();
        return this.definitionList.get(lastPos - 1);
    }

    public WordPhrase AddWordPhrase(WordPhrase wordPhrase)
    {
        if (phraseList == null) phraseList = new ArrayList<>();
        phraseList.add(wordPhrase);
        int lastPos = phraseList.size();
        return phraseList.get(lastPos - 1);
    }


    public String GetInfo() {
        String temp = "<h2>" + wordType + "</h2>";
        for (WordDefinition wordDefinition : definitionList) {
            temp += wordDefinition.GetInfo("\t");
        }

        if (phraseList == null) {
            return temp;
        }
        for (WordPhrase wordPhrase : phraseList) {
            temp += wordPhrase.GetInfo("\t") ;
        }
        return temp;
    }

}
