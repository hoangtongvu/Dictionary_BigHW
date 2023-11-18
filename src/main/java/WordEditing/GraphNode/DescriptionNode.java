package WordEditing.GraphNode;

import Word.WordDefinition;
import Word.WordDescription;
import Word.WordPhrase;
import WordEditing.EditorPanes.DescriptionEditor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class DescriptionNode extends DicNode {
    private WordDescription description;
    private Label descriptionLabel;
    private DescriptionEditor editor = new DescriptionEditor(this);
    public Label getDescriptionLabel() {
        return descriptionLabel;
    }

    public DescriptionEditor getEditor() {
        return editor;
    }

    public DescriptionNode() {
        super("Description");
        setOptions();

        description = new WordDescription("<EMPTY>");
        descriptionLabel = new Label("Word type: " + description.getWordType());
        labelProperty(descriptionLabel, "node-content");
        nodePane.getChildren().add(descriptionLabel);

//        options.getConnect().setOnAction(event -> {
//            temporaryLine = new Line();
//            temporaryLine.setStartX(nodePane.getLayoutX() + nodePane.getWidth()/2);
//            temporaryLine.setStartY(nodePane.getLayoutY() + nodePane.getHeight()/2);
//            inConnectMode = true;
//        });
    }

    public DescriptionNode(WordDescription description) {
        super("Description");
        this.description = description;
        descriptionLabel = new Label("Word type: " + description.getWordType());
        labelProperty(descriptionLabel, "node-content");
        nodePane.getChildren().add(descriptionLabel);
    }

    public WordDescription getDescription() {
        return description;
    }

    public void setDefinition(WordDescription description) {
        this.description = description;
    }


    @Override
    public void convertToWordBlock() {
        for (DicNode node : childrenNodeList) {
            if (node instanceof PhraseNode) {
                description.addPhrase(((PhraseNode) node).getPhrase());
            } else if (node instanceof DefinitionNode) {
                description.addDefinition(((DefinitionNode) node).getDefinition());
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
        options.getOptions().getItems().addAll(options.getConnect(),options.getDelete(),options.getAddDef(),options.getAddPhrase());
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
        //If is parent node

        //If is child node
        if (endNode instanceof PhraseNode) {
            System.out.println("Des - Phr");
            addChild(endNode);
            endNode.setParents(this);
//            for (DicNode node : this.childrenNodeList) {
//                System.out.println(node.toString());
//            }
        } else if (endNode instanceof DefinitionNode) {
            System.out.println("Des - Def");
            addChild(endNode);
            endNode.setParents(this);
//            for (DicNode node : this.childrenNodeList) {
//                System.out.println(node.toString());
//            }
        } else if (endNode instanceof WordNode) {
            System.out.println("Des - Word");
            endNode.addChild(this);
            setParents(endNode);
        }
    }

    @Override
    protected String getID() {
        return description.getDescriptionID();
    }

    @Override
    public boolean checkLink() {
        //Parent nodes

        //Children nodes
        if (endNode instanceof PhraseNode) {
            return true;
        } else if (endNode instanceof DefinitionNode) {
            return true;
        } else if (endNode instanceof WordNode) {
            return true;
        } else {
            return false;
        }
    }

    public void createNodeGraph() {
        if (description.getPhraseList() != null) {
            for (WordPhrase phrase : description.getPhraseList()) {
                PhraseNode newPhraseNode = new PhraseNode(phrase);
                newPhraseNode.setParents(this);
                nodeList.add(newPhraseNode);
                childrenNodeList.add(newPhraseNode);
                newPhraseNode.createNodeGraph();
            }
        }

        if (description.getDefinitionList() != null) {
            for (WordDefinition definition : description.getDefinitionList()) {
                DefinitionNode newDefinitionNode = new DefinitionNode(definition);
                newDefinitionNode.setParents(this);
                nodeList.add(newDefinitionNode);
                childrenNodeList.add(newDefinitionNode);
                newDefinitionNode.createNodeGraph();
            }
        }

    }
}
