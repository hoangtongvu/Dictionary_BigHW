import java.io.File;
import java.util.Collections;
import java.util.Scanner;

public class DictionaryManagement 
{
    
    private Dictionary dictionary;
    private String defaultFilePath = "D:\\Java\\Dictionary_BigHW\\data\\anhviet109K.txt";

    public DictionaryManagement()
    {
        this.dictionary = new Dictionary();
    }


    public void LoadNewWordFromFile() throws Exception
    {
        //String filePath = "D:\\Java\\Dictionary_BigHW\\data\\anhviet109K.txt";
        File file = new File(this.defaultFilePath);

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

        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine();//remember to enter 1 time at dictionary.txt;


            if (line != "") 
            {
                switch (line.charAt(0)) 
                {
                    case '@':
                        newWordBlock = new WordBlock();


                        String[] rawEngWord = line.substring(1).strip().split("/", 2);
                        int size = rawEngWord.length;
                        String word = 0 < size ? rawEngWord[0].strip() : "null";
                        String spelling = "/" + (1 < size ? rawEngWord[1].strip() : "null");

                        //System.out.println("");
                        //System.out.println(wordBlockCount + ". " + word);
                        //System.out.println(spelling);

                        newWordBlock.SetWordAndSpelling(word, spelling);

                        break;
                    case '*':
                        newWordDescription = new WordDescription();

                        String wordType = line.substring(1).strip();

                        //System.out.println("wordType: " + wordType);

                        newWordDescription.setWordType(wordType);
                        newWordDescription = newWordBlock.AddWordDescription(newWordDescription);
                        break;
                    case '-':
                        newWordDefinition = new WordDefinition();

                        String definition = line.substring(1).strip();

                        //System.out.println("definition: " + definition);

                        newWordDefinition.setDefinition(definition);
                        newWordDefinition = newWordDescription.AddWordDefinition(newWordDefinition);
                        break;
                    case '=':
                    
                        String rawExample = line.substring(1).strip();
                        String[] splitString = rawExample.split("\\+ ");
                        int length = splitString.length;
                        String example = 0 < length ? splitString[0] : "null";
                        String definitionOfExample = 1 < length ? splitString[1] : "null";

                        //System.out.println("example: " + example);
                        //System.out.println("definitionOfExample: " + definitionOfExample);

                        newWordExample = new WordExample(example, definitionOfExample);
                        newWordExample = newWordDefinition.AddWordExample(newWordExample);
                    // case '!':
                    //     example = line.substring(1);
                    //     definitionOfExample = "todo later";//scanner.nextLine().substring(1);

                    //     //System.out.println("example: " + example);
                    //     //System.out.println("definitionOfExample: " + definitionOfExample);

                    //     newWordExample = new WordExample(example, definitionOfExample);
                    //     newWordExample = newWordDefinition.AddWordExample(newWordExample);

                    //     break;
                    default:
                        break;
                }

                
            }
            else if (line == "")
            {
                //todo add all in to list
                this.AddNewWord(newWordBlock);
            }
            
        }
        
        this.AddNewWord(newWordBlock);//add last

        scanner.close();
    }

    public WordBlock AddNewWord(WordBlock wordBlock)
    {
        return this.dictionary.AddWordBlock(wordBlock);
    }

    
    public void ShowAllWords()
    {
        this.dictionary.ShowAllWords();
    }

    public void LookUpWord(String lookupString)
    {
        WordBlock dummy = new WordBlock();
        dummy.SetWordAndSpelling(lookupString, "");
        int lookupPos = Collections.binarySearch(this.dictionary.getWordBlocks(), dummy);
        this.dictionary.ShowWordAt(lookupPos);
    }



}
