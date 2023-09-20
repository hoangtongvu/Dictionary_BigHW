package CmdWindow.CmdOptions;


public class ClearScreenOption extends CmdWindowOption
{
    public ClearScreenOption() 
    {
        super();
        this.title = "Clear screen.";
    }

    @Override
    public void Action()
    {
        System.out.print("\033[H\033[2J");
    }
}
