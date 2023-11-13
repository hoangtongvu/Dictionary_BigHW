package Event.ZeroParameter;

import java.util.ArrayList;
import java.util.List;

public class CustomEvent
{
    private final List<ICustomEvent> listeners;

    public CustomEvent()
    {
        this.listeners = new ArrayList<>();
    }

    public void Invoke() { this.listeners.forEach(ICustomEvent::Invoke); }

    public void AddListener(ICustomEvent listener)
    {
        this.listeners.add(listener);
    }

    public void RemoveListener(ICustomEvent listener)
    {
        this.listeners.remove(listener);
    }


}
