package AIChatBot.Voice;

public class VoiceThread extends Thread
{
    private boolean isRunning = true;


    public boolean isRunning() {
        return isRunning;
    }

    public VoiceThread(Runnable task) {
        super(task);
    }

    public void TerminateThread()
    {
        this.isRunning = false;
    }

}
