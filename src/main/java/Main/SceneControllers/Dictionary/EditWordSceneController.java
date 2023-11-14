package Main.SceneControllers.Dictionary;

import WordEditing.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class EditWordSceneController {
    @FXML
    protected ScrollPane canvas;
    private double mouseStartX;
    private double mouseStartY;
    @FXML
    private Rectangle selectionRectangle;
    //TODO: Get selected attribute to here instead of in wordScene node, this is more of a front end thing
    //TODO: Remove wordSceneNodeList in wordSceneNode

    protected AnchorPane canvasPane;
    public static Line temporaryLine;
    static final NodeOptions options = new NodeOptions();

    @FXML
    protected AnchorPane editorPane;


    @FXML
    public void initialize() {
        temporaryLine = new Line();
        temporaryLine.setVisible(false);
        options.getOptions().getItems().addAll(
                options.getConnect(),
                options.getDelete(),
                options.getAddEx(),
                options.getAddDes(),
                options.getAddDef(),
                options.getAddPhrase()
        );

        options.getAddDes().setOnAction(event -> {
            addDescription();
        });

        options.getConnect().setOnAction(event -> {
            DicNode.setInConnectMode(!DicNode.isInConnectMode());
        });

        options.getAddDef().setOnAction(event -> {
            addDefinition();
        });

        options.getDelete().setOnAction(event -> {
            deleteNode();
        });

        options.getAddPhrase().setOnAction(event -> {
            addPhrase();
        });

        options.getAddEx().setOnAction(event -> {
            addExample();
        });

        canvas.getContent().addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressHandler);
        canvas.getContent().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragHandler);
        canvas.getContent().addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleaseHandler);
        canvas.getContent().addEventHandler(MouseEvent.DRAG_DETECTED, dragDetected);
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
        ((AnchorPane) canvas.getContent()).getChildren().add(temporaryLine);

    }



    @FXML
    public void addDescription() {
        addNode(new DescriptionNode());
    }

    @FXML
    public void addDefinition() {
        addNode(new DefinitionNode());
    }

    @FXML
    public void addExample() {
        addNode(new ExampleNode());
    }

    @FXML
    public void addPhrase() {
        addNode(new PhraseNode());
    }

    @FXML
    public void toggleConnectMode() {
        DicNode.setInConnectMode(!DicNode.isInConnectMode());
    }
    public void addNode(DicNode node) {
        DicNode.getNodeList().add(node);
        canvasPane = (AnchorPane) canvas.getContent();
        canvasPane.getChildren().add(node.getNodePane());
        canvasPane.getChildren().add(node.getLineToParent());
        node.getLineToParent().toBack();
        node.setNodePanePosition((-1) * canvas.getViewportBounds().getMinX(),
                (-1) * canvas.getViewportBounds().getMinY());
    }


    EventHandler<MouseEvent> mousePressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (DicNode.isInConnectMode()) {
//                temporaryLine.toFront();
                DicNode.deselectAllExcept(DicNode.getCurrentlySelected());
                VBox tmp = DicNode.getCurrentlySelected().getNodePane();
                temporaryLine.setStartX(tmp.getLayoutX() + tmp.getWidth()/2);
                temporaryLine.setStartY(tmp.getLayoutY() + tmp.getHeight()/2);
                temporaryLine.setEndX(tmp.getLayoutX());
                temporaryLine.setEndY(tmp.getLayoutY());
                temporaryLine.setVisible(true);
            } else if (event.getButton() == MouseButton.PRIMARY) {
                DicNode.deselectAll();
                mouseStartX = event.getX();
                mouseStartY = event.getY();
                selectionRectangle.setLayoutX(mouseStartX);
                selectionRectangle.setLayoutY(mouseStartY);
                selectionRectangle.setVisible(true);
                selectionRectangle.toFront();
            }
        }
    };

    EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

        }
    };

    EventHandler<MouseEvent> mouseDragHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (DicNode.isInConnectMode()) {
                if (DicNode.getEndNode() != null) {
                    temporaryLine.setEndX(DicNode.getEndNode().getNodePane().getLayoutX() + DicNode.getEndNode().getNodePane().getWidth()/2);
                    temporaryLine.setEndY(DicNode.getEndNode().getNodePane().getLayoutY() + DicNode.getEndNode().getNodePane().getHeight()/2);
                } else {
                    temporaryLine.setEndX(event.getX());
                    temporaryLine.setEndY(event.getY());
                }
            } else if (event.getButton() == MouseButton.PRIMARY) {
                double currentX = event.getX();
                double currentY = event.getY();

                // Calculate the width and height based on the mouse movement
                double width = Math.abs(currentX - mouseStartX);
                double height = Math.abs(currentY - mouseStartY);

                // Adjust the position of the rectangle based on the mouse movement
                double layoutX = Math.min(currentX, mouseStartX);
                double layoutY = Math.min(currentY, mouseStartY);

                selectionRectangle.setLayoutX(layoutX);
                selectionRectangle.setLayoutY(layoutY);

                selectionRectangle.setWidth(width);
                selectionRectangle.setHeight(height);

                for (DicNode node : DicNode.getNodeList()) {
                    node.compareWithMouse(selectionRectangle);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                canvas.setPannable(true);
            }
        }
    };

    public void setAnchor(AnchorPane editorPane) {
        AnchorPane.setBottomAnchor(editorPane, 0d);
        AnchorPane.setTopAnchor(editorPane, 0d);
        AnchorPane.setRightAnchor(editorPane, 0d);
        AnchorPane.setLeftAnchor(editorPane, 0d);

    }

    public void switchScene() {
        if (DicNode.getCurrentlySelected() instanceof DescriptionNode) {
            setAnchor(DicNode.getDescriptionEditor());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(DicNode.getDescriptionEditor());
        } else if (DicNode.getCurrentlySelected() instanceof ExampleNode) {
                setAnchor(DicNode.getExampleEditor());
                editorPane.getChildren().clear();
                editorPane.getChildren().add(DicNode.getExampleEditor());
        } else if (DicNode.getCurrentlySelected() instanceof DefinitionNode) {
            setAnchor(DicNode.getDefinitionEditor());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(DicNode.getDefinitionEditor());
        } else {
            setAnchor(DicNode.getPhraseEditor());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(DicNode.getPhraseEditor());
        }
    }

    EventHandler<MouseEvent> mouseReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (DicNode.isInConnectMode()) {
                temporaryLine.setVisible(false);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                selectionRectangle.setLayoutX(event.getX());
                selectionRectangle.setLayoutY(event.getY());
//            System.out.println(event.getSource());
                if (!canvas.isPannable()) {
                    options.getOptions().show(selectionRectangle, Side.BOTTOM, 0 , 0);
                }
                canvas.setPannable(false);
            } else if (event.getButton() == MouseButton.PRIMARY) {
                switchScene();
            }


            int cnt = 0;
            for (DicNode node : DicNode.getNodeList()) {
                if (node.isSelected()) {
                    cnt++;
                }
                if (cnt > 1) {
                    DicNode.setBulkSelect(true);
                    break;
                }
            }
            resetSelectionRectangle();
        }
    };



    EventHandler<KeyEvent> keyPressHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.DELETE && !DicNode.getNodeList().isEmpty()) {
                deleteNode();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                DicNode.deselectAll();
            }
        }
    };


    // This method resets the selection rectangle to its initial state
    private void resetSelectionRectangle() {
        selectionRectangle.setVisible(false);
        selectionRectangle.setWidth(0);
        selectionRectangle.setHeight(0);
        selectionRectangle.setLayoutX(-5);
        selectionRectangle.setLayoutY(-5);
    }

    public void deleteNode() {
        for (int i = 0; i < DicNode.getNodeList().size(); i++) {
            if (DicNode.getNodeList().get(i).isSelected()) {
                canvasPane = (AnchorPane) canvas.getContent();
                canvasPane.getChildren().remove(DicNode.getNodeList().get(i).getNodePane());
                canvasPane.getChildren().remove(DicNode.getNodeList().get(i).getLineToParent());
                DicNode.getNodeList().remove(i);
                i--;
                DicNode.setBulkSelect(false);
            }
        }
    }

}
