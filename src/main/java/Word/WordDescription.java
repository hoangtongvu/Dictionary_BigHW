package Word;
import Main.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class WordDescription {
    private String prefixSymbol = "\uD83D\uDF93 ";
    private String wordType;
    private List<WordDefinition> definitionList;
    private List<WordPhrase> phraseList;
    private static WordDefinition wordDefinition;
    private static WordPhrase wordPhrase;
    private String descriptionID = null;

    public List<WordPhrase> getPhraseList() {
        return phraseList;
    }

    public String getDescriptionID() {
        return descriptionID;
    }

    public WordDescription() {

    }

    public WordDescription(String wordType) {
        this.wordType = wordType;
    }
    public void setWordType(String wordType) {
        this.wordType = wordType;
    }

    public String getWordType() {
        return wordType;
    }

    public List<WordDefinition> getDefinitionList() {
        return this.definitionList;
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
        String wordTypeFormat = "<div class = \"wordType\"> <h2> " + wordType + "</h2> </div>";
        String body = "";
        if (definitionList != null) {
            for (WordDefinition wordDefinition : definitionList) {
                body += wordDefinition.GetInfo("\t");
            }
        }

        if (phraseList != null) {
            for (WordPhrase wordPhrase : phraseList) {
                body += wordPhrase.GetInfo("\t") ;
            }
        }

        return "<div class = \"description\"> " + wordTypeFormat + body + " </div>";
    }

    public void loadData(String descriptionID) throws SQLException {
        this.descriptionID = descriptionID;
        Statement statement = Database.getConnection().createStatement();
        String query = "SELECT * FROM description where description_id=" + descriptionID;
        ResultSet resultSet = statement.executeQuery(query);
        wordType = resultSet.getString("word_type");

        query = "SELECT * FROM phrase where description_id =" + descriptionID;
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            wordPhrase = new WordPhrase();
            wordPhrase.loadData(resultSet.getString("phrase_id"));
            addPhrase(wordPhrase);
        }

        query = "SELECT * FROM definition where description_id =" + descriptionID;
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            wordDefinition = new WordDefinition();
            wordDefinition.loadData(resultSet.getString("definition_id"));
            addDefinition(wordDefinition);
        }
    }

    public void saveData(String wordID) throws SQLException {
        String update = "INSERT INTO description (word_type, word_id) VALUES (?,?)";
        PreparedStatement statement = Database.getConnection().prepareStatement(update);
        statement.setString(1, wordType);
        statement.setString(2, String.valueOf(wordID));
        statement.execute();

        Statement getID = Database.getConnection().createStatement();
        ResultSet rs = getID.executeQuery("SELECT last_insert_rowid()");
        String id = rs.getString(1); //descriptionID
        this.descriptionID = id;

        if (phraseList != null) {
            for (int i = 0; i < phraseList.size(); i++) {
                phraseList.get(i).saveData(descriptionID);
            }
        }
        if (definitionList != null) {
            for (int i = 0; i < definitionList.size(); i++) {
                definitionList.get(i).saveData(descriptionID, false);
            }
        }
    }

    public void deleteFromDatabase(String word_id) throws SQLException {
        //Delete definition first
        if (definitionList != null) {
            for (WordDefinition definition : definitionList) {
                definition.deleteFromDatabase(descriptionID, false);
            }
        }
        if (phraseList != null) {
            for (WordPhrase phrase : phraseList) {
                phrase.deleteFromDatabase(descriptionID);
            }
        }

        String query = "DELETE FROM description WHERE word_id = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, word_id);
        statement.execute();
    }
}
