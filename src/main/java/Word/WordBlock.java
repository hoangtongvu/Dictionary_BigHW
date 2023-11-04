package Word;
import java.util.ArrayList;
import java.util.List;

public class WordBlock implements Comparable<WordBlock> {
    private String word;
    private String spelling;
    private List<WordDescription> descriptionsList;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getSpelling() {
        return spelling;
    }

    public void setSpelling(String spelling) {
        this.spelling = spelling;
    }

    public void addDescription(WordDescription wordDescription) {
        if (descriptionsList == null) {
            descriptionsList = new ArrayList<>();
        }
        descriptionsList.add(wordDescription);
    }


    public String GetInfo() {
        String temp = "<h1>" + word + "\n" + spelling + "</h1>";
        for (WordDescription wordDescription : descriptionsList) {
            temp += wordDescription.GetInfo() ;
        }
        return temp;
    }

    @Override
    public int compareTo(WordBlock o) 
    {
        //throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
        return word.compareToIgnoreCase(o.getWord());
    }


}
