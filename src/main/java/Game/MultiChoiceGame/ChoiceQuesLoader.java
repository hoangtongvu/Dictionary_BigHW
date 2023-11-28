package Game.MultiChoiceGame;

import Main.ProjectDirectory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ChoiceQuesLoader
{

    private final String folderDir = ProjectDirectory.resourcesPath + "/MultiChoiceGame/";
    private final String questionFilePath = this.folderDir + "multiChoiceQues.txt";
    private final String rightAnswerFilePath = this.folderDir + "multiChoiceAnswer.txt";

    private final ChoiceGameCtrl choiceGameCtrl;


    public ChoiceQuesLoader(ChoiceGameCtrl choiceGameCtrl)
    {
        this.choiceGameCtrl = choiceGameCtrl;
    }


    public void LoadDefault()
    {
        try {
            this.LoadQuestions();
            this.LoadAnswers();
        } catch (FileNotFoundException e) {
            System.err.println(e.getMessage());
        }
    }

    private void LoadQuestions() throws FileNotFoundException
    {

        Scanner scanner = this.GetScanner(this.questionFilePath);


        while (scanner.hasNextLine())
        {
            String ques = "";
            String[] answers = new String[4];


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

            this.choiceGameCtrl.getChoiceQuesStorage().getQuestions().add(new MultiChoiceQues(ques, answers));
        }


    }

    private void LoadAnswers() throws FileNotFoundException
    {

        Scanner scanner = this.GetScanner(this.rightAnswerFilePath);

        String line = scanner.nextLine();
        int length = line.length();
        for (int i = 0; i < length; i++)
        {
            this.choiceGameCtrl.getChoiceQuesStorage().getQuestions().get(i).setRightAnswerCode(line.substring(i, i + 1));
        }


    }


    private Scanner GetScanner(String path) throws FileNotFoundException
    {
        File file = new File(path);

        if (!file.exists())
        {
            System.out.println("FILE NOT FOUND");
            System.out.println("UserDir = " + ProjectDirectory.currentWorkingDir);
            throw new FileNotFoundException("can't find file with path: " + path);
        }
        return new Scanner(file, "UTF-8");
    }


}
