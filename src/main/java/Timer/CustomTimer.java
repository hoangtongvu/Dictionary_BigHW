package Timer;

import CustomEventPackage.ZeroParameter.CustomEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class CustomTimer
{
    public final CustomEventPackage.TwoParameters.CustomEvent<Integer, Integer> onTickEvent;
    public final CustomEvent onStopEvent;

    private Timeline timeline;
    private boolean isRunning = false;
    private int counter = 0;
    
    public void setMaxTimeSecond(int maxTimeSecond)
    {
        this.timeline.setCycleCount(maxTimeSecond);
    }

    public CustomTimer()
    {
        this.InitTimeline();
        this.setMaxTimeSecond(0);

        this.onTickEvent = new CustomEventPackage.TwoParameters.CustomEvent<>(this);
        this.onStopEvent = new CustomEvent(this);
    }

    private void InitTimeline()
    {
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), e -> this.Tick());
        this.timeline = new Timeline(keyFrame);
    }

    public void Start()
    {
        if (this.isRunning) return;
        this.isRunning = true;
        this.ResetCounter();
        this.timeline.play();
    }

    public void Stop()
    {
        if (!this.isRunning) return;
        this.isRunning = false;
        this.onStopEvent.Invoke(this);
        this.timeline.stop();
    }

    private void Tick()
    {
        counter++;
        this.onTickEvent.Invoke(this, this.counter, this.timeline.getCycleCount());
        if (this.counter >= this.timeline.getCycleCount()) this.Stop();
    }

    public void ResetCounter()
    {
        this.counter = 0;
    }

}
