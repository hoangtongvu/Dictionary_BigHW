package WordEditing.EditorPanes;

import WordEditing.GraphNode.DefinitionNode;
import WordEditing.GraphNode.DicNode;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.util.Objects;

public class DefinitionEditor {
    protected TextArea definitionTextArea = new TextArea();
    protected Label definitionLabel = new Label("DEFINITION");
    protected Label definitionTextLabel = new Label("Definition");
    protected AnchorPane editorPane = new AnchorPane();
    protected DefinitionNode node;

    public TextArea getDefinitionTextArea() {
        return definitionTextArea;
    }

    public AnchorPane getEditorPane() {
        return editorPane;
    }

    public DefinitionEditor(DefinitionNode node) {
        this.node = node;
        definitionTextArea.setWrapText(true);
        editorPane.setPrefSize(240.0, 400.0);

        definitionLabel.setLayoutX(61.0);
        definitionLabel.setLayoutY(25.0);
        definitionLabel.setPrefSize(197.0, 26.0);
        AnchorPane.setTopAnchor(definitionLabel, 0.0);
        AnchorPane.setLeftAnchor(definitionLabel, 0.0);
        AnchorPane.setRightAnchor(definitionLabel, 0.0);

        definitionTextLabel.setLayoutX(7.0);
        definitionTextLabel.setLayoutY(41.0);
        definitionTextLabel.setPrefSize(65.0, 17.0);
        AnchorPane.setLeftAnchor(definitionTextLabel, 14.0);

        definitionTextArea.setLayoutX(20.0);
        definitionTextArea.setLayoutY(65.0);
        definitionTextArea.setPrefSize(212.0, 117.0);
        AnchorPane.setTopAnchor(definitionTextArea, 65.0);
        AnchorPane.setLeftAnchor(definitionTextArea, 14.0);
        AnchorPane.setRightAnchor(definitionTextArea, 14.0);

        editorPane.getChildren().addAll(definitionLabel, definitionTextLabel, definitionTextArea);
        definitionTextArea.addEventHandler(KeyEvent.KEY_RELEASED, typeHandler);

        editorPane.getStylesheets().add(String.valueOf(getClass().getResource("/css/EditWord.css")));

        // Assign style classes
        editorPane.getStyleClass().add("editor-anchor-pane");
        definitionTextArea.getStyleClass().add("editor-text-field");
        definitionLabel.getStyleClass().add("editor-name");
        definitionTextLabel.getStyleClass().add("field-name");
    }

    EventHandler<KeyEvent> typeHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getDefinitionLabel().setText(definitionTextArea.getText());
                node.getDefinition().setDefinition(definitionTextArea.getText());
                DicNode.setChangesSaved(false);
            }
        }
    };
}
