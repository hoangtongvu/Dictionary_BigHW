package UnsortedScript.FileManager;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public abstract class FileLoader <T>
{

    private final FileManager<T> fileManager;

    public FileLoader(FileManager<T> fileManager)
    {
        this.fileManager = fileManager;
    }

    public final void Load()
    {
        File file = this.fileManager.getFile();
        List<T> list = this.fileManager.getObservableList();
        list.clear();

        Scanner scanner = this.GetScanner(file);

        while (scanner.hasNextLine())
        {
            this.LoadIntoList(scanner, list);
        }

        scanner.close();
    }

    protected abstract void LoadIntoList(Scanner scanner, List<T> list);

    private Scanner GetScanner(File file)
    {
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
