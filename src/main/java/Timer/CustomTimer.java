package Timer;

import Event.ZeroParameter.CustomEvent;

import java.util.*;

public class CustomTimer
{

    public final CustomEvent onTickEvent;
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

        this.onTickEvent = new CustomEvent();
        this.onStopEvent = new CustomEvent();
    }

    public CustomTimer(int maxTimeSecond)
    {
        this.maxTimeSecond = maxTimeSecond;

        this.onTickEvent = new CustomEvent();
        this.onStopEvent = new CustomEvent();

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
        this.onStopEvent.Invoke();
        this.timer.purge();

    }

    private void Tick()
    {
        counter++;
        this.onTickEvent.Invoke();
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
                if (counter >= maxTimeSecond) Stop();
            }
        };

        int delayTime = 1000;
        this.timer.scheduleAtFixedRate(timerTask, 0, delayTime);
    }




}
