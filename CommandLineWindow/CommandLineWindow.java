package CommandLineWindow;

import java.util.ArrayList;
import java.util.List;

public abstract class CommandLineWindow 
{
    protected List<CommandLineWindowOption> commandLineWindowOptions;
    protected boolean isMainCommandLineWindow;
    

    public boolean isMainCommandLineWindow() {
        return isMainCommandLineWindow;
    }

    public void setMainCommandLineWindow(boolean isMainCommandLineWindow) {
        this.isMainCommandLineWindow = isMainCommandLineWindow;
    }

    public CommandLineWindow()
    {
        this.commandLineWindowOptions = new ArrayList<>();
        this.isMainCommandLineWindow = false;
    }



    public void Update()
    {
        this.Render();
    }

    public void Render()
    {
        //Render List of options
        int size = this.commandLineWindowOptions.size();
        for (int i = 1; i <= size; i++) 
        {
            System.out.println("[" + i +"] " + commandLineWindowOptions.get(i - 1).getTitle());
        }
    }

    public void AddCommandlineOption(CommandLineWindowOption commandLineWindowOption)
    {
        this.commandLineWindowOptions.add(commandLineWindowOption);
    }

}
