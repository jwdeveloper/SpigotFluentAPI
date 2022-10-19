package jw.fluent_api.minecraft.gui.implementation.list_ui.content_manger;

public interface FilterContentEvent<T>
{
     boolean execute(T input);
}
