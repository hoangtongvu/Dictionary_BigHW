package WordEditing;

import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class NodeOptions {
    private final MenuItem connect = new MenuItem("Connect");
    private final MenuItem delete = new MenuItem("Delete");
    private final MenuItem addDef = new MenuItem("Add Definition");
    private final MenuItem addDes = new MenuItem("Add Description");
    private final MenuItem addEx  = new MenuItem("Add Example");
    private final MenuItem addPhrase = new MenuItem("Add Phrase");
    ContextMenu options = new ContextMenu();
    public NodeOptions() {

    }


    public MenuItem getAddDef() {
        return addDef;
    }

    public MenuItem getAddDes() {
        return addDes;
    }

    public MenuItem getAddEx() {
        return addEx;
    }

    public MenuItem getAddPhrase() {
        return addPhrase;
    }

    public MenuItem getConnect() {
        return connect;
    }

    public MenuItem getDelete() {
        return delete;
    }

    public ContextMenu getOptions() {
        return options;
    }

}
