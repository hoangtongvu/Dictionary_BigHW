package Main.SceneControllers.Account;

import Main.FxmlFileManager;
import Main.ProjectDirectory;
import Main.SceneControllers.BaseSceneController;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class TimePickerController extends BaseSceneController {

    @FXML
    protected Label notification;
    @FXML
    protected Spinner<Integer> hourField;
    @FXML
    protected Spinner<Integer> minuteField;
    @FXML
    protected Spinner<Integer> secondField;
    @FXML
    protected AnchorPane root;

    public void initialize() {
        System.out.println("Good");
        hourField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0));
        minuteField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));
        secondField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0));

        EventHandler<KeyEvent> handler = event -> {
            if (!"0123456789".contains(event.getCharacter())) {
                event.consume();
            }
        };

        hourField.getEditor().addEventFilter(KeyEvent.KEY_TYPED, handler);
        minuteField.getEditor().addEventFilter(KeyEvent.KEY_TYPED, handler);
        secondField.getEditor().addEventFilter(KeyEvent.KEY_TYPED, handler);

    }


    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {

    }

    public void addToParent(StackPane parent) {
        parent.getChildren().addAll(root);
    }

    public static TimePickerController loadInstance() throws IOException {
        FXMLLoader loader;
        String absolutePath = ProjectDirectory.resourcesPath + "\\fxml\\application\\TimePicker.fxml";
        URL fxmlURL = Paths.get(absolutePath).toUri().toURL();
        loader = new FXMLLoader(fxmlURL);
        loader.load();

        return loader.getController();
    }

    @FXML
    public void exitTimePicker() {
        FxmlFileManager.getInstance().profileSC.timePickerPane.setVisible(false);
    }

    @FXML
    public void saveGoal() {

    }

}
