package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.bind_strategy.interfaces;


import jw.spigot_fluent_api.gui_legacy.button.Button;
import jw.spigot_fluent_api.gui_legacy.chest_gui.ChestGUI;

public interface OnChangeEvent<T>
{
    public  void OnValueChanged(ChestGUI inventoryGUI, Button button, T newValue);
}
