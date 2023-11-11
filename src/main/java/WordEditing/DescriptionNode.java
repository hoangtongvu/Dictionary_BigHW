package WordEditing;

import Word.WordDefinition;
import javafx.scene.control.Label;

public class DescriptionNode extends WordSceneNode {
    private WordDefinition content;
    private Label contentLabel;

    public DescriptionNode() {
        super("Description");
        setOptions();

        content = new WordDefinition("<EMPTY>");
        contentLabel = new Label();
        contentLabel.prefWidthProperty().bind(nodePane.widthProperty());
        contentLabel.setText("Word type: " + content.getDefinition());
        setStyleSheet(contentLabel);
        setStyleClass(contentLabel, "node-content");
        nodePane.getChildren().add(contentLabel);
    }

    public WordDefinition getContent() {
        return content;
    }

    public void setContent(WordDefinition content) {
        this.content = content;
    }



    @Override
    protected void setOptions() {
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete(),options.getAddDef(),options.getAddPhrase());
    }

    @Override
    public void addChild(WordSceneNode wordSceneNode) {

    }
}
