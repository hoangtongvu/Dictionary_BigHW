package Main.SceneControllers.Account;

import Interfaces.IHasNavPane;
import Main.SceneControllers.BaseSceneController;
import User.DailyRecord;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jnr.ffi.LastError;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserProfileSceneController extends BaseSceneController implements Initializable, IHasNavPane {

    @FXML
    Label sessionTimeLabel;
    @FXML
    Label studyTimeLabel;
    @FXML
    Label scoreLabel;

    @Override
    public void StartShow() {
        List<DailyRecord> dailyRecordList = User.getCurrentUser().getDailyRecordList();
        int totalAccessTime = 0;
        int totalStudyTime = 0;
        double score = User.getCurrentUser().getScore();
        for (DailyRecord record : dailyRecordList) {
            totalAccessTime += record.getSession_time();
            totalStudyTime += record.getStudy_time();
        }

        if (totalAccessTime < 3600) {
            sessionTimeLabel.setText(String.format("%dm", totalAccessTime/60));
        } else {
            sessionTimeLabel.setText(String.format("%dh%dm", totalAccessTime/3600, totalAccessTime%3600/60));
        }
        if (totalStudyTime < 3600) {
            studyTimeLabel.setText(String.format("%dm", totalStudyTime/60));
        } else {
            studyTimeLabel.setText(String.format("%dh%dm", totalStudyTime/3600, totalStudyTime%3600/60));
        }
        scoreLabel.setText(String.valueOf(score));
    }

    @Override
    public void EndShow() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
