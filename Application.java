import Dictionary.DicCmdCtrl;
import Dictionary.DicManager;

public class Application 
{
    private DicManager dicManager;
    private DicCmdCtrl dicCmdCtrl;

    public DicManager getDicManager() {
        return dicManager;
    }

    public DicCmdCtrl getDicCmdCtrl() {
        return dicCmdCtrl;
    }

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
        DicManager dicManager = application.getDicManager();
        dicManager.getDicWordLoader().DefaultLoad();
        System.out.println(dicManager.getDicWordSearcher().Search("transform"));
        // application.RunCmd();

        System.out.println(dicManager.LookUpWord("long"));
        
        
        
    }

    
    public void RunCmd() throws Exception
    {
        this.dicManager.getDicWordLoader().DefaultLoad();
        this.dicCmdCtrl.getDicCmdManager().Update();
    }

}
