import Dictionary.DicCmdCtrl;
import Dictionary.DicManager;

public class Application 
{
    private DicManager dicManager;
    private DicCmdCtrl dicCmdCtrl;

    Application()
    {
        this.dicManager = new DicManager();
        this.dicCmdCtrl = new DicCmdCtrl();
        dicCmdCtrl.setDicManager(dicManager);
    }
    public static void main(String[] args) throws Exception
    {

        //dicManager.getDictionary().ShowWordAt(7);
        //dicManager.getDictionary().ShowAllWords();

        Application application = new Application();
        application.RunCmd();
        
        
    }

    
    public void RunCmd() throws Exception
    {
        this.dicManager.LoadWords_Default();
        this.dicCmdCtrl.getDicCmdManager().Update();
    }

}
