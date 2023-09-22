package CmdWindow;

import java.util.ArrayList;
import java.util.List;

import CmdWindow.CmdOptions.CmdWindowOption;
import Dictionary.DicCmdCtrl;
import Dictionary.DicCmdManager;

public abstract class CmdWindow 
{
    protected DicCmdCtrl dicCmdCtrl;

    protected CmdWindow previousCmdWindow;
    
    protected List<CmdWindowOption> cmdWindowOptions;
    protected boolean isMainCommandLineWindow;

    protected String windowTitle;
    protected String windowContent;
    
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

    public DicCmdCtrl getDicCmdCtrl() {
        return dicCmdCtrl;
    }

    /**
     * Constructor.
     */
    public CmdWindow(DicCmdCtrl dicCmdCtrl)
    {
        this.windowTitle = "No Title";
        this.windowContent = "";
        this.dicCmdCtrl = dicCmdCtrl;
        this.previousCmdWindow = null;
        this.cmdWindowOptions = new ArrayList<>();
        this.isMainCommandLineWindow = false;


        this.AddingOptions();

    }

    protected void AddingOptions() {};

    public void Update()
    {
        this.Render();
    }

    public void Render()
    {
        this.RenderWindowTitle();
        this.RenderOptions();
        this.RenderContents();
        this.RenderInputText();

    }


    protected void RenderWindowTitle() 
    {
        System.out.println("| " + windowTitle + " |");
    }

    protected void RenderOptions()
    {
        System.out.println();
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


    protected void RenderContents() 
    {
        if (this.windowContent == "") return;
        System.out.println("__________________");
        System.out.println(this.windowContent);
    }


    private void RenderInputText()
    {
        System.out.println("__________________");
        System.out.print(this.GetInputText());
    }   

    protected String GetInputText()
    {
        return "Type option: ";
    }

    public void AddCommandlineOption(CmdWindowOption commandLineWindowOption)
    {
        this.cmdWindowOptions.add(commandLineWindowOption);
    }

    public boolean CheckInputCommand(String rawInputCommand, DicCmdManager dictionaryCmd)
    {
        if (!isNumeric(rawInputCommand))
        {
            this.StringInputCommands(rawInputCommand);
            return true;
        }
        else
        {
            Integer inputCommand = Integer.parseInt(rawInputCommand);
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
            this.cmdWindowOptions.get(actualInput).Action();
            return true;

        }
    }


    private static boolean isNumeric(String str)
    {
        return str != null && str.matches("[0-9.]+");
    }

    protected void StringInputCommands(String stringCommand)
    {
        this.windowContent = stringCommand + " is invalid input command";
    }

    protected void ClearWindowContent()
    {
        this.windowContent = "";
    }

}
