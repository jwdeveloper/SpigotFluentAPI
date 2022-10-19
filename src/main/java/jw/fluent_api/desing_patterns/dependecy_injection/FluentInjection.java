package jw.fluent_api.desing_patterns.dependecy_injection;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.SearchContainer;
import jw.fluent_api.desing_patterns.dependecy_injection.implementation.containers.SpigotPlayersContainer;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FluentInjection {

    static
    {
        instance = new FluentInjection();
    }

    private static FluentInjection instance;
    private SearchContainer pluginContainer;
    private SpigotPlayersContainer playerContainer;

    public static <T> T findInjection(Class<T> injectionType)
    {
        return (T)instance.pluginContainer.find(injectionType);
    }

    public static <T> T findPlayerInjection(Class<T> injectionType, UUID uuid)
    {
        return instance.playerContainer.find(injectionType,uuid);
    }

    public static <T> T findPlayerInjection(Class<T> injectionType, Player player)
    {
        return instance.playerContainer.find(injectionType,player.getUniqueId());
    }

    public static SearchContainer getContainer()
    {
        return instance.pluginContainer;
    }

    public static void setContainer(SearchContainer container)
    {
        if(container == null)
            return;

        instance.pluginContainer = container;
        instance.playerContainer = new SpigotPlayersContainer(container, new ConcurrentHashMap<>());
    }
}
