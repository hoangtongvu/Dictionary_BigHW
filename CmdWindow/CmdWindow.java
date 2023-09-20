package CmdWindow;

import java.util.ArrayList;
import java.util.List;

import CmdWindow.CmdOptions.CmdWindowOption;
import Dictionary.DictionaryCmd;

public abstract class CmdWindow 
{
    protected CmdWindow previousCmdWindow;
    
    protected List<CmdWindowOption> cmdWindowOptions;
    protected boolean isMainCommandLineWindow;
    
    public CmdWindow getPreviousCmdWindow() {
        return previousCmdWindow;
    }

    public void setPreviousCmdWindow(CmdWindow previousCmdWindow) {
        this.previousCmdWindow = previousCmdWindow;
    }

    public boolean isMainCommandLineWindow() {
        return isMainCommandLineWindow;
    }

    public void setMainCommandLineWindow(boolean isMainCommandLineWindow) {
        this.isMainCommandLineWindow = isMainCommandLineWindow;
    }

    public CmdWindow()
    {
        this.previousCmdWindow = null;
        this.cmdWindowOptions = new ArrayList<>();
        this.isMainCommandLineWindow = false;
    }



    public void Update()
    {
        this.Render();
    }

    public void Render()
    {
        this.RenderOptions();
        this.RenderInputText();


    }

    protected void RenderOptions()
    {
        //Render List of options
        int size = this.cmdWindowOptions.size();
        for (int i = 1; i <= size; i++) 
        {
            System.out.println("[" + i +"] " + cmdWindowOptions.get(i - 1).getTitle());
        }

        String specialOptionText;
        if (this.isMainCommandLineWindow) 
            specialOptionText = "Exit.";
        else
            specialOptionText = "Back";

        System.out.println("[" + 0 + "] " + specialOptionText);
    }

    protected void RenderInputText()
    {
        System.out.println();

    }   

    public void AddCommandlineOption(CmdWindowOption commandLineWindowOption)
    {
        this.cmdWindowOptions.add(commandLineWindowOption);
    }

    public boolean CheckInputCommand(int inputCommand, DictionaryCmd dictionaryCmd)
    {
        if (inputCommand == 0) 
        {
            if (this.isMainCommandLineWindow) 
            {
                dictionaryCmd.ExitCmd();
            }
            else
            {
                dictionaryCmd.Switch2PreviousWindow();
            }
            return true;
        }
        int size = this.cmdWindowOptions.size();
        int actualInput = inputCommand - 1;
        if (actualInput >= size || actualInput < 0) return false;
        this.cmdWindowOptions.get(actualInput).Action(dictionaryCmd);
        return true;
    }

}
