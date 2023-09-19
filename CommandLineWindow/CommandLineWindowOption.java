package CommandLineWindow;


public abstract class CommandLineWindowOption 
{
    protected String title;

    public String getTitle() {
        return title;
    }

    //abstract
    public abstract void Action();
}
