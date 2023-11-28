package Game.MultiChoiceGame;

import Timer.CustomTimer1;
import javafx.scene.text.Text;


public class ChoiceGameTimerManager
{
    private final CustomTimer1 customTimer;
    private final int maxTimerTimeSecond = 7;
    private final Text timerText;

    public CustomTimer1 getCustomTimer()
    {
        return this.customTimer;
    }

    public ChoiceGameTimerManager(Text timerText)
    {
        this.customTimer = new CustomTimer1();
        this.customTimer.setMaxTimeSecond(this.maxTimerTimeSecond);
        this.timerText = timerText;
        this.SubEvent();
    }

    private void SubEvent()
    {
        this.customTimer.onTickEvent.AddListener(this::UpdateTimerUI);
    }

    private void UpdateTimerUI(int counter, int maxTimeSecond)
    {
        int tempSec = maxTimeSecond - counter;
        int tempMin = tempSec / 60;
        tempSec -= tempMin * 60;
        int tempHour = tempMin / 60;
        tempMin -= tempHour * 60;
        this.timerText.setText(tempHour + ":" + tempMin + ":" + tempSec);
        //System.out.println("Update on ticking " + this.customTimer.getCounter());

    }



}
