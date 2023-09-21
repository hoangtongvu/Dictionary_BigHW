package CmdWindow.CmdOptions;

import CmdWindow.CmdWindow;
import Dictionary.DicCmdCtrl;

public class LoggingOption1 extends CmdWindowOption
{
    public LoggingOption1(CmdWindow cmdWindow) 
    {
        super(cmdWindow);
        this.title = "Logging 111111.";
    }

    @Override
    public void Action() 
    {
        DicCmdCtrl dicCmdCtrl = this.cmdWindow.getDicCmdCtrl();
        CmdWindow newCmdWindow = dicCmdCtrl.getLoggingCmdWindow1();
        dicCmdCtrl.getDicCmdManager().SwitchCmdWindow(newCmdWindow);
        
    }
}
