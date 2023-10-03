package DicCmd;
import Dictionary.DicManager;

public class CmdApp 
{
    private DicManager dicManager;
    private DicCmdCtrl dicCmdCtrl;

    CmdApp()
    {
        this.dicManager = DicManager.getInstance();
        this.dicCmdCtrl = DicCmdCtrl.getInstance();
        this.dicCmdCtrl.setDicManager(this.dicManager);
    }
    public static void main(String[] args) throws Exception
    {
        CmdApp application = new CmdApp();
        application.RunCmd();
        
    }

    
    public void RunCmd() throws Exception
    {
        this.dicManager.getDicWordLoader().DefaultLoad();
        this.dicCmdCtrl.getDicCmdManager().Run();
    }

}
