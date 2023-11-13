package Event.TwoParameters;

public interface ICustomEvent<T, U>
{
    public abstract void Invoke(T para1, U para2);
}
