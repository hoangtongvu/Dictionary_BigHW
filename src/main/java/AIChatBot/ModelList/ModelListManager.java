package AIChatBot.ModelList;

import Main.ProjectDirectory;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.util.Pair;

import java.io.File;
import java.io.IOException;

public class ModelListManager
{
    private final String modelListDir = ProjectDirectory.resourcesPath + "/AIBotData/ModelList.txt";

    private File modelListFile;

    private final ModelListLoader modelListLoader;
    private final ModelListSaver modelListSaver;

    private final ObservableList<Pair<String, String>> modelNameAndPaths;

    public File getModelListFile() {
        return modelListFile;
    }

    public ObservableList<Pair<String, String>> getModelNameAndPaths() {
        return modelNameAndPaths;
    }



    public ModelListManager()
    {
        this.LoadFile();
        this.modelListLoader = new ModelListLoader(this);
        this.modelListSaver = new ModelListSaver(this);
        this.modelNameAndPaths = FXCollections.observableArrayList();
        this.modelListLoader.Load();
        this.modelNameAndPaths.addListener((ListChangeListener<Pair<String, String>>) change -> modelListSaver.Save());
        this.modelListLoader.RemoveInvalidPaths();

    }

    private void LoadFile()
    {
        this.modelListFile = new File(this.modelListDir);
        if (this.modelListFile.exists()) return;
        //create a new file.
        try {
            this.modelListFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean AddFileIntoList(File file)
    {
        if (!file.exists()) return false;
        String path = file.getPath();
        String name = GetFileNameAfterRemoveExtension(file);
        Pair<String, String> pair = new Pair<>(name, path);
        if (this.modelNameAndPaths.contains(pair)) return false;
        this.modelNameAndPaths.add(0, pair);
        return true;
    }

    public static String GetFileNameAfterRemoveExtension(File file)
    {
        String rawFileName = file.getName();
        int lastDotPos = rawFileName.lastIndexOf(".");
        return rawFileName.substring(0, lastDotPos);
    }

    public static void main(String[] args) {
        ModelListManager modelListManager = new ModelListManager();
        File file = new File("D:\\Java\\Dictionary_BigHW\\src\\main\\resources\\AIBotData\\ModelList.txt");
        modelListManager.AddFileIntoList(file);

    }
}
