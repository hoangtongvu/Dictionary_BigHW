import java.util.Scanner;

import CommandLineWindow.MainCommandLineWindow;

public class DictionaryCommandline 
{
    private MainCommandLineWindow mainCommandLineWindow;

    public MainCommandLineWindow getMainCommandLineWindow() {
        return mainCommandLineWindow;
    }

    public DictionaryCommandline()
    {
        this.mainCommandLineWindow = new MainCommandLineWindow();
    }

    public void Update()
    {
        this.Clrscr();
        Scanner scanner = new Scanner(System.in);

        int inputCommand;

        do 
        {
            this.mainCommandLineWindow.Update();
            System.out.print("Type your command: ");
            inputCommand = scanner.nextInt();
            this.Clrscr();

            // switch (inputCommand) 
            // {
            //     case 1:
            //         this.Clrscr();
            //         break;
            //     case 2:
            //         this.Clrscr();
            //         System.out.print("Looking up word: ");
            //         String lookUpWord = scanner.nextLine();
            //         //dictionaryManagement.LookUpWord(lookUpWord);
            //         break;
            //     default:
            //         break;
            // }



            
        } while (inputCommand != 0);



        scanner.close();
    }


    private void Clrscr()
    {
        System.out.print("\033[H\033[2J");
    }


}
