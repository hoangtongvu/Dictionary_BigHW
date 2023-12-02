package Main.SceneControllers.Account;

import Interfaces.IHasNavPane;
import Main.SceneControllers.BaseSceneController;
import User.DailyRecord;
import User.User;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import User.AccountManager;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserProfileSceneController extends BaseSceneController implements Initializable, IHasNavPane {

    @FXML
    Label totalSessionTimeLabel;
    @FXML
    Label studyTimeLabel;
    @FXML
    Label scoreLabel;
    @FXML
    LineChart<String, Number> dailyChart;
    @FXML
    PieChart dailyGoalChart;
    @FXML
    Label ratioLabel;
    @FXML
    Label dailyGoalLabel;
    @FXML
    Label totalStudyTimeLabel;
    @FXML
    Label ratioLabel1;

    @Override
    public void StartShow() {
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

    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
