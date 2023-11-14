package WordEditing;

import Word.WordDefinition;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.shape.Line;

public class DescriptionNode extends DicNode {
    private WordDefinition definition;
    private Label definitionLabel;


    public DescriptionNode() {
        super("Description");
        setOptions();

        definition = new WordDefinition("<EMPTY>");
        definitionLabel = new Label("Word type: " + definition.getDefinition());
        labelProperty(definitionLabel, "node-content");
        nodePane.getChildren().add(definitionLabel);

//        options.getConnect().setOnAction(event -> {
//            temporaryLine = new Line();
//            temporaryLine.setStartX(nodePane.getLayoutX() + nodePane.getWidth()/2);
//            temporaryLine.setStartY(nodePane.getLayoutY() + nodePane.getHeight()/2);
//            inConnectMode = true;
//        });
    }

    public WordDefinition getDefinition() {
        return definition;
    }

    public void setDefinition(WordDefinition definition) {
        this.definition = definition;
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
            endNode.addParent(this);
//            for (DicNode node : this.childrenNodeList) {
//                System.out.println(node.toString());
//            }
        } else if (endNode instanceof DefinitionNode) {
            System.out.println("Des - Def");
            addChild(endNode);
            endNode.addParent(this);
//            for (DicNode node : this.childrenNodeList) {
//                System.out.println(node.toString());
//            }
        }
    }

    @Override
    public boolean checkLink() {
        //Parent nodes

        //Children nodes
        if (endNode instanceof PhraseNode) {
            return true;
        } else if (endNode instanceof DefinitionNode) {
            return true;
        } else {
            return false;
        }
    }

}
