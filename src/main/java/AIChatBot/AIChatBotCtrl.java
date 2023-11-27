package AIChatBot;

import AIChatBot.ModelList.ModelListManager;

public class AIChatBotCtrl
{
    private static AIChatBotCtrl instance;
    public static AIChatBotCtrl getInstance() {
        if (instance == null) instance = new AIChatBotCtrl();
        return instance;
    }


    private final AIChatBotManager aiChatBotManager;
    private final ModelListManager modelListManager;

    public AIChatBotManager getAiChatBotManager() {
        return aiChatBotManager;
    }

    public ModelListManager getModelListManager() {
        return modelListManager;
    }

    private AIChatBotCtrl()
    {
        this.aiChatBotManager = new AIChatBotManager();
        this.modelListManager = new ModelListManager();
    }
}
