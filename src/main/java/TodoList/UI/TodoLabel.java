package TodoList.UI;

import CustomEventPackage.ZeroParameter.CustomEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TodoLabel implements Initializable
{
    @FXML private HBox rootHbox;
    @FXML private Label label;

    private TodoList todoList;
    public final CustomEvent onDoubleClickEvent;


    public TodoLabel()
    {
        this.onDoubleClickEvent = new CustomEvent(this);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.SetOnMouseClick();
    }

    public static TodoLabel CreateInstance(TodoList todoList)
    {
        FXMLLoader loader = new FXMLLoader(TodoLabel.class.getResource("/fxml/TodoList/TodoLabel.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        TodoLabel todoLabel = loader.getController();
        todoLabel.todoList = todoList;
        return todoLabel;
    }

    private void SetOnMouseClick()
    {
        this.label.setOnMouseClicked(mouseEvent ->
        {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
            {
                if (mouseEvent.getClickCount() == 2)
                {
                    OnDoubleClick();
                }
            }
        });
    }

    private void OnDoubleClick()
    {
        System.out.println("hi");
        this.onDoubleClickEvent.Invoke(this);
    }

    public void SetNewParentPane(Pane pane)
    {
        pane.getChildren().add(this.rootHbox);
    }

    public void SetUnderLine(boolean value)
    {
        this.label.setUnderline(value);
    }

    public void SetContent(String value)
    {
        this.label.setText(value);
    }

}
