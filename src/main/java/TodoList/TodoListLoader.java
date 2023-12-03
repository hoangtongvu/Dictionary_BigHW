package TodoList;

import UnsortedScript.FileManager.FileLoader;
import UnsortedScript.FileManager.FileManager;

import java.util.List;
import java.util.Scanner;

public class TodoListLoader extends FileLoader<String>
{

    public TodoListLoader(FileManager<String> fileManager)
    {
        super(fileManager);
    }

    @Override
    protected void LoadIntoList(Scanner scanner, List<String> list)
    {
        list.add(scanner.nextLine());
    }
}
