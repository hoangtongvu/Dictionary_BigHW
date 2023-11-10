package AddWordGraph;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;

public class NodeOptions {
    private MenuItem connect = new MenuItem("Connect");
    ContextMenu options = new ContextMenu();
    public NodeOptions() {
        Label temp = new Label("Connect");
        temp.setMinWidth(100);
        connect.setGraphic(temp);
        options.getItems().add(connect);
    }

    public ContextMenu getOptions() {
        return options;
    }

}
