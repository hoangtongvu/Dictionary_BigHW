package Event;

import java.util.ArrayList;
import java.util.List;

public class CustomEvent
{
    private final List<Runnable> listeners;

    public CustomEvent()
    {
        this.listeners = new ArrayList<>();
    }

    public void Invoke() { this.listeners.forEach(Runnable::run); }

    public void AddListener(Runnable runnable)
    {
        this.listeners.add(runnable);
    }

    public void RemoveListener(Runnable runnable)
    {
        this.listeners.remove(runnable);
    }


}
