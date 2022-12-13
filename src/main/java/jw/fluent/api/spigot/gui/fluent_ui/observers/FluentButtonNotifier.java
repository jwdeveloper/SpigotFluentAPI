package jw.fluent.api.spigot.gui.fluent_ui.observers;

import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonNotifier;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.ButtonObserverEvent;
import lombok.Getter;

import java.util.List;

public abstract class FluentButtonNotifier<T> implements ButtonNotifier<T> {


    @Getter
    private final String id;

    @Getter
    private boolean isInitialized = false;
    @Getter
    private int descriptionIndex = -1;

    public FluentButtonNotifier(NotifierOptions notifierOptions)
    {
        id = notifierOptions.getId();
    }

     protected abstract void onInitialize(ButtonObserverEvent<T> event);

     protected abstract void onUpdate(ButtonObserverEvent<T> event);

    @Override
    public final void onValueChanged(ButtonObserverEvent<T> event)
    {
        if(!isInitialized)
        {
            if(!initialize(event.getButton().getDescription()))
            {
                return;
            }

            onInitialize(event);
            isInitialized = true;
        }
        onUpdate(event);
    }

    private boolean initialize(List<String> description) {
        for (var i = 0; i < description.size(); i++) {
            var line = description.get(i);
            if (line.contains("<observer>")) {
                descriptionIndex = i;
                var index = line.indexOf('>');
                var id = line.substring(index+1);
                if (getId().equals(id)) {
                    return true;
                }
            }
        }
        return false;
    }
}
