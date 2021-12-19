package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.list_gui;

import jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.button.ButtonMapper;
import org.bukkit.Material;

import java.util.List;

public interface IListGUI<T> {
    void setBackgroundMaterial(Material material);

    void addButtons(List<T> data, ButtonMapper<T> buttonMapper);

    void clearContentButtons();

    void nextPage();

    void backPage();
}
