package WordEditing.EditorPanes;

import WordEditing.PhraseNode;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.security.Key;

public class PhraseEditor {
    protected TextField phraseTextField = new TextField();
    protected Label phraseLabel = new Label("PHRASE");
    protected Label phraseTextLabel = new Label("Phrase");
    protected AnchorPane editorPane = new AnchorPane();

    public AnchorPane getEditorPane() {
        return editorPane;
    }

    private PhraseNode node;

    public PhraseEditor(PhraseNode node) {
        this.node = node;
        editorPane.setPrefSize(240.0, 400.0);
        editorPane.setStyle("-fx-background-color: white;");

        phraseLabel.setLayoutX(61.0);
        phraseLabel.setLayoutY(25.0);
        phraseLabel.setPrefSize(197.0, 26.0);
        phraseLabel.setStyle("-fx-background-color: pink; -fx-alignment: center;");
        AnchorPane.setTopAnchor(phraseLabel, 0.0);
        AnchorPane.setLeftAnchor(phraseLabel, 0.0);
        AnchorPane.setRightAnchor(phraseLabel, 0.0);

        phraseTextLabel.setLayoutX(7.0);
        phraseTextLabel.setLayoutY(41.0);
        phraseTextLabel.setPrefSize(65.0, 17.0);
        AnchorPane.setLeftAnchor(phraseTextLabel, 14.0);

        phraseTextField.setLayoutX(14.0);
        phraseTextField.setLayoutY(58.0);
        phraseTextField.setPrefSize(214.0, 25.0);
        AnchorPane.setLeftAnchor(phraseTextField, 14.0);
        AnchorPane.setRightAnchor(phraseTextField, 12.0);

        editorPane.getChildren().addAll(phraseLabel, phraseTextLabel, phraseTextField);
        phraseTextField.addEventHandler(KeyEvent.KEY_RELEASED, typeHandler);
    }

    EventHandler<KeyEvent> typeHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getPhrase().setPhrase(phraseTextField.getText());
                node.getPhraseLabel().setText(phraseTextField.getText());
            }
        }
    };
}
