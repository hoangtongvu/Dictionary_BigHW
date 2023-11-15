package Main.SceneControllers.Dictionary;

import Word.WordBlock;
import WordEditing.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import org.controlsfx.control.spreadsheet.Grid;

public class EditWordSceneController {

    private double mouseStartX;
    private double mouseStartY;
    protected AnchorPane canvasPane;
    public static Line temporaryLine;
    static final NodeOptions options = new NodeOptions();
    protected boolean isEditing = false;

    GridPane grid = new GridPane();

    @FXML
    private Rectangle selectionRectangle;
    @FXML
    protected ScrollPane canvas;
    @FXML
    protected AnchorPane editorPane;
    @FXML
    protected Button descriptionButton;
    @FXML
    protected Button definitionButton;
    @FXML
    protected Button phraseButton;
    @FXML
    protected Button exampleButton;
    @FXML
    protected Button saveButton;
    @FXML
    protected Button connectButton;
    @FXML
    protected Button deleteButton;



    @FXML
    public void addNewWord() {
        if (isEditing) {
            if (Warnings.getInstance().addWordWarning()) {
                DicNode.saveAll();
                DicNode.setCurrentlySelected(new WordNode());
                reset();
                addNode(DicNode.getCurrentlySelected());
            }
        } else {
            setDefault(true);
            DicNode.setCurrentlySelected(new WordNode());
            isEditing = true;
            addNode(DicNode.getCurrentlySelected());
        }
    }

    public void save() {

    }

    public void delete() {

    }

    public void reset(){
        //Save the word
        canvasPane.getChildren().clear();
        DicNode.reset();
        canvasPane.getChildren().add(selectionRectangle);
        canvasPane.getChildren().add(temporaryLine);
    }

    public void setDefault(boolean flag) {
        exampleButton.setVisible(flag);
        descriptionButton.setVisible(flag);
        definitionButton.setVisible(flag);
        phraseButton.setVisible(flag);
        saveButton.setVisible(flag);
        deleteButton.setVisible(flag);
        connectButton.setVisible(flag);
    }

    @FXML
    public void deleteWord() {

    }



    @FXML
    public void initialize() {
        canvasPane = ((AnchorPane) canvas.getContent());
        temporaryLine = new Line();
        temporaryLine.setVisible(false);
        temporaryLine.setStrokeWidth(1.5);
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
        canvasPane.getChildren().add(temporaryLine);
        setDefault(false);
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


    EventHandler<MouseEvent> mousePressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (DicNode.isInConnectMode()) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    canvas.setPannable(false);
                    DicNode.deselectAllExcept(DicNode.getCurrentlySelected());
                    if (DicNode.getCurrentlySelected() != null) {
                        VBox tmp = DicNode.getCurrentlySelected().getNodePane();
                        temporaryLine.setStartX(tmp.getLayoutX() + tmp.getWidth()/2);
                        temporaryLine.setStartY(tmp.getLayoutY() + tmp.getHeight()/2);
                        temporaryLine.setEndX(tmp.getLayoutX());
                        temporaryLine.setEndY(tmp.getLayoutY());
                        temporaryLine.setVisible(true);
                    }
                } else {
                    canvas.setPannable(true);
                }
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
                if (DicNode.getNodeList().isEmpty()) {
                    temporaryLine.setVisible(false);
                }
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

    EventHandler<MouseEvent> mouseReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (DicNode.isInConnectMode()) {
                temporaryLine.setVisible(false);
                canvas.setPannable(false);
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
                DicNode.nodeList.get(i).selfDelete();
                i--;
                DicNode.setBulkSelect(false);
            }
        }
    }

    public void setAnchor(AnchorPane editorPane) {
        AnchorPane.setBottomAnchor(editorPane, 0d);
        AnchorPane.setTopAnchor(editorPane, 0d);
        AnchorPane.setRightAnchor(editorPane, 0d);
        AnchorPane.setLeftAnchor(editorPane, 0d);

    }

    public void switchScene() {
        if (DicNode.getCurrentlySelected() instanceof DescriptionNode) {
            setAnchor(((DescriptionNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((DescriptionNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        } else if (DicNode.getCurrentlySelected() instanceof ExampleNode) {
            setAnchor(((ExampleNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((ExampleNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        } else if (DicNode.getCurrentlySelected() instanceof DefinitionNode) {
            setAnchor(((DefinitionNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((DefinitionNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        } else if (DicNode.getCurrentlySelected() instanceof PhraseNode) {
            setAnchor(((PhraseNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((PhraseNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        } else if (DicNode.getCurrentlySelected() instanceof WordNode) {
            setAnchor(((WordNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((WordNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        }
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

}
