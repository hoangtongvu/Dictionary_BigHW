package Main.SceneControllers.Dictionary;

import Dictionary.DicManager;
import Main.FxmlFileManager;
import Main.application.App;
import Word.WordBlock;
import Word.WordDescription;
import WordEditing.GraphNode.*;
import WordEditing.Warnings;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EditWordSceneController {

    private double mouseStartX;
    private double mouseStartY;
    protected AnchorPane canvasPane;
    public static Line temporaryLine;
    static final NodeOptions options = new NodeOptions();

    GridPane grid = new GridPane();

    @FXML
    private Rectangle selectionRectangle;
    @FXML
    protected ScrollPane canvas;
    @FXML
    protected AnchorPane editorPane;
    @FXML
    protected Button descriptionButton;
    @FXML
    protected Button definitionButton;
    @FXML
    protected Button phraseButton;
    @FXML
    protected Button exampleButton;
    @FXML
    protected Button saveButton;
    @FXML
    protected Button connectButton;
    @FXML
    protected Button deleteButton;
    @FXML
    protected ListView wordListView;
    private static List<WordBlock> editableWordList = new ArrayList<>();

    public static List<WordBlock> getEditableWordList() {
        return editableWordList;
    }

    //TODO: Save functionality for words
    //TODO: Add word to favourite list
    //TODO: Add study timer on the side and probably spotify API

    /**
     * Create: Done
     * Read: In progress
     * Update: In progress
     * Delete: In progress
     */

    @FXML
    public void saveWord() throws SQLException {
//        //TODO: Divide saving into 2 cases, when word doesnt exist and when editing a word
//        //TODO: add repeated word warning
        DicNode.getCurrentlyEditedWord().saveToJSON();
        updateListView();
        save();
    }

    @FXML
    public void addNewWord() throws SQLException {
        if (DicNode.getCurrentlyEditedWord() == null) {
            DicNode.setCurrentlyEditedWord(new WordNode());
            addNode(DicNode.getCurrentlyEditedWord());

            showEditingTools(true);
        } else if (DicNode.getCurrentlyEditedWord() != null) {
            if (DicNode.isChangesSaved()) {
                //Reset and create new word
                DicNode.reset();
                DicNode.setCurrentlyEditedWord(new WordNode());
                addNode(DicNode.getCurrentlyEditedWord());
            } else { //Changes not saved
                //Show warning - save and continue/ continue without save/ cancel
                int flag = Warnings.getInstance().newWordWarning();
                if (flag > 0) {
                    //Save -> Reset -> New
                    if (save() > 0) {
                        DicNode.reset();
                        DicNode.setCurrentlyEditedWord(new WordNode());
                        addNode(DicNode.getCurrentlyEditedWord());
                    }
                } else if (flag < 0) {
                    //Reset -> New
                    DicNode.reset();
                    DicNode.setCurrentlyEditedWord(new WordNode());
                    addNode(DicNode.getCurrentlyEditedWord());
                }
            }
            updateListView();
        }
    }

    @FXML
    public void toDictionary() {
        Scene temp = new Scene(FxmlFileManager.getInstance().root);
        App.getPrimaryStage().setScene(temp);
    }

    @FXML
    protected TextField editWordSearchBar;
    @FXML
    public void searchEditable() {
        wordListView.getItems().clear();
        for (WordBlock wordBlock : editableWordList) {
            if (wordBlock.getWord().contains(editWordSearchBar.getText())) {
                wordListView.getItems().add(wordBlock.getWord());
            }
        }
    }

    public int save() throws SQLException {
        //Saving new word
        if (DicNode.getCurrentlyEditedWord().getWordBlock().getWord().equals("<EMPTY>")
            || DicNode.getCurrentlyEditedWord().getWordBlock().getWord() == null) {
            Warnings.getInstance().showEmptyWordWarning();
            return 0;
        } else if (Collections.binarySearch(DicManager.getInstance().getDictionary().getWordBlocks()
                , DicNode.getCurrentlyEditedWord().getWordBlock()) >= 0 && !DicNode.getCurrentlyEditedWord().isEditing()) {

            Warnings.getInstance().showWordExistWarning();
            return -1;

        } else { //Saving saved word
            if (!DicNode.isChangesSaved()) {
                //Remove links between wordBlocks - wordDefinition - ...
                for (DicNode node : DicNode.nodeList) {
                    if (node instanceof WordNode) {
                        if (((WordNode) node).getWordBlock().getDescriptionsList() != null)
                            ((WordNode) node).getWordBlock().getDescriptionsList().clear();
                    } else if (node instanceof DefinitionNode) {
                        if (((DefinitionNode) node).getDefinition().getExampleList() != null)
                            ((DefinitionNode) node).getDefinition().getExampleList().clear();
                    } else if (node instanceof DescriptionNode) {
                        if (((DescriptionNode) node).getDescription().getDefinitionList() != null)
                            ((DescriptionNode) node).getDescription().getDefinitionList().clear();
                        if (((DescriptionNode) node).getDescription().getPhraseList() != null)
                            ((DescriptionNode) node).getDescription().getPhraseList().clear();
                    } else if (node instanceof PhraseNode) {
                        if (((PhraseNode) node).getPhrase().getDefinitionList() != null)
                            ((PhraseNode) node).getPhrase().getDefinitionList().clear();
                    }
                }

                for (DicNode node : DicNode.getNodeList()) {
                    if (node instanceof WordNode || node.getParent() != null) {
                        node.convertToWordBlock();
                    }
                }

                if (DicNode.getCurrentlyEditedWord().isNewWord() && !DicNode.getCurrentlyEditedWord().isEditing()) {
                    DicNode.getCurrentlyEditedWord().getWordBlock().insertInOrder();
                    DicNode.getCurrentlyEditedWord().getWordBlock().saveData();

                    DicNode.getCurrentlyEditedWord().setEditing(true);
                    editableWordList.add(DicNode.getCurrentlyEditedWord().getWordBlock());
                    System.out.println("Save when adding new word");

                    //Set load status, so it won't reload in a same session
//                    DicNode.getCurrentlyEditedWord().getWordBlock().setLoadStatus(true);
                } else {
                    DicNode.getCurrentlyEditedWord().setNewWord(false);
                    DicNode.getCurrentlyEditedWord().getWordBlock().updateInDatabase();
                    System.out.println("Saved while editing");
                    //Set load status, so it won't reload in a same session
//                    DicNode.getCurrentlyEditedWord().getWordBlock().setLoadStatus(true);
                }
                updateListView();
                Warnings.getInstance().showSavedNotice();
                DicNode.setChangesSaved(true);
                return 1;
            }
            return 0;
        }

    }

    public void loadWordOnPane(String word) throws SQLException {
        //Warning

        //Reset
        DicNode.reset();
        showEditingTools(true);
        //Loading the word into a new WordNode
        System.out.println(word);
        WordBlock target = new WordBlock(word, "");
        int position = Collections.binarySearch(DicManager.getInstance().getDictionary().getWordBlocks(), target);
        WordNode newNode = new WordNode(DicManager.getInstance().getDictionary().getWordBlocks().get(position));
        newNode.getWordBlock().loadData(newNode.getWordBlock().getWordID());

        DicNode.setCurrentlyEditedWord(newNode);
        DicNode.nodeList.add(newNode);
        newNode.setNewWord(false);
        newNode.setEditing(true);

        //Start drawing
        DicNode.getCurrentlyEditedWord().setNewWord(false);
        DicNode.getCurrentlyEditedWord().createNodeGraph();
        for (DicNode node : DicNode.getNodeList()) {
            canvasPane.getChildren().add(node.getLineToParent());
            canvasPane.getChildren().add(node.getNodePane());
            node.getLineToParent().toBack();
        }
    }

    @FXML
    public void deleteWord() throws SQLException {
        //Delete -> Reset -> Empty
        if (Warnings.getInstance().showDeleteWordWarning()) {
            DicManager.getInstance().getDictionary().getWordBlocks().remove(DicNode.getCurrentlyEditedWord().getWordBlock());
            editableWordList.remove(DicNode.getCurrentlyEditedWord().getWordBlock());
            DicNode.getCurrentlyEditedWord().getWordBlock().deleteFromDatabase();
            updateListView();
            DicNode.setCurrentlyEditedWord(null);
            DicNode.reset();
            showEditingTools(false);
        }
    }

////////////////////////////////////////////////////////////


    @FXML
    public void addDescription() {
        addNode(new DescriptionNode());
    }

    @FXML
    public void addDefinition() {
        addNode(new DefinitionNode());
    }

    @FXML
    public void addExample() {
        addNode(new ExampleNode());
    }

    @FXML
    public void addPhrase() {
        addNode(new PhraseNode());
    }

    @FXML
    public void toggleConnectMode() {
        if (!DicNode.isToggleConnectMode()) {
            connectButton.setTextFill(Color.GREEN);
        } else {
            connectButton.setTextFill(Color.RED);
        }
        DicNode.setToggleConnectMode(!DicNode.isToggleConnectMode());
    }

    @FXML
    public void listViewMouseClicked(MouseEvent event) throws SQLException {
        if (event.getClickCount() > 1) {
            String currentlySelectedItem = wordListView.getSelectionModel().getSelectedItem().toString();
            loadWordOnPane(currentlySelectedItem);
        }
    }

    @FXML
    public void initialize() {
        canvasPane = ((AnchorPane) canvas.getContent());
        temporaryLine = new Line();
        temporaryLine.setVisible(false);
        temporaryLine.setStrokeWidth(1.5);
        options.getOptions().getItems().addAll(
                options.getConnect(),
                options.getDelete(),
                options.getAddEx(),
                options.getAddDes(),
                options.getAddDef(),
                options.getAddPhrase()
        );

        for (WordBlock wordBlock : editableWordList) {
            wordListView.getItems().add(wordBlock.getWord());
        }

        options.getDelete().setText("Delete selected");

        options.getAddDes().setOnAction(event -> {
            addDescription();
        });

        options.getAddDef().setOnAction(event -> {
            addDefinition();
        });

        options.getDelete().setOnAction(event -> {
            deleteSelectedNode();
        });

        options.getAddPhrase().setOnAction(event -> {
            addPhrase();
        });

        options.getAddEx().setOnAction(event -> {
            addExample();
        });

        canvas.getContent().addEventHandler(MouseEvent.MOUSE_PRESSED, mousePressHandler);
        canvas.getContent().addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseDragHandler);
        canvas.getContent().addEventHandler(MouseEvent.MOUSE_RELEASED, mouseReleaseHandler);
        canvas.getContent().addEventHandler(MouseEvent.DRAG_DETECTED, dragDetected);
        canvas.addEventHandler(KeyEvent.KEY_PRESSED, keyPressHandler);
        canvasPane.getChildren().add(temporaryLine);
        showEditingTools(false);
    }


    public void showEditingTools(boolean flag) {
        exampleButton.setVisible(flag);
        descriptionButton.setVisible(flag);
        definitionButton.setVisible(flag);
        phraseButton.setVisible(flag);
        saveButton.setVisible(flag);
        deleteButton.setVisible(flag);
        connectButton.setVisible(flag);
    }

    public void updateListView() {
        wordListView.getItems().clear();
        for (WordBlock wordBlock : editableWordList) {
            wordListView.getItems().add(wordBlock.getWord());
        }

    }

    EventHandler<MouseEvent> mousePressHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (DicNode.isInConnectMode()) {
                if (event.getButton() == MouseButton.PRIMARY) {
                    canvas.setPannable(false);
                    DicNode.deselectAllExcept(DicNode.getCurrentlySelected());
                    if (DicNode.getCurrentlySelected() != null) {
                        VBox tmp = DicNode.getCurrentlySelected().getNodePane();
                        temporaryLine.setStartX(tmp.getLayoutX() + tmp.getWidth()/2);
                        temporaryLine.setStartY(tmp.getLayoutY() + tmp.getHeight()/2);
                        temporaryLine.setEndX(tmp.getLayoutX());
                        temporaryLine.setEndY(tmp.getLayoutY());
                        temporaryLine.setVisible(true);
                    }
                } else {
                    canvas.setPannable(true);
                }
            } else if (event.getButton() == MouseButton.PRIMARY) {
                editorPane.getChildren().clear();
                DicNode.deselectAll();
                mouseStartX = event.getX();
                mouseStartY = event.getY();
                selectionRectangle.setLayoutX(mouseStartX);
                selectionRectangle.setLayoutY(mouseStartY);
                selectionRectangle.setVisible(true);
                selectionRectangle.toFront();
            }
        }
    };

    EventHandler<MouseEvent> dragDetected = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

        }
    };

    EventHandler<MouseEvent> mouseDragHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (DicNode.isInConnectMode()) {
                if (DicNode.getNodeList().isEmpty()) {
                    temporaryLine.setVisible(false);
                }
                if (DicNode.getEndNode() != null) {
                    temporaryLine.setEndX(DicNode.getEndNode().getNodePane().getLayoutX() + DicNode.getEndNode().getNodePane().getWidth()/2);
                    temporaryLine.setEndY(DicNode.getEndNode().getNodePane().getLayoutY() + DicNode.getEndNode().getNodePane().getHeight()/2);
                } else {
                    temporaryLine.setEndX(event.getX());
                    temporaryLine.setEndY(event.getY());
                }
            } else if (event.getButton() == MouseButton.PRIMARY) {
                double currentX = event.getX();
                double currentY = event.getY();

                // Calculate the width and height based on the mouse movement
                double width = Math.abs(currentX - mouseStartX);
                double height = Math.abs(currentY - mouseStartY);

                // Adjust the position of the rectangle based on the mouse movement
                double layoutX = Math.min(currentX, mouseStartX);
                double layoutY = Math.min(currentY, mouseStartY);

                selectionRectangle.setLayoutX(layoutX);
                selectionRectangle.setLayoutY(layoutY);

                selectionRectangle.setWidth(width);
                selectionRectangle.setHeight(height);

                for (DicNode node : DicNode.getNodeList()) {
                    node.compareWithMouse(selectionRectangle);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                canvas.setPannable(true);
            }
        }
    };

    EventHandler<MouseEvent> mouseReleaseHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            if (DicNode.isInConnectMode()) {
                temporaryLine.setVisible(false);
                canvas.setPannable(false);
                if (!DicNode.isToggleConnectMode()) {
                    DicNode.setInConnectMode(false);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                selectionRectangle.setLayoutX(event.getX());
                selectionRectangle.setLayoutY(event.getY());
//            System.out.println(event.getSource());
                canvas.setPannable(false);
                if (!canvas.isPannable() && DicNode.getCurrentlyEditedWord() != null) {
                    options.getOptions().show(selectionRectangle, Side.BOTTOM, 0 , 0);
                }

            } else if (event.getButton() == MouseButton.PRIMARY) {
                if (DicNode.getCurrentlySelected() != null) {
                    switchScene();
                }
            }


            int cnt = 0;
            for (DicNode node : DicNode.getNodeList()) {
                if (node.isSelected()) {
                    cnt++;
                }
                if (cnt > 1) {
                    DicNode.setBulkSelect(true);
                    editorPane.getChildren().clear();
                    break;
                }
            }
            resetSelectionRectangle();
        }
    };

    EventHandler<KeyEvent> keyPressHandler = new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.DELETE && !DicNode.getNodeList().isEmpty()) {
                deleteSelectedNode();
            }
            if (event.getCode() == KeyCode.ESCAPE) {
                DicNode.deselectAll();
            }
        }
    };

    // This method resets the selection rectangle to its initial state
    private void resetSelectionRectangle() {
        selectionRectangle.setVisible(false);
        selectionRectangle.setWidth(0);
        selectionRectangle.setHeight(0);
        selectionRectangle.setLayoutX(-5);
        selectionRectangle.setLayoutY(-5);
    }

    public void deleteSelectedNode() {
        for (int i = 0; i < DicNode.getNodeList().size(); i++) {
            if (DicNode.getNodeList().get(i).isSelected()
                && DicNode.getNodeList().get(i) != DicNode.getCurrentlyEditedWord()) {
                DicNode.nodeList.get(i).selfDelete();
                i--;
                DicNode.setBulkSelect(false);
            }
        }
    }

    public void setAnchor(AnchorPane editorPane) {
        AnchorPane.setBottomAnchor(editorPane, 0d);
        AnchorPane.setTopAnchor(editorPane, 0d);
        AnchorPane.setRightAnchor(editorPane, 0d);
        AnchorPane.setLeftAnchor(editorPane, 0d);

    }

    public void switchScene() {
        if (DicNode.getCurrentlySelected() instanceof DescriptionNode) {
            setAnchor(((DescriptionNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((DescriptionNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        } else if (DicNode.getCurrentlySelected() instanceof ExampleNode) {
            setAnchor(((ExampleNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((ExampleNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        } else if (DicNode.getCurrentlySelected() instanceof DefinitionNode) {
            setAnchor(((DefinitionNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((DefinitionNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        } else if (DicNode.getCurrentlySelected() instanceof PhraseNode) {
            setAnchor(((PhraseNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((PhraseNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        } else if (DicNode.getCurrentlySelected() instanceof WordNode) {
            setAnchor(((WordNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
            editorPane.getChildren().clear();
            editorPane.getChildren().add(((WordNode) DicNode.getCurrentlySelected()).getEditor().getEditorPane());
        }
    }

    public void addNode(DicNode node) {
        DicNode.getNodeList().add(node);
        canvasPane = (AnchorPane) canvas.getContent();
        canvasPane.getChildren().add(node.getNodePane());
        canvasPane.getChildren().add(node.getLineToParent());
        node.getLineToParent().toBack();
        node.setNodePanePosition((-1) * canvas.getViewportBounds().getMinX(),
                (-1) * canvas.getViewportBounds().getMinY());
    }

}
