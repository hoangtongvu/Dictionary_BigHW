package WordEditing;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Warnings {
    private static Warnings instance;
    protected ButtonType proceed = new ButtonType("Proceed");
    protected ButtonType cancel  = new ButtonType("Cancel");
    Alert addWordWarning = new Alert(Alert.AlertType.NONE);

    private Warnings() {
        addWordWarning.setTitle("Adding a new word");
        addWordWarning.setHeaderText("Make sure all components are linked before continuing");
        addWordWarning.setContentText("All unlinked components except for DESCRIPTION will be deleted");
        addWordWarning.getButtonTypes().setAll(proceed, cancel);
    }

    public boolean addWordWarning() {
        Optional<ButtonType> result = addWordWarning.showAndWait();
        if (result.isPresent() && result.get() == proceed) {
            addWordWarning.close();
            return true;
        } else {
            addWordWarning.close();
            return false;
        }
    }

    public static Warnings getInstance() {
        if (instance == null) {
            instance = new Warnings();
        }
        return instance;
    }
}
