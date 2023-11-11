package WordEditing;

import Word.WordDefinition;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Line;

import java.util.List;

public class DescriptionNode extends WordSceneNode {
    private WordDefinition definition;
    private Label definitionLabel;
    private List<Edge> edgeList;
    private Line temporaryLine;
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
    public void addChild(WordSceneNode wordSceneNode) {

    }
}
