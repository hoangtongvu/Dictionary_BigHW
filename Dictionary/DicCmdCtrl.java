package Dictionary;
import CmdWindow.LoggingCmdWindow;
import CmdWindow.LoggingCmdWindow1;
import CmdWindow.LookupCmdWindow;
import CmdWindow.MainCMDWindow;

public class DicCmdCtrl 
{

    private DicManager dicManager;


    private DicCmdManager dicCmdManager;

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

    public DicCmdManager getDicCmdManager() {
        return dicCmdManager;
    }

    public DicManager getDicManager() {
        return dicManager;
    }

    public void setDicManager(DicManager dicManager) {
        this.dicManager = dicManager;
    }


    /**
     * Constructor.
     */
    public DicCmdCtrl()
    {
        this.mainCommandLineWindow = new MainCMDWindow(this);
        this.loggingCmdWindow = new LoggingCmdWindow(this);
        this.loggingCmdWindow1 = new LoggingCmdWindow1(this);
        this.LookupCmdWindow = new LookupCmdWindow(this);
        
        this.dicCmdManager = new DicCmdManager(this);

    }


}
