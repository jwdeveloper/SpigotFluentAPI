package jw.fluent_api.desing_patterns.observer.implementation;

import lombok.Getter;

public class ObserverBag<T>
{
    @Getter
    private Observer<T> observer;
    private T value;
    public ObserverBag(T initValue)
    {
        value = initValue;
        observer = new Observer<>();
        observer.bind(this.getClass(),"value");
        observer.setObject(this);
    }

    public T get()
    {
        return observer.get();
    }


}
