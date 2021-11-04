package jw.spigot_fluent_api.utilites.binding;

import java.util.function.Consumer;

public class BindingObject<T> extends BindingField<T> {


    private String objectName;

    public BindingObject(String name) {
        super(name, new Object());
    }
    public BindingObject() {
        this("Object");
    }
    @Override
    protected boolean bind(String filed, Object classObject) {
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