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
        this.InitModel(Path.of(modelFilePath));
        this.DestroyModel();
    }

    public void InitModel(Path path)
    {
        this.DestroyModel();
        try
        {
            this.model = new LLModel(path);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        this.config = LLModel.config()
                .withNPredict(4096).build();
        model.setThreadCount(7);
    }

    private void DestroyModel()
    {
        if (this.model == null) return;
        try {
            this.model.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        this.model = null;
    }

    public String Chat(String input)
    {
        if (this.model == null) return "#No model found";
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

    public boolean ModelExist()
    {
        return this.model != null;
    }

    public static void main(String[] args)
    {
        AIChatBotManager aiChatBotManager = new AIChatBotManager();
        System.out.println(aiChatBotManager.Chat("hello, my name is Hoang, i am from Hanoi."));
        System.out.println(aiChatBotManager.Chat("where am i from? and tell me my name"));
        System.out.println("[" + aiChatBotManager.prompt + "]");
    }
}
