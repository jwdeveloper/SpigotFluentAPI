package jw.fluent_api.desing_patterns.observer.api;

public interface Observable<T>
{
     void set(T value);

     T get();
}
