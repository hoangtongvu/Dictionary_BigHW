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
    protected String GetInputText()
    {
        return "Type option or text: ";
    }

    @Override
    protected void StringInputCommands(String stringCommand)
    {

        //this.ClearWindowContent();
        String content = this.dicCmdCtrl.getDicManager().LookUpWordString(stringCommand);
        this.windowContent = content;
    }
    

}
