package jw.spigot_fluent_api.desing_patterns.observer;

import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Observer<T> implements Observable<T> {

    protected Field field;
    protected Object object;
    protected Class fieldType;
    protected boolean isBinded;
    protected List<Consumer<T>> onChange = new ArrayList<>();
    public Observer() {

    }

    public Observer(Object classObject, String filed) {
        isBinded = bind(classObject.getClass(), filed);
        this.object = classObject;
    }

    public Observer(Object classObject, Field filed) {
        this.field = filed;
        this.object = classObject;
        this.fieldType = field.getType();
        isBinded = true;
    }

    public Class getType() {
        return fieldType;
    }

    public boolean isBinded() {
        return isBinded;
    }

    public void onChange(Consumer<T> onChangeEvent) {
        this.onChange.add(onChangeEvent);
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public String getFieldName() {
        return field != null ? field.getName() : "";
    }



    public void setAsync(T value) {
        Bukkit.getScheduler().runTask(FluentPlugin.getPlugin(), () ->
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
            FluentLogger.error("Set binding field: " ,e);
        }
    }
    public boolean bind(Class _class, String filedName) {
        try {
            this.field = _class.getDeclaredField(filedName);
            this.field.setAccessible(true);
            this.fieldType = this.field.getType();
            isBinded = true;
            return true;
        } catch (NoSuchFieldException e) {
            FluentLogger.error("Binding error:" + e.getMessage() + " Field: " + filedName ,e);
            return false;
        }
    }
}
