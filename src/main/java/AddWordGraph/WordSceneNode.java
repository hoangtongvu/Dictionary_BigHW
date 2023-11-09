package AddWordGraph;

import Main.ProjectDirectory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.util.List;

public abstract class WordSceneNode {
    private Node lastClicked = null;
    protected Label title;
    protected boolean selected = false;
    protected WordSceneNode parent;
    protected List<WordSceneNode> childrenWordSceneNodes;
    protected VBox nodePane;


    public abstract void addChild(WordSceneNode wordSceneNode);

    public WordSceneNode() {

    }

    public WordSceneNode(String titleString) {
        nodePane = new VBox();
        title = new Label();
        title.setText(titleString);

        title.getStylesheets().getClass().getResource("/css/test.css");
        nodePane.getStylesheets().getClass().getResource("/css/test.css");
        nodePane.getStyleClass().add("node-pane");
        title.getStyleClass().add("node-title");
        nodePane.setPrefWidth(title.getWidth() + 100);
        nodePane.getChildren().add(title);
        nodePane.setLayoutX(0);
        nodePane.setLayoutY(0);
        nodePane.addEventHandler(MouseEvent.MOUSE_PRESSED, clickHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);
    }

    public VBox getNodePane() {
        return nodePane;
    }

    private double startX = 0;
    private double startY = 0;

    EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                selected = true;
                startX = event.getSceneX() - nodePane.getTranslateX();
                startY = event.getSceneY() - nodePane.getTranslateY();
            }
        }
    };

    EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            event.consume();
            nodePane.setTranslateX(event.getSceneX() - startX);
            nodePane.setTranslateY(event.getSceneY() - startY);
        }
    };

}
