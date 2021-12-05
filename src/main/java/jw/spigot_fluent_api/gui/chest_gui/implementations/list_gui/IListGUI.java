package jw.spigot_fluent_api.gui.chest_gui.implementations.list_gui;

import jw.spigot_fluent_api.gui.chest_gui.implementations.button.ButtonMapper;
import jw.spigot_fluent_api.gui.chest_gui.implementations.utilites.ListGUIPagination;
import jw.spigot_fluent_api.gui.events.InventoryEvent;
import org.bukkit.Material;

import java.util.List;
import java.util.function.Consumer;

public interface IListGUI<T> {
    void setBackgroundMaterial(Material material);

    void addButtons(List<T> data, ButtonMapper<T> buttonMapper);

    void clearContentButtons();

    void nextPage();

    void backPage();
}
