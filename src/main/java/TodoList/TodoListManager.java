package TodoList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;

public class TodoListManager
{
    private final ObservableList<Pair<TickStatus, String>> list;

    public ObservableList<Pair<TickStatus, String>> getList() {
        return list;
    }

    public TodoListManager()
    {
        this.list = FXCollections.observableArrayList();
    }

    public void Tick(int i)
    {
        Pair<TickStatus, String> oldPair = this.list.get(i);
        TickStatus newStatus = (oldPair.getKey() == TickStatus.YES)? TickStatus.NO :TickStatus.YES;
        this.list.set(i, new Pair<>(newStatus, oldPair.getValue()));
    }

    public void CreateNewTodoElement(String content)
    {
        Pair<TickStatus, String> newPair = new Pair<>(TickStatus.NO, content);
        this.list.add(newPair);
    }

    public void DeleteTodoElementAt(int i)
    {
        this.list.remove(i);
    }

}
