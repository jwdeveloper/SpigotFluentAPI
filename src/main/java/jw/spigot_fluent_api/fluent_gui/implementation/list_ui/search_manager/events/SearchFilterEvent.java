package jw.spigot_fluent_api.fluent_gui.implementation.list_ui.search_manager.events;


public interface SearchFilterEvent<T>
{
    boolean execute(SearchEvent<T> event);
}
