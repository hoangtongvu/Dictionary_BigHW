package Dictionary;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Dictionary;
import java.util.Scanner;

import Main.Database;
import Main.ProjectDirectory;
import Word.*;

public class DicWordLoader {
    private final DicManager dicManager;
//    private final String defaultFilePath = ProjectDirectory.resourcesPath + "/data/anhviet109K.txt";
    private static WordBlock wordBlock;

    public DicWordLoader(DicManager dicManager) {
        this.dicManager = dicManager;
    }

//    public void DefaultLoad() throws Exception {
//        LoadFromDatabase(this.defaultFilePath);
//    }

    public void LoadFromDatabase() throws SQLException {
        Statement statement =  Database.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM word ORDER BY word ASC");

        while (resultSet.next()) {
            wordBlock = new WordBlock(resultSet.getString("word"),resultSet.getString("sound"));
            wordBlock.setWordID(resultSet.getString("word_id"));
            if (resultSet.getString("is_editable").equals("1")) {
                wordBlock.setEditable(true);
            }
//            System.out.println(wordBlock.getWord());
            DicManager.getInstance().addWordBlock(wordBlock);
        }

    }
}
