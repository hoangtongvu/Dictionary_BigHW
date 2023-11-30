package AIChatBot;

import AIChatBot.ModelList.ModelListManager;
import AIChatBot.Voice.AIVoice;

public class AIChatBotCtrl
{
    private static AIChatBotCtrl instance;
    public static AIChatBotCtrl getInstance() {
        if (instance == null) instance = new AIChatBotCtrl();
        return instance;
    }


    private final AIChatBotManager aiChatBotManager;
    private final ModelListManager modelListManager;
    private final AIVoice aiVoice;

    public AIChatBotManager getAiChatBotManager() {
        return aiChatBotManager;
    }

    public ModelListManager getModelListManager() {
        return modelListManager;
    }
    public AIVoice getAiVoice() {
        return aiVoice;
    }

    private AIChatBotCtrl()
    {
        this.aiChatBotManager = new AIChatBotManager();
        this.modelListManager = new ModelListManager();
        this.aiVoice = new AIVoice();
    }
}
