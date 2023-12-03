package TodoList;

import UnsortedScript.FileManager.FileManager;
import UnsortedScript.FileManager.FileSaver;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class TodoListSaver extends FileSaver<String>
{

    public TodoListSaver(FileManager<String> fileManager)
    {
        super(fileManager);
    }

    @Override
    protected void WriteIntoFile(BufferedWriter writer, List<String> list) throws IOException
    {
        writer.write("hci");
    }
}
