package jw.spigot_fluent_api.utilites.binding;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

public class ObservableList<T> extends ArrayList<T> {
    @Setter
    @Getter
    private Consumer<Boolean> onValueChanged = (a) -> {
    };

    public ObservableList(List<T> values) {
        this.addAll(values);
    }



    public void setContent(Collection<? extends T> c) {
        this.clear();
        addAll(c);
    }

    @Override
    public T remove(int index) {
        var value = super.remove(index);
        onValueChanged.accept(true);
        return value;
    }
    @Override
    public boolean remove(Object o)
    {
        var value = super.remove(o);
        onValueChanged.accept(value);
        return value;
    }

    @Override
    public boolean add(T t)
    {
        var value = super.add(t);
        onValueChanged.accept(value);
        return value;
    }

    @Override
    public boolean addAll(Collection<? extends T> c)
    {
        var value = super.addAll(c);
        onValueChanged.accept(value);
        return value;
    }
}
