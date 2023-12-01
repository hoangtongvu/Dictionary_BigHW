package Main.SceneControllers.Dictionary;

import Main.SceneControllers.BaseSceneController;
import Interfaces.IHasNavPane;
import Main.SceneControllers.Widget.StudyTimerController;
import User.User;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import User.DailyRecord;
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

    /**This part is for side menu*/
    @FXML
    public void initialize() {
        //this.SwitchToLookUpScene(); //dunno why this bug.
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
    }

    public void addChartLine(XYChart.Series<String, Number> series, String name) {
            //Add toolTip
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

    @Override
    public void StartShow() {
        updateChart();
    }

    @Override
    public void EndShow() {

    }
}
