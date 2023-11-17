package WordEditing.EditorPanes;

import WordEditing.GraphNode.DescriptionNode;
import WordEditing.GraphNode.DicNode;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class DescriptionEditor {
    protected TextField wordTypeTextField = new TextField();
    protected Label wordTypeLabel = new Label("Word Type");
    protected Label descriptionLabel = new Label("DESCRIPTION");
    protected AnchorPane editorPane = new AnchorPane();

    public AnchorPane getEditorPane() {
        return editorPane;
    }

    protected DescriptionNode node;

    public DescriptionEditor(DescriptionNode node) {
        this.node = node;
        editorPane.setPrefSize(240.0, 400.0);
        editorPane.setStyle("-fx-background-color: white;");

        descriptionLabel.setLayoutX(61.0);
        descriptionLabel.setLayoutY(25.0);
        descriptionLabel.setPrefSize(197.0, 26.0);
        descriptionLabel.setStyle("-fx-background-color: pink; -fx-alignment: center;");
        AnchorPane.setTopAnchor(descriptionLabel, 0.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 0.0);
        AnchorPane.setRightAnchor(descriptionLabel, 0.0);


        wordTypeTextField.setLayoutX(83.0);
        wordTypeTextField.setLayoutY(37.0);
        wordTypeTextField.setPrefSize(143.0, 25.0);
        AnchorPane.setLeftAnchor(wordTypeTextField, 84.0);
        AnchorPane.setRightAnchor(wordTypeTextField, 13.0);


        wordTypeLabel.setLayoutX(7.0);
        wordTypeLabel.setLayoutY(41.0);
        wordTypeLabel.setPrefSize(65.0, 17.0);
        AnchorPane.setLeftAnchor(wordTypeLabel, 14.0);

        editorPane.getChildren().addAll(descriptionLabel, wordTypeTextField, wordTypeLabel);
        wordTypeTextField.addEventHandler(KeyEvent.KEY_RELEASED, typeHandler);
    }
    EventHandler<KeyEvent> typeHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getDescriptionLabel().setText("WordType: " + wordTypeTextField.getText());
                node.getDescription().setWordType(wordTypeTextField.getText());
                DicNode.setChangesSaved(false);
            }
        }
    };



}
