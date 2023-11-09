package AddWordGraph;

import Main.ProjectDirectory;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class WordSceneNode {
    protected Label title;
    protected boolean selected = false;
    protected WordSceneNode parent;
    protected static List<WordSceneNode> wordSceneNodeList = new ArrayList<>();
    protected List<WordSceneNode> childrenNode;
    protected VBox nodePane;
    public static PseudoClass clicked;
    public static WordSceneNode selector = null;
    public static int selectedCount = 0;

    public abstract void addChild(WordSceneNode wordSceneNode);

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public WordSceneNode() {

    }

    public WordSceneNode(String titleString) {
        nodePane = new VBox();
        title = new Label();
        title.setText(titleString);

        title.getStylesheets().getClass().getResource("/css/test.css");
        nodePane.getStylesheets().getClass().getResource("/css/test.css");

        title.getStyleClass().add("node-title");
        clicked = PseudoClass.getPseudoClass("clicked");
        nodePane.getStyleClass().add("node-pane");

        nodePane.setPrefWidth(title.getWidth() + 100);
        nodePane.getChildren().add(title);
        nodePane.setLayoutX(0);
        nodePane.setLayoutY(0);
        nodePane.addEventHandler(MouseEvent.MOUSE_PRESSED, pressHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);

        wordSceneNodeList.add(this);
    }

    public VBox getNodePane() {
        return nodePane;
    }

    private double startX = 0;
    private double startY = 0;

    EventHandler<MouseEvent> pressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
//                System.out.println(nodePane.getTranslateX());
                startX = event.getSceneX() - nodePane.getTranslateX();
                startY = event.getSceneY() - nodePane.getTranslateY();

                if (selector != null) {
                    selector.getNodePane().pseudoClassStateChanged(clicked, false);
                    selector.setSelected(false);
                }
                selector = WordSceneNode.this;
                selector.getNodePane().pseudoClassStateChanged(clicked,true);
                selector.setSelected(true);
            }
//            System.out.println(selectedCount);
        }
    };

    EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                nodePane.setTranslateX(event.getSceneX() - startX);
                nodePane.setTranslateY(event.getSceneY() - startY);
            }
        }
    };

    public static void deselectAll() {
        if (!wordSceneNodeList.isEmpty()) {
            for (WordSceneNode node : wordSceneNodeList) {
                node.setSelected(false);
                node.getNodePane().pseudoClassStateChanged(clicked, false);
            }
        }
    }

    public void compareWithMouse(Rectangle rectangle) {
        if (rectangle.getX() <= nodePane.getTranslateX() && rectangle.getY() <= nodePane.getTranslateY()
            && (rectangle.getX() + rectangle.getWidth()) >= nodePane.getTranslateX()
            && (rectangle.getY() + rectangle.getHeight()) >= nodePane.getTranslateY()) {
            System.out.println(rectangle.getX() + " " + rectangle.getY() + " "+nodePane.getTranslateX() + " " + nodePane.getTranslateY());
            selector.getNodePane().pseudoClassStateChanged(clicked,true);
            selector.setSelected(true);
        }
    }



}
