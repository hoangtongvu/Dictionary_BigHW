package Main.SceneControllers.Dictionary;

import AddWordGraph.DescriptionWordSceneNode;
import AddWordGraph.WordSceneNode;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class EditWordSceneController {
    @FXML
    private ScrollPane canvas;
    private double mouseStartX;
    private double mouseStartY;
    @FXML
    private Rectangle selectionRectangle;

    List<WordSceneNode> wordSceneNodeList = new ArrayList<>();
    static int selectedNodeCount = 0;

    @FXML
    public void addDescription() {
        WordSceneNode tmp = new DescriptionWordSceneNode();
        wordSceneNodeList.add(tmp);
        AnchorPane canvasPane = (AnchorPane) canvas.getContent();
        canvasPane.getChildren().add(tmp.getNodePane());
    }

    @FXML
    public void canvasMousePressed (MouseEvent event) {
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

        } else if (event.getButton() == MouseButton.SECONDARY) {
            canvas.setPannable(true);
        }
    }
    @FXML
    public void canvasMouseReleased(MouseEvent event) {
        resetSelectionRectangle();
        canvas.setPannable(false);
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
