package jw.fluent_api.spigot.inventory_gui.implementation.list_ui.search_manager;

import jw.fluent_api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent_api.spigot.inventory_gui.button.button_observer.ButtonObserver;
import jw.fluent_api.spigot.inventory_gui.button.button_observer.observer_impl.ListSelectorObserver;
import jw.fluent_api.spigot.inventory_gui.implementation.list_ui.search_manager.events.SearchEvent;
import jw.fluent_api.spigot.inventory_gui.implementation.list_ui.search_manager.events.SearchFilterEvent;
import org.bukkit.entity.Player;

import java.util.*;

public class SearchManager<T> {
    private final List<SearchProfile<T>> searchProfiles;

    private final ObserverBag<Integer> indexObserver;

    private ButtonObserver<Integer> observer;


    public SearchManager() {
        searchProfiles = new ArrayList<>();
        indexObserver = new ObserverBag<>(0);
        observer = new ButtonObserver<Integer>(indexObserver.getObserver(),
                new ListSelectorObserver<SearchProfile<T>>(searchProfiles,  SearchProfile::name));
    }

    public ButtonObserver<Integer> getObserver()
    {
        return observer;
    }

    public boolean search(String searchKey, T data, Player player) {
        var event = new SearchEvent<T>(searchKey, data, player);
        var profile = searchProfiles.get(indexObserver.get());
        return profile.event.execute(event);
    }

    public void addSearchProfile(String name, SearchFilterEvent<T> event)
    {
        searchProfiles.add(new SearchProfile<>(name,event));
    }

    public record SearchProfile<T>(String name,  SearchFilterEvent<T> event)
    {
    }

}
