package AIChatBot.ModelList;

import javafx.util.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class ModelListLoader
{
    private final ModelListManager modelListManager;

    public ModelListLoader(ModelListManager modelListManager)
    {
        this.modelListManager = modelListManager;
    }

    public void Load()
    {
        File file = this.modelListManager.getModelListFile();
        List<Pair<String, String>> modelNameAndPaths = this.modelListManager.getModelNameAndPaths();
        modelNameAndPaths.clear();

        Scanner scanner = this.GetScanner(file);

        while (scanner.hasNextLine())
        {
            String name = scanner.nextLine().strip();
            String path = scanner.nextLine().strip();
            Pair<String, String> newPair = new Pair<>(name, path);
            modelNameAndPaths.add(newPair);
        }

        scanner.close();
    }

    public void RemoveInvalidPaths()
    {
        List<Pair<String, String>> modelNameAndPaths = this.modelListManager.getModelNameAndPaths();
        File file;

        for (int i = 0; i < modelNameAndPaths.size(); i++)
        {
            Pair<String, String> pair = modelNameAndPaths.get(i);
            file = new File(pair.getValue());
            if (file.exists()) continue;
            modelNameAndPaths.remove(i);
            i--;
        }
    }

    private Scanner GetScanner(File file)
    {
        try {
            return new Scanner(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


}
