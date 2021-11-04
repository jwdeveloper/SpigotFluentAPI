package jw.spigot_fluent_api.utilites.binding;

public interface Bindable<T>
{
     void set(T value);

     T get();
}
