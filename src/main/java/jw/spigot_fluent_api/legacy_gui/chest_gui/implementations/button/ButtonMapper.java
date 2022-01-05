package jw.spigot_fluent_api.legacy_gui.chest_gui.implementations.button;


import jw.spigot_fluent_api.legacy_gui.button.Button;

public interface ButtonMapper<T>
{
    public void mapButton(T data, Button button);
}
