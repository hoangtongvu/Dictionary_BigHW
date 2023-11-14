package CustomEventPackage.ZeroParameter;

import CustomEventPackage.CustomEventAbstract;
import java.util.ArrayList;
import java.util.List;

public class CustomEvent extends CustomEventAbstract
{
    private final List<ICustomEvent> listeners;

    public CustomEvent(Object invoker)
    {
        super(invoker);
        this.listeners = new ArrayList<>();
    }

    /**
     * @param invoker must be the Object that call Invoke().
     */
    public void Invoke(Object invoker)
    {
        if (this.IsWrongInvoker(invoker))
        {
            this.PrintErrorMessage(invoker);
            return;
        }
        this.listeners.forEach(ICustomEvent::Invoke);
    }

    public void AddListener(ICustomEvent listener)
    {
        this.listeners.add(listener);
    }

    public void RemoveListener(ICustomEvent listener)
    {
        this.listeners.remove(listener);
    }


}
