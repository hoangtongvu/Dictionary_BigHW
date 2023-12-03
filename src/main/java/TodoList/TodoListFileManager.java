package TodoList;

import UnsortedScript.FileManager.FileLoader;
import UnsortedScript.FileManager.FileManager;
import UnsortedScript.FileManager.FileSaver;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TodoListFileManager extends FileManager<String>
{


    public TodoListFileManager(ObservableList<String> observableList) {
        super(observableList);
    }

    @Override
    protected FileLoader<String> CreateFileLoader() {
        return new TodoListLoader(this);
    }

    @Override
    protected FileSaver<String> CreateFileSaver() {
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
        TodoListFileManager todoListFileManager = new TodoListFileManager(FXCollections.observableArrayList());
        todoListFileManager.getLoader().Load();
        todoListFileManager.getSaver().Save();
    }
}
