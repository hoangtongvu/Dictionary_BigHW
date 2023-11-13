package CustomEventPackage;


public abstract class CustomEventAbstract
{
    protected final Object invoker;

    protected CustomEventAbstract(Object invoker)
    {
        this.invoker = invoker;
    }

    protected final boolean IsWrongInvoker(Object invoker) { return this.invoker != invoker; }

    protected final void PrintErrorMessage(Object wrongInvoker)
    {
        System.err.println(this.GetErrorMessage(wrongInvoker));
    }

    private final String GetErrorMessage(Object wrongInvoker)
    {
        String message = "[WRONG INVOKER] " + wrongInvoker.getClass() + ", INVOKER must be " + this.invoker.getClass();
        return message;
    }
}
