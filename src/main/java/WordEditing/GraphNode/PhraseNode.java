package WordEditing.GraphNode;

import Word.WordDefinition;
import Word.WordPhrase;
import WordEditing.EditorPanes.PhraseEditor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class PhraseNode extends DicNode {
    private WordPhrase phrase;
    private Label phraseLabel;
    public WordPhrase getPhrase() {
        return phrase;
    }
    private PhraseEditor editor;
    public void setPhrase(WordPhrase phrase) {
        this.phrase = phrase;
    }

    public PhraseEditor getEditor() {
        return editor;
    }

    public Label getPhraseLabel() {
        return phraseLabel;
    }

    public PhraseNode() {
        super("Phrase");
        setOptions();

        phrase = new WordPhrase("<EMPTY>");
        phraseLabel = new Label(phrase.getPhrase());
        labelProperty(phraseLabel, "node-content");
        nodePane.getChildren().add(phraseLabel);

        editor = new PhraseEditor(this);
    }

    public PhraseNode(WordPhrase phrase) {
        super("Phrase");
        this.phrase = phrase;

        phraseLabel = new Label(phrase.getPhrase());
        labelProperty(phraseLabel, "node-content");
        nodePane.getChildren().add(phraseLabel);
        editor = new PhraseEditor(this);

    }
    @Override
    public void convertToWordBlock() {
        for (DicNode node : childrenNodeList) {
            if (node instanceof DefinitionNode) {
                phrase.addDefinition(((DefinitionNode) node).getDefinition());
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
    protected void establishLink() {
        if (endNode instanceof DefinitionNode) {
            System.out.println("Phr - Ex");
            addChild(endNode);
            endNode.setParents(this);
        } else if (endNode instanceof DescriptionNode) {
            System.out.println("Phr - Des");
            endNode.addChild(this);
            setParents(endNode);
        }
    }

    @Override
    protected String getID() {
        return phrase.getPhraseID();
    }

    @Override
    public boolean checkLink() {
        if (endNode instanceof DefinitionNode) {
            return true;
        } else if (endNode instanceof DescriptionNode) {
            return true;
        } else {
            return false;
        }
    }

    public void createNodeGraph() {
        if (phrase.getDefinitionList() != null) {
            for (WordDefinition definition : phrase.getDefinitionList()) {
                DefinitionNode newDefinitionNode = new DefinitionNode(definition);
                newDefinitionNode.setParents(this);
                nodeList.add(newDefinitionNode);
                childrenNodeList.add(newDefinitionNode);
                newDefinitionNode.createNodeGraph();
            }
        }

    }
}
