package Dictionary;

import java.io.File;
import java.util.Scanner;

import Main.ProjectDirectory;
import Word.*;

public class DicWordLoader 
{

    private final DicManager dicManager;

        private final String defaultFilePath = ProjectDirectory.resourcesPath + "/data/anhviet109K.txt";


    public DicWordLoader(DicManager dicManager)
    {
        this.dicManager = dicManager;
    }

    public void DefaultLoad() throws Exception
    {
        this.LoadFromFile(this.defaultFilePath);
    }

    public void LoadFromFile(String filePath) throws Exception
    {
        File file = new File(filePath);

        Scanner scanner;
        if (!file.exists()) 
        {
            System.out.println("FILE NOT FOUND");
            System.out.println("UserDir = " + ProjectDirectory.currentWorkingDir);

            return;
        }

        scanner = new Scanner(file, "UTF-8");


        WordBlock newWordBlock = null;
        WordDefinition newWordDefinition = null;
        WordDescription newWordDescription = null;
        WordPhrase newWordPhrase = null;
        WordExample newWordExample = null;

        boolean inPhraseBlock = false;


        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine();//TODO: remember to enter 1 time at dictionary.txt; unknown error if not do so.


            if (!line.isEmpty())
            {
                switch (line.charAt(0)) 
                {
                    case '@'://word and its spelling
                        inPhraseBlock = false;
                        newWordBlock = new WordBlock();


                        String[] rawEngWord = line.substring(1).strip().split("/", 2);
                        int size = rawEngWord.length;
                        String word = 0 < size ? rawEngWord[0].strip() : "null";
                        String spelling = "/" + (1 < size ? rawEngWord[1].strip() : "null");

                        newWordBlock.SetWordAndSpelling(word, spelling);

                        break;
                    case '*'://word type
                        inPhraseBlock = false;
                        newWordDescription = new WordDescription();

                        String wordType = line.substring(1).strip();


                        newWordDescription.setWordType(wordType);
                        newWordDescription = newWordBlock.AddWordDescription(newWordDescription);
                        break;
                    case '-'://word def
                    
                        String definition = line.substring(1).strip();

                        newWordDefinition = new WordDefinition();
                        newWordDefinition.setDefinition(definition);

                        if (!inPhraseBlock)//in wordBlock
                            newWordDefinition = newWordDescription.AddWordDefinition(newWordDefinition);
                        else//in phraseBlock
                            newWordDefinition = newWordPhrase.AddDefinition(newWordDefinition);
                        
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
                        inPhraseBlock = true;

                        String phrase = line.substring(1).strip();
                        newWordPhrase = new WordPhrase(phrase);
                        newWordPhrase = newWordDescription.AddWordPhrase(newWordPhrase);

                        break;
                    default:
                        break;
                }


            }
            else
            {
                //todo add all in to list
                this.dicManager.AddNewWord(newWordBlock);
            }

        }
        
        this.dicManager.AddNewWord(newWordBlock);//add last

        scanner.close();
    }
}
