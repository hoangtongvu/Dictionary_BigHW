package CustomEventPackage.OneParameters;

import CustomEventPackage.CustomEventAbstract;
import java.util.ArrayList;
import java.util.List;

public class CustomEvent<T> extends CustomEventAbstract
{
    private final List<ICustomEvent<T>> listeners;

    public CustomEvent(Object invoker)
    {
        super(invoker);
        this.listeners = new ArrayList<>();
    }

    /**
     * @param invoker must be the Object that call Invoke().
     */
    public void Invoke(Object invoker, T para1)
    {
        if (this.IsWrongInvoker(invoker))
        {
            this.PrintErrorMessage(invoker);
            return;
        }
        this.listeners.forEach(lis -> lis.Invoke(para1));
    }

    public void AddListener(ICustomEvent<T> listener)
    {
        this.listeners.add(listener);
    }

    public void RemoveListener(ICustomEvent<T> listener)
    {
        this.listeners.remove(listener);
    }


}
