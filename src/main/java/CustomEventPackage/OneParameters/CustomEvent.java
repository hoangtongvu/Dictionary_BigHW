package CustomEventPackage.OneParameters;

import java.util.ArrayList;
import java.util.List;

public class CustomEvent<T>
{
    private final List<ICustomEvent<T>> listeners;

    public CustomEvent()
    {
        this.listeners = new ArrayList<>();
    }

    public void Invoke(T para1) { this.listeners.forEach(lis -> lis.Invoke(para1)); }

    public void AddListener(ICustomEvent<T> listener)
    {
        this.listeners.add(listener);
    }

    public void RemoveListener(ICustomEvent<T> listener)
    {
        this.listeners.remove(listener);
    }


}
