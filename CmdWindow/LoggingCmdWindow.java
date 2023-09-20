package CmdWindow;

import CmdWindow.CmdOptions.*;

public class LoggingCmdWindow extends CmdWindow
{

    public LoggingCmdWindow()
    {
        super();
        this.AddOptions();
    }

    private void AddOptions()
    {
        CmdWindowOption logging1 = new LoggingOption1();
        this.cmdWindowOptions.add(logging1);
    }

    protected void RenderInputText()
    {
        super.RenderInputText();
        System.out.print("Type your text: ");
    }


}
