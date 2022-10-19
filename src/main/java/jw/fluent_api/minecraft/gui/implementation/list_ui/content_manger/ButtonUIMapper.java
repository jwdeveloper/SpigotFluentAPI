package jw.fluent_api.minecraft.gui.implementation.list_ui.content_manger;
import jw.fluent_api.minecraft.gui.button.ButtonUI;

public interface ButtonUIMapper<T>
{
    void mapButton(T data, ButtonUI button);
}
