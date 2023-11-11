package Main.SceneControllers.Dictionary;

import WordEditing.*;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class EditWordSceneController {
    @FXML
    protected ScrollPane canvas;
    private double mouseStartX;
    private double mouseStartY;
    @FXML
    private Rectangle selectionRectangle;
    protected AnchorPane canvasPane;
    protected List<WordSceneNode> canvasNodeList = new ArrayList<>();
    static int selectedNodeCount = 0;
    static final NodeOptions options = new NodeOptions();

    @FXML
    public void initialize() {
        options.getAddDes().setOnAction(event -> {
            addDescription();
        });

        options.getConnect().setOnAction(event -> {

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

    public void addNode(WordSceneNode node) {
        canvasNodeList.add(node);
        canvasPane = (AnchorPane) canvas.getContent();
        canvasPane.getChildren().add(node.getNodePane());
        node.setNodePanePosition((-1) * canvas.getViewportBounds().getMinX(),
                (-1) * canvas.getViewportBounds().getMinY());
    }

    @FXML
    public void canvasMousePressed (MouseEvent event) {
        if (event.getClickCount() > 1) {
            WordSceneNode.deselectAll();
        }

        if (event.getButton() == MouseButton.PRIMARY) {
            mouseStartX = event.getX();
            mouseStartY = event.getY();
            selectionRectangle.setLayoutX(mouseStartX);
            selectionRectangle.setLayoutY(mouseStartY);
            selectionRectangle.setVisible(true);
            selectionRectangle.toFront();
        }

    }
    @FXML
    public void canvasDrag(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
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

            for (WordSceneNode node : canvasNodeList) {
                node.compareWithMouse(selectionRectangle);
            }
        } else if (event.getButton() == MouseButton.SECONDARY) {
            canvas.setPannable(true);
        }
    }
    @FXML
    public void mouseRelease(MouseEvent event) {
        int cnt = 0;
        for (WordSceneNode node : canvasNodeList) {
            if (node.isSelected()) {
                cnt++;
            }
            if (cnt > 1) {
                WordSceneNode.setBulkSelect(true);
                break;
            }
        }
//        System.out.println(WordSceneNode.isBulkSelect());
        resetSelectionRectangle();
        canvas.setPannable(false);
    }

    public void deleteNode() {
        for (int i = 0; i < canvasNodeList.size(); i++) {
            if (canvasNodeList.get(i).isSelected()) {
                canvasPane = (AnchorPane) canvas.getContent();
                canvasPane.getChildren().remove(canvasNodeList.get(i).getNodePane());
                canvasNodeList.remove(i);
                i--;
                WordSceneNode.setBulkSelect(false);
            }
        }
    }
    @FXML
    public void onKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.DELETE && !canvasNodeList.isEmpty()) {
            deleteNode();
        }
        if (event.getCode() == KeyCode.ESCAPE) {
            WordSceneNode.deselectAll();
        }
    }

    @FXML
    public void mouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            selectionRectangle.setLayoutX(event.getX());
            selectionRectangle.setLayoutY(event.getY());
            System.out.println(event.getSource());
            options.getOptions().show(selectionRectangle, Side.BOTTOM, 0 , 0);
        }
    }
    // This method resets the selection rectangle to its initial state
    private void resetSelectionRectangle() {
        selectionRectangle.setVisible(false);
        selectionRectangle.setWidth(0);
        selectionRectangle.setHeight(0);
        selectionRectangle.setLayoutX(-5);
        selectionRectangle.setLayoutY(-5);
    }
}