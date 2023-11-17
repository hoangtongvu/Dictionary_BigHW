package Word;


import Main.Database;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class WordExample {
    private String prefixSymbol = "➥ ";
    private String example = "";
    private String translation = "";
    private String exampleID = null;

    public void setExampleID(String exampleID) {
        this.exampleID = exampleID;
    }

    public WordExample(){}
    public WordExample(String example, String translation) {
        if (example != null ) {
            this.example = example;
        }
        if (translation != null) {
            this.translation = translation;
        }
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }


    public String GetInfo(String prefixSpace)   {
        String exampleText= "<span class = \"exampleText\"> " + prefixSpace + prefixSymbol + example + " </span> <br>";
        exampleText += "<span class=\"translation\">" + translation  + "</span> <br>";
        return exampleText;
    }

    public void saveData(int definitionID) throws SQLException {
        String update = "INSERT INTO example (example, translation, definition_id) VALUES (?,?,?)";
        PreparedStatement statement = Database.getConnection().prepareStatement(update);
        statement.setString(1,example);
        statement.setString(2,translation);
        statement.setString(3,String.valueOf(definitionID));
        statement.execute();
    }

}
