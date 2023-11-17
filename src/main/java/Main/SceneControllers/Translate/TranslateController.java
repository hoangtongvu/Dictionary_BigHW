package Main.SceneControllers.Translate;

import TextToSpeech.TextToSpeech;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.AccessibleAction;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class TranslateController {
    @FXML
    private TextArea VietArea;

    @FXML
    private TextArea EngArea;


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
    public void onViSoundButton(ActionEvent event) {
        TextToSpeech.ViTextToSpeech(VietArea.getText());
    }

    @FXML
    public void onEnSoundButton(ActionEvent event) {
        TextToSpeech.EnTextToSpeech(EngArea.getText());
    }
}
