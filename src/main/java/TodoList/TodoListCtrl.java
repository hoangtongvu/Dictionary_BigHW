package TodoList;

import TodoList.FileManager.TodoListFileManager;
import javafx.util.Pair;

import java.util.List;

public class TodoListCtrl
{
    private static TodoListCtrl instance;

    private final TodoListManager todoListManager;
    private final TodoListFileManager todoListFileManager;


    public static TodoListCtrl getInstance() {
        if (instance == null) instance = new TodoListCtrl();
        return instance;
    }

    public TodoListManager getTodoListManager() {
        return todoListManager;
    }

    private TodoListCtrl()
    {
        this.todoListManager = new TodoListManager();
        this.todoListFileManager = new TodoListFileManager(this);
    }

    public static void main(String[] args) {
        TodoListCtrl todoListCtrl = TodoListCtrl.getInstance();
        List<Pair<TickStatus, String>> list = todoListCtrl.getTodoListManager().getList();
        list.add(new Pair<>(TickStatus.YES, "say hi to me"));
        list.add(new Pair<>(TickStatus.NO, "adfafe"));
        list.add(new Pair<>(TickStatus.NO, "t"));
        list.add(new Pair<>(TickStatus.YES, "5 me"));
        list.add(new Pair<>(TickStatus.YES, "q"));

        todoListCtrl.todoListManager.Tick(1);
        todoListCtrl.todoListManager.Tick(2);
        todoListCtrl.todoListManager.Tick(2);
        todoListCtrl.todoListFileManager.getSaver().Save();
        todoListCtrl.todoListFileManager.getLoader().Load();

        list.forEach(System.out::println);
    }
}
