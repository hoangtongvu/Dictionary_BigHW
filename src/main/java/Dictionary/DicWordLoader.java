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
        WordBlock.loadWordBlocks();
    }
}
