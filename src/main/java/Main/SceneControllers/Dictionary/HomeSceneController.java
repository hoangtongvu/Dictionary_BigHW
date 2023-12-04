package Main.SceneControllers.Dictionary;

import Main.Database;
import Main.SceneControllers.BaseSceneController;
import Interfaces.IHasNavPane;
import Main.SceneControllers.Widget.StudyTimerController;
import TodoList.UI.TodoList;
import User.User;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.layout.VBox;
import User.AccountManager;


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
    @FXML
    protected Label ratioLabel;

    @FXML
    private AnchorPane todoListHolder;

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

        TodoList todoList = TodoList.CreateInstance();
        todoList.ChangeParent(this.todoListHolder);

    }

    public void updateChart() {
        if (User.getCurrentUser().isOnline()) {
            setUpLineChart();
            setUpPieChart();
        } else {
            PieChart.Data incomplete = new PieChart.Data("Incomplete", 100);
            dailyGoalChart.getData().add(incomplete);
            dailyGoalChart.setLabelsVisible(false);
            incomplete.getNode().setStyle("-fx-background-color: grey");
        }
    }

    public void setUpPieChart() {
        dailyGoalChart.getData().clear();
        AccountManager.getInstance().getPieChart(dailyGoalChart);
        AccountManager.getInstance().getLabel(ratioLabel, AccountManager.DataCategory.COMPLETION_RATIO);
    }

    public void setUpLineChart() {
        try {
            dailyChart.getData().clear();
            AccountManager.getInstance().getLineChart(dailyChart, AccountManager.DataCategory.STUDY_TIME, "Study time");
            AccountManager.getInstance().getLineChart(dailyChart, AccountManager.DataCategory.SESSION_TIME, "Access time");
        } catch (Exception e) {
            e.printStackTrace();
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
