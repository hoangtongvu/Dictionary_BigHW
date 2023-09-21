package CmdWindow.CmdOptions;

import CmdWindow.CmdWindow;
import Dictionary.DicCmdCtrl;

public class LookupOption extends CmdWindowOption
{
    public LookupOption(CmdWindow cmdWindow) 
    {
        super(cmdWindow);
        this.title = "Look up word.";
    }

    @Override
    public void Action() 
    {
        //CmdWindow newCmdWindow = dictionaryCmd.getLookupCmdWindow();
        DicCmdCtrl dicCmdCtrl = this.cmdWindow.getDicCmdCtrl();
        CmdWindow newCmdWindow = dicCmdCtrl.getLookupCmdWindow();
        dicCmdCtrl.getDicCmdManager().SwitchCmdWindow(newCmdWindow);
        
    }
}
