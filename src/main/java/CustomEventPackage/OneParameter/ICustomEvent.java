package CustomEventPackage.OneParameter;

public interface ICustomEvent<T>
{
    public abstract void Invoke(T para1);
}
