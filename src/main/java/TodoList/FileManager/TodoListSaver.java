package TodoList.FileManager;

import TodoList.TickStatus;
import UnsortedScript.FileManager.FileManager;
import UnsortedScript.FileManager.FileSaver;
import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public class TodoListSaver extends FileSaver<Pair<TickStatus, String>>
{

    public TodoListSaver(FileManager<Pair<TickStatus, String>> fileManager)
    {
        super(fileManager);
    }

    @Override
    protected void WriteIntoFile(BufferedWriter writer, List<Pair<TickStatus, String>> list) throws IOException
    {
        for (Pair<TickStatus, String> pair : list)
        {
            this.Writeln(writer, pair.getKey().toString());
            this.Writeln(writer, pair.getValue());
        }
    }

    private void Writeln(BufferedWriter writer, String content) throws IOException
    {
        writer.write(content);
        writer.newLine();
    }

}
