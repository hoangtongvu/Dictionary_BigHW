package Dictionary;
import java.util.ArrayList;
import java.util.List;

import Word.WordBlock;

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
            System.out.print(wordBlock.GetInfo());
            count++;
        }

    }


    public void ShowWordAt(int i)
    {
        System.out.println(this.GetWordInfoAt(i));
    }

    
    public String GetWordInfoAt(int i)
    {
        WordBlock wordBlock = this.wordBlocks.get(i);
        return wordBlock.GetInfo();
    }


    public WordBlock AddWordBlock(WordBlock wordBlock)
    {
        this.wordBlocks.add(wordBlock);
        int lastPos = this.wordBlocks.size();
        return this.wordBlocks.get(lastPos - 1);
    }
    

    

}
