package Dictionary;

import java.io.File;
import java.util.Scanner;

public class TestPhraseFile 
{
    
    private String defaultFilePath = "D:\\Java\\Dictionary_BigHW\\data\\test.txt";

    public static void main(String[] args) throws Exception
    {
        TestPhraseFile testPhraseFile = new TestPhraseFile();
        testPhraseFile.LoadNewWordFromFile();
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

        String prevLine = null;

        while (scanner.hasNextLine()) 
        {
            String line = scanner.nextLine();//remember to enter 1 time at dictionary.txt;


            // if (line != "") 
            // {
            //     switch (line.charAt(0)) 
            //     {
            //         case '-':
            //             if (prevLine == null || prevLine.charAt(0) != '!')
            //             {
                      
            //                 // String definition = line.substring(1).strip();
            //                 // System.out.println("definitionOfWord: " + definition);
            //             }
            //             else if (prevLine.charAt(0) == '!') 
            //             {
            //                 String definition = line.substring(1).strip();
            //                 //System.out.println("definitionOfPhrase: " + definition);
            //                 //System.out.println("prev: " + prevLine);
            //             }

            //             break;
                    
            //         case '!':
            //             String phrase = line.substring(1).strip();

            //             //System.out.println("phrase: " + phrase);
            //             break;
            //         default:
            //             break;
            //     }

                
            // }
            // else if (line == "")
            // {
            // }

            

            

            prevLine = line;
            
        }
        

        scanner.close();
    }

}
