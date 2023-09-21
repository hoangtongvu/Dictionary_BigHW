package CmdWindow.CmdOptions;

import CmdWindow.CmdWindow;

public class ClearScreenOption extends CmdWindowOption
{
    public ClearScreenOption(CmdWindow cmdWindow) 
    {
        super(cmdWindow);
        this.title = "Clear screen.";
    }

    @Override
    public void Action()
    {
        System.out.print("\033[H\033[2J");
    }
}
