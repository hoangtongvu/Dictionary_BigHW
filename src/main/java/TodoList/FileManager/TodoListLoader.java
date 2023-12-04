package TodoList.FileManager;

import TodoList.TickStatus;
import UnsortedScript.FileManager.FileLoader;
import UnsortedScript.FileManager.FileManager;
import javafx.util.Pair;

import java.util.List;
import java.util.Scanner;

public class TodoListLoader extends FileLoader<Pair<TickStatus, String>>
{

    public TodoListLoader(FileManager<Pair<TickStatus, String>> fileManager)
    {
        super(fileManager);
    }

    @Override
    protected void LoadIntoList(Scanner scanner, List<Pair<TickStatus, String>> list)
    {
        String tickStatusString = scanner.nextLine();
        TickStatus tickStatus = this.GetTickStatus(tickStatusString);

        String content = scanner.nextLine();

        list.add(new Pair<>(tickStatus, content));

    }

    private TickStatus GetTickStatus(String arg)
    {
        try {
            return TickStatus.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return TickStatus.UNKNOWN;
        }
    }


}
