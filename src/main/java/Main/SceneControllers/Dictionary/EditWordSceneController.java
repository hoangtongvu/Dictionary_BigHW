package Main.SceneControllers.Dictionary;

import WordEditing.*;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import javax.xml.stream.EventFilter;
import java.util.ArrayList;
import java.util.List;

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
    public static List<WordSceneNode> canvasNodeList = new ArrayList<>();
    static int selectedNodeCount = 0;
    static final NodeOptions options = new NodeOptions();

    @FXML
    public void initialize() {
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


//        ((AnchorPane) canvas.getContent()).addEventFilter(ScrollEvent.ANY, scrollEvent -> {
//            double zoomFactor = 1.05;
//            double delta = scrollEvent.getDeltaY();
//            if (delta < 0) {
//                zoomFactor = 2.0 - zoomFactor;
//            }
//            double newScaleX = canvas.getContent().getScaleX() * zoomFactor;
//            double newScaleY = canvas.getContent().getScaleY() * zoomFactor;
//            if (newScaleX < 0.3) newScaleX = 0.3;
//            if (newScaleY < 0.3) newScaleY = 0.3;
//            canvas.getContent().setScaleX(canvas.getContent().getScaleX() * zoomFactor);
//            canvas.getContent().setScaleY(canvas.getContent().getScaleY() * zoomFactor);
//            // Consume the event
//            scrollEvent.consume();
//        });


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
        if (event.getButton() == MouseButton.PRIMARY) {
            WordSceneNode.deselectAll();
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
//            System.out.println(event.getSource());
            if (!canvas.isPannable()) {
                options.getOptions().show(selectionRectangle, Side.BOTTOM, 0 , 0);
            }
            canvas.setPannable(false);

        }
    }

    @FXML
    public void mouseMoved(MouseEvent event) {

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
