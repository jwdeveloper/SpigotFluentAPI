package jw.fluent.api.spigot.gui.fluent_ui.observers.enums;

import jw.fluent.api.spigot.gui.fluent_ui.observers.FluentButtonNotifier;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonObserverEvent;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.api.utilites.messages.Emoticons;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class FluentEnumNotifier<T extends Enum<T>> extends FluentButtonNotifier<T> {
    private final Class<T> clazz;
    private final Map<T, Integer> indexes;
    private int currentIndex = Integer.MIN_VALUE;
    private T[] values;
    private String[] catchDescription;
    private final EnumNotifierOptions options;

    public FluentEnumNotifier(Class<T> clazz, EnumNotifierOptions options) {
        super(options);
        this.clazz = clazz;
        this.options = options;
        indexes = new HashMap<>();
    }


    @Override
    public void onRightClick(ButtonObserverEvent<T> event) {
        if(event.getValue() == null)
        {
            event.getObserver().setValue(values[0]);
        }

        currentIndex = indexes.get(event.getValue());
        currentIndex = (currentIndex - 1);
        if (currentIndex < 0) {
            currentIndex = values.length - 1;
        }
        event.getObserver().setValue(values[currentIndex]);
    }

    @Override
    public void onLeftClick(ButtonObserverEvent<T> event) {
        if(event.getValue() == null)
        {
            event.getObserver().setValue(values[0]);
        }

        currentIndex = indexes.get(event.getValue());
        currentIndex = (currentIndex + 1) % values.length;
        event.getObserver().setValue(values[currentIndex]);
    }

    @Override
    protected void onInitialize(ButtonObserverEvent<T> event) {
        values = clazz.getEnumConstants();
        catchDescription = new String[values.length];

        Function<String, String> nameMapping = this::defaultNamesMapping;
        if (options.getOnNameMapping() != null) {
            nameMapping = options.getOnNameMapping();
        }
        for (var i = 0; i < values.length; i++)
        {
            var name = nameMapping.apply(values[i].name());
            catchDescription[i] = name;
            indexes.put(values[i], i);
        }

        var button = event.getButton();
        var description =button.getDescription();
        var result = new ArrayList<String>();
        for (var i = 0; i < description.size(); i++) {
            if (i == getDescriptionIndex())
            {
                result.addAll(Arrays.asList(catchDescription));
            } else
            {
                result.add(description.get(i));
            }
        }
        button.setDescription(result);
        onUpdate(event);
    }


    @Override
    protected void onUpdate(ButtonObserverEvent<T> event) {
        if(event.getValue() == null)
        {
            return;
        }
        currentIndex = indexes.get(event.getValue());
        final var button = event.getButton();
        final var description = new String[values.length];
        for (var i = 0; i < values.length; i++) {
            if (i == currentIndex) {
                description[i] = ChatColor.AQUA + Emoticons.dot + catchDescription[i];
            } else {
                description[i] = ChatColor.GRAY + catchDescription[i];
            }
            button.updateDescription(getDescriptionIndex()+i, description[i]);
        }
    }

    private String defaultNamesMapping(String input) {
        input = input.toLowerCase();
        input = input.replaceAll("_", " ");
        input = StringUtils.capitalize(input);
        input = " " + input;
        return input;
    }
}
