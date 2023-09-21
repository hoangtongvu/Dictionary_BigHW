package CmdWindow;

import CmdWindow.CmdOptions.*;
import Dictionary.DicCmdCtrl;

public class LoggingCmdWindow extends CmdWindow
{


    public LoggingCmdWindow(DicCmdCtrl dicCmdCtrl) 
    {
        super(dicCmdCtrl);
    }

    @Override
    protected void AddingOptions()
    {
        CmdWindowOption logging1 = new LoggingOption1(this);
        this.cmdWindowOptions.add(logging1);
    }



}
