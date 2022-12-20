package jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.search_manager;

import jw.fluent.api.spigot.gui.fluent_ui.observers.list.FluentListNotifier;
import jw.fluent.api.spigot.gui.fluent_ui.observers.list.ListNotifierOptions;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.search_manager.events.SearchEvent;
import jw.fluent.api.spigot.gui.inventory_gui.implementation.list_ui.search_manager.events.SearchFilterEvent;
import jw.fluent.api.desing_patterns.observer.implementation.ObserverBag;
import jw.fluent.api.spigot.gui.inventory_gui.button.observer_button.observers.ButtonObserver;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.*;

public class SearchManager<T> {

    public record SearchProfile<T>(String name,  SearchFilterEvent<T> event)
    {
    }

    private final List<SearchProfile<T>> searchProfiles;

    private final ObserverBag<Integer> indexObserver;

    @Getter
    private final ButtonObserver<Integer> buttonObserver;


    public SearchManager() {
        searchProfiles = new ArrayList<>();
        indexObserver = new ObserverBag<>(0);
        var options = new ListNotifierOptions<SearchProfile<T>>();
        options.setOnNameMapping(SearchProfile::name);
        buttonObserver = new ButtonObserver<>(indexObserver.getObserver(), new FluentListNotifier<>(searchProfiles, options));
    }


    public boolean hasProfiles()
    {
        var index = indexObserver.get();
        if(index >= searchProfiles.size())
        {
            return false;
        }
        return true;
    }

    public boolean search(String searchKey, T data, Player player) {
        var event = new SearchEvent<T>(searchKey, data, player);
        if(!hasProfiles())
        {
            return false;
        }
        var profile = searchProfiles.get(indexObserver.get());
        return profile.event.execute(event);
    }

    public void addSearchProfile(String name, SearchFilterEvent<T> event)
    {
        searchProfiles.add(new SearchProfile<>(name,event));
    }
}
