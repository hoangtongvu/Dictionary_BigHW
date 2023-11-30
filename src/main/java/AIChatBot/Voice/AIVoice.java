package AIChatBot.Voice;

import Main.SceneControllers.Translate.TextToSpeech;

public class AIVoice
{

    public VoiceThread Speak(String response)
    {
        String[] strings = response.split("[.,?!]");

        VoiceThread voiceThread = new VoiceThread(() -> {
            VoiceThread currThread = (VoiceThread) Thread.currentThread();
            for (String s : strings)
            {
                if (!currThread.isRunning()) return;
                TextToSpeech.EnTextToSpeech(s);
            }
        });
        voiceThread.setDaemon(true);
        voiceThread.start();
        return voiceThread;
    }
}
