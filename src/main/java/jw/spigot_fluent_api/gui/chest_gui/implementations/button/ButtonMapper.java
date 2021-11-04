package jw.spigot_fluent_api.gui.chest_gui.implementations.button;


import jw.spigot_fluent_api.gui.button.Button;

public interface ButtonMapper<T>
{
    public void mapButton(T data, Button button);
}
