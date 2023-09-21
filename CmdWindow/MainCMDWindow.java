package CmdWindow;

import CmdWindow.CmdOptions.ClearScreenOption;
import CmdWindow.CmdOptions.CmdWindowOption;
import CmdWindow.CmdOptions.LoggingOption;
import CmdWindow.CmdOptions.LookupOption;
import Dictionary.DicCmdCtrl;

public class MainCMDWindow extends CmdWindow
{

    public MainCMDWindow(DicCmdCtrl dicCmdCtrl)
    {
        super(dicCmdCtrl);
        this.windowTitle = "Main Window";
        this.isMainCommandLineWindow = true;
        this.AddOptions();
    }

    private void AddOptions()
    {
        CmdWindowOption clrscrOption = new ClearScreenOption(this);
        CmdWindowOption loggingOption = new LoggingOption(this);
        CmdWindowOption lookUpOption = new LookupOption(this);
        this.AddCommandlineOption(clrscrOption);
        this.AddCommandlineOption(loggingOption);
        this.AddCommandlineOption(lookUpOption);
    }



}
