package jw.spigot_fluent_api.gui.implementation.picker_list_ui;

import jw.spigot_fluent_api.gui.events.ButtonUIEvent;
import jw.spigot_fluent_api.gui.implementation.list_ui.ListUI;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;

@Getter
@Setter
public class PickerUI<T> extends ListUI<T> {
    private ButtonUIEvent onItemPicked;

    public PickerUI(String name) {
        super(name, 6);
        onContentClick((player, button) ->
        {
            if (onItemPicked == null)
                return;

            onItemPicked.execute(player, button);
        });
    }
}
