package jw.fluent.api.spigot.inventory_gui.button.button_observer.observer_impl;

import com.google.common.base.Function;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonNotifier;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonObserverEvent;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.observer_impl.events.onSelectEvent;
import jw.fluent.api.spigot.inventory_gui.button.ButtonUI;
import jw.fluent.api.utilites.messages.Emoticons;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class ListSelectorObserver<T> implements ButtonNotifier<Integer> {
    private int currentIndex = Integer.MIN_VALUE;
    private final List<T> values;
    private final String prefix;
    private final Function<T, String> mapping;
    private final Consumer<jw.fluent.api.spigot.inventory_gui.button.button_observer.observer_impl.events.onSelectEvent<T>> onSelectEvent;
    private  List<String> defaultDescription;

    public ListSelectorObserver(List<T> values, Function<T, String> mapping) {
        this(values,mapping, (e)->{});
    }

    public ListSelectorObserver(List<T> values, Function<T, String> mapping, Consumer<onSelectEvent<T>> event) {

        this.values = values;
        this.prefix = ChatColor.AQUA + Emoticons.dot + " ";
        this.mapping = mapping;
        this.onSelectEvent = event;
        defaultDescription = new ArrayList<>();
        finalDescription = new ArrayList<>();
    }


    @Override
    public void onRightClick(ButtonObserverEvent<Integer> event) {
        if (values.size() == 0) {
            return;
        }
        currentIndex = (currentIndex + 1) % values.size();
        event.getObserver().setValue(currentIndex);
    }

    @Override
    public void onClick(ButtonObserverEvent<Integer> event) {

    }

    private ButtonUI buttonHolder;
    private List<String> finalDescription;
    private int lastDescriptionSize = -1;

    @Override
    public void onValueChanged(ButtonObserverEvent<Integer> event) {
        if (currentIndex == Integer.MIN_VALUE) {
            currentIndex = event.getValue();
        }
        buttonHolder = event.getButton();
        if(hasValuesChanged())
        {
            finalDescription = createDescription(event.getPlayer());
        }
        else
        {
           updateDescription(event.getPlayer());
        }
        buttonHolder.setDescription(finalDescription);
    }

    public List<String> createDescription(Player player)
    {
        defaultDescription = buttonHolder.getDescription();
        finalDescription = new ArrayList<>(defaultDescription.size()+values.size());
        for (var i = 0; i < values.size(); i++) {
            if (i == currentIndex) {
                finalDescription.add(prefix + mapping.apply(values.get(i)));
                onSelectEvent.accept(new onSelectEvent<T>(buttonHolder,values.get(i),player));
            } else {
                finalDescription.add(mapping.apply(values.get(i)));
            }
        }
        finalDescription.addAll(defaultDescription);
        lastDescriptionSize = finalDescription.size();
        return finalDescription;
    }

    public void updateDescription(Player player)
    {
        for (var i = 0; i < values.size(); i++) {
            if (i == currentIndex) {
                finalDescription.set(i,prefix + mapping.apply(values.get(i)));
                onSelectEvent.accept(new onSelectEvent<T>(buttonHolder,values.get(i),player));
            } else {
                finalDescription.set(i,mapping.apply(values.get(i)));
            }
        }
    }

    public boolean hasValuesChanged()
    {
       return values.size()+defaultDescription.size() != lastDescriptionSize;
    }
}