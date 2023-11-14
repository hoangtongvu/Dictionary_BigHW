package Game.MultiChoiceGame;

import Timer.CustomTimer;
import javafx.scene.text.Text;


public class ChoiceGameTimerManager
{
    private final CustomTimer customTimer;
    private final int maxTimerTimeSecond = 7;
    private final Text timerText;

    public CustomTimer getCustomTimer()
    {
        return this.customTimer;
    }

    public ChoiceGameTimerManager(Text timerText)
    {
        this.customTimer = new CustomTimer(this.maxTimerTimeSecond);
        this.timerText = timerText;
        this.SubEvent();
    }

    private void SubEvent()
    {
        this.customTimer.onTickEvent.AddListener(this::UpdateTimerUI);
    }

    private void UpdateTimerUI(int counter)
    {
        int tempSec = this.customTimer.getMaxTimeSecond() - counter;
        int tempMin = tempSec / 60;
        tempSec -= tempMin * 60;
        int tempHour = tempMin / 60;
        tempMin -= tempHour * 60;
        this.timerText.setText(tempHour + ":" + tempMin + ":" + tempSec);
        //System.out.println("Update on ticking " + this.customTimer.getCounter());

    }



}
