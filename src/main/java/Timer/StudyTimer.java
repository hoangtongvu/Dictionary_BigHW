package Timer;

import Main.ProjectDirectory;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.w3c.dom.Text;
import javafx.scene.media.Media;

import java.net.URL;

public class StudyTimer {
    private static StudyTimer instance;
    private Integer timeSeconds;
    private Timeline timeline;
    private boolean isPlaying = false;
    private StringProperty time = new SimpleStringProperty();

    URL resource = getClass().getResource("/Audio/alarm.mp3");
    private Media media = new Media(resource.toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(media);

    private StudyTimer() {
        timeSeconds = 0;
        time.set(toString());
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        event -> {
                            this.timeSeconds--;
//                            System.out.println(this.toString());
                            time.set(toString());
                            System.out.println(toString());
                            if (this.timeSeconds <= 0) {
                                timeline.stop();
                                mediaPlayer.setStopTime(Duration.millis(8500));
                                mediaPlayer.play();
                                isPlaying = false;
                            }
                        }
                )
        );
    }

    public static StudyTimer getInstance() {
        if (instance == null) {
            instance = new StudyTimer();
        }
        return instance;
    }


    public void start() {
        timeline.playFromStart();
        isPlaying = true;
    }

    public void setTime(int minutes) {
        this.timeSeconds = minutes * 60;
        time.set(toString());
    }

    public void pause() {
        timeline.pause();
        isPlaying = false;
    }

    public void resume() {
        timeline.play();
        isPlaying = true;
    }

    public void reset() {
        timeSeconds = 0;
        isPlaying = false;
        time.set(toString());
        timeline.stop();
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }

    public StringProperty timeProperty() {
        return time;
    }

    public String toString() {
        int hour    = timeSeconds/3600;
        int minutes = (timeSeconds/60)%60;
        int seconds = timeSeconds % 60;

        return String.format("%02d:%02d:%02d",hour, minutes, seconds);
    }
}