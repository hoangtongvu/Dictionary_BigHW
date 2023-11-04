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

    public String getWordType() {
        return wordType;
    }

    public void addDefinition(WordDefinition wordDefinition) {
        if (definitionList == null) {
            definitionList =new ArrayList<>();
        }
        definitionList.add(wordDefinition);
    }

    public void addPhrase(WordPhrase wordPhrase) {
        if (phraseList == null) {
            phraseList = new ArrayList<>();
        }
        phraseList.add(wordPhrase);
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
