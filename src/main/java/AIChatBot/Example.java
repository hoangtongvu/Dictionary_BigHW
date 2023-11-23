package AIChatBot;

import AIChatBot.gpt4all.LLModel;

import java.nio.file.Path;
import java.util.Scanner;

public class Example {
    public static void main(String[] args) {

        // Replace the hardcoded path with the actual path where your model file resides
        String modelFilePath = "F:\\gpt4all\\gpt4all-falcon-q4_0.gguf";


        try (LLModel model = new LLModel(Path.of(modelFilePath))) {

            // May generate up to 4096 tokens but generally stops early
            LLModel.GenerationConfig config = LLModel.config()
                    .withNPredict(4096).build();
            model.setThreadCount(8);

            // Will also stream to standard output

            Scanner scanner = new Scanner(System.in);
            String prompt = "";
            String input = "";
            String exitCode = "0";
            while (true)
            {
                input = scanner.nextLine();
                if (input.equals(exitCode)) break;
                prompt += "### Instruction:\n" + input + "\n### Response:";
                prompt += "\n";
                String fullGeneration = model.generate(prompt, config, true);
                prompt += fullGeneration + "\n";
                //System.out.println("[" + prompt + "]");
                System.out.println();

            }

            prompt = "### Instruction:\nhello, my name is Hoang, i come from VietNam\n### Response:Hello Hoang, it's nice to meet you! How are you doing today?" + "\n### Instruction:\nwhere am I come from?\n### Response:";
            String fullGeneration = model.generate(prompt, config, true);

            scanner.close();

        } catch (Exception e) {
            // Exceptions generally may happen if the model file fails to load
            // for a number of reasons such as a file not found.
            // It is possible that Java may not be able to dynamically load the native shared library or
            // the llmodel shared library may not be able to dynamically load the backend
            // implementation for the model file you provided.
            //
            // Once the LLModel class is successfully loaded into memory the text generation calls
            // generally should not throw exceptions.
            e.printStackTrace(); // Printing here but in a production system you may want to take some action.
        }


    }

}