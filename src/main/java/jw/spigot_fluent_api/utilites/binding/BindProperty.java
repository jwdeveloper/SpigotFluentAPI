package jw.spigot_fluent_api.utilites.binding;

import jw.spigot_fluent_api.initialization.FluentPlugin;
import org.bukkit.Bukkit;
import javax.naming.Binding;
import java.util.HashSet;
import java.util.function.Consumer;

public class BindProperty<T> implements Bindable<T> {
    private Binding binding;
    protected HashSet<Consumer<T>> onChange = new HashSet<>();

    public BindProperty(String fieldName, Object parent) {
        binding = new Binding(fieldName, parent);
    }

    @Override
    public void set(T value) {
        binding.setObject(value);
        for (var changeEvent : onChange) {
            changeEvent.accept(value);
        }
    }

    @Override
    public T get() {
        return (T) binding.getObject();
    }

    public void onChange(Consumer<T> onChangeEvent) {
        this.onChange.add(onChangeEvent);
    }

    public void setAsync(T value) {
        Bukkit.getScheduler().runTask(FluentPlugin.getPlugin(), () ->
        {
            set(value);
        });
    }

    public Class getType() {
        return binding.getClass();
    }


    public void setObject(Object object) {
        binding.setObject(object);
    }

    public String getFieldName() {
        return binding.getName();
    }
}
