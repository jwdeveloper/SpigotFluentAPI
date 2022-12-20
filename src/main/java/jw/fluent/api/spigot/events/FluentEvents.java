package jw.fluent.api.spigot.events;

import jw.fluent.plugin.implementation.FluentApi;
import jw.fluent.plugin.implementation.modules.files.logger.FluentLogger;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentEvents implements Listener {
    private final List<SimpleEvent<PluginDisableEvent>> onPluginDisableEvents;
    private final List<SimpleEvent<PluginEnableEvent>> onPluginEnableEvents;

    public FluentEvents(JavaPlugin plugin)
    {
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        onPluginDisableEvents = new ArrayList<>();
        onPluginEnableEvents = new ArrayList<>();
    }


    @EventHandler
    protected final void onPluginStart(PluginEnableEvent pluginEnableEvent)
    {
        if(pluginEnableEvent.getPlugin() == FluentApi.plugin())
        {
            for(var fluentEvent :onPluginEnableEvents)
            {
                fluentEvent.invoke(pluginEnableEvent);
            }
        }
    }

    @EventHandler
    protected final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent)
    {
        if(pluginDisableEvent.getPlugin() == FluentApi.plugin())
        {
            for(var fluentEvent :onPluginDisableEvents)
            {
                fluentEvent.invoke(pluginDisableEvent);
            }
        }
    }

    public  <T extends Event> SimpleEvent<T> onEvent(Class<T> eventType, Consumer<T> action)
    {
        var fluentEvent = new SimpleEvent<T>(action);

        if(eventType.equals(PluginDisableEvent.class))
        {
            onPluginDisableEvents.add((SimpleEvent<PluginDisableEvent>)fluentEvent);
            return fluentEvent;
        }
        if(eventType.equals(PluginEnableEvent.class))
        {
            onPluginEnableEvents.add((SimpleEvent<PluginEnableEvent>)fluentEvent);
            return fluentEvent;
        }
        Bukkit.getPluginManager().registerEvent(eventType,this, EventPriority.NORMAL,
                (listener, event) ->
                {
                    if(!fluentEvent.isRegister())
                    {
                        event.getHandlers().unregister(this);
                        return;
                    }

                    if(!event.getClass().getSimpleName().equalsIgnoreCase(eventType.getSimpleName()))
                    {
                        return;
                    }
                    fluentEvent.invoke((T) event);
                }, FluentApi.plugin());
        return fluentEvent;
    }

    public <T extends Event> SimpleEvent<T> onEventAsync(Class<T> tClass, Consumer<T> action)
    {

        var fluentEvent = new SimpleEvent<T>(action);
        Bukkit.getPluginManager().registerEvent(tClass,this, EventPriority.NORMAL,
                (listener, event) ->
                {
                    Bukkit.getScheduler().runTask(FluentApi.plugin(),()->
                    {
                        fluentEvent.invoke((T)event);
                    });
                }, FluentApi.plugin());
        return fluentEvent;
    }
}
