package CmdWindow.CmdOptions;

import CmdWindow.CmdWindow;
import Dictionary.DicCmdCtrl;

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
