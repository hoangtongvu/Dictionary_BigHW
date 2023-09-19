import java.util.ArrayList;
import java.util.List;

public class Dictionary 
{
    private List<WordBlock> wordBlocks;


    public List<WordBlock> getWordBlocks() {
        return wordBlocks;
    }


    public Dictionary()
    {
        this.wordBlocks = new ArrayList<>();
    }

    public void ShowAllWords()
    {
        int count = 1;
        for (WordBlock wordBlock : this.wordBlocks) 
        {
            System.out.println("");
            System.out.print(count + ". ");
            wordBlock.PrintOut();
            count++;
        }

    }

    public void ShowWordAt(int i)
    {
        WordBlock wordBlock = this.wordBlocks.get(i);
        wordBlock.PrintOut();
    }

    
    public WordBlock AddWordBlock(WordBlock wordBlock)
    {
        this.wordBlocks.add(wordBlock);
        int lastPos = this.wordBlocks.size();
        return this.wordBlocks.get(lastPos - 1);
    }
    

    

}
