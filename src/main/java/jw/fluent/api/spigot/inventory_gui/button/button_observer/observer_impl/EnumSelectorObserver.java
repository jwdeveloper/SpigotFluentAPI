package jw.fluent.api.spigot.inventory_gui.button.button_observer.observer_impl;

import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonNotifier;
import jw.fluent.api.spigot.inventory_gui.button.button_observer.ButtonObserverEvent;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.ChatColor;

public class EnumSelectorObserver <T extends Enum<T>> implements ButtonNotifier<T>
{
    private int currentIndex = Integer.MIN_VALUE;
    private T[] values;
    private String[] catchDescription;

    public EnumSelectorObserver(Class<T> _enumClass)
    {
        values = _enumClass.getEnumConstants();
        catchDescription = new String[values.length];
        for(var i =0;i<values.length;i++)
        {
            catchDescription[i] = " "+values[i].name();
        }
    }

    @Override
    public void onClick(ButtonObserverEvent<T> event)
    {
        currentIndex = findIndex(event.getValue());
        currentIndex = (currentIndex+1)%values.length;
        event.getObserver().setValue(values[currentIndex]);
    }

    @Override
    public void onValueChanged(ButtonObserverEvent<T> event)
    {
         currentIndex = findIndex(event.getValue());
         final var button = event.getButton();
         final var descrition = new String[values.length];
         for (var i=0;i<values.length;i++)
         {
             if(i == currentIndex)
             {
                 descrition[i] = ChatColor.AQUA+Emoticons.dot+catchDescription[i];
             }
             else
             {
                 descrition[i] = catchDescription[i];
             }
         }
        button.setDescription(descrition);
    }

    private int findIndex(T _enum)
    {
        int temp =0;
        for(var value:values)
        {
            if(value.equals(_enum))
            {
                return temp;
            }
            temp++;
        }
        return -1;
    }
}
