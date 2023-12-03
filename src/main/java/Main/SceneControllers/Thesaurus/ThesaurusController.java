package Main.SceneControllers.Thesaurus;

import Interfaces.IHasNavPane;
import Main.SceneControllers.BaseSceneController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.action.Action;
import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class ThesaurusController extends BaseSceneController implements IHasNavPane {
    @FXML
    private TextField sourceWord;
    @FXML
    private TextArea synonymArea;
    @FXML
    private TextArea antonymArea;
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
    public void onContinueButton(ActionEvent e) {
        String text = sourceWord.getText();
        if (!text.isEmpty()) {
            JSONObject list = ThesaurusAPI.thesaurus(sourceWord.getText());
            synonymArea.setText(fetchWords(list, "synonyms"));
            antonymArea.setText(fetchWords(list, "antonyms"));
        }
    }

    public static String fetchWords(JSONObject list, String type) {
        JSONArray wordlist = list.getJSONArray(type);
        String res = "";
        if (wordlist.isEmpty()) {
            return "Can't find anything";
        }

        for (Object v : wordlist) {
            res += (String) v + "\n";
        }
        return res;
    }

}
