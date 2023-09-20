package CmdWindow.CmdOptions;

import CmdWindow.CmdWindow;
import Dictionary.DictionaryCmd;

public class LoggingOption extends CmdWindowOption
{
    public LoggingOption() 
    {
        super();
        this.title = "Logging.";
    }

    @Override
    public void Action(DictionaryCmd dictionaryCmd) 
    {
        CmdWindow newCmdWindow = dictionaryCmd.getLoggingCmdWindow();
        dictionaryCmd.SwitchCmdWindow(newCmdWindow);
        
    }
}
