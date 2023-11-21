package WordEditing.GraphNode;

import Word.WordBlock;
import Word.WordDescription;
import WordEditing.EditorPanes.WordEditor;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class WordNode extends DicNode {
    private WordBlock wordBlock;
    private Label wordLabel;
    private Label soundLabel;
    private WordEditor editor;
    private boolean isNewWord = true;
    private boolean isEditing = false;

    public boolean isBeingEdited() {
        return isEditing;
    }

    public void setEditing(boolean editing) {
        isEditing = editing;
    }

    public boolean isNewWord() {
        return isNewWord;
    }

    public void setNewWord(boolean isNewWord) {
        this.isNewWord = isNewWord;
    }

    public WordBlock getWordBlock() {
        return wordBlock;
    }



    public WordEditor getEditor() {
        return editor;
    }

    public Label getSoundLabel() {
        return soundLabel;
    }

    public Label getWordLabel() {
        return wordLabel;
    }

    @Override
    public String getID() {
        return wordBlock.getWordID();
    }

    public WordNode() {
        super("Word");
        setOptions();
        wordBlock = new WordBlock("<EMPTY>","<EMPTY>");
        wordLabel = new Label("Word: " + wordBlock.getWord());
        soundLabel = new Label("Sound: " + wordBlock.getSpelling());
        labelProperty(wordLabel, "node-content");
        labelProperty(soundLabel, "node-content");
        nodePane.getChildren().addAll(wordLabel, soundLabel);
        editor = new WordEditor(this);
    }

    public WordNode(WordBlock wordBlock) {
        super("Word");
        setOptions();
        this.wordBlock = wordBlock;
        wordLabel = new Label("Word: " + wordBlock.getWord());
        soundLabel = new Label("Sound: " + wordBlock.getSpelling());
        labelProperty(wordLabel, "node-content");
        labelProperty(soundLabel, "node-content");
        nodePane.getChildren().addAll(wordLabel, soundLabel);
        editor = new WordEditor(this);
    }

    public void setWordBlock(WordBlock wordBlock) {
        this.wordBlock = wordBlock;
    }

    @Override
    public void convertToWordBlock() {
        for (DicNode node : childrenNodeList) {
            if (node instanceof DescriptionNode) {
                wordBlock.addDescription(((DescriptionNode) node).getDescription());
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
        if (endNode instanceof DescriptionNode) {
            System.out.println("Word - Des");
            addChild(endNode);
            endNode.setParents(this);
        }
    }

    @Override
    public boolean checkLink() {
        if (endNode instanceof DescriptionNode) {
            return true;
        } else {
            return  false;
        }
    }

    public void createNodeGraph() {
//        System.out.println("DescList");
//        for (WordDescription description : wordBlock.getDescriptionsList()) {
//            System.out.println(description);
//        }
        if (wordBlock.getDescriptionsList() != null) {
            for (WordDescription description : wordBlock.getDescriptionsList()) {
//                System.out.println("Before");
//                for (DicNode node : nodeList) {
//                    System.out.println(node);
//                }
                DescriptionNode newNode = new DescriptionNode(description);
                newNode.setParents(this);
                nodeList.add(newNode);
                childrenNodeList.add(newNode);
                newNode.createNodeGraph();
//                System.out.println("After");
//                for (DicNode node : nodeList) {
//                    System.out.println(node);
//                }
            }

        }

    }
}
