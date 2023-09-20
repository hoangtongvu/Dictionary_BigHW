package CmdWindow.CmdOptions;

import CmdWindow.CmdWindow;
import Dictionary.DictionaryCmd;

public class LoggingOption1 extends CmdWindowOption
{
    public LoggingOption1() 
    {
        super();
        this.title = "Logging 111111.";
    }

    @Override
    public void Action(DictionaryCmd dictionaryCmd) 
    {
        CmdWindow newCmdWindow = dictionaryCmd.getLoggingCmdWindow1();
        dictionaryCmd.SwitchCmdWindow(newCmdWindow);
        
    }
}
