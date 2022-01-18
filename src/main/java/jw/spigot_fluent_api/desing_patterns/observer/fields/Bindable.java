package jw.spigot_fluent_api.desing_patterns.observer.fields;

public interface Bindable<T>
{
     void set(T value);

     T get();
}
