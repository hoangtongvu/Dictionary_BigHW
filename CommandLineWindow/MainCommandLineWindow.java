package CommandLineWindow;


public class MainCommandLineWindow extends CommandLineWindow
{

    public MainCommandLineWindow()
    {
        super();
        this.isMainCommandLineWindow = true;
        this.AddOptions();
    }

    private void AddOptions()
    {
        CommandLineWindowOption clrscrOption = new ClearScreenOption();
        this.AddCommandlineOption(clrscrOption);
    }


}
