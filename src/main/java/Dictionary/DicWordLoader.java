package Dictionary;

import java.io.File;
import java.sql.SQLException;
import java.util.Scanner;

import Main.ProjectDirectory;
import Word.*;

public class DicWordLoader {
    private final DicManager dicManager;
    private final String defaultFilePath = ProjectDirectory.resourcesPath + "/data/anhviet109K.txt";


    public DicWordLoader(DicManager dicManager) {
        this.dicManager = dicManager;
    }

    public void DefaultLoad() throws Exception {
        LoadFromDatabase(this.defaultFilePath);
    }

    public void LoadFromDatabase(String filePath) throws SQLException {

    }
}
