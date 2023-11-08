package Timer;

import Event.CustomEvent;

import java.util.*;

public class CustomTimer
{

    public final CustomEvent onTickEvent;
    public final CustomEvent onStopEvent;

    private final Timer timer;

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

    public CustomTimer()
    {
        this.maxTimeSecond = 0;
        this.timer = new Timer();

        this.onTickEvent = new CustomEvent();
        this.onStopEvent = new CustomEvent();
    }

    public CustomTimer(int maxTimeSecond)
    {
        this.maxTimeSecond = maxTimeSecond;
        this.timer = new Timer();

        this.onTickEvent = new CustomEvent();
        this.onStopEvent = new CustomEvent();

    }


    public void Start()
    {
        this.ResetCounter();
        this.AddTimerTask();
    }

    public void Stop()
    {
        this.timer.cancel();
        this.onStopEvent.Invoke();

    }

    private void Tick()
    {
        counter++;
        this.onTickEvent.Invoke();
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
