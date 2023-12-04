package UnsortedScript.FileManager;

import Main.ProjectDirectory;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public abstract class FileManager <T>
{
    private final ObservableList<T> observableList;
    private File file;
    private final FileLoader<T> loader;
    private final FileSaver<T> saver;

    public FileLoader<T> getLoader() {
        return loader;
    }

    public FileSaver<T> getSaver() {
        return saver;
    }

    public File getFile() {
        return file;
    }

    public FileManager(ObservableList<T> observableList)
    {
        this.observableList = observableList;
        this.LoadFile();
        this.loader = this.CreateFileLoader();
        this.saver = this.CreateFileSaver();
    }

    private void LoadFile()
    {
        this.file = new File(ProjectDirectory.resourcesPath + this.GetDirectoryPath() + this.GetLocalFilePath());
        if (this.file.exists()) return;
        //create a new file.
        this.CreateDataDirectory();
        this.CreateModelListFile();

    }

    protected abstract FileLoader<T> CreateFileLoader();
    protected abstract FileSaver<T> CreateFileSaver();

    private void CreateDataDirectory()
    {
        try {
            Files.createDirectory(Paths.get(ProjectDirectory.resourcesPath + this.GetDirectoryPath()));
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }

    private void CreateModelListFile()
    {
        try {
            this.file.createNewFile();
        } catch (IOException e) {
            //throw new RuntimeException(e);
        }
    }

    protected abstract String GetDirectoryPath();
    protected abstract String GetLocalFilePath();

    public ObservableList<T> getObservableList() {
        return observableList;
    }
}
