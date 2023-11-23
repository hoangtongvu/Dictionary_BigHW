package Main.SceneControllers.Widget;

import Main.ProjectDirectory;
import Timer.StudyTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;

public class StudyTimerController {
    @FXML
    AnchorPane timerPane;
    @FXML
    protected TextField timerTextField;
    @FXML
    protected Button pauseButton;
    @FXML
    protected Button startButton;
    @FXML
    protected Button resetButton;
    @FXML
    protected CheckBox soundCheckBox;
    @FXML
    protected Label timerLabel;

    public static StudyTimerController loadInstance() throws IOException {
        String absolutePath = ProjectDirectory.resourcesPath + "\\fxml\\Widget\\StudyTimer.fxml";
        URL fxmlURL = Paths.get(absolutePath).toUri().toURL();
        FXMLLoader loader = new FXMLLoader(fxmlURL);
        loader.load();

        return loader.getController();
    }

    public void addToParent(Pane parent) {
        parent.getChildren().addAll(timerPane.getChildren());
    }

    @FXML
    public void onTick() {

    }

    @FXML
    public void onTimerSet() {
        StudyTimer.getInstance().setTime(Integer.parseInt(timerTextField.getText()));
    }

    @FXML
    public void filterNumber(KeyEvent event) {

    }

    @FXML
    public void startTimer() {
        StudyTimer.getInstance().start();
    }

    @FXML
    public void pauseTimer() {
        if (StudyTimer.getInstance().isPlaying()) {
            pauseButton.setText("Resume");
            StudyTimer.getInstance().pause();
            resetButton.setDisable(false);
        } else {
            pauseButton.setText("Pause");
            StudyTimer.getInstance().resume();
            resetButton.setDisable(true);
        }
    }

    @FXML
    public void resetTimer() {
        StudyTimer.getInstance().reset();
    }

    @FXML
    public void onKeyTyped() {
        if (timerTextField.getText() != null) {
            StudyTimer.getInstance().setTime(Integer.parseInt(timerTextField.getText()));
        }
    }
    public void initialize() {
        timerLabel.textProperty().bind(StudyTimer.getInstance().timeProperty());
        resetButton.setDisable(true);
        timerTextField.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (!event.getCharacter().matches("\\d")) {
                    event.consume();
                }
            }
        });
    }




}
