package Game.CreateWord4DirGame;


import Logger.LoggersCtrl;
import Word.WordBlock;
import Word.WordDefinition;
import Word.WordDescription;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

public class CreatingWord
{

    private final CreateWord4DirGameManager gameManager;
    private final List<WordBlock> wordBlocks;
    private final int wordBlocksSize;

    private String word;
    private String result;
    private String hint;
    private int currentCharPos;

    private char[] choiceChars;


    public String getResult() {
        return this.result;
    }

    public String getHint() {
        return hint;
    }

    public CreatingWord(CreateWord4DirGameManager gameManager, List<WordBlock> wordBlocks, int wordBlocksSize)
    {
        this.gameManager = gameManager;
        this.wordBlocks = wordBlocks;
        this.wordBlocksSize = wordBlocksSize;

        this.Reset();
    }

    public void Reset()
    {
        //todo init hint.
        //this.result = this.GetRandomWordFromDictionary();
        WordBlock wordBlock = this.GetRandomWordBlockFromDictionary();
        this.InitResultWord(wordBlock);
        this.InitHint(wordBlock);
        this.InitFirstChar4CurrentCreatingWord();
        this.ResetCurrentCharPos();
        //this.GenerateChoiceChars();
        this.choiceChars = GetChoiceChars();

    }

    public String getCurrentCreatingWord() {
        int resultLength = this.result.length();
        int creatingLength = this.word.length();

        String tempString = this.word;
        return tempString + "_".repeat(resultLength - creatingLength);
    }


    private void ResetCurrentCharPos() { this.currentCharPos = 0; }

    private void InitFirstChar4CurrentCreatingWord()
    {
        this.word = "" + result.charAt(0);
    }

    private void InitResultWord(WordBlock wordBlock)
    {
        this.result = wordBlock.getWord().toUpperCase().strip();
    }

    private void InitHint(WordBlock wordBlock)
    {
        try {
            wordBlock.loadData(wordBlock.getWordID());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            LoggersCtrl.systemLogger.Log("Error loading data.");
        }
        List<WordDescription> wordDescriptions = wordBlock.getDescriptionsList();
        List<WordDefinition> wordDefinitions = wordDescriptions.get(0).getDefinitionList();
        if (wordDefinitions == null) return;
        this.hint = "[" + wordDescriptions.get(0).getWordType() + "] " + wordDefinitions.get(0).getDefinition();
    }

    public WordBlock GetRandomWordBlockFromDictionary()
    {
        Random rand = new Random();
        int randIndex = rand.nextInt(0, this.wordBlocksSize);
        return this.wordBlocks.get(randIndex);
    }

    private char[] GetChoiceChars()
    {
        char nextChar = this.GetNextChar();
        int choiceCharsSize = 4;
        char[] otherChars = this.Get3RandomCharsExcept(nextChar);
        char[] choiceChars = new char[choiceCharsSize];


        Random random = new Random();
        int randIndex = random.nextInt(0, choiceCharsSize);
        choiceChars[randIndex] = nextChar;

        for (int i = 0; i < choiceCharsSize - 1; i++)
        {
            do
            {
                randIndex = random.nextInt(0, choiceCharsSize);
            }
            while (choiceChars[randIndex] != '\0');
            choiceChars[randIndex] = otherChars[i];
        }

        return choiceChars;
    }

    public char GetNextChar()//
    {
        if (this.currentCharPos + 1 >= this.result.length()) return '\0';
        char nextChar = this.result.charAt(this.currentCharPos + 1);
        //if (nextChar == ' ') nextChar = '_';
        return nextChar;
    }

    private char[] Get3RandomCharsExcept(char exceptChar)
    {
        int size = 3;
        char[] chars = new char[size];
        Random r = new Random();

        for (int i = 0; i < size; i++) {
            char c;
            do
            {
                c = (char) (r.nextInt(26) + 'A');
            }
            while (c == exceptChar);
            chars[i] = c;
        }
        return chars;
    }

    public void ChooseChar(int index)
    {
        if (this.word.length() >= this.result.length()) return;
        //choiceChars[index] == nextChar addChar, currentCharPos++.
        char nextChar = this.GetNextChar();
        char choseChar = this.choiceChars[index];

        // == means chose correct char.
        if (nextChar == choseChar)
        {
            this.OnChooseCorrectChoice();
            this.gameManager.InvokeOnChooseCharEvent(true, index);

            //Only need to choose length - 1 times, so limit is length - 1.
            //System.out.println("[POS] " + this.currentCharPos + " of " + this.result.length());
            if (this.currentCharPos >= this.result.length() - 1)
            {
                this.gameManager.MoveToNextCreatingWord();
                //System.out.println("[MOVE TO NEXT WORD]");
                return;
            }

        }
        else
        {
            this.OnChooseIncorrectChoice();
            this.gameManager.InvokeOnChooseCharEvent(false, index);
        }


        //Regenerate choiceChars.
        this.GenerateChoiceChars();

    }

    private void OnChooseCorrectChoice()
    {
        this.word += this.GetNextChar();
        this.currentCharPos++;

        this.gameManager.InvokeOnCreatingWordChangeEvent(this.getCurrentCreatingWord());
        this.gameManager.AddPoint();

    }

    private void OnChooseIncorrectChoice()
    {
        this.gameManager.DeductPoint();
    }

    private void GenerateChoiceChars()
    {
        this.choiceChars = this.GetChoiceChars();
        LoggersCtrl.gameLogger.Log("NEXT CHAR", this.GetNextChar() + "");

        Character[] characters = this.Convert_chars_to_Characters(this.choiceChars);
        this.gameManager.InvokeOnChoiceCharsChangeEvent(characters);
    }

    private Character[] Convert_chars_to_Characters(char[] chars)
    {
        int size = chars.length;
        Character[] characters = new Character[size];
        for (int i = 0; i < size; i++) characters[i] = chars[i];
        return characters;
    }

    public Character[] GetChoiceCharacters()
    {
        return this.Convert_chars_to_Characters(this.choiceChars);
    }


}
