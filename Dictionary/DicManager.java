package Dictionary;
import java.io.File;
import java.util.Collections;
import java.util.Scanner;

import Word.WordBlock;
import Word.WordDefinition;
import Word.WordDescription;
import Word.WordExample;

public class DicManager 
{
    
    private Dictionary dictionary;
    
    //private String defaultFilePath = "D:\\Java\\Dictionary_BigHW\\data\\anhviet109K.txt";
    private String currentWorkingDir = System.getProperty("user.dir");
    private String defaultFilePath = this.currentWorkingDir + "/data/anhviet109K.txt";
    
    public Dictionary getDictionary() {
        return dictionary;
    }

    public DicManager()
    {
        this.dictionary = new Dictionary();
    }


    public void LoadWords_Default() throws Exception
    {
        this.LoadWordsFromFile(this.defaultFilePath);
    }

    public void LoadWordsFromFile(String filePath) throws Exception
    {
        File file = new File(filePath);

        Scanner scanner;
        if (!file.exists()) 
        {
            System.out.println("FILE NOT FOUND");
            return;
        }

        scanner = new Scanner(file, "UTF-8");


        WordBlock newWordBlock = null;
        WordDefinition newWordDefinition = null;
        WordDescription newWordDescription = null;
        WordExample newWordExample = null;

        String prevLine = null;

        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine();//TODO: remember to enter 1 time at dictionary.txt; unknown error if not do so.


            if (line != "") 
            {
                switch (line.charAt(0)) 
                {
                    case '@'://word and its spelling
                        newWordBlock = new WordBlock();


                        String[] rawEngWord = line.substring(1).strip().split("/", 2);
                        int size = rawEngWord.length;
                        String word = 0 < size ? rawEngWord[0].strip() : "null";
                        String spelling = "/" + (1 < size ? rawEngWord[1].strip() : "null");

                        newWordBlock.SetWordAndSpelling(word, spelling);

                        break;
                    case '*'://word type
                        newWordDescription = new WordDescription();

                        String wordType = line.substring(1).strip();

                        //System.out.println("wordType: " + wordType);

                        newWordDescription.setWordType(wordType);
                        newWordDescription = newWordBlock.AddWordDescription(newWordDescription);
                        break;
                    case '-'://word def
                    
                        String definition = line.substring(1).strip();
                        
                        if (prevLine.charAt(0) != '!')
                        {
                            newWordDefinition = new WordDefinition();
                            newWordDefinition.setDefinition(definition);
                            newWordDefinition = newWordDescription.AddWordDefinition(newWordDefinition);
                        }
                        else if (prevLine.charAt(0) == '!')
                        {
                            newWordExample.setExampleDefinition(definition);
                        }
                        break;
                    case '='://word example
                    
                        String rawExample = line.substring(1).strip();
                        String[] splitString = rawExample.split("\\+ ");
                        int length = splitString.length;
                        String example = 0 < length ? splitString[0] : "null";
                        String definitionOfExample = 1 < length ? splitString[1] : "null";

                        newWordExample = new WordExample(example, definitionOfExample);
                        newWordExample = newWordDefinition.AddWordExample(newWordExample);
                        break;
                    case '!'://word phrase
                        example = line.substring(1);
                        definitionOfExample = "todo later";//scanner.nextLine().substring(1);

                        newWordExample = new WordExample(example, definitionOfExample);
                        newWordExample = newWordDefinition.AddWordExample(newWordExample);

                        break;
                    default:
                        break;
                }

                
            }
            else if (line == "")
            {
                //todo add all in to list
                this.AddNewWord(newWordBlock);
            }

            prevLine = line;
            
        }
        
        this.AddNewWord(newWordBlock);//add last

        scanner.close();
    }


    public WordBlock AddNewWord(WordBlock wordBlock)
    {
        return this.dictionary.AddWordBlock(wordBlock);
    }


    public String LookUpWord(String lookupString)
    {
        WordBlock dummy = new WordBlock();
        dummy.SetWordAndSpelling(lookupString, "");
        int lookupPos = Collections.binarySearch(this.dictionary.getWordBlocks(), dummy);
        if (lookupPos < 0) return "Can't find your word.";
        return this.dictionary.GetWordInfoAt(lookupPos);
    }



}
