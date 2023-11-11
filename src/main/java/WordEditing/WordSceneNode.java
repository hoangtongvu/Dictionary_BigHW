package WordEditing;

import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Side;
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
    public static WordSceneNode currentlySelected = null;
    protected static boolean bulkSelect = false;
    private double startX = 0;
    private double startY = 0;
    private static NodeOptions options = new NodeOptions();

    public static WordSceneNode getCurrentlySelected() {
        return currentlySelected;
    }

    public static void setCurrentlySelected(WordSceneNode currentlySelected) {
        WordSceneNode.currentlySelected = currentlySelected;
    }


    public abstract void addChild(WordSceneNode wordSceneNode);

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public static boolean isBulkSelect() {
        return bulkSelect;
    }

    public WordSceneNode() {

    }

    public double getStartX() {
        return startX;
    }

    public double getStartY() {
        return startY;
    }
    public VBox getNodePane() {
        return nodePane;
    }

    public void setStartX(double startX) {
        this.startX = startX;
    }

    public void setStartY(double startY) {
        this.startY = startY;
    }
    public void setNodePanePosition(double x, double y) {
        nodePane.setLayoutX(x);
        nodePane.setLayoutY(y);
    }



    public static void setBulkSelect(boolean bulkSelect) {
        WordSceneNode.bulkSelect = bulkSelect;
    }


    public WordSceneNode(String titleString) {
        nodePane = new VBox();
        title = new Label();
        title.setText(titleString);


        title.getStylesheets().getClass().getResource("/css/EditWord.css");
        nodePane.getStylesheets().getClass().getResource("/css/EditWord.css");

        title.getStyleClass().add("node-title");
        nodePane.getStyleClass().add("node-pane");
        clicked = PseudoClass.getPseudoClass("clicked");

        title.prefWidthProperty().bind(nodePane.widthProperty());
        nodePane.setMinWidth(200);
        nodePane.setMaxWidth(200);
        nodePane.getChildren().add(title);
        nodePane.setLayoutX(0);
        nodePane.setLayoutY(0);
        nodePane.addEventHandler(MouseEvent.MOUSE_PRESSED, pressHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_CLICKED, clickHandler);
        nodePane.setPrefHeight(100);
        wordSceneNodeList.add(this);

    }

    EventHandler<MouseEvent> clickHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.SECONDARY) {
                event.consume();
                System.out.println(event.getSource());
                options.getOptions().show(nodePane, Side.BOTTOM, 0, 0);
                deselectAll();
                select(WordSceneNode.this);
                currentlySelected = WordSceneNode.this;
            }
            if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                deselectAll();
                select(WordSceneNode.this);
                currentlySelected = WordSceneNode.this;
            }
        }
    };

    EventHandler<MouseEvent> pressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                if (!bulkSelect) {
                    startX = event.getSceneX() - nodePane.getLayoutX();
                    startY = event.getSceneY() - nodePane.getLayoutY();
                    if (currentlySelected != null) {
                        WordSceneNode.deselect(currentlySelected);
                    }
                    currentlySelected = WordSceneNode.this;
                    WordSceneNode.select(currentlySelected);
                } else {
                    for (WordSceneNode wordSceneNode : wordSceneNodeList) {
                        wordSceneNode.setStartX(event.getSceneX() - wordSceneNode.getNodePane().getLayoutX());
                        wordSceneNode.setStartY(event.getSceneY() - wordSceneNode.getNodePane().getLayoutY());
                    }
                }
            }
//            System.out.println(selectedCount);
        }
    };

    EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            options.getOptions().hide();
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!bulkSelect) {
                    event.consume();
                    nodePane.setLayoutX(event.getSceneX() - startX); //MouseX - offSet
                    nodePane.setLayoutY(event.getSceneY() - startY);

//                    System.out.println("B" + event.getSceneX() + " " + offSetX);
                } else {
                    event.consume();
                    for (WordSceneNode wordSceneNode : wordSceneNodeList) {
                        if (selected) {
                            if (wordSceneNode.isSelected()) {
                                double currentX = wordSceneNode.getStartX();
                                double currentY = wordSceneNode.getStartY();
                                wordSceneNode.getNodePane().setLayoutX(event.getSceneX() - currentX);
                                wordSceneNode.getNodePane().setLayoutY(event.getSceneY() - currentY);
                            }
                        }
                    }
                }
            }
        }
    };


    public static void deselectAll() {
        if (!wordSceneNodeList.isEmpty()) {
            for (WordSceneNode node : wordSceneNodeList) {
                deselect(node);
            }
            bulkSelect = false;
        }
    }

    public static void select(WordSceneNode node) {
        node.setSelected(true);
        node.getNodePane().pseudoClassStateChanged(clicked, true);
    }

    public static void deselect(WordSceneNode node) {
        node.setSelected(false);
        node.getNodePane().pseudoClassStateChanged(clicked, false);
    }
    public void compareWithMouse(Rectangle rectangle) {
        //Collision detection
        double rectLeft     = rectangle.getLayoutX();
        double rectRight    = rectangle.getLayoutX() + rectangle.getWidth();
        double rectTop      = rectangle.getLayoutY();
        double rectBot      = rectangle.getLayoutY() + rectangle.getHeight();

        double paneLeft     = nodePane.getLayoutX();
        double paneRight    = nodePane.getLayoutX() + nodePane.getWidth();
        double paneTop      = nodePane.getLayoutY();
        double paneBot      = nodePane.getLayoutY() + nodePane.getHeight();

        boolean collisionDetected = false;

        if (rectLeft < paneRight && rectRight > paneLeft) {
            if (rectTop < paneBot && rectBot > paneTop) {
                collisionDetected = true;
            }
        }
        if (collisionDetected) {
            getNodePane().pseudoClassStateChanged(clicked,true);
            setSelected(true);
        }

    }
}
