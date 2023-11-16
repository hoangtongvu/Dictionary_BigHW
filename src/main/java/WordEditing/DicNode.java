package WordEditing;

import Main.SceneControllers.Dictionary.EditWordSceneController;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public abstract class DicNode {
    public abstract void delete();
    public abstract boolean checkLink();
    protected abstract void setOptions();
    protected abstract void labelProperty(Label label, String styleClass);
    protected abstract void establishLink();

    //Covert all components displayed and saved in nodeList to a cohesive block of word
    public abstract void convertToWordBlock();


    public static List<DicNode> nodeList = new ArrayList<>();
    protected Label title;
    protected static WordNode currentlyEditedWord;
    protected static DicNode currentlySelected = null;
    protected static DicNode endNode = null;
    protected static boolean bulkSelect = false;
    protected static boolean inConnectMode = false;
    protected boolean selected = false;
    public static PseudoClass clicked;
    protected DicNode parent;
    protected List<DicNode> childrenNodeList = new ArrayList<>();
    protected VBox nodePane;
    protected Line lineToParent = new Line();
    protected NodeOptions options = new NodeOptions();
    private double startX = 0;
    private double startY = 0;


    public static List<DicNode> getNodeList() {
        return nodeList;
    }

    public Line getLineToParent() {
        return lineToParent;
    }

    public static WordNode getCurrentlyEditedWord() {
        return currentlyEditedWord;
    }

    public static DicNode getCurrentlySelected() {
        return currentlySelected;
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

    public static void setInConnectMode(boolean inConnectMode) {
        DicNode.inConnectMode = inConnectMode;
    }

    public static boolean isInConnectMode() {
        return inConnectMode;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    //Set the parent of a node and update the graphical line
    protected void setParents(DicNode parent) {
        this.parent = parent;
        updateFromChild();
    }

    public static void setCurrentlyEditedWord(WordNode newNode) {
        currentlyEditedWord = newNode;
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


    //Add child node the children node list, check for duplicates before adding
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
        nodePane.addEventHandler(MouseDragEvent.ANY, mouseDragEventEventHandler);

        options.getDelete().setOnAction(event -> {
            selfDelete();
        });
    }

    //
    public static void save() {
        for (DicNode node : nodeList) {
            if ( node instanceof WordNode || node.parent != null) {
                node.convertToWordBlock();
            }
        }
    }

    //Reset all static attributes to default value
    public static void reset() {
        currentlySelected = null;
        for (int i = 0; i < nodeList.size(); i++) {
            nodeList.get(i).selfDelete();
            i--;
        }
        inConnectMode = false;
        bulkSelect = false;
        endNode = null;

    }

    //From the current node, which is a child node of another node, this function update the line to it's parent
    protected void updateFromChild() {
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

    //
    protected void updateLine() {
        updateFromChild();
        updateFromParent();
    }

    //Traverse down to all children nodes from root node and update the line from the current node to child node
    protected void traverseDownward(DicNode root) {
        for (DicNode node : root.childrenNodeList) {
            updateFromParent();
            root.traverseDownward(node);
        }
    }

    /**Traverse the whole graph and update connected nodes
     * when in bulk selection mode. If the current node has height < leaf and height > root,
     * it will traverse to the root node and traverse down to all children node except for
     * the excluded node.
     * @param excluded this node will be excluded
     */
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

    public static void deselectAll() {
        if (!nodeList.isEmpty()) {
            for (DicNode node : nodeList) {
                deselect(node);
            }
            bulkSelect = false;
        }
        currentlySelected = null;
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

    //SEt
    public static void deselect(DicNode node) {
        node.setSelected(false);
        node.getNodePane().pseudoClassStateChanged(clicked, false);
    }

    /** Delete the node from the instance where this method was called
     * + Remove from nodeList
     * + Remove from nodePane
     * + Remove the line from parents
     * + Set all children nodes' parent to null and set the line INVISIBLE NOT DELETE
     */
    public void selfDelete() {
        try {
            ((AnchorPane) nodePane.getParent()).getChildren().remove(nodePane);
            ((AnchorPane) lineToParent.getParent()).getChildren().remove(lineToParent);
            for (DicNode node : childrenNodeList) {
                node.setParents(null);
                node.delete();
                node.lineToParent.setVisible(false);
            }
            nodeList.remove(this);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**Checking collision with selection rectangle
     *
     * @param rectangle input rectangle which is the selection rectangle from the controller
     */
    public void compareWithMouse(Rectangle rectangle) {
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

    EventHandler<MouseEvent> pressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            //Handling connect mode
            if (inConnectMode && event.getButton() == MouseButton.PRIMARY) {
                deselectAll();
                select(DicNode.this);
                DicNode.currentlySelected = DicNode.this;
            } else if (event.getButton() == MouseButton.PRIMARY) {
                //Handling dragging object mode
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
        }
    };

    EventHandler<MouseEvent> mouseReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (inConnectMode) {
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

    EventHandler<MouseDragEvent> mouseDragEventEventHandler = new EventHandler<MouseDragEvent>() {
        @Override
        public void handle(MouseDragEvent event) {
            if (event.getEventType() == MouseDragEvent.MOUSE_DRAG_ENTERED) {
                if (DicNode.this != DicNode.currentlySelected) {
                    if (endNode != null) {
                        deselect(endNode);
                    }
                    DicNode.endNode = DicNode.this;
                    select(endNode);
                    if (currentlySelected.checkLink()) {
                        EditWordSceneController.temporaryLine.setStroke(Color.GREEN);
//                        System.out.println("VALID");
                    } else {
//                        System.out.println("INVALID");
                        EditWordSceneController.temporaryLine.setStroke(Color.RED);
                    }
                }
            }

            if (event.getEventType() == MouseDragEvent.MOUSE_DRAG_EXITED) {
                EditWordSceneController.temporaryLine.setStroke(Color.BLACK);
                endNode = null;
            }
        }
    };


}


