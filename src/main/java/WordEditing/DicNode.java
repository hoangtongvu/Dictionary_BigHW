package WordEditing;

import Word.WordDefinition;
import javafx.css.PseudoClass;
import javafx.event.EventHandler;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.w3c.dom.NodeList;

import java.security.Key;
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

    protected Label title;
    protected static boolean inConnectMode = false;
    protected boolean selected = false;
    public static PseudoClass clicked;
    public static DicNode currentlySelected = null;
    protected static boolean bulkSelect = false;
    protected DicNode parent;
    protected List<DicNode> childrenNode;
    protected VBox nodePane;

    protected static DicNode endNode;
    protected abstract void setOptions();

    protected abstract void labelProperty(Label label, String styleClass);
    private double startX = 0;
    private double startY = 0;
    //TODO: make NodeOptions options static
    protected NodeOptions options = new NodeOptions();

    public static DicNode getCurrentlySelected() {
        return currentlySelected;
    }

    public static void setCurrentlySelected(DicNode currentlySelected) {
        DicNode.currentlySelected = currentlySelected;
    }

    public static boolean isInConnectMode() {
        return inConnectMode;
    }

    public static void setInConnectMode(boolean inConnectMode) {
        DicNode.inConnectMode = inConnectMode;
    }

    public abstract void addChild(DicNode dicNode);

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public static boolean isBulkSelect() {
        return bulkSelect;
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
        nodePane.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEnterHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_PRESSED, pressHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_DRAGGED, dragHandler);
        nodePane.addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleaseHandler);
        options.getDelete().setOnAction(event -> {
            selfDelete();
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
                System.out.println(nodePane.toString());
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
                System.out.println(nodePane.toString());
            } else if (event.getButton() == MouseButton.SECONDARY) {
                event.consume();
                options.getOptions().show(nodePane, Side.BOTTOM, 0, 0);
                deselectAll();
                select(DicNode.this);
                currentlySelected = DicNode.this;
            } else if (event.getButton() == MouseButton.PRIMARY) {
                event.consume();
                deselectAll();
                select(DicNode.this);
                currentlySelected = DicNode.this;
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

                } else if (bulkSelect) {
                    event.consume();
                    for (DicNode dicNode : nodeList) {
                        if (selected) {
                            if (dicNode.isSelected()) {
                                double currentX = dicNode.getStartX();
                                double currentY = dicNode.getStartY();
                                dicNode.getNodePane().setLayoutX(event.getSceneX() - currentX);
                                dicNode.getNodePane().setLayoutY(event.getSceneY() - currentY);
                            }
                        }
                    }
                }
            }
        }
    };

    EventHandler<MouseEvent> mouseEnterHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (inConnectMode) {
                if (DicNode.this != currentlySelected) {
                    if (endNode != null) {
                        deselect(endNode);
                    }
                    endNode = DicNode.this;
                    select(endNode);
                }
            }
        }
    };



    public static void deselectAll() {
        if (!nodeList.isEmpty()) {
            for (DicNode node : nodeList) {
                deselect(node);
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
            nodeList.remove(this);
//            System.out.println("Removed" + this.getNodePane().toString());
        } catch (Exception e) {
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


