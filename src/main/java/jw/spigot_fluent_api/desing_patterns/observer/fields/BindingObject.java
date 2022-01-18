package jw.spigot_fluent_api.desing_patterns.observer.fields;

import java.util.function.Consumer;

public class BindingObject<T> extends Observable<T> {


    private String objectName;

    public BindingObject(String name) {
        super(new Object(), name);
    }
    public BindingObject() {
        this("Object");
    }

    protected boolean bind(Object classObject, String filed) {
        this.objectName = filed;
        this.object = classObject;
        return true;
    }

    @Override
    public void set(T value) {
        this.object = value;
        for (Consumer<T> event : onChange) {
            event.accept(value);
        }
    }

    @Override
    public T get() {
        return (T) this.object;
    }


    @Override
    public void setObject(Object object) {

    }
}
