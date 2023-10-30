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

    public String getSpelling() {
        return spelling;
    }

    public void SetWordAndSpelling(String word, String spelling) {
        this.word = word;
        this.spelling = spelling;
    }

    public WordDescription AddWordDescription(WordDescription wd) {
        descriptionsList.add(wd);
        int lastPos = this.descriptionsList.size();
        return this.descriptionsList.get(lastPos - 1);
    }

    public WordBlock() {
        descriptionsList = new ArrayList<>();
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
