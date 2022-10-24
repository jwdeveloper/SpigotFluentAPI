package jw.fluent_api.spigot.gui.implementation.list_ui.search_manager.events;


public interface SearchFilterEvent<T>
{
    boolean execute(SearchEvent<T> event);
}
