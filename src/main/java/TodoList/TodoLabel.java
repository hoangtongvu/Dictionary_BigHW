package TodoList;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TodoLabel implements Initializable
{
    @FXML private Label label;


    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.SetOnMouseClick();
    }

    public static TodoLabel CreateInstance()
    {
        FXMLLoader loader = new FXMLLoader(TodoLabel.class.getResource("/fxml/TodoList/TodoLabel.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.getController();
    }

    private void SetOnMouseClick()
    {
        this.label.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent mouseEvent)
            {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                {
                    if (mouseEvent.getClickCount() == 2)
                    {
                        System.out.println("hi");
                    }
                }
            }
        });
    }

}
