package CmdWindow;

import Dictionary.DicCmdCtrl;

public class LookedUpWordCmdWindow extends CmdWindow
{

    public LookedUpWordCmdWindow(DicCmdCtrl dicCmdCtrl) 
    {
        super(dicCmdCtrl);
        this.windowTitle = "Looked up Word";
    }

    @Override
    protected void RenderContents()
    {
        String content = "This is the contents of this Window";
        System.out.println(content);
    }
    
}
