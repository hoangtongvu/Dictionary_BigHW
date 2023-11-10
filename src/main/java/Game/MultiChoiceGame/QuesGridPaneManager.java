package Game.MultiChoiceGame;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.List;

public class QuesGridPaneManager
{
    private final GridPane gridPane;
    private final List<Button> buttons;


    public List<Button> getButtons() {
        return this.buttons;
    }

    public QuesGridPaneManager(GridPane gridPane)
    {
        this.gridPane = gridPane;
        this.buttons = new ArrayList<>();
    }

    public void AddingButtonsToGridPane(int maxQues)
    {
        int col = this.gridPane.getColumnCount();
        int row = this.gridPane.getRowCount();

        int quesCount = 1;
        for (int i = 0; i < row; i++)
        {
            for (int j = 0; j < col; j++)
            {
                if (quesCount > maxQues) return;

                Button newBut = new Button(Integer.toString(quesCount));
                newBut.setMaxWidth(Integer.MAX_VALUE);
                newBut.setMaxHeight(Integer.MAX_VALUE);

                //add colorAdjust effect.
                this.AddColorAdjust(newBut);

                //Set Cursor.
                this.SetHandCursor(newBut);

                //add buttons to gridPane & list.
                this.gridPane.add(newBut, j, i);
                this.buttons.add(newBut);

                quesCount++;
            }

        }
    }

    private void AddColorAdjust(Button button)
    {
        ColorAdjust colorAdjust = new ColorAdjust();
        button.setEffect(colorAdjust);
    }

    private void SetHandCursor(Button button)
    {
        button.setCursor(Cursor.HAND);
    }

    private void SetButtonColor(Button button, double brightness, double contrast, double hue, double saturation)
    {
        ColorAdjust colorAdjust = (ColorAdjust) button.getEffect();
        colorAdjust.setBrightness(brightness);
        colorAdjust.setContrast(contrast);
        colorAdjust.setHue(hue);
        colorAdjust.setSaturation(saturation);
    }
    private void SetButtonColorGreen(Button button)
    {
        this.SetButtonColor(button, 0, 0, 0.5, 1);
    }

    private void SetButtonColorGrey(Button button)
    {
        this.SetButtonColor(button, -0.3, 0, 0, 0);
    }

    private void SetButtonColorRed(Button button)
    {
        this.SetButtonColor(button, 0, 0, 0, 1);
    }

    public void SetButCorrect(final int i)
    {
        Button button = this.buttons.get(i);
        this.SetButtonColorGreen(button);
    }

    public void SetButDone(final int i)
    {
        Button button = this.buttons.get(i);
        this.SetButtonColorGrey(button);
    }

    public void SetButIncorrect(final int i)
    {
        Button button = this.buttons.get(i);
        this.SetButtonColorRed(button);
    }


}
