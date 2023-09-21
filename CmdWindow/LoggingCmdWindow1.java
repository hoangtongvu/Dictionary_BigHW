package CmdWindow;

import Dictionary.DicCmdCtrl;

public class LoggingCmdWindow1 extends CmdWindow
{

    public LoggingCmdWindow1(DicCmdCtrl dicCmdCtrl) 
    {
        super(dicCmdCtrl);
    }

    protected void RenderInputText()
    {
        super.RenderInputText();
        System.out.print("Type your text 1111111111: ");
    }


}
