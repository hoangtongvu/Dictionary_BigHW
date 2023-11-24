package Word;
import Dictionary.DicManager;
import Main.Database;
import Main.SceneControllers.Dictionary.DictionarySceneController;
import Main.SceneControllers.Dictionary.EditWordSceneController;

import java.sql.PreparedStatement;
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
    private boolean loadStatus = false;
    private boolean isStarred = false;
    boolean editable;

    public boolean isStarred() {
        return isStarred;
    }

    public void setStarred(boolean starred) {
        isStarred = starred;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public void setLoadStatus(boolean loadStatus) {
        this.loadStatus = loadStatus;
    }

    public WordBlock() {

    }

    public String getWordID() {
        return wordID;
    }

    public void setWordID(String wordID) {
        this.wordID = wordID;
    }

    public List<WordDescription> getDescriptionsList() {
        return descriptionsList;
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
        String wordBlock = "<div class = \"wordBox\"> " +
                "<h1>" + word + "</h1>" +
                "<h2>" + spelling + "</h2> " +
                "</div>";
        if (descriptionsList != null) {
            for (WordDescription wordDescription : descriptionsList) {
                wordBlock += wordDescription.GetInfo() ;
            }
        }
        return wordBlock;
    }

    @Override
    public int compareTo(WordBlock wordBlock) {
        //throw new UnsupportedOperationException("Unimplemented method 'compareTo'");
        return word.compareToIgnoreCase(wordBlock.getWord());
    }

    public void loadData(String wordID) throws SQLException {
        if (!loadStatus) {
            Statement statement = Database.getConnection().createStatement();
            String query = "SELECT * FROM description where word_id =" + wordID;
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                wordDescription = new WordDescription();
                wordDescription.loadData(resultSet.getString("description_id"));
                addDescription(wordDescription);
            }
        }
        loadStatus = true;
    }

    public void insertInOrder() {
        List<WordBlock> list = DicManager.getInstance().getDictionary().getWordBlocks();
        int index = 0;
        while (index < list.size() && this.compareTo(list.get(index)) > 0) {
            index++;
        }
        System.out.println(index);
        list.add(index, this);
    }

    //For saving newly added word
    public void saveData() throws SQLException {
        //Insert word into table
        String update = "INSERT INTO word (word, sound, is_editable, favourite) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = Database.getConnection().prepareStatement(update);
        statement.setString(1, word);
        if (spelling != null) {
            statement.setString(2, spelling);
        }
        statement.setString(3, "1");
        statement.setString(4, String.valueOf(isStarred));
        statement.execute();

        //Get last inserted ID
        Statement getID = Database.getConnection().createStatement();
        ResultSet rs = getID.executeQuery("SELECT last_insert_rowid()");
        String id = rs.getString(1);
        this.wordID = id;
        if (descriptionsList != null) {
            for (int i = 0; i < descriptionsList.size(); i++) {
                descriptionsList.get(i).saveData(id);
                System.out.println(descriptionsList.get(i));
            }
        }
    }

     //TODO: instead of actually updating the database we delete everything from the old word
    //Pseudo-update
    //Perform delete all child of WordBlock first then save them again
    //The only actual database update is on WordBlock
    public void updateInDatabase() throws SQLException {
        deleteFromDatabase();
        saveData();
    }

    public void starInDatabase(Boolean isStarred) throws SQLException {
        String query ="UPDATE word SET favourite = ? WHERE word_id = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, isStarred.toString());
        statement.setString(2, wordID);
        statement.executeUpdate();
    }

    public void deleteFromDatabase() throws SQLException {
        if (descriptionsList != null) {
            for (WordDescription description : descriptionsList) {
                description.deleteFromDatabase(wordID);
            }
        }

        String query = "DELETE FROM word WHERE word_id = ?";
        PreparedStatement statement = Database.getConnection().prepareStatement(query);
        statement.setString(1, wordID);
        statement.execute();
    }

//    public static void main(String[] args) throws SQLException {
//        loadWordBlocks();
//
//        for (int j = 82252; j < 82305; j++ ) {
//            String key = String.valueOf(j);
//            for (int i = DicManager.getInstance().getDictionary().getWordBlocks().size() - 1; i >= 0; i--) {
//                if (DicManager.getInstance().getDictionary().getWordBlocks().get(i).getWordID().equals(key)) {
//                    DicManager.getInstance().getDictionary().getWordBlocks().get(i).loadData(key);
//                    DicManager.getInstance().getDictionary().getWordBlocks().get(i).deleteFromDatabase();
//                    System.out.println(DicManager.getInstance().getDictionary().getWordBlocks().get(i).getWord());
//                }
//            }
//        }
//
//    }

    public static void loadWordBlocks() throws SQLException {
        Statement statement =  Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM word ORDER BY LOWER(word)");

        while (resultSet.next()) {
            WordBlock wordBlock = new WordBlock(resultSet.getString("word"), resultSet.getString("sound"));
            wordBlock.setWordID(resultSet.getString("word_id"));
            if (resultSet.getString("is_editable").equals("1")) {
                wordBlock.setEditable(true);
            }

            if (resultSet.getString("star").equals("false")) {
                wordBlock.isStarred = false;
            }
//            System.out.println(wordBlock.getWord());
            DicManager.getInstance().addWordBlock(wordBlock);
            if (wordBlock.editable) {
                EditWordSceneController.getEditableWordList().add(wordBlock);
            }

            if (wordBlock.isStarred) {
                DictionarySceneController.getStarredWordList().add(wordBlock);
            }
        }
    }

}
