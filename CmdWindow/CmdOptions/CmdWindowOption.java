package CmdWindow.CmdOptions;

import Dictionary.DictionaryCmd;

public abstract class CmdWindowOption 
{
    protected String title;

    public String getTitle() {
        return title;
    }

    //abstract
    public abstract void Action(DictionaryCmd dictionaryCmd);
}
