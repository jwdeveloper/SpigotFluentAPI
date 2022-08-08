package jw.spigot_fluent_api.fluent_gui.button.button_observer.observer_impl;

import com.google.common.base.Function;
import jw.spigot_fluent_api.fluent_gui.button.ButtonUI;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonNotifier;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserverEvent;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.observer_impl.events.onSelectEvent;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.function.Consumer;


public class ListSelectorObserver<T> implements ButtonNotifier<Integer> {
    private int currentIndex = Integer.MIN_VALUE;
    private final List<T> values;
    private final String prefix;
    private final Function<T, String> mapping;
    private final Consumer<jw.spigot_fluent_api.fluent_gui.button.button_observer.observer_impl.events.onSelectEvent<T>> onSelectEvent;

    public ListSelectorObserver(List<T> values, Function<T, String> mapping) {
        this(values,mapping, (e)->{});
    }

    public ListSelectorObserver(List<T> values, Function<T, String> mapping, Consumer<jw.spigot_fluent_api.fluent_gui.button.button_observer.observer_impl.events.onSelectEvent<T>> event) {

        this.values = values;
        this.prefix = ChatColor.AQUA + Emoticons.dot + " ";
        this.mapping = mapping;
        this.onSelectEvent = event;
    }


    @Override
    public void onClick(ButtonObserverEvent<Integer> event) {
        if (values.size() == 0) {
            return;
        }
        currentIndex = (currentIndex + 1) % values.size();
        event.getObserver().setValue(currentIndex);
    }

    private ButtonUI buttonHolder;
    private String[] descriptionHolder;

    @Override
    public void onValueChanged(ButtonObserverEvent<Integer> event) {
        if (currentIndex == Integer.MIN_VALUE) {
            currentIndex = event.getValue();
        }
        buttonHolder = event.getButton();
        descriptionHolder = new String[values.size()];
        for (var i = 0; i < values.size(); i++) {
            if (i == currentIndex) {
                descriptionHolder[i] = prefix + mapping.apply(values.get(i));
                onSelectEvent.accept(new onSelectEvent<T>(buttonHolder,values.get(i),event.getPlayer()));
            } else {
                descriptionHolder[i] = mapping.apply(values.get(i));
            }
        }
        buttonHolder.setDescription(descriptionHolder);
    }
}