package Main.SceneControllers.Translate;

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
        EngArea.setText(TranslatorAPI.translateViToEn(text));

    }

    @FXML
    public void onEnToViButton(ActionEvent event) {
        String text = EngArea.getText();
        VietArea.setText(TranslatorAPI.translateEnToVi(text));

    }
}
