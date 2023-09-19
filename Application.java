import java.util.Scanner;

public class Application 
{

    public static void main(String[] args) throws Exception
    {
        //Application application = new Application();
        DictionaryManagement dictionaryManagement = new DictionaryManagement();
        dictionaryManagement.LoadNewWordFromFile();
        //dictionaryManagement.ShowAllWords();
        //dictionaryManagement.LookUpWord("minute");

        //application.DictionaryCommandline(dictionaryManagement);
        DictionaryCommandline dictionaryCommandline = new DictionaryCommandline();
        dictionaryCommandline.Update();
        
        
        
    }

    
    private void DictionaryCommandline(DictionaryManagement dictionaryManagement)
    {
        this.Clrscr();
        Scanner scanner = new Scanner(System.in);

        int inputCommand;

        do 
        {
            this.PrintCommandline();
            System.out.print("Type your command: ");
            inputCommand = scanner.nextInt();

            switch (inputCommand) 
            {
                case 1:
                    this.Clrscr();
                    break;
                case 2:
                    this.Clrscr();
                    System.out.print("Looking up word: ");
                    String lookUpWord = scanner.nextLine();
                    //dictionaryManagement.LookUpWord(lookUpWord);
                    break;
                default:
                    break;
            }



            
        } while (inputCommand != 0);



        scanner.close();
    }


    private void PrintCommandline()
    {
        System.out.println("___[Dictionary made with Java]___");
        System.out.println("[" + 0 + "] Exit.");
        System.out.println("[" + 1 + "] Clrscr.");
        System.out.println("[" + 2 + "] Look-up for word.");
    }

    private void Clrscr()
    {
        System.out.print("\033[H\033[2J");
    }


}
