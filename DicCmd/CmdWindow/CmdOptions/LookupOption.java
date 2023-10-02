package DicCmd.CmdWindow.CmdOptions;

import DicCmd.DicCmdCtrl;
import DicCmd.CmdWindow.CmdWindow;

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
