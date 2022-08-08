package jw.spigot_fluent_api.fluent_gui.button.button_observer.observer_impl;

import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonNotifier;
import jw.spigot_fluent_api.fluent_gui.button.button_observer.ButtonObserverEvent;
import jw.spigot_fluent_api.utilites.messages.Emoticons;
import org.bukkit.ChatColor;

import java.util.List;

public class TextListSelectorObserver implements ButtonNotifier<Integer>
{
    private int currentIndex = Integer.MIN_VALUE;
    private final List<String> values;
    private final String prefix;

    public TextListSelectorObserver(List<String> values)
    {
        this.values = values;
        this.prefix = ChatColor.AQUA+Emoticons.dot+" ";

    }

    @Override
    public void onClick(ButtonObserverEvent<Integer> event)
    {
        if(values.size() == 0)
        {
            return;
        }
        currentIndex = (currentIndex+1)% values.size();
        event.getObserver().setValue(currentIndex);
    }

    @Override
    public void onValueChanged(ButtonObserverEvent<Integer> event)
    {
        if(currentIndex == Integer.MIN_VALUE)
        {
            currentIndex = event.getValue();
        }
        final var button = event.getButton();
        final var description = new String[values.size()];
        for (var i = 0; i< values.size(); i++)
        {
            if(i == currentIndex)
            {
                description[i] = prefix+values.get(i);
            }
            else
            {
                description[i] = values.get(i);
            }
        }
        button.setDescription(description);
    }
}
