package DicCmd.CmdWindow;

import DicCmd.DicCmdCtrl;
import DicCmd.CmdWindow.CmdOptions.ClearScreenOption;
import DicCmd.CmdWindow.CmdOptions.CmdWindowOption;
import DicCmd.CmdWindow.CmdOptions.LoggingOption;
import DicCmd.CmdWindow.CmdOptions.LookupOption;

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
