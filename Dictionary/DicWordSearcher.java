package Dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Word.WordBlock;

public class DicWordSearcher 
{
    private DicManager dicManager;
    private int suggestLimit = 5;


    public int getSuggestLimit() {
        return suggestLimit;
    }


    public void setSuggestLimit(int suggestLimit) {
        this.suggestLimit = suggestLimit;
    }


    public DicWordSearcher(DicManager dicManager) {
        this.dicManager = dicManager;
    }


    public List<String> Search(String searchString)
    {
        Dictionary dictionary = this.dicManager.getDictionary();
        int suggestCounter = 0;
        List<String> suggestedWords = new ArrayList<>();
        
        if (searchString == "") return suggestedWords;
        if (this.dicManager.getDictionary().getWordBlocks().isEmpty()) return suggestedWords;

        WordBlock dummy = new WordBlock();
        dummy.SetWordAndSpelling(searchString, "");

        int searchPos = Collections.binarySearch(dictionary.getWordBlocks(), dummy);

        int insertPos = searchPos >= 0 ? searchPos : - searchPos - 1;

        boolean isMatched = false;
        do 
        {
            String word = dictionary.getWordBlocks().get(insertPos).getWord();
            isMatched = word.matches(searchString + "(.*)");
            insertPos++;
            if (isMatched && suggestCounter < suggestLimit) 
            {
                suggestedWords.add(word);
                suggestCounter++;
            }
        } while (isMatched);

        return suggestedWords;
    }

}
