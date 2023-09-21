package CmdWindow;

import Dictionary.DicCmdCtrl;

public class LookupCmdWindow extends CmdWindow
{
    
    
    public LookupCmdWindow(DicCmdCtrl dicCmdCtrl) 
    {
        super(dicCmdCtrl);
        this.windowTitle = "Looking up word Window";
    }


    @Override
    protected void RenderInputText()
    {
        super.RenderInputText();
        System.out.print("Type command or text: ");
    }

    @Override
    protected void StringInputCommand()
    {
        CmdWindow newCmdWindow = this.dicCmdCtrl.getLookedUpWordCmdWindow();
        this.dicCmdCtrl.getDicCmdManager().SwitchCmdWindow(newCmdWindow);
    }
    

}
