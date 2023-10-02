package DicCmd.CmdWindow.CmdOptions;

import DicCmd.CmdWindow.CmdWindow;

public abstract class CmdWindowOption 
{
    protected CmdWindow cmdWindow;
    protected String title;

    public CmdWindowOption(CmdWindow cmdWindow) 
    {
        this.cmdWindow = cmdWindow;
    }

    public String getTitle() {
        return title;
    }

    //abstract
    public abstract void Action();
}
