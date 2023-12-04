package TodoList.UI;

import TodoList.TodoListCtrl;
import TodoList.TodoListManager;
import TodoList.TickStatus;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class TodoList implements Initializable
{

    @FXML private AnchorPane rootAnchorPane;
    @FXML private VBox todoHolderVbox;

    private final TodoListCtrl todoListCtrl;
    private final ObservableList<Pair<TickStatus, String>> list;
    private final List<TodoLabel> todoLabels;
    private final List<Node> nodes;


    public TodoList()
    {
        this.todoListCtrl = TodoListCtrl.getInstance();
        this.list = this.todoListCtrl.getTodoListManager().getList();
        this.todoLabels = new ArrayList<>();
        this.nodes = new ArrayList<>();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.UpdateListIntoVbox();
        list.addListener((ListChangeListener<Pair<TickStatus, String>>) change -> UpdateListIntoVbox());
        this.nodes.addAll(this.rootAnchorPane.getChildren());
    }

    public static TodoList CreateInstance()
    {
        FXMLLoader loader = new FXMLLoader(TodoList.class.getResource("/fxml/TodoList/TodoList.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.getController();
    }

    public void ChangeParent(Parent newParent)
    {
        ((Pane) newParent).getChildren().addAll(this.nodes);
    }

    private void UpdateListIntoVbox()
    {
        this.ClearAllLists();
        list.forEach(this::CreateNewTodoLabel);
        this.SubEventsForAllLabels();
    }

    private void ClearAllLists()
    {
        this.todoHolderVbox.getChildren().clear();
        this.todoLabels.clear();
    }

    private void SubEventsForAllLabels()
    {
        TodoListManager todoListManager = this.todoListCtrl.getTodoListManager();
        for (int i = 0; i < this.todoLabels.size(); i++)
        {
            TodoLabel label = this.todoLabels.get(i);
            int finalI = i;
            label.onDoubleClickEvent.AddListener(() -> todoListManager.Tick(finalI));
            label.onDeleteEvent.AddListener(() -> todoListManager.DeleteTodoElementAt(finalI));
        }
    }

    private void CreateNewTodoLabel(Pair<TickStatus, String> pair)
    {
        //System.out.println(pair);
        TickStatus tickStatus = pair.getKey();
        String content = pair.getValue();

        TodoLabel label = TodoLabel.CreateInstance(this);
        label.SetContent(content);

        switch (tickStatus)
        {
            case YES -> label.SetUnderLine(true);
            case NO -> label.SetUnderLine(false);
        }

        label.SetNewParentPane(this.todoHolderVbox);
        this.todoLabels.add(label);
    }

    @FXML
    private void CreateNewTodoLabel()
    {
        //create dummy label.
        //sub create in manager to onUserConfirm.

        TodoLabel todoLabel = TodoLabel.CreateInstance(this);
        todoLabel.SetNewParentPane(this.todoHolderVbox);
        todoLabel.ToggleEditTextField(true);

        todoLabel.onUserConfirmEvent.AddListener(this.todoListCtrl.getTodoListManager()::CreateNewTodoElement);

    }

}
