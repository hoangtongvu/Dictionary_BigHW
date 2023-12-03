package Main.SceneControllers.Account;

import Interfaces.IHasNavPane;
import Main.FxmlFileManager;
import Main.ProjectDirectory;
import Main.SceneControllers.BaseSceneController;
import Main.SceneControllers.Dictionary.HomeSceneController;
import Main.SceneControllers.Widget.StudyTimerController;
import User.DailyRecord;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import User.AccountManager;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Stack;

public class UserProfileSceneController extends BaseSceneController implements Initializable, IHasNavPane {

    @FXML
    protected Label totalSessionTimeLabel;
    @FXML
    protected Label studyTimeLabel;
    @FXML
    protected Label scoreLabel;
    @FXML
    protected LineChart<String, Number> dailyChart;
    @FXML
    protected PieChart dailyGoalChart;
    @FXML
    protected Label ratioLabel;
    @FXML
    protected Label dailyGoalLabel;
    @FXML
    protected Label totalStudyTimeLabel;
    @FXML
    protected Label ratioLabel1;
    @FXML
    protected Circle profilePic;
    @FXML
    protected StackPane editPicPane;
    @FXML
    AnchorPane root;
    @FXML
    protected StackPane timePickerPane;

    @Override
    public void StartShow() {
        update();
    }

    public void logOut() {
        User.getCurrentUser().logoutHandler();
        FxmlFileManager.getInstance().homeSC.update();
        try {
            StudyTimerController.loadInstance().resetTimer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().homeSC);
    }

    @FXML
    public void editStudyGoal() {
        int second = User.getCurrentUser().getStudyGoal();
        int hour = second/3600;
        int minute = second%3600/60;
        second = second%3600%60;

        try {
            TimePickerController.loadInstance().hourField.getValueFactory().setValue(hour);
            TimePickerController.loadInstance().minuteField.getValueFactory().setValue(minute);
            TimePickerController.loadInstance().secondField.getValueFactory().setValue(second);

            TimePickerController.loadInstance().notification.setText("Your current goal is set to "
                    + String.format("%dh%dm%ds", hour, minute, second));

            System.out.println("all set");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        timePickerPane.setVisible(true);
    }

    @FXML
    public void editProfile() {
        editPicPane.setVisible(true);
    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {
        if (User.getCurrentUser().isOnline()) {
            dailyGoalChart.getData().clear();
            dailyChart.getData().clear();
            AccountManager.getInstance().getLabel(dailyGoalLabel, AccountManager.DataCategory.DAILY_GOAL);
            AccountManager.getInstance().getLabel(ratioLabel1, AccountManager.DataCategory.COMPLETION_RATIO);
            AccountManager.getInstance().getLabel(ratioLabel, AccountManager.DataCategory.COMPLETION_RATIO);
            AccountManager.getInstance().getLabel(studyTimeLabel, AccountManager.DataCategory.STUDY_TIME);
            AccountManager.getInstance().getLabel(totalSessionTimeLabel, AccountManager.DataCategory.TOTAL_SESSION_TIME);
            AccountManager.getInstance().getLabel(totalStudyTimeLabel, AccountManager.DataCategory.TOTAL_STUDY_TIME);
            AccountManager.getInstance().getLabel(scoreLabel, AccountManager.DataCategory.SCORE);
            AccountManager.getInstance().getLineChart(dailyChart, AccountManager.DataCategory.STUDY_TIME, "Study time");
            AccountManager.getInstance().getLineChart(dailyChart, AccountManager.DataCategory.SESSION_TIME, "Access time");
            AccountManager.getInstance().getPieChart(dailyGoalChart);
            AccountManager.getInstance().loadProfilePic(profilePic);
        } else {

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        editPicPane.setVisible(false);
        timePickerPane.setVisible(false);
        try {
            EditProfilePic.loadInstance().addToParent(editPicPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            TimePickerController.loadInstance().addToParent(timePickerPane);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
