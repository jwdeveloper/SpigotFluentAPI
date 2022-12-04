package jw.fluent.plugin.implementation.modules.player_context.implementation;

import jw.fluent.api.spigot.events.EventBase;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FluentPlayerContextListener extends EventBase
{

    private Map<UUID, FluentPlayerImpl> fluentPlayerMap = new HashMap<>();

    public void register(FluentPlayerImpl player)
    {
        fluentPlayerMap.put(player.getUuid(), player);
    }


    @EventHandler
    private void onLeave(PlayerQuitEvent event)
    {
        var id = event.getPlayer().getUniqueId();
        if(!fluentPlayerMap.containsKey(event.getPlayer().getUniqueId()))
        {
            return;
        }
        fluentPlayerMap.get(id).clear();
    }
}
