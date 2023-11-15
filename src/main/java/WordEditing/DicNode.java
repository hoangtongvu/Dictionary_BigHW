package WordEditing;

import Main.SceneControllers.Dictionary.EditWordSceneController;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class DicNode {
    public static List<DicNode> nodeList = new ArrayList<>();

    public static List<DicNode> getNodeList() {
        return nodeList;
    }

    public static void setNodeList(List<DicNode> nodeList) {
        DicNode.nodeList = nodeList;
    }

    public Line getLineToParent() {
        return lineToParent;
    }

    protected Label title;
    protected static boolean inConnectMode = false;
    protected boolean selected = false;
    public static PseudoClass clicked;
    protected static DicNode currentlySelected = null;
    protected static DicNode endNode = null;
    protected static boolean bulkSelect = false;
    protected DicNode parent;
    protected List<DicNode> childrenNodeList = new ArrayList<>();
    protected VBox nodePane;
    protected Line lineToParent = new Line();
    protected static WordNode currentlyEditedWord;

    public static WordNode getCurrentlyEditedWord() {
        return currentlyEditedWord;
    }

    public static void setCurrentlyEditedWord(WordNode newNode) {
        currentlyEditedWord = newNode;
    }

    public abstract void save();
    public static void saveAll() {
        for (DicNode node : nodeList) {
            if (node.parent != null) {
                node.save();
            }
        }
    }
    public abstract void delete();
    protected abstract void setOptions();

    protected abstract void labelProperty(Label label, String styleClass);
    protected abstract void establishLink();
    private double startX = 0;
    private double startY = 0;
    //TODO: make NodeOptions options static
    protected NodeOptions options = new NodeOptions();

    public static DicNode getCurrentlySelected() {
        return currentlySelected;
    }
    public abstract boolean checkLink();

    public static void setCurrentlySelected(DicNode currentlySelected) {
        DicNode.currentlySelected = currentlySelected;
    }

    public static void reset() {
        currentlySelected = null;
        nodeList.clear();
        inConnectMode = false;
        bulkSelect = false;
        endNode = null;
    }
    public static boolean isInConnectMode() {
        return inConnectMode;
    }

    public static void setInConnectMode(boolean inConnectMode) {
        DicNode.inConnectMode = inConnectMode;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public static boolean isBulkSelect() {
        return bulkSelect;
    }

    protected void addChild(DicNode childNode) {
        boolean flag = false;
        if (!childrenNodeList.isEmpty()) {
            for (DicNode node : childrenNodeList) {
                if (node == childNode) {
                    flag = true;
                    break;
                }
            }
            if (!flag) childrenNodeList.add(childNode);
        } else {
            childrenNodeList.add(childNode);
        }

    }

    protected void setParents(DicNode parent) {
        this.parent = parent;
        System.out.println(this.toString());
        updateFromChild();
    }

    protected void updateFromChild() {//child node is 'this' and update to parent
        if (parent != null) {
            lineToParent.setStrokeWidth(1.5);
            lineToParent.setStartX(nodePane.getLayoutX() + nodePane.getWidth()/2);
            lineToParent.setStartY(nodePane.getLayoutY() + nodePane.getHeight()/2);
            lineToParent.setEndX(parent.getNodePane().getLayoutX() + parent.getNodePane().getWidth()/2);
            lineToParent.setEndY(parent.getNodePane().getLayoutY() + parent.getNodePane().getHeight()/2);
        }
    }

    protected void updateFromParent() {//From parent node parent (parent is 'this')
        for (DicNode node : childrenNodeList) {
            node.updateFromChild();
        }
    }

    protected void updateLine() {
        updateFromChild();
        updateFromParent();
    }

    protected void traverseDownward(DicNode root) {
        for (DicNode node : root.childrenNodeList) {
            updateFromParent();
            root.traverseDownward(node);
        }
    }

    protected void updateLine(DicNode excluded) { //For bulk selection mode
        if (parent != null) {
            for (DicNode node : parent.childrenNodeList) {
                if (node != excluded) {
                    //Traverse all children nodes
                    traverseDownward(parent);
                }
            }
            parent.updateLine(this);
        } else {
            //Traverse all children nodes
            traverseDownward(this);
        }
    }

    public DicNode() {

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

    public static DicNode getEndNode() {
        return endNode;
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
        DicNode.bulkSelect = bulkSelect;
    }

    public void setStyleSheet(Node object) {
        try {
            object.getStyleClass().getClass().getResource("/css/EditWord.css");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("INVALID TYPE");
        }
    }

    public void setStyleClass(Node object, String styleClass) {
        try {
            object.getStyleClass().add(styleClass);
        } catch (Exception e) {
            System.out.println("INVALID TYPE TO SET STYLE CLASS");
        }
    }
    public DicNode(String titleString) {
        nodePane = new VBox();
        title = new Label();
        title.setText(titleString);


        title.getStylesheets().getClass().getResource("/css/EditWord.css");
        nodePane.getStylesheets().getClass().getResource("/css/EditWord.css");
        setStyleSheet(title);
        setStyleSheet(nodePane);
        setStyleClass(title, "node-title");
        setStyleClass(nodePane, "node-pane");
        clicked = PseudoClass.getPseudoClass("clicked");

        title.prefWidthProperty().bind(nodePane.widthProperty());
        nodePane.setMinWidth(150);
        nodePane.setMaxWidth(150);
        nodePane.getChildren().add(title);
        nodePane.setLayoutX(0);
        nodePane.setLayoutY(0);
        nodePane.addEventHandler(MouseEvent.MOUSE_PRESSED, pressHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleaseHandler);
        nodePane.addEventHandler(MouseEvent.DRAG_DETECTED, dragDetected);
        options.getDelete().setOnAction(event -> {
            selfDelete();
        });

        nodePane.setOnMouseDragEntered(event -> {
                if (this != currentlySelected) {
                    if (endNode != null) {
                        deselect(endNode);
                    }
                    endNode = this;
                    select(endNode);
                    if (currentlySelected.checkLink()) {
                        EditWordSceneController.temporaryLine.setStroke(Color.GREEN);
                        System.out.println("VALID");
                    } else {
                        System.out.println("INVALID");
                        EditWordSceneController.temporaryLine.setStroke(Color.RED);
                    }
//                    System.out.println("Mouse drag entered: " + nodePane.toString());
//                    System.out.println(currentlySelected.getNodePane().toString());
                }
        });

        nodePane.setOnMouseDragExited(dragEvent -> {
            EditWordSceneController.temporaryLine.setStroke(Color.BLACK);
            endNode = null;
        });
    }



    EventHandler<MouseEvent> pressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            //Handling connect mode
            if (inConnectMode && event.getButton() == MouseButton.PRIMARY) {
                deselectAll();
                select(DicNode.this);
                DicNode.currentlySelected = DicNode.this;
//                System.out.println("Mouse pressed on" + nodePane.toString());
//                System.out.println(currentlySelected.getNodePane().toString());

            } else if (event.getButton() == MouseButton.PRIMARY) {
                //Handling dragging obbject mode
                event.consume();
                if (!bulkSelect) {
                    startX = event.getSceneX() - nodePane.getLayoutX();
                    startY = event.getSceneY() - nodePane.getLayoutY();
                    if (currentlySelected != null) {
                        DicNode.deselect(currentlySelected);
                    }
                    currentlySelected = DicNode.this;
                    DicNode.select(currentlySelected);
                } else if (bulkSelect) {
                    event.consume();
                    for (DicNode dicNode : nodeList) {
                        dicNode.setStartX(event.getSceneX() - dicNode.getNodePane().getLayoutX());
                        dicNode.setStartY(event.getSceneY() - dicNode.getNodePane().getLayoutY());
                    }
                }
            }
//            System.out.println(selectedCount);
        }
    };

    EventHandler<MouseEvent> mouseReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (inConnectMode) {
//                select(endNode);
//                System.out.println("Mouse released "  + nodePane.toString());
//                System.out.println(currentlySelected.getNodePane().toString());
                if (checkLink()) {
                    System.out.println("ESTABLISHED LINK");
                    establishLink();
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                event.consume();
                options.getOptions().show(nodePane, Side.BOTTOM, 0, 0);
                deselectAll();
                select(DicNode.this);
                currentlySelected = DicNode.this;
            } else if (event.getButton() == MouseButton.PRIMARY) {
//                event.consume();
                deselectAll();
                select(DicNode.this);
                currentlySelected = DicNode.this;
            }
        }
    };

    EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (inConnectMode) {
                nodePane.startFullDrag();
            }
        }
    };
    EventHandler<MouseEvent> dragHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            options.getOptions().hide();
            if (inConnectMode) {

            } else if (event.getButton() == MouseButton.PRIMARY) {
                if (!bulkSelect) {
                    event.consume();
                    nodePane.setLayoutX(event.getSceneX() - startX); //MouseX - offSet
                    nodePane.setLayoutY(event.getSceneY() - startY);
                    updateLine();
                } else if (bulkSelect) {
                    event.consume();
                    for (DicNode node : nodeList) {
                        if (selected) {
                            if (node.isSelected()) {
                                double currentX = node.getStartX();
                                double currentY = node.getStartY();
                                node.getNodePane().setLayoutX(event.getSceneX() - currentX);
                                node.getNodePane().setLayoutY(event.getSceneY() - currentY);
                                node.updateLine(node);
                            }
                        }
                    }

                }
            }
        }
    };


