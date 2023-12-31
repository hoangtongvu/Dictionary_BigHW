package Main.SceneControllers.Dictionary;

import Main.FxmlFileManager;
import Main.SceneControllers.BaseSceneController;
import Interfaces.IHasNavPane;
import Main.SceneControllers.Widget.TodayDateTimeBox;
import Main.SceneControllers.Widget.StudyTimerController;

import TodoList.UI.TodoList;

import Ranking.LeaderBoard;

import User.User;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;

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
    protected VBox leaderboardVbox;
    @FXML
    protected ScrollPane leaderBoardScrollPane;
    @FXML
    protected Label ratioLabel;

    @FXML
    private AnchorPane todoListHolder;

    @FXML
    private AnchorPane todayPlaceHolder;

    @FXML
    protected Label goalLabel;

    /**This part is for side menu*/
    @FXML
    public void initialize() {
        //this.SwitchToLookUpScene(); //dunno why this bug.
//        leaderBoardVbox.prefWidthProperty().bind(leaderBoardScrollPane.widthProperty());
        leaderBoardScrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        try {
            StudyTimerController.loadInstance().addToParent(timerPlaceHolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        blurPane.setVisible(false);
        update();
        TodoList todoList = TodoList.CreateInstance();
        todoList.ChangeParent(this.todoListHolder);
        leaderboardVbox.setSpacing(10);
        this.UpdateTodayPlaceHolder();
    }

    private void UpdateTodayPlaceHolder()
    {
        TodayDateTimeBox todayDateTimeBox = TodayDateTimeBox.CreateInstance();
        todayDateTimeBox.SetNewParentPane(this.todayPlaceHolder);
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
            dailyChart.getData().clear();
        }
    }

    public void setUpPieChart() {
        dailyGoalChart.getData().clear();
        if (User.getCurrentUser().isOnline()) {
            AccountManager.getInstance().getPieChart(dailyGoalChart);
            AccountManager.getInstance().getLabel(ratioLabel, AccountManager.DataCategory.COMPLETION_RATIO);
        } else {
            dailyGoalChart.getData().add(new PieChart.Data("", 1));
            dailyGoalChart.setLegendVisible(false);
        }

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


    @Override
    public void StartShow() {
        update();
    }

    @Override
    public void EndShow() {

    }

    @Override
    public void update() {
        updateChart();
        leaderboardVbox.getChildren().clear();
        if (User.getCurrentUser().isOnline()) {
            AccountManager.getInstance().getLabel(goalLabel, AccountManager.DataCategory.DAILY_GOAL);
        }
        LeaderBoard.getInstance().updateGUI(leaderboardVbox);
    }

    @FXML
    public void toDictionary() {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().dictionarySC);
    }

    @FXML
    public void toChatBot() {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().aiSC);
    }

    @FXML
    public void toTranslate() {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().translateSC);
    }

    @FXML
    public void toGames() {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().chooseGameSC);
    }

    @FXML
    public void toThesaurus() {
        FxmlFileManager.SwitchScene(FxmlFileManager.getInstance().thesaurusSC);
    }
}
