package WordEditing.GraphNode;

import Word.WordExample;
import WordEditing.EditorPanes.ExampleEditor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class ExampleNode extends DicNode {
    private WordExample example;
    private Label exampleLabel;
    private Label translationLabel;

    private ExampleEditor editor;

    public ExampleEditor getEditor() {
        return editor;
    }

    public Label getExampleLabel() {
        return exampleLabel;
    }

    public Label getTranslationLabel() {
        return translationLabel;
    }



    public WordExample getExample() {
        return example;
    }

    public void setExample(WordExample example) {
        this.example = example;
    }

    public ExampleNode() {
        super("Example");
        setOptions();
        example = new WordExample("<EMPTY>","<EMPTY>");
        exampleLabel = new Label("Example:\n"  + example.getExample());
        translationLabel = new Label("Translation:\n" + example.getTranslation());
        labelProperty(exampleLabel, "node-content");
        labelProperty(translationLabel, "node-content");
        nodePane.getChildren().addAll(exampleLabel, translationLabel);
        editor = new ExampleEditor(this);
    }

    @Override
    public void convertToWordBlock() {

    }

    @Override
    public void removeEditor() {
        if (editor.getEditorPane().getParent() != null) {
            ((AnchorPane) editor.getEditorPane().getParent()).getChildren().remove(editor.getEditorPane());
        }
    }

    @Override
    protected void setOptions() {
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete());
    }

    @Override
    protected void labelProperty(Label label, String styleClass) {
        label.setWrapText(true);
        setStyleSheet(label);
        setStyleClass(label, styleClass);
        label.prefWidthProperty().bind(nodePane.prefWidthProperty());
    }

    @Override
    protected void establishLink() {
        if (endNode instanceof DefinitionNode) {
            System.out.println("Dex - Def");
            endNode.addChild(this);
            setParents(endNode);
        }
    }

    @Override
    public boolean checkLink() {
        if (endNode instanceof DefinitionNode) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void addChild(DicNode dicNode) {

    }
}
