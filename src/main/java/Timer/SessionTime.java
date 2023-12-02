package Timer;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class SessionTime {
    private int seconds;
    private Timeline timeline;
    private static SessionTime instance;

    private SessionTime() {
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), event -> {
            seconds++;
        }));
    }

    public static SessionTime getInstance() {
        if (instance == null) {
            instance = new SessionTime();
        }
        return instance;
    }

    public void startCounter() {
        timeline.playFromStart();
    }

    public void stopCounter() {
        timeline.stop();
    }

    public int getSeconds() {
        return seconds;
    }
}
