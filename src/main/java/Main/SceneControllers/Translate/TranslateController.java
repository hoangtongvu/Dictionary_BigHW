package Main.SceneControllers.Translate;

import Main.SceneControllers.BaseSceneController;
import Interfaces.IHasNavPane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import LanguageTranslation.TranslatorAPI;


public class TranslateController extends BaseSceneController implements IHasNavPane {
    @FXML
    private TextArea VietArea;

    @FXML
    private TextArea EngArea;

    @FXML
    private Label recordingStatus;

    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {

    }

    @FXML
    public void onViToEnButton(ActionEvent event) {
        String text = VietArea.getText();
        if (!text.isEmpty()) {
            EngArea.setText(TranslatorAPI.translateViToEn(text));
        }
    }

    @FXML
    public void onEnToViButton(ActionEvent event) {
        String text = EngArea.getText();
        if (!text.isEmpty()) {
            VietArea.setText(TranslatorAPI.translateEnToVi(text));
        }
    }

    @FXML
    public void ViSoundButton(ActionEvent event) {
        String text = VietArea.getText();
        if (!text.isEmpty()) {
            TextToSpeech.ViTextToSpeech(text);
        }
    }

    @FXML
    public void EnSoundButton(ActionEvent event) {
        String text = EngArea.getText();
        if (!text.isEmpty()) {
            TextToSpeech.EnTextToSpeech(text);
        }
    }

    @FXML
    public void onMicrophoneButton(ActionEvent e) {
        if (!AudioManager.isRecording()) {
            recordingStatus.setText("Listening...");
            Thread voiceRegThread = new Thread(() -> {
                AudioManager.startRecording();
                EngArea.setText("Retrieving data...");
                String searchRes = SpeechToText.getSpeechToText();
                Platform.runLater(() -> {
                    EngArea.setText(searchRes);
                });
            });
            voiceRegThread.setDaemon(true);
            voiceRegThread.start();
        } else {
            recordingStatus.setText("");
            AudioManager.stopRecording();
            System.out.println("Recording stopped");
        }
    }
}
