package CommandLineWindow;


public class ClearScreenOption extends CommandLineWindowOption
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
