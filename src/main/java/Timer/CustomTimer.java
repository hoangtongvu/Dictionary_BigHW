package Timer;

import java.util.*;
import CustomEventPackage.ZeroParameter.CustomEvent;
import javafx.application.Platform;

public class CustomTimer
{

    public final CustomEventPackage.OneParameter.CustomEvent<Integer> onTickEvent;
    public final CustomEvent onStopEvent;

    private Timer timer;
    private boolean isRunning = false;

    private int counter = 0;
    private int maxTimeSecond;

    public int getCounter()
    {
        return this.counter;
    }

    public void setMaxTimeSecond(int maxTimeSecond)
    {
        this.maxTimeSecond = maxTimeSecond;
    }
    public int getMaxTimeSecond() { return this.maxTimeSecond; }

    public CustomTimer()
    {
        this.maxTimeSecond = 0;

        this.onTickEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        this.onStopEvent = new CustomEvent(this);
    }

    public CustomTimer(int maxTimeSecond)
    {
        this.maxTimeSecond = maxTimeSecond;

        this.onTickEvent = new CustomEventPackage.OneParameter.CustomEvent<>(this);
        this.onStopEvent = new CustomEvent(this);

    }


    public void Start()
    {
        if (this.isRunning) return;
        this.AssignNewTimer();
        this.isRunning = true;
        this.ResetCounter();
        this.AddTimerTask();
    }

    public void Stop()
    {
        if (!this.isRunning) return;
        this.isRunning = false;
        this.timer.cancel();
        this.onStopEvent.Invoke(this);
        this.timer.purge();

    }

    private void Tick()
    {
        counter++;
        this.onTickEvent.Invoke(this, this.counter);
    }

    private void AssignNewTimer()
    {
        this.timer = new Timer();
    }
    public void ResetCounter()
    {
        this.counter = 0;
    }

    private void AddTimerTask()
    {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run()
            {
                Tick();
                OnApplicationExitChecking();
                if (counter >= maxTimeSecond) Stop();
            }
        };

        int delayTime = 1000;
        this.timer.scheduleAtFixedRate(timerTask, 0, delayTime);
    }

    private void OnApplicationExitChecking()
    {
        boolean isClose = Platform.isImplicitExit();
        if (isClose) this.Stop();
    }


}
