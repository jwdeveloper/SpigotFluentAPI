package jw.fluent.api.spigot.gui.fluent_ui.observers.list;

import jw.fluent.api.spigot.gui.fluent_ui.observers.NotifierOptions;
import jw.fluent.api.spigot.gui.fluent_ui.observers.events.onSelectEvent;
import lombok.Setter;

import java.util.function.Consumer;
import java.util.function.Function;

@Setter
public class ListNotifierOptions<T> extends NotifierOptions
{
    private String prefix;
    private Function<T,String> onNameMapping;
    private Consumer<onSelectEvent<T>> onSelectionChanged;

    Function<T, String> getOnNameMapping() {
        return onNameMapping;
    }

    String getPrefix()
    {
        return prefix;
    }

    Consumer<onSelectEvent<T>> getOnSelectionChanged()
    {
        return onSelectionChanged;
    }

}
