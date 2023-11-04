package Word;
import Main.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

public class WordDescription 
{
    private String prefixSymbol = "\uD83D\uDF93 ";
    private String wordType;
    private List<WordDefinition> definitionList;
    private List<WordPhrase> phraseList;
    private static WordDefinition wordDefinition;
    private static WordPhrase wordPhrase;

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
        if (definitionList != null) {
            for (WordDefinition wordDefinition : definitionList) {
                temp += wordDefinition.GetInfo("\t");
            }
        }

        if (phraseList == null) {
            return temp;
        }
        for (WordPhrase wordPhrase : phraseList) {
            temp += wordPhrase.GetInfo("\t") ;
        }
        return temp;
    }

    public void loadData(String wordID) throws SQLException {
        Statement statement = Database.getConnection().createStatement();
        String query = "SELECT * FROM description where word_id=" + wordID;
        ResultSet resultSet = statement.executeQuery(query);
        wordType = resultSet.getString("word_type");
        String descriptionID = resultSet.getString("description_id");


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
}
