package DicCmd.CmdWindow.CmdOptions;

import DicCmd.DicCmdCtrl;
import DicCmd.CmdWindow.CmdWindow;

public class LoggingOption extends CmdWindowOption
{
    public LoggingOption(CmdWindow cmdWindow) 
    {
        super(cmdWindow);
        this.title = "Logging.";
    }

    @Override
    public void Action() 
    {
        DicCmdCtrl dicCmdCtrl = this.cmdWindow.getDicCmdCtrl();
        CmdWindow newCmdWindow = dicCmdCtrl.getLoggingCmdWindow();
        dicCmdCtrl.getDicCmdManager().SwitchCmdWindow(newCmdWindow);
        
    }
}
