package jw.spigot_fluent_api.desing_patterns.observer;

public interface Observable<T>
{
     void set(T value);

     T get();
}
