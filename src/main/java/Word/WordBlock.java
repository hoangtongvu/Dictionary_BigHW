package Word;
import Main.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WordBlock implements Comparable<WordBlock> {
    private String word;
    private String spelling;
    private List<WordDescription> descriptionsList;
    private static WordDescription wordDescription = null;
    private String wordID = "";

    public String getWordID() {
        return wordID;
    }

    public void setWordID(String wordID) {
        this.wordID = wordID;
    }

    public WordBlock(String word, String spelling) {
        this.word = word;
        this.spelling = spelling;
    }

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
        String wordBlock = compileFormat();
        if (descriptionsList != null) {
            for (WordDescription wordDescription : descriptionsList) {
                wordBlock += wordDescription.GetInfo() ;
            }
        }
        return wordBlock;
    }

    public String compileFormat() {
        return "<div class = \"wordBox\"> " +
                        "<h1>" + word + "</h1> <br>" +
                        "<h1>" + spelling + "</h1> " +
                        "</div>";
    }
    @Override
    public int compareTo(WordBlock wordBlock) {
        //throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
        return word.compareToIgnoreCase(wordBlock.getWord());
    }

    public void loadData(String wordID) throws SQLException {
        Statement statement = Database.getConnection().createStatement();
        String query = "SELECT * FROM description where word_id =" + wordID;
        ResultSet resultSet = statement.executeQuery(query);


        while (resultSet.next()) {
            wordDescription = new WordDescription();
            wordDescription.loadData(wordID);
            addDescription(wordDescription);
        }
    }



}
