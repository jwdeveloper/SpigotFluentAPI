package jw.fluent.api.spigot.gui.fluent_ui.observers.list;

import jw.fluent.api.spigot.gui.fluent_ui.observers.FluentButtonNotifier;
import jw.fluent.api.spigot.gui.inventory_gui.button.ButtonUI;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserverEvent;
import jw.fluent.api.spigot.gui.fluent_ui.observers.events.onSelectEvent;
import jw.fluent.api.utilites.java.StringUtils;
import jw.fluent.api.utilites.messages.Emoticons;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.*;


public class FluentListNotifier<T> extends FluentButtonNotifier<Integer> {
    private final List<T> values;

    private List<String> initialDescription;
    private final ListNotifierOptions<T> options;
    private int currentIndex = Integer.MIN_VALUE;
    private int lastValuesSize = Integer.MIN_VALUE;

    public FluentListNotifier(List<T> values, ListNotifierOptions<T> options) {
        super(options);
        this.options = options;
        this.values = values;
        this.initialDescription = new ArrayList<>();
    }

    @Override
    protected void onInitialize(ButtonObserverEvent<Integer> event) {
        if (options.getOnNameMapping() == null) {
            options.setOnNameMapping(Object::toString);
        }
        if (options.getOnSelectionChanged() == null) {
            options.setOnSelectionChanged(tonSelectEvent -> {
            });
        }
        if (StringUtils.isNullOrEmpty(options.getSelectedPrefix())) {
            options.setSelectedPrefix(ChatColor.AQUA + Emoticons.dot + " ");
        }
        if (StringUtils.isNullOrEmpty(options.getUnSelectedPrefix())) {
            options.setUnselectedPrefix(ChatColor.WHITE + " ");
        }
        initialDescription = event.getButton().getDescription();
        onUpdate(event);
    }

    @Override
    public void onLeftClick(ButtonObserverEvent<Integer> event) {
        if (values.size() == 0) {
            return;
        }
        currentIndex = (currentIndex + 1) % values.size();
        event.getObserver().setValue(currentIndex);
    }


    @Override
    public void onRightClick(ButtonObserverEvent<Integer> event) {
        if (options.isIgnoreRightClick()) {
            return;
        }

        if (values.size() == 0) {
            return;
        }
        currentIndex = (currentIndex - 1);
        if (currentIndex < 0) {
            currentIndex = values.size() - 1;
        }
        event.getObserver().setValue(currentIndex);
    }


    @Override
    protected void onUpdate(ButtonObserverEvent<Integer> event) {
        if (hasValueBeenChanged()) {
            createDescription(event.getButton());
        }
        currentIndex = event.getValue();
        if (currentIndex < 0) {
            currentIndex = 0;
        }
        if (currentIndex > lastValuesSize) {
            currentIndex = lastValuesSize;
        }
        updateDescription(event.getPlayer(), event.getButton());
    }

    public void createDescription(ButtonUI button) {
        var result = new ArrayList<String>();
        for (var i = 0; i < initialDescription.size(); i++) {
            if (i == getDescriptionIndex()) {
                for (int j = 0; j < values.size(); j++) {
                    result.add(" ");
                }
            } else {
                result.add(initialDescription.get(i));
            }
        }

        if (values.size() - 1 < currentIndex) {
            currentIndex = values.size() - 1;
        }

        lastValuesSize = values.size();
        button.setDescription(result);
    }

    public void updateDescription(Player player, ButtonUI button) {
        T value = null;
        var index = getDescriptionIndex();
        for (var i = 0; i < values.size(); i++) {
            value = values.get(i);
            if (i == currentIndex) {
                button.updateDescription(index + i, options.getSelectedPrefix() + options.getOnNameMapping().apply(value));
                options.getOnSelectionChanged().accept(new onSelectEvent<T>(button, value, currentIndex, player));
                continue;
            }
            button.updateDescription(index + i, options.getUnSelectedPrefix() + options.getOnNameMapping().apply(value));
        }
    }

    public boolean hasValueBeenChanged() {
        return values.size() != lastValuesSize;
    }


}