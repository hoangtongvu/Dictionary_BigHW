package CmdWindow;

import CmdWindow.CmdOptions.CmdWindowOption;
import CmdWindow.CmdOptions.*;
import Dictionary.DicCmdCtrl;

public class LookupCmdWindow extends CmdWindow
{
    
    
    public LookupCmdWindow(DicCmdCtrl dicCmdCtrl) 
    {
        super(dicCmdCtrl);
        this.windowTitle = "Looking up word Window";
    }


    @Override
    protected void AddingOptions()
    {
        CmdWindowOption clrscrOption = new ClearScreenOption(this);
        this.cmdWindowOptions.add(clrscrOption);
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
        String content = this.dicCmdCtrl.getDicManager().LookUpWord(stringCommand);
        this.windowContent = content;
    }
    

}
