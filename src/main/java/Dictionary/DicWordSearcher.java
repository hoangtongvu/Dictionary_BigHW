package Dictionary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Word.WordBlock;

public class DicWordSearcher 
{
    private DicManager dicManager;
    private int suggestLimit = 5;



    public DicWordSearcher(DicManager dicManager) {
        this.dicManager = dicManager;
    }


    public List<String> Search(String searchString)
    {
        //strip searchString before searching.
        searchString = searchString.strip();
        if (searchString.isEmpty()) return Collections.emptyList();

        List<String> searchResults = this.SearchInDictionary(searchString);
        this.ReassignInSearchedWordsOrder(searchResults);

        return searchResults;

    }


    private List<String> SearchInDictionary(String searchString)
    {
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

            if (!isMatched || suggestCounter >= this.suggestLimit) return suggestedWords;

            suggestedWords.add(word);
            suggestCounter++;
            insertPos++;

        } while (true);
    }


    private void ReassignInSearchedWordsOrder(List<String> searchResults)
    {
        List<String> searchedWords = this.dicManager.getRecentlySearchedWordManager().getSearchedWords();

        int counter = 0;


        //loop through searchedWords in reversed order (newest searchedWord lay in bottom of the list).
        for (int i = searchedWords.size() - 1; i >= 0; i--)
        {
            String s = searchedWords.get(i);

            //check if searchResults contains s.
            boolean contained = false;
            int containedPos = -1;

            for (int j = 0; j < searchResults.size(); j++)
            {
                if (!searchResults.get(j).equals(s)) continue;
                contained = true;
                containedPos = j;
                break;
            }

            if (!contained) continue;
            if (containedPos == counter) continue;


            //swim up to counter position.
            searchResults.remove(containedPos);
            searchResults.add(counter, s);

            counter++;
        }

    }



}
