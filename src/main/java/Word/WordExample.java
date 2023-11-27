package Word;


import Main.Database;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WordExample {
    private String prefixSymbol = "➥ ";
    private String example = "";
    private String translation = "";
    private String exampleID = null;

    public String getExampleID() {
        return exampleID;
    }

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

    public void saveData(String definitionID) throws SQLException {
        String update = "INSERT INTO example (example, translation, definition_id) VALUES (?,?,?)";
        PreparedStatement statement = Database.getConnection().prepareStatement(update);
        statement.setString(1, example);
        statement.setString(2, translation);
        statement.setString(3, definitionID);
        statement.execute();

        Statement getID = Database.getConnection().createStatement();
        ResultSet rs = getID.executeQuery("SELECT last_insert_rowid()");
        String id = rs.getString(1);
        this.exampleID = id;
    }

    public void deleteFromDatabase(String definitionID) throws SQLException {
        String query = "DELETE FROM example WHERE definition_id = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, definitionID);
        statement.execute();
    }

}
