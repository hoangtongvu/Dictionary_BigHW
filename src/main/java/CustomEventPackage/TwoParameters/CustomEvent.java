package CustomEventPackage.TwoParameters;

import java.util.ArrayList;
import java.util.List;

public class CustomEvent<T, U>
{
    private final List<ICustomEvent<T, U>> listeners;

    public CustomEvent()
    {
        this.listeners = new ArrayList<>();
    }

    public void Invoke(T para1, U para2) { this.listeners.forEach(lis -> lis.Invoke(para1, para2)); }

    public void AddListener(ICustomEvent<T, U> listener)
    {
        this.listeners.add(listener);
    }

    public void RemoveListener(ICustomEvent<T, U> listener)
    {
        this.listeners.remove(listener);
    }


}
