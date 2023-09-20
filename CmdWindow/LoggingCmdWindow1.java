package CmdWindow;



public class LoggingCmdWindow1 extends CmdWindow
{

    public LoggingCmdWindow1()
    {
        super();
        this.AddOptions();
    }

    private void AddOptions()
    {
    }

    protected void RenderInputText()
    {
        super.RenderInputText();
        System.out.print("Type your text 1111111111: ");
    }


}
