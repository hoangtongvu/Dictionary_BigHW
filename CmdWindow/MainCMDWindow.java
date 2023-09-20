package CmdWindow;

import CmdWindow.CmdOptions.ClearScreenOption;
import CmdWindow.CmdOptions.CmdWindowOption;
import CmdWindow.CmdOptions.LoggingOption;

public class MainCMDWindow extends CmdWindow
{

    public MainCMDWindow()
    {
        super();
        this.isMainCommandLineWindow = true;
        this.AddOptions();
    }

    private void AddOptions()
    {
        CmdWindowOption clrscrOption = new ClearScreenOption();
        CmdWindowOption loggingOption = new LoggingOption();
        this.AddCommandlineOption(clrscrOption);
        this.AddCommandlineOption(loggingOption);
    }

    protected void RenderInputText()
    {
        super.RenderInputText();
        System.out.print("Type your command: ");
    }


}
