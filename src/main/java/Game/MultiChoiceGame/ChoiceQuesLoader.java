package Game.MultiChoiceGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ChoiceQuesLoader
{

    private String currentWorkingDir = System.getProperty("user.dir");
    private String folderDir = this.currentWorkingDir + "/src/main/resources/MultiChoiceGame/";
    private String questionFilePath;
    private String rightAnswerFilePath;

    private ChoiceGameCtrl choiceGameCtrl;
    public ChoiceQuesLoader(ChoiceGameCtrl choiceGameCtrl)
    {
        this.choiceGameCtrl = choiceGameCtrl;
        this.questionFilePath = this.folderDir + "multiChoiceQues.txt";
        this.rightAnswerFilePath = this.folderDir + "multiChoiceAnswer.txt";
    }


    public void LoadDefault() throws FileNotFoundException
    {
        this.LoadQuestions();
        this.LoadAnswers();
    }

    private void LoadQuestions() throws FileNotFoundException
    {
        File file = new File(questionFilePath);
        Scanner scanner;
        if (!file.exists())
        {
            System.out.println("FILE NOT FOUND");
            System.out.println("UserDir = " + currentWorkingDir);

            return;
        }
        scanner = new Scanner(file, "UTF-8");

        while (scanner.hasNextLine())
        {
            String ques = "";
            String answers[] = new String[4];


            for (int i = 0; i < 2; i++) {
                String line = scanner.nextLine();

                switch (i)
                {
                    case 0:
                        ques = line.substring(line.indexOf('.') + 1).strip();
                        break;
                    case 1:
                        answers[0] = line.substring(line.indexOf("A."),line.indexOf("B."));
                        answers[1] = line.substring(line.indexOf("B."),line.indexOf("C."));
                        answers[2] = line.substring(line.indexOf("C."),line.indexOf("D."));
                        answers[3] = line.substring(line.indexOf("D."));
                        break;
                    default:
                        break;
                }


            }

            this.choiceGameCtrl.getQuestions().add(new MultiChoiceQues(ques, answers));
        }


    }

    private void LoadAnswers() throws FileNotFoundException
    {
        File file = new File(this.rightAnswerFilePath);
        Scanner scanner;
        if (!file.exists())
        {
            System.out.println("FILE NOT FOUND");
            System.out.println("UserDir = " + currentWorkingDir);

            return;
        }
        scanner = new Scanner(file, "UTF-8");

        String line = scanner.nextLine();
        int length = line.length();
        for (int i = 0; i < length; i++)
        {
            this.choiceGameCtrl.getQuestions().get(i).setRightAnswerCode(line.substring(i, i + 1));
        }


    }


}
