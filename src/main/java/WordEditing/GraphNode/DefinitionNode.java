package WordEditing.GraphNode;

import Word.WordDefinition;
import Word.WordExample;
import WordEditing.EditorPanes.DefinitionEditor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

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

    public DefinitionNode(WordDefinition definition) {
        super("Definition");
        this.definition = definition;
        setOptions();
        definitionLabel = new Label(definition.getDefinition());
        labelProperty(definitionLabel, "node-content");
        nodePane.getChildren().add(definitionLabel);
        editor = new DefinitionEditor(this);

        if (!definition.getDefinition().equals("<EMPTY>")) {
            editor.getDefinitionTextArea().setText(definition.getDefinition());
        }
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
            setParents(endNode);
        } else if (endNode instanceof ExampleNode) {
            System.out.println("Des - Ex");
            endNode.setParents(this);
            addChild(endNode);
        } else if (endNode instanceof PhraseNode) {
            System.out.println("Def - Phr");
            endNode.addChild(this);
            setParents(endNode);
        }
    }

    @Override
    public String getID() {
        return definition.getDefinitionID();
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
    public void convertToWordBlock() {
        for (DicNode node : childrenNodeList) {
            if (node instanceof ExampleNode) {
                definition.addExample(((ExampleNode) node).getExample());
            }
        }
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

    public void createNodeGraph() {
        if (definition.getExampleList() != null) {
            for (WordExample example : definition.getExampleList()) {
                ExampleNode newNode = new ExampleNode(example);
                newNode.setParents(this);
                nodeList.add(newNode);
                childrenNodeList.add(newNode);
                newNode.createNodeGraph();
            }
        }
    }

}
