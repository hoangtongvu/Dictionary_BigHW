package Main.SceneControllers.Translate;

import Main.SceneControllers.BaseSceneController;
import Main.SceneControllers.IHasNavPane;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;


public class TranslateController extends BaseSceneController implements IHasNavPane {
    @FXML
    private TextArea VietArea;

    @FXML
    private TextArea EngArea;



    @Override
    public void StartShow() {

    }

    @Override
    public void EndShow() {

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
}
