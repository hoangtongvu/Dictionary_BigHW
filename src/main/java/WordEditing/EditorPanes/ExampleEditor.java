package WordEditing.EditorPanes;

import WordEditing.ExampleNode;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.security.Key;

public class ExampleEditor {
    protected TextArea exampleTextArea = new TextArea();
    protected TextArea translationTextArea = new TextArea();
    protected Label exampleLabel = new Label("EXAMPLE");
    protected Label exampleTextLabel = new Label("Example:");
    protected Label translationLabel = new Label("Translation");
    protected AnchorPane editorPane = new AnchorPane();

    public AnchorPane getEditorPane() {
        return editorPane;
    }

    ExampleNode node;

    public ExampleEditor(ExampleNode node) {
        translationTextArea.setWrapText(true);
        exampleTextArea.setWrapText(true);
        this.node = node;
        editorPane.setPrefSize(240.0, 400.0);
        editorPane.setStyle("-fx-background-color: white;");

        exampleLabel.setLayoutX(61.0);
        exampleLabel.setLayoutY(25.0);
        exampleLabel.setPrefSize(197.0, 26.0);
        exampleLabel.setStyle("-fx-background-color: pink; -fx-alignment: center;");
        AnchorPane.setTopAnchor(exampleLabel, 0.0);
        AnchorPane.setLeftAnchor(exampleLabel, 0.0);
        AnchorPane.setRightAnchor(exampleLabel, 0.0);

        exampleTextLabel.setLayoutX(7.0);
        exampleTextLabel.setLayoutY(41.0);
        exampleTextLabel.setPrefSize(65.0, 17.0);
        AnchorPane.setLeftAnchor(exampleTextLabel, 14.0);

        exampleTextArea.setLayoutX(14.0);
        exampleTextArea.setLayoutY(67.0);
        exampleTextArea.setPrefSize(212.0, 93);
//        AnchorPane.setTopAnchor(exampleTextArea, 67.0);
//        AnchorPane.setBottomAnchor(exampleTextArea, 214.0);
        AnchorPane.setLeftAnchor(exampleTextArea, 14.0);
        AnchorPane.setRightAnchor(exampleTextArea, 14.0);

        translationLabel.setLayoutX(14.0);
        translationLabel.setLayoutY(175.0);
        translationLabel.setPrefSize(65.0, 17.0);

        translationTextArea.setLayoutX(14.0);
        translationTextArea.setLayoutY(200.0);
        translationTextArea.setPrefSize(212.0, 93);
        AnchorPane.setTopAnchor(translationTextArea, 200.0);
        AnchorPane.setBottomAnchor(translationTextArea, 20.0);
        AnchorPane.setLeftAnchor(translationTextArea, 14.0);
        AnchorPane.setRightAnchor(translationTextArea, 14.0);

        editorPane.getChildren().addAll(exampleLabel, exampleTextLabel, exampleTextArea, translationLabel, translationTextArea);
        exampleTextArea.addEventHandler(KeyEvent.KEY_RELEASED, exampleTypeHandler);
        translationTextArea.addEventHandler(KeyEvent.KEY_RELEASED, translationTypeHandler);
    }

    EventHandler<KeyEvent> exampleTypeHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getExampleLabel().setText("Example:\n" + exampleTextArea.getText() + "\n");
                node.getExample().setExample(exampleTextArea.getText());
            }
        }
    };

    EventHandler<KeyEvent> translationTypeHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getEventType() == KeyEvent.KEY_RELEASED) {
                node.getTranslationLabel().setText("Translation:\n" + translationTextArea.getText() + "\n");
                node.getExample().setTranslation(translationTextArea.getText());
            }
        }
    };
}
