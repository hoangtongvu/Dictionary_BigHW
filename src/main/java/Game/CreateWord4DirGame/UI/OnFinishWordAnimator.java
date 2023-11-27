package Game.CreateWord4DirGame.UI;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

import java.util.List;

public class OnFinishWordAnimator
{
    private final List<Label> labels;

    public OnFinishWordAnimator(List<Label> wordLabels)
    {
        this.labels = wordLabels;
    }

    public void PlayAnimation(double maxSecond)
    {
        maxSecond *= 0.5;
        int size = this.labels.size();
        double startValue = 0;
        double finalValue = -20;
        double delayBetweenSecond = maxSecond / size;
        int i = 1;
        for (Label label : this.labels)
        {
            TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(delayBetweenSecond * i), label);
            translateTransition.setInterpolator(Interpolator.EASE_OUT);
            translateTransition.setFromY(startValue);
            translateTransition.setToY(finalValue);
            translateTransition.play();

            label.setTextFill(Paint.valueOf( "#CAF7E2"));
            i++;
        }
    }

}
