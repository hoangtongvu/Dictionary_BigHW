package TodoList;

import UnsortedScript.FileManager.FileLoader;
import UnsortedScript.FileManager.FileManager;
import UnsortedScript.FileManager.FileSaver;
import javafx.util.Pair;

public class TodoListFileManager extends FileManager<Pair<TickStatus, String>>
{


    public TodoListFileManager(TodoListCtrl todoListCtrl) {
        super(todoListCtrl.getTodoListManager().getList());
    }

    @Override
    protected FileLoader<Pair<TickStatus, String>> CreateFileLoader() {
        return new TodoListLoader(this);
    }

    @Override
    protected FileSaver<Pair<TickStatus, String>> CreateFileSaver() {
        return new TodoListSaver(this);
    }

    @Override
    protected String GetDirectoryPath() {
        return "/data";
    }

    @Override
    protected String GetLocalFilePath() {
        return "/userTodoList.txt";
    }

    public static void main(String[] args)
    {

    }
}
