package jw.fluent.api.desing_patterns.observer.implementation;

import jw.fluent.api.desing_patterns.observer.api.Observable;
import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class Observer<T> implements Observable<T> {

    protected Field field;
    protected Object object;
    protected Class fieldType;
    protected boolean isBinded;
    protected Set<Consumer<T>> onChange = new LinkedHashSet<>();
    public Observer() {

    }

    public Observer(Object classObject, String field) {
        isBinded = bind(classObject.getClass(), field);
        this.object = classObject;
    }

    public Observer(Object classObject, Field field) {
        this.field = field;
        this.object = classObject;
        this.fieldType = this.field.getType();
        isBinded = true;
    }

    public Class getValueType() {
        return fieldType;
    }

    public boolean isBinded() {
        return isBinded;
    }

    public void onChange(Consumer<T> onChangeEvent) {
        this.onChange.add(onChangeEvent);
    }

    public void unsubscribe(Consumer<T> onChangeEvent) {
        this.onChange.remove(onChangeEvent);
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getFieldName() {
        return field != null ? field.getName() : "";
    }



    public void setAsync(T value) {
        Bukkit.getScheduler().runTask(FluentApi.plugin(), () ->
        {
            set(value);
        });
    }

    @Override
    public T get() {
        if (!isBinded)
            return null;
        try {
            return (T) field.get(object);
        } catch (Exception e) {
            return null;
        }
    }
    @Override
    public void set(T value) {
        if (!isBinded)
            return;
        try {
            field.set(object, value);  //set new value to field
            for (Consumer<T> onChangeEvent : onChange)
            {
                onChangeEvent.accept(value);          //trigger all OnValueChange events
            }
        } catch (Exception e) {
            FluentApi.logger().error("Set binding field: " ,e);
        }
    }

    public void invoke()
    {
        set(get());
    }

    public boolean bind(Class _class, String filedName) {
        try {
            this.field = _class.getDeclaredField(filedName);
            this.field.setAccessible(true);
            this.fieldType = this.field.getType();
            isBinded = true;
            return true;
        } catch (NoSuchFieldException e) {
            FluentApi.logger().error("Binding error:" + e.getMessage() + " Field: " + filedName ,e);
            return false;
        }
    }
}
