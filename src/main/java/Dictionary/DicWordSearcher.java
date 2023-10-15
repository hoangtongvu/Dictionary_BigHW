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
        //strip searchString before searching.
        searchString = searchString.strip();
        if (searchString.isEmpty()) return Collections.emptyList();

        //get wordBlocks from Dictionary.
        List<WordBlock> wordBlocks = this.dicManager.getDictionary().getWordBlocks();
        if (wordBlocks.isEmpty()) return Collections.emptyList();

        //initialize return list;
        List<String> suggestedWords = new ArrayList<>();

        //create dummy WordBlock for binarySearch.
        WordBlock dummy = new WordBlock();
        dummy.SetWordAndSpelling(searchString, "");

        //store search position.
        int searchPos = Collections.binarySearch(wordBlocks, dummy);

        int insertPos = searchPos >= 0 ? searchPos : - searchPos - 1;


        int suggestCounter = 0;
        boolean isMatched = false;
        int wordBlocksSize = wordBlocks.size();


        do
        {
            if (insertPos >= wordBlocksSize) return suggestedWords;
            String word = wordBlocks.get(insertPos).getWord();

            isMatched = word.matches(searchString + "(.*)");

            if (!isMatched || suggestCounter >= 5) return suggestedWords;

            suggestedWords.add(word);
            suggestCounter++;
            insertPos++;

        } while (true);

    }

}
