package jw.fluent_api.spigot.gui.implementation.list_ui.content_manger;

public interface FilterContentEvent<T>
{
     boolean execute(T input);
}
