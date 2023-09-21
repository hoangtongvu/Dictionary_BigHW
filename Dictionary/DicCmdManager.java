package Dictionary;
import java.util.Scanner;

import CmdWindow.CmdWindow;

public class DicCmdManager 
{

    private DicCmdCtrl dicCmdCtrl;
    private boolean isClose = false;
    private CmdWindow currentCmdWindow;
    
        
    /**
     * Constructor.
     */
    public DicCmdManager(DicCmdCtrl _dicCmdCtrl)
    {
        this.dicCmdCtrl = _dicCmdCtrl;
        this.currentCmdWindow = this.dicCmdCtrl.getMainCommandLineWindow();
    }


    public void Update()
    {
        this.Clrscr();
        Scanner scanner = new Scanner(System.in);

        String inputCommand;

        do 
        {
            this.Clrscr();
            this.currentCmdWindow.Update();
            inputCommand = scanner.nextLine();
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