//    protected abstract void linkingRule();




    public static void deselectAll() {
        if (!nodeList.isEmpty()) {
            for (DicNode node : nodeList) {
                deselect(node);
            }
            bulkSelect = false;
        }
    }

    public static void deselectAllExcept(DicNode excludeNode) {
        if (!nodeList.isEmpty()) {
            for (DicNode node : nodeList) {
                if (node != excludeNode) {
                    deselect(node);
                }
            }
            bulkSelect = false;
        }
    }

    public static void select(DicNode node) {
        node.setSelected(true);
        node.getNodePane().pseudoClassStateChanged(clicked, true);
    }

    public static void deselect(DicNode node) {
        node.setSelected(false);
        node.getNodePane().pseudoClassStateChanged(clicked, false);
    }

    public void selfDelete() {
        try {
            ((AnchorPane) nodePane.getParent()).getChildren().remove(nodePane);
            ((AnchorPane) lineToParent.getParent()).getChildren().remove(lineToParent);
            for (DicNode node : childrenNodeList) {
                node.setParents(null);
                node.lineToParent.setVisible(false);
            }
            nodeList.remove(this);
//            System.out.println("Removed" + this.getNodePane().toString());
        } catch (Exception e) {
            System.out.println(e.toString());
            System.out.println("Oi dick'ead we got a problem here");
            System.out.println(nodePane.toString());
        }

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


