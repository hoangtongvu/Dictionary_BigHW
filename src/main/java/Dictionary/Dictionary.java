package Dictionary;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Word.WordBlock;

public class Dictionary 
{
    private final List<WordBlock> wordBlockList;


    public List<WordBlock> getWordBlocks() {
        return wordBlockList;
    }

    public Dictionary()
    {
        this.wordBlockList = new ArrayList<>();
    }

    
    public void ShowAllWords()
    {
        int count = 1;
        for (WordBlock wordBlock : this.wordBlockList)
        {
            System.out.println("");
            System.out.print(count + ". ");
            System.out.print(wordBlock.GetInfo());
            count++;
        }

    }

    
    public String GetWordInfoAt(int i) throws SQLException {
        WordBlock wordBlock = this.wordBlockList.get(i);
        wordBlock.loadData(wordBlock.getWordID());
        return wordBlock.GetInfo();
    }


    public void AddWordBlock(WordBlock wordBlock) {
        this.wordBlockList.add(wordBlock);
    }
}
