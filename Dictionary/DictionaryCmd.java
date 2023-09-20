package Dictionary;
import java.util.Scanner;

import CmdWindow.CmdWindow;
import CmdWindow.LoggingCmdWindow;
import CmdWindow.LoggingCmdWindow1;
import CmdWindow.LookupCmdWindow;
import CmdWindow.MainCMDWindow;

public class DictionaryCmd 
{
    private boolean isClose = false;


    public boolean isClose() {
        return isClose;
    }

    private CmdWindow currentCmdWindow;
    
    private MainCMDWindow mainCommandLineWindow;
    private LoggingCmdWindow loggingCmdWindow;
    private LoggingCmdWindow1 loggingCmdWindow1;
    private LookupCmdWindow LookupCmdWindow;

    public LookupCmdWindow getLookupCmdWindow() {
        return LookupCmdWindow;
    }

    public LoggingCmdWindow1 getLoggingCmdWindow1() {
        return loggingCmdWindow1;
    }

    public LoggingCmdWindow getLoggingCmdWindow() {
        return loggingCmdWindow;
    }

    public MainCMDWindow getMainCommandLineWindow() {
        return mainCommandLineWindow;
    }


    /**
     * Constructor.
     */
    public DictionaryCmd()
    {
        this.mainCommandLineWindow = new MainCMDWindow();
        this.loggingCmdWindow = new LoggingCmdWindow();
        this.loggingCmdWindow1 = new LoggingCmdWindow1();
        this.LookupCmdWindow = new LookupCmdWindow();

        this.currentCmdWindow = this.mainCommandLineWindow;
    }



    public void Update()
    {
        this.Clrscr();
        Scanner scanner = new Scanner(System.in);

        int inputCommand;

        do 
        {
            this.Clrscr();
            this.currentCmdWindow.Update();
            inputCommand = scanner.nextInt();
            this.currentCmdWindow.CheckInputCommand(inputCommand, this);
            
        } while (!isClose);



        scanner.close();
    }


    private void Clrscr()
    {
        System.out.print("\033[H\033[2J");
    }

    public void SwitchCmdWindow(CmdWindow newCmdWindow)
    {
        if (newCmdWindow == null) return;
        CmdWindow tempCmdWindow = this.currentCmdWindow;
        this.currentCmdWindow = newCmdWindow;
        this.currentCmdWindow.setPreviousCmdWindow(tempCmdWindow);

    }

    public void Switch2PreviousWindow()
    {
        CmdWindow prevWindow = this.currentCmdWindow.getPreviousCmdWindow();
        if (prevWindow == null) return;
        // this.SwitchCmdWindow(this.currentCmdWindow.getPreviousCmdWindow());
        this.currentCmdWindow = prevWindow;
    }

    public void ExitCmd()
    {
        this.isClose = true;
    }

}
