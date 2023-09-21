import Dictionary.DicCmdCtrl;
import Dictionary.DicManager;

public class Application 
{

    public static void main(String[] args) throws Exception
    {
        DicManager dicManager = new DicManager();
        dicManager.LoadNewWordFromFile();
        

        DicCmdCtrl dicCmdCtrl = new DicCmdCtrl();
        dicCmdCtrl.setDicManager(dicManager);
        dicCmdCtrl.getDicCmdManager().Update();
        
        //dictionaryManagement.ShowAllWords();
        //dictionaryManagement.LookUpWord("minute");
        
    }

    

}
