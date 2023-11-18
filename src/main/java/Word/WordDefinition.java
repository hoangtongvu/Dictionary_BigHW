package Word;
import Main.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WordDefinition {
    private String prefixSymbol = "▶ ";
    private String definition;
    private List<WordExample> exampleList;

    private String definitionID = null;

    public String getDefinitionID() {
        return definitionID;
    }

    public WordDefinition() {

    }

    public List<WordExample> getExampleList() {
        return exampleList;
    }

    public WordDefinition(String definition) {
        this.definition = definition;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }
    
    public void addExample(WordExample wordExample) {
        if (exampleList == null) {
            this.exampleList = new ArrayList<>();
        }
        exampleList.add(wordExample);
    }


    public String GetInfo(String prefixSpace) {
        String exampleFormat = "<div class = \"example\">";
        if (exampleList != null) {
            for (WordExample wordExample : exampleList) {
                exampleFormat += wordExample.GetInfo(prefixSpace + "\t") + "\n";
            }
        }
        exampleFormat += "</div>";
        String definitionFormat = "<div class = \"definition\">" + "<h3>" + prefixSpace + prefixSymbol + definition + "</h3>" + exampleFormat + "</div>";
        return definitionFormat;
    }

    public void loadData(String definitionID) throws SQLException {
        Statement statement = Database.getConnection().createStatement();
        this.definitionID = definitionID;
        String query = "SELECT * FROM definition where definition_id =" + definitionID;
        ResultSet resultSet = statement.executeQuery(query);
        definition =  resultSet.getString("definition");

        query = "SELECT * FROM example where definition_id=" + definitionID;
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            WordExample temp = new WordExample(resultSet.getString("example"), resultSet.getString("translation"));
            temp.setExampleID(resultSet.getString("example_id"));
            addExample(temp);
        }
    }

    public void saveData(String baseID, boolean isPhrase) throws SQLException {
        String update = "";

        if (isPhrase) {
            //Flag = 0 for phrase
            update = "INSERT INTO definition (definition, phrase_id) VALUES (?,?)";
        } else {
            //Flag = 1 for description
            update = "INSERT INTO definition (definition, description_id) VALUES (?,?)";
        }
        PreparedStatement statement = Database.getConnection().prepareStatement(update);
        statement.setString(1,definition);
        statement.setString(2,String.valueOf(baseID));
        statement.execute();

        Statement getID = Database.getConnection().createStatement();
        ResultSet rs = getID.executeQuery("SELECT last_insert_rowid()");
        String id = rs.getString(1);
        this.definitionID = id;

        //Get examples
        if (exampleList != null) {
            for (WordExample example : exampleList) {
                example.saveData(id);
            }
        }
    }

    public void deleteFromDatabase(String id, boolean isPhrase) throws SQLException {
        if (exampleList != null) {
            for (WordExample example : exampleList) {
                example.deleteFromDatabase(definitionID);
            }
        }

        String query = "DELETE FROM definition WHERE ? = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        if (isPhrase) {
            statement.setString(1, "phrase_id");
        } else {
            statement.setString(1, "description_id");
        }
        statement.setString(2, id);
        statement.execute();
    }
}