package Word;
import Main.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WordDefinition 
{
    private String prefixSymbol = "▶ ";
    private String definition;
    private List<WordExample> exampleList;

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
        String temp = "<h3>" + prefixSpace + prefixSymbol + definition + "\n" + "</h3>";
            
        if (exampleList == null) {
            return temp;
        }

        for (WordExample wordExample : exampleList) {
            temp += wordExample.GetInfo(prefixSpace + "\t") + "\n";
        }
        return temp;
    }

    public void loadData(String definitionID) throws SQLException {
        Statement statement = Database.getConnection().createStatement();
        String query = "SELECT * FROM definition where definition_id =" + definitionID;
        ResultSet resultSet = statement.executeQuery(query);

        query = "SELECT * FROM example where definition_id=" + definitionID;
        resultSet = statement.executeQuery(query);
        while (resultSet.next()) {
            addExample(new WordExample(resultSet.getString("example"), resultSet.getString("translation")));
        }
    }

}