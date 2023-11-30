package LanguageTranslation;

import Game.MultiChoiceGame.ChoiceGameCtrl;
import Game.MultiChoiceGame.MultiChoiceQues;
import Main.ProjectDirectory;
import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LangLoader
{

    private final String questionFilePath = ProjectDirectory.resourcesPath + "/LanguageCode.txt";


    private final Languages languages;

    public LangLoader(Languages languages)
    {
        this.languages = languages;
    }



    public void LoadLanguages()
    {
        Scanner scanner = null;
        try {
            scanner = this.GetScanner(this.questionFilePath);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());;
        }
        if (scanner == null) return;

        while (scanner.hasNextLine())
        {
            String langName = scanner.nextLine();
            String langCode = scanner.nextLine();
            Pair<String, String> pair = new Pair<>(langName, langCode);
            this.languages.getLangs().add(pair);
        }

        scanner.close();

    }


    private Scanner GetScanner(String path) throws FileNotFoundException
    {
        File file = new File(path);

        if (!file.exists())
        {
            System.out.println("FILE NOT FOUND");
            throw new FileNotFoundException("can't find file with path: " + path);
        }
        return new Scanner(file, "UTF-8");
    }
}
