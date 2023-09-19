import java.util.ArrayList;
import java.util.List;

public class Application 
{
    private static List<WordBlock> wordBlocks;

    public static void main(String[] args) throws Exception
    {
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.LoadNewWordFromFile();
        //dictionaryManagement.ShowAllWords();
        dictionaryManagement.LookUpWord("hello");

        // wordBlocks = new ArrayList<>();
        // WordBlock neWordBlock = new WordBlock();
        // neWordBlock.SetWordAndSpelling("sd", "dsd we");
        // neWordBlock = AddWordBlock(neWordBlock);
        // //neWordBlock.SetWordAndSpelling("sdfix", "dsddfs we");
        // neWordBlock = new WordBlock();
        // for (WordBlock wordBlock : wordBlocks) 
        // {
        //     System.out.println(wordBlock.getWord() + " " + wordBlock.getSpelling());
        // }
        
        
    }

    public static WordBlock AddWordBlock(WordBlock wordBlock)
    {
        Application.wordBlocks.add(wordBlock);
        int length = Application.wordBlocks.size();
        return Application.wordBlocks.get(length - 1);
    }
    


}
