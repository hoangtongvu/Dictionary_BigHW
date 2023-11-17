package WordEditing;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.util.Optional;

public class Warnings {
    private static Warnings instance;
    protected ButtonType yes = new ButtonType("Yes");
    protected ButtonType cancel  = new ButtonType("Cancel");
    protected ButtonType no = new ButtonType("No");
    protected ButtonType close = new ButtonType("Close");
    
    Alert addWordWarning = new Alert(Alert.AlertType.NONE);
    Alert wordExisted = new Alert(Alert.AlertType.NONE);
    Alert emptyWordWarning = new Alert(Alert.AlertType.NONE);
    Alert saveChanges = new Alert(Alert.AlertType.NONE);
    Alert deleteWordWarning = new Alert(Alert.AlertType.NONE);
    private Warnings() {
        addWordWarning.setTitle("Dictionary");
        addWordWarning.setHeaderText("Make sure all components are linked before continuing");
        Label content = new Label("Any unlinked components will be deleted. Would you like to save before continuing?");
        content.setWrapText(true);
        addWordWarning.getDialogPane().setContent(content);
        addWordWarning.getButtonTypes().setAll(yes, no, cancel);
        
        wordExisted.setTitle("Dictionary");
        wordExisted.setHeaderText("Cannot add word");
        wordExisted.setContentText("This word already existed");
        wordExisted.getButtonTypes().setAll(close);

        emptyWordWarning.setTitle("Dictionary");
        emptyWordWarning.setHeaderText("Cannot add word");
        emptyWordWarning.setContentText("This word is empty");
        emptyWordWarning.getButtonTypes().setAll(close);

        saveChanges.setTitle("Dictionary");
        saveChanges.setHeaderText("Successfully saved word");
        saveChanges.getButtonTypes().setAll(close);

        deleteWordWarning.setTitle("Dictionary");
        deleteWordWarning.setHeaderText("You are DELETING this word");
        deleteWordWarning.setContentText("This word will be permanently deleted. \nAre you sure you want to delete?");
        deleteWordWarning.getButtonTypes().setAll(yes, no);
    }

    public int newWordWarning() {
        Optional<ButtonType> result = addWordWarning.showAndWait();
        if (result.isPresent()) {
            if (result.get() == yes) {
                addWordWarning.close();
                return 1;
            } else if (result.get() == no) {
                addWordWarning.close();
                return -1;
            } else {
                addWordWarning.close();
                return 0;
            }
        }
        return 0;
    }

    public boolean showDeleteWordWarning() {
        Optional<ButtonType> result = deleteWordWarning.showAndWait();
        if (result.isPresent()) {
            if (result.get() == yes) {
                deleteWordWarning.close();
                return true;
            } else {
                deleteWordWarning.close();
                return false;
            }
        }
        return false;
    }

    public void showSavedNotice() {
        saveChanges.showAndWait();
    }
    
    public void showWordExistWarning() {
        wordExisted.showAndWait();
    }

    public void showEmptyWordWarning() {
        emptyWordWarning.showAndWait();
    }

    public static Warnings getInstance() {
        if (instance == null) {
            instance = new Warnings();
        }
        return instance;
    }
}
