package AIChatBot;

import AIChatBot.gpt4all.LLModel;

import java.nio.file.Path;
import java.util.Scanner;

public class AIChatBotManager
{

    private final String modelFilePath = "F:\\gpt4all\\gpt4all-falcon-q4_0.gguf";
    private LLModel model;
    private LLModel.GenerationConfig config;

    private String prompt;



    public AIChatBotManager()
    {
        this.Initialize();
        this.prompt = "";
    }

    private void Initialize()
    {
        try {
            this.model = new LLModel(Path.of(modelFilePath));
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

        this.config = LLModel.config()
                .withNPredict(4096).build();
        model.setThreadCount(7);



    }


    public String Chat(String input)
    {
        Scanner scanner = new Scanner(System.in);
        //String prompt = "";
        //String exitCode = "0";

        prompt += "### Instruction:\n" + input + "\n### Response:";
        prompt += "\n";
        String fullGeneration = model.generate(prompt, this.config, false);
        prompt += fullGeneration + "\n";
        //System.out.println("[" + prompt + "]");
        //System.out.println();

        scanner.close();
        return fullGeneration;
    }

    public void ChatTestThread(String input)
    {
        prompt += "### Instruction:\n" + input + "\n### Response:";
        prompt += "\n";
        String fullGeneration = model.generate(prompt, this.config, false);
        prompt += fullGeneration + "\n";
    }


    public static void main(String[] args)
    {
        AIChatBotManager aiChatBotManager = new AIChatBotManager();
        System.out.println(aiChatBotManager.Chat("hello, my name is Hoang, i am from Hanoi."));
        System.out.println(aiChatBotManager.Chat("where am i from? and tell me my name"));
        System.out.println("[" + aiChatBotManager.prompt + "]");
    }
}
