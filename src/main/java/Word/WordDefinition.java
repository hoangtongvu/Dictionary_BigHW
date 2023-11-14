package Word;
import Main.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WordDefinition {
    private String prefixSymbol = "▶ ";
    private String definition;
    private List<WordExample> exampleList;

    public WordDefinition() {

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
        String query = "SELECT * FROM definition where definition_id =" + definitionID;
        ResultSet resultSet = statement.executeQuery(query);
        definition =  resultSet.getString("definition");

        query = "SELECT * FROM example where definition_id=" + definitionID;
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            addExample(new WordExample(resultSet.getString("example"), resultSet.getString("translation")));
        }
    }

}