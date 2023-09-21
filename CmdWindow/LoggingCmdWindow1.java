package CmdWindow;

import Dictionary.DicCmdCtrl;

public class LoggingCmdWindow1 extends CmdWindow
{

    public LoggingCmdWindow1(DicCmdCtrl dicCmdCtrl) 
    {
        super(dicCmdCtrl);
    }

    @Override
    protected String GetInputText()
    {
        return "Type your text 1111111111: ";
    }

}
