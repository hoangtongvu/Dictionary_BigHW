package AIChatBot;

public class AIChatBotCtrl
{
    private static AIChatBotCtrl instance;
    public static AIChatBotCtrl getInstance() {
        if (instance == null) instance = new AIChatBotCtrl();
        return instance;
    }


    private final AIChatBotManager aiChatBotManager;

    public AIChatBotManager getAiChatBotManager() {
        return aiChatBotManager;
    }

    private AIChatBotCtrl()
    {
        this.aiChatBotManager = new AIChatBotManager();
    }

}
