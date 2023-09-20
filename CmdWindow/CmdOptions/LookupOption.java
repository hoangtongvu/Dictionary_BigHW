package CmdWindow.CmdOptions;

import CmdWindow.CmdWindow;
import Dictionary.DictionaryCmd;

public class LookupOption extends CmdWindowOption
{
    public LookupOption() 
    {
        super();
        this.title = "Look up word.";
    }

    @Override
    public void Action(DictionaryCmd dictionaryCmd) 
    {
        CmdWindow newCmdWindow = dictionaryCmd.getLookupCmdWindow();
        dictionaryCmd.SwitchCmdWindow(newCmdWindow);
        
    }
}
