package jw.spigot_fluent_api.gui_legacy.chest_gui.implementations.button;


import jw.spigot_fluent_api.gui_legacy.button.Button;

public interface ButtonMapper<T>
{
    public void mapButton(T data, Button button);
}
