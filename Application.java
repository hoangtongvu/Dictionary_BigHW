import Dictionary.DicCmdCtrl;
import Dictionary.DictionaryManagement;

public class Application 
{

    public static void main(String[] args) throws Exception
    {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.LoadNewWordFromFile();
        

        DicCmdCtrl dicCmdCtrl = new DicCmdCtrl();
        dicCmdCtrl.getDicCmdManager().Update();
        
        //dictionaryManagement.ShowAllWords();
        //dictionaryManagement.LookUpWord("minute");
        
    }

    

}
