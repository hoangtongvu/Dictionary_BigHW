package WordEditing;

import org.w3c.dom.Node;

import java.util.List;

public class NodeJSON {
    private String type;
    private String ID;
    private double layoutX;
    private double layoutY;

    public String getType() {
        return type;
    }

    public double getLayoutX() {
        return layoutX;
    }

    public double getLayoutY() {
        return layoutY;
    }

    public String getID() {
        return ID;
    }

    public NodeJSON(String type, String ID, double layoutX, double layoutY) {
        this.type = type;
        this.ID = ID;
        this.layoutX = layoutX;
        this.layoutY = layoutY;
    }
}
