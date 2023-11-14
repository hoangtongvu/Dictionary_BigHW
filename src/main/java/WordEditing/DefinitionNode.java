package WordEditing;

import Word.WordDefinition;
import javafx.scene.control.Label;

public class DefinitionNode extends DicNode {
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
    protected void establishLink() {
        if (endNode instanceof DescriptionNode) {
            System.out.println("Des - Def");
            endNode.addChild(this);
            addParent(endNode);
        } else if (endNode instanceof ExampleNode) {
            System.out.println("Des - Ex");
            endNode.addParent(this);
            addChild(endNode);
        } else if (endNode instanceof PhraseNode) {
            System.out.println("Def - Phr");
            endNode.addChild(this);
            addParent(endNode);
        }
    }

    @Override
    public boolean checkLink() {
        if (endNode instanceof ExampleNode) {
            return true;
        } else if (endNode instanceof DescriptionNode){
            return true;
        } else if (endNode instanceof PhraseNode) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void setOptions() {
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete(),options.getAddEx());
    }
}
