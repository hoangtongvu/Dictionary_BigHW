package Main.SceneControllers.Dictionary;

import Main.Database;
import Main.SceneControllers.BaseSceneController;
import Interfaces.IHasNavPane;
import Main.SceneControllers.Widget.StudyTimerController;
import User.User;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import User.DailyRecord;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import jnr.ffi.annotations.In;


public class HomeSceneController extends BaseSceneController implements IHasNavPane {
    @FXML
    protected AnchorPane root;
    @FXML
    AnchorPane timerPlaceHolder;

    @FXML
    Pane blurPane;

    @FXML
    LineChart<String, Number> dailyChart;
    @FXML
    PieChart dailyGoalChart;

    @FXML
    protected VBox leaderBoardVbox;
    @FXML
    protected ScrollPane leaderBoardScrollPane;

    /**This part is for side menu*/
    @FXML
    public void initialize() {
        //this.SwitchToLookUpScene(); //dunno why this bug.
        leaderBoardVbox.prefWidthProperty().bind(leaderBoardScrollPane.widthProperty());
        leaderBoardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        try {
            StudyTimerController.loadInstance().addToParent(timerPlaceHolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        blurPane.setVisible(false);

    }

    public void updateChart() {
        if (User.getCurrentUser().isOnline()) {
            dailyChart.getData().clear();
            setUpLineChart();
            dailyGoalChart.getData().clear();
            setUpPieChart();
        } else {
            PieChart.Data incomplete = new PieChart.Data("Incomplete", 100);
            dailyGoalChart.getData().add(incomplete);
            dailyGoalChart.setLabelsVisible(false);
            incomplete.getNode().setStyle("-fx-background-color: grey");
        }
    }

    public void setUpPieChart() {
        Integer dailyGoal = User.getCurrentUser().getStudyGoal();
        Integer studyTime = User.getCurrentUser().getCurrentRecord().getStudy_time();
        Double completionRate = ((double)studyTime / dailyGoal);
        if (dailyGoal == 0) {
            PieChart.Data incomplete = new PieChart.Data("Incomplete", 100);
            dailyGoalChart.getData().add(incomplete);
            incomplete.getNode().setStyle("-fx-background-color: grey");
        }

        if (completionRate > 1) {
            PieChart.Data progress = new PieChart.Data("Completed", 100);
            dailyGoalChart.getData().add(progress);
            progress.getNode().setStyle("-fx-background-color: green");
        } else {
            PieChart.Data progress = new PieChart.Data("Completed", completionRate * 100);
            PieChart.Data incomplete = new PieChart.Data("Incomplete", (1 - completionRate) * 100);
            dailyGoalChart.getData().add(progress);
            dailyGoalChart.getData().add(incomplete);


            progress.getNode().setStyle("-fx-background-color: green");
            incomplete.getNode().setStyle("-fx-background-color: grey");

            dailyGoalChart.setLabelsVisible(false);

        }
    }

    public void setUpLineChart() {
        try {
            XYChart.Series<String, Number> sessionTimeSeries = new XYChart.Series<>();
            List<DailyRecord> recordList = User.getCurrentUser().getDailyRecordList();

            //Create series
            for (DailyRecord record : recordList) {
                sessionTimeSeries.getData().add(new XYChart.Data<>(record.getAccessDate(), record.getSession_time()));
            }
            //Add series to chart
            dailyChart.getData().add(sessionTimeSeries);
            addChartLine(sessionTimeSeries, "Online time");

            XYChart.Series<String, Number> studyTimeSeries = new XYChart.Series<>();
            //Create series
            for (DailyRecord record : recordList) {
                studyTimeSeries.getData().add(new XYChart.Data<>(record.getAccessDate(), record.getStudy_time()));
            }
            //Add series to chart
            dailyChart.getData().add(studyTimeSeries);
            addChartLine(studyTimeSeries, "Study time");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addChartLine(XYChart.Series<String, Number> series, String name) {
            for (XYChart.Data<String, Number> entry : series.getData()) {
                Integer time = (Integer)entry.getYValue();
                String[] tmp = entry.getXValue().split("-");
                String date = tmp[2] + "/" + tmp[1] + "/" + tmp[0];
                Tooltip tooltip = null;
                if (time < 3600) {
                    tooltip = new Tooltip(name + ":\n" + String.format("%.2f", time/60f) + " minutes\n" + String.format("Date: %s", date));
                } else {
                    tooltip = new Tooltip(name + ":\n" + String.format("%.2f", time/60f/60f) + " hours\n" + String.format("Date: %s", date));
                }

                Tooltip.install(entry.getNode(), tooltip);
                tooltip.setShowDelay(Duration.seconds(0));
            }
    }

    public void setUpLeaderBoard() {
        String query = "SELECT user_name, score FROM user_credentials ORDER BY score, user_name DESC LIMIT 10";
        try {
            PreparedStatement statement = Database.getUserDB().prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            int ranking = 1;
            while (resultSet.next()) {

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRankingCard(String userName, int score, int rank) {
        AnchorPane pane = new AnchorPane();
        pane.setPrefWidth(260); pane.setPrefHeight(62);

    }


    @Override
    public void StartShow() {
        updateChart();
    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {

    }
}
