import Dictionary.DictionaryCmd;
import Dictionary.DictionaryManagement;

public class Application 
{

    public static void main(String[] args) throws Exception
    {
        //Application application = new Application();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.LoadNewWordFromFile();
        //dictionaryManagement.ShowAllWords();
        //dictionaryManagement.LookUpWord("minute");

        //application.DictionaryCommandline(dictionaryManagement);
        DictionaryCmd dictionaryCommandline = new DictionaryCmd();
        dictionaryCommandline.Update();
        
        
        
    }

    

}
