package WordEditing.EditorPanes;

import WordEditing.GraphNode.DicNode;
import WordEditing.GraphNode.PhraseNode;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class PhraseEditor {
    protected TextArea phraseTextArea = new TextArea();
    protected Label phraseLabel = new Label("PHRASE");
    protected Label phraseTextLabel = new Label("Phrase");
    protected AnchorPane editorPane = new AnchorPane();

    public AnchorPane getEditorPane() {
        return editorPane;
    }

    private PhraseNode node;

    public PhraseEditor(PhraseNode node) {
        phraseTextArea.setWrapText(true);
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

        phraseTextArea.setLayoutX(14.0);
        phraseTextArea.setLayoutY(58.0);
        phraseTextArea.setPrefSize(214.0, 60);
        AnchorPane.setLeftAnchor(phraseTextArea, 14.0);
        AnchorPane.setRightAnchor(phraseTextArea, 12.0);
//        AnchorPane.setBottomAnchor(phraseTextArea, 20.0);
        AnchorPane.setTopAnchor(phraseTextArea,58.0);

        editorPane.getChildren().addAll(phraseLabel, phraseTextLabel, phraseTextArea);
        phraseTextArea.addEventHandler(KeyEvent.KEY_RELEASED, typeHandler);
    }

    EventHandler<KeyEvent> typeHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getPhrase().setPhrase(phraseTextArea.getText());
                node.getPhraseLabel().setText(phraseTextArea.getText());
                DicNode.setChangesSaved(false);
            }
        }
    };
}
