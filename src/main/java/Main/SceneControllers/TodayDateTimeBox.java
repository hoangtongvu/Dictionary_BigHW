package Main.SceneControllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class TodayDateTimeBox implements Initializable
{

    @FXML private Label timeLabel;
    @FXML private Label dayLabel;
    @FXML private Label dateLabel;
    @FXML private VBox rootVbox;



    public static void main(String[] args)
    {
        LocalDateTime localDateTime = java.time.LocalDateTime.now();
        System.out.println(localDateTime.getDayOfMonth());
        System.out.println(localDateTime.getMonth());
        System.out.println(localDateTime.getYear());
        System.out.println(localDateTime.getDayOfWeek());
    }

    public TodayDateTimeBox()
    {
    }

    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        this.SetSync(1);
    }

    private void SetSync(int delaySecond)
    {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(delaySecond), e -> this.SetAll());
        Timeline timeline = new Timeline(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void SetAll()
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        this.SetTime(localDateTime);
        this.SetDay(localDateTime);
        this.SetDate(localDateTime);
    }

    private void SetTime(LocalDateTime localDateTime)
    {
        this.timeLabel.setText(localDateTime.getHour() + ":" + localDateTime.getMinute() + ":" + localDateTime.getSecond());
        this.timeLabel.setText(String.format("%02d:%02d:%02d", localDateTime.getHour(), localDateTime.getMinute(), localDateTime.getSecond()));
    }

    private void SetDay(LocalDateTime localDateTime)
    {
        this.dayLabel.setText(localDateTime.getDayOfWeek() + "");
    }

    private void SetDate(LocalDateTime localDateTime)
    {
        this.dateLabel.setText(localDateTime.getDayOfMonth() + " " + localDateTime.getMonth() + " " + localDateTime.getYear());
    }


    public static TodayDateTimeBox CreateInstance()
    {
        FXMLLoader loader = new FXMLLoader(TodayDateTimeBox.class.getResource("/fxml/TodayDateTime/todayDateTime.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return loader.getController();
    }

    public void SetNewParentPane(Pane pane)
    {
        pane.getChildren().add(this.rootVbox);
    }

}
