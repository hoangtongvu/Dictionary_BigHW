package WordEditing.EditorPanes;

import WordEditing.GraphNode.DicNode;
import WordEditing.GraphNode.WordNode;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class WordEditor {
    private TextField wordTextField = new TextField();
    private TextField soundTextField = new TextField();
    private Label wordBlockLabel = new Label("WORD");
    private Label wordLabel = new Label("Word");
    private Label soundLabel = new Label("Sound");
    private AnchorPane editorPane = new AnchorPane();

    private WordNode node;

    public WordEditor(WordNode node) {
        this.node = node;
        editorPane.setPrefSize(240.0, 400.0);
        editorPane.setStyle("-fx-background-color: white;");

        wordBlockLabel.setLayoutX(61.0);
        wordBlockLabel.setLayoutY(25.0);
        wordBlockLabel.setPrefSize(197.0, 26.0);
        wordBlockLabel.setStyle("-fx-background-color: pink; -fx-alignment: center;");
        AnchorPane.setTopAnchor(wordBlockLabel, 0.0);
        AnchorPane.setLeftAnchor(wordBlockLabel, 0.0);
        AnchorPane.setRightAnchor(wordBlockLabel, 0.0);

        wordLabel.setLayoutX(7.0);
        wordLabel.setLayoutY(41.0);
        wordLabel.setPrefSize(65.0, 17.0);
        AnchorPane.setLeftAnchor(wordLabel, 14.0);

        wordTextField.setLayoutX(79.0);
        wordTextField.setLayoutY(37.0);
        AnchorPane.setLeftAnchor(wordTextField, 79.0);
        AnchorPane.setRightAnchor(wordTextField, 12.0);

        soundLabel.setLayoutX(14.0);
        soundLabel.setLayoutY(72.0);
        soundLabel.setPrefSize(65.0, 17.0);

        soundTextField.setLayoutX(79.0);
        soundTextField.setLayoutY(68.0);
        AnchorPane.setLeftAnchor(soundTextField, 79.0);
        AnchorPane.setRightAnchor(soundTextField, 12.0);

        editorPane.getChildren().addAll(wordBlockLabel, wordLabel, wordTextField, soundLabel, soundTextField);
        wordTextField.addEventHandler(KeyEvent.KEY_RELEASED, wordTypeHandler);
        soundTextField.addEventHandler(KeyEvent.KEY_RELEASED, soundTypeHandler);
    }

    EventHandler<KeyEvent> wordTypeHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getWordLabel().setText("Word: " + wordTextField.getText() + "\n");
                node.getWordBlock().setWord(wordTextField.getText());
                DicNode.setChangesSaved(false);
            }
        }
    };

    EventHandler<KeyEvent> soundTypeHandler = new EventHandler<>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getSoundLabel().setText("Sound: " + soundTextField.getText());
                node.getWordBlock().setSpelling(soundTextField.getText());
                DicNode.setChangesSaved(false);
            }
        }
    };

    public AnchorPane getEditorPane() {
        return editorPane;
    }
}
