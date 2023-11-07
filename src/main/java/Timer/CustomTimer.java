package Timer;

import java.util.*;

public class CustomTimer
{

    public final CustomEvent onTickEvent;
    public final CustomEvent onStopEvent;

    private final Timer timer;

    private int counter = 0;
    private final int maxTimeSecond;

    public int getCounter()
    {
        return this.counter;
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

    public void Stop()
    {
        this.timer.cancel();
        this.onStopEvent.Invoke();

    }

    private void Tick()
    {
        System.out.println(this.counter);
        counter++;
        this.onTickEvent.Invoke();
    }

    public void ResetCounter()
    {
        this.counter = 0;
    }


    public static void main(String[] args)
    {
        CustomTimer customTimer = new CustomTimer(10);
        TestClass testClass = new TestClass(customTimer);

        customTimer.Start();
    }


}
