package jw.spigot_fluent_api.desing_patterns.observer;

import lombok.Getter;

public class ObserverBag<T>
{
    @Getter
    private Observer<T> observer;
    private T field;
    public ObserverBag(T initValue)
    {
        field = initValue;
        observer = new Observer<>();
        observer.bind(this.getClass(),"field");
        observer.setObject(this);
    }

    public T get()
    {
        return observer.get();
    }


}
