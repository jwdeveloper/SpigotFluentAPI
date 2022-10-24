package jw.fluent_plugin.implementation.modules.player_context;

import jw.fluent_api.desing_patterns.dependecy_injection.api.containers.Container;
import jw.fluent_api.desing_patterns.dependecy_injection.api.models.RegistrationInfo;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class FluentPlayerContext
{
    private final SpigotPlayerContainer playerContainer;

    public FluentPlayerContext(Container container)
    {
        playerContainer = new SpigotPlayerContainer(container, new ConcurrentHashMap<>());
    }

    public <T> T find(Class<T> injectionType, UUID uuid)
    {
        return playerContainer.find(injectionType,uuid);
    }

    public <T> T find(Class<T> injectionType, Player player)
    {
        return playerContainer.find(injectionType,player.getUniqueId());
    }

    public boolean register(RegistrationInfo injectionType, Player player) throws Exception {
        return playerContainer.register(injectionType,player.getUniqueId());
    }

    public <T> T clear(Class<T> injectionType, Player player)
    {
        //TODO this
        return null;
    }
}
