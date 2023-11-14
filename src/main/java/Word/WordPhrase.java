package Word;

import Main.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 1 phrase has 1 to many definitions, each definition has it own 0 to many example.
 */
public class WordPhrase {
    private String prefixSymbol = "➤ ";
    private String phrase;
    private List<WordDefinition> definitionList;
    private static WordDefinition wordDefinition;

    public WordPhrase(String phrase) {
        this.phrase = phrase;
    }

    public WordPhrase() {

    }

    public void setPhrase(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }


    public void addDefinition(WordDefinition wordDefinition) {
        if (definitionList == null) {
            definitionList = new ArrayList<>();
        }
        definitionList.add(wordDefinition);
    }

    public String GetInfo(String prefixSpace) {
        String definitionFormat = "";
        if (definitionList != null) {
            for (WordDefinition definition : definitionList) {
                definitionFormat += definition.GetInfo(prefixSpace + "\t") + "\n";
            }
        }
        String phraseFormat = "<div class = \"phrase\">" + "<h3>" + prefixSpace + prefixSymbol + phrase + "</h3>" + definitionFormat + "</div>";
        return phraseFormat;
    }

    public void loadData(String phraseID) throws SQLException {
        Statement statement = Database.getConnection().createStatement();
        String query = "SELECT * FROM phrase where phrase_id =" + phraseID;
        ResultSet resultSet = statement.executeQuery(query);
        phrase = resultSet.getString("phrase");

        query = "SELECT * FROM definition where phrase_id =" + phraseID;
        statement.executeQuery(query);

        while (resultSet.next()) {
            wordDefinition = new WordDefinition();
            wordDefinition.loadData(resultSet.getString("definition_id"));
            addDefinition(wordDefinition);
        }
    }

}
