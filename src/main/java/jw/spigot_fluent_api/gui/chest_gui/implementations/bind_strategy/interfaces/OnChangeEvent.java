package jw.spigot_fluent_api.gui.chest_gui.implementations.bind_strategy.interfaces;


import jw.spigot_fluent_api.gui.button.Button;
import jw.spigot_fluent_api.gui.chest_gui.ChestGUI;

public interface OnChangeEvent<T>
{
    public  void OnValueChanged(ChestGUI inventoryGUI, Button button, T newValue);
}
