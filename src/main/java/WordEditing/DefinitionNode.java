package WordEditing;

import Word.WordDefinition;
import WordEditing.EditorPanes.DefinitionEditor;
import javafx.scene.control.Label;

public class DefinitionNode extends DicNode {
    private WordDefinition definition;
    private Label definitionLabel;
    DefinitionEditor editor;

    public DefinitionEditor getEditor() {
        return editor;
    }

    public WordDefinition getDefinition() {
        return definition;
    }

    public Label getDefinitionLabel() {
        return definitionLabel;
    }

    public void setDefinition(WordDefinition definition) {
        this.definition = definition;
    }

    public DefinitionNode() {
        super("Definition");
        definition = new WordDefinition("<EMPTY>");
        setOptions();

        definitionLabel = new Label(definition.getDefinition());
        labelProperty(definitionLabel, "node-content");
        nodePane.getChildren().add(definitionLabel);
        editor = new DefinitionEditor(this);
    }

    @Override
    public void labelProperty(Label label, String styleClass) {
        label.setWrapText(true);
        setStyleSheet(label);
        setStyleClass(label, styleClass);
        definitionLabel.prefWidthProperty().bind(nodePane.prefWidthProperty());
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
