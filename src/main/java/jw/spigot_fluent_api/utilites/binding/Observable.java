package jw.spigot_fluent_api.utilites.binding;

import jw.spigot_fluent_api.initialization.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Observable<T> implements Bindable<T> {

    protected Field field;
    protected Object object;
    protected Class fieldType;
    protected boolean isBinded;
    protected List<Consumer<T>> onChange = new ArrayList<>();

    public Observable(Object classObject, String filed) {
        isBinded = bind(classObject, filed);
    }

    public Observable(Object classObject, Field filed) {
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
            FluentPlugin.logError("Set binding field: " + e.getMessage());
        }
    }

    protected boolean bind(Object classObject, String filedName) {
        try {
            this.field = classObject.getClass().getDeclaredField(filedName);
            this.field.setAccessible(true);
            this.object = classObject;
            this.fieldType = this.field.getType();
            return true;
        } catch (NoSuchFieldException e) {
            FluentPlugin.logError("Binding error:" + e.getMessage() + " Field: " + filedName);
            return false;
        }
    }
}
