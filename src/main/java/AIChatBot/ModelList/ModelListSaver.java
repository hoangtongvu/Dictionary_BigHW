package AIChatBot.ModelList;

import javafx.util.Pair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

public class ModelListSaver
{
    private final ModelListManager modelListManager;

    public ModelListSaver(ModelListManager modelListManager)
    {
        this.modelListManager = modelListManager;
    }

    public void Save()
    {
        File file = this.modelListManager.getModelListFile();
        List<Pair<String, String>> modelNameAndPaths = this.modelListManager.getModelNameAndPaths();

        try
        {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            int size = modelNameAndPaths.size();
            for (int i = 0; i < size; i++)
            {
                Pair<String, String> pair = modelNameAndPaths.get(i);
                String name = pair.getKey();
                String path = pair.getValue();
                writer.write(name);
                writer.newLine();
                writer.write(path);

                //if reach last line then no newLine.
                if (i == size - 1) break;
                writer.newLine();
            }
            writer.close();


        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }


}
