package WordEditing.EditorPanes;

import WordEditing.DefinitionNode;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class DefinitionEditor {
    protected TextArea definitionTextArea = new TextArea();
    protected Label definitionLabel = new Label("DEFINITION");
    protected Label definitionTextLabel = new Label("Definition");
    protected AnchorPane editorPane = new AnchorPane();
    protected DefinitionNode node;

    public AnchorPane getEditorPane() {
        return editorPane;
    }

    public DefinitionEditor(DefinitionNode node) {
        this.node = node;
        editorPane.setPrefSize(240.0, 400.0);
        editorPane.setStyle("-fx-background-color: white;");

        definitionLabel.setLayoutX(61.0);
        definitionLabel.setLayoutY(25.0);
        definitionLabel.setPrefSize(197.0, 26.0);
        definitionLabel.setStyle("-fx-background-color: pink; -fx-alignment: center;");
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
    }

    EventHandler<KeyEvent> typeHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getDefinitionLabel().setText(definitionTextArea.getText());
                node.getDefinition().setDefinition(definitionTextArea.getText());
            }
        }
    };
}
