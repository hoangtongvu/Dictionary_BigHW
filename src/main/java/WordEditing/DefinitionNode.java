package WordEditing;

import Word.WordDefinition;
import javafx.scene.control.Label;

public class DefinitionNode extends WordSceneNode {
    private WordDefinition content;
    private Label contentLabel;

    public WordDefinition getContent() {
        return content;
    }

    public void setContent(WordDefinition content) {
        this.content = content;
    }

    public DefinitionNode() {
        super("Definition");
        content = new WordDefinition("<Neque porro quisquam est qui dolorem ipsum quia dolor sit amet, consectetur, adipisci velit...>");
        setOptions();

        contentLabel = new Label(content.getDefinition());
        labelProperty(contentLabel, "node-content");
        nodePane.getChildren().add(contentLabel);
    }

    @Override
    public void labelProperty(Label label, String styleClass) {
        label.setWrapText(true);
        setStyleSheet(label);
        setStyleClass(label, styleClass);
        contentLabel.prefWidthProperty().bind(nodePane.prefWidthProperty());
    }

    @Override
    protected void setOptions() {
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete(),options.getAddEx());
    }

    @Override
    public void addChild(WordSceneNode wordSceneNode) {

    }
}
