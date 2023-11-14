package CustomEventPackage.TwoParameters;

import CustomEventPackage.CustomEventAbstract;
import java.util.ArrayList;
import java.util.List;

public class CustomEvent<T, U> extends CustomEventAbstract
{
    private final List<ICustomEvent<T, U>> listeners;

    public CustomEvent(Object invoker)
    {
        super(invoker);
        this.listeners = new ArrayList<>();
    }

    /**
     * @param invoker must be the Object that call Invoke().
     */
    public void Invoke(Object invoker, T para1, U para2)
    {
        if (this.IsWrongInvoker(invoker))
        {
            this.PrintErrorMessage(invoker);
            return;
        }
        this.listeners.forEach(lis -> lis.Invoke(para1, para2));
    }

    public void AddListener(ICustomEvent<T, U> listener)
    {
        this.listeners.add(listener);
    }

    public void RemoveListener(ICustomEvent<T, U> listener)
    {
        this.listeners.remove(listener);
    }


}
