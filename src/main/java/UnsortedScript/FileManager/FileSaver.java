package UnsortedScript.FileManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class FileSaver <T>
{
    private final FileManager<T> fileManager;

    public FileSaver(FileManager<T> fileManager)
    {
        this.fileManager = fileManager;
    }

    public final void Save()
    {
        File file = this.fileManager.getFile();
        List<T> list = this.fileManager.getObservableList();

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            this.WriteIntoFile(writer, list);

            writer.close();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    protected abstract void WriteIntoFile(BufferedWriter writer, List<T> list) throws IOException;

}
