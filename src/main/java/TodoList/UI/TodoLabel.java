package TodoList.UI;

import CustomEventPackage.ZeroParameter.CustomEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TodoLabel implements Initializable
{
    @FXML private AnchorPane rootHbox;
    @FXML private Label label;
    @FXML private TextField editTextField;

    private TodoList todoList;
    public final CustomEvent onDoubleClickEvent;
    public final CustomEvent onDeleteEvent;
    public final CustomEventPackage.OneParameter.CustomEvent<String> onUserConfirmEvent;


    public TodoLabel()
    {
        this.onDoubleClickEvent = new CustomEvent(this);
        this.onDeleteEvent = new CustomEvent(this);
        this.onUserConfirmEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
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
        this.onDoubleClickEvent.Invoke(this);
    }

    public void SetNewParentPane(Pane pane)
    {
        pane.getChildren().add(this.rootHbox);
    }

    public void SetNormal()
    {
        List<String> styles = this.label.getStylesheets();
        String normStyle = this.todoList.labelNormalStyle;
        String tickedStyle = this.todoList.labelTickedStyle;

        styles.remove(tickedStyle);
        if (!styles.contains(normStyle)) {
            styles.add(normStyle);
        }
    }

    public void SetStrikeThrough()
    {
        List<String> styles = this.label.getStylesheets();
        String normStyle = this.todoList.labelNormalStyle;
        String tickedStyle = this.todoList.labelTickedStyle;

        styles.remove(normStyle);
        if (!styles.contains(tickedStyle)) {
            styles.add(tickedStyle);
        }
    }

    public void SetContent(String value)
    {
        this.label.setText(value);
    }

    public void ToggleEditTextField(boolean value)
    {
        this.editTextField.setVisible(value);
    }

    @FXML
    private void OnTextFieldConfirm()
    {
        String content = this.editTextField.getText();
        if (content.isEmpty()) return;
        this.ToggleEditTextField(false);
        this.onUserConfirmEvent.Invoke(this, content);
    }

    @FXML
    private void Delete()
    {
        this.onDeleteEvent.Invoke(this);
    }

}
