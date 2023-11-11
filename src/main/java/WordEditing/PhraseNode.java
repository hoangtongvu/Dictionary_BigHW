package WordEditing;

import Word.WordPhrase;
import javafx.scene.control.Label;

public class PhraseNode extends WordSceneNode {
    private WordPhrase phrase;
    private Label phraseLabel;
    public WordPhrase getPhrase() {
        return phrase;
    }

    public void setPhrase(WordPhrase phrase) {
        this.phrase = phrase;
    }

    public PhraseNode() {
        super("Phrase");
        setOptions();

        phrase = new WordPhrase("<EMPTY>");
        phraseLabel = new Label(phrase.getPhrase());
        labelProperty(phraseLabel, "node-content");
        nodePane.getChildren().add(phraseLabel);
    }

    @Override
    protected void setOptions() {
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete(),options.getAddDef());
    }

    @Override
    protected void labelProperty(Label label, String styleClass) {
        label.setWrapText(true);
        setStyleSheet(label);
        setStyleClass(label, styleClass);
        label.prefWidthProperty().bind(nodePane.prefWidthProperty());
    }

    @Override
    public void addChild(WordSceneNode wordSceneNode) {

    }
}
