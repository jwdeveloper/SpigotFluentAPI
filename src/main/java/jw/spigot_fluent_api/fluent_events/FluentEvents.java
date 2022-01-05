package jw.spigot_fluent_api.fluent_events;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class FluentEvents implements Listener {
    private static FluentEvents instnace;
    private static List<FluentEvent<PluginDisableEvent>> onPluginDisableEvents;
    private static List<FluentEvent<PluginEnableEvent>> onPluginEnableEvents;
    private static FluentEvents getInstnace()
    {
        if(instnace == null)
        {
            instnace = new FluentEvents();
            Bukkit.getPluginManager().registerEvents(instnace, FluentPlugin.getPlugin());
            onPluginDisableEvents = new ArrayList<>();
            onPluginEnableEvents = new ArrayList<>();
        }
        return  instnace;
    }

    @EventHandler
    public final void onPluginStart(PluginEnableEvent pluginEnableEvent)
    {
        if(pluginEnableEvent.getPlugin() == FluentPlugin.getPlugin())
        {
            for(var fluentEvent :onPluginEnableEvents)
            {
                fluentEvent.invoke(pluginEnableEvent);
            }
        }
    }

    @EventHandler
    public final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent)
    {
        if(pluginDisableEvent.getPlugin() == FluentPlugin.getPlugin())
        {
            for(var fluentEvent :onPluginDisableEvents)
            {
                fluentEvent.invoke(pluginDisableEvent);
            }
        }
    }

    public static <T extends Event> FluentEvent<T> onEvent(Class<T> eventType, Consumer<T> action)
    {
        var fluentEvent = new FluentEvent<T>(action);

        if(eventType.equals(PluginDisableEvent.class))
        {
            getInstnace().onPluginDisableEvents.add((FluentEvent<PluginDisableEvent>)fluentEvent);
            return fluentEvent;
        }
        if(eventType.equals(PluginEnableEvent.class))
        {
            getInstnace().onPluginEnableEvents.add((FluentEvent<PluginEnableEvent>)fluentEvent);
            return fluentEvent;
        }

        Bukkit.getPluginManager().registerEvent(eventType, getInstnace(), EventPriority.NORMAL,
                (listener, event) ->
                {
                    fluentEvent.invoke((T)event);
                }, FluentPlugin.getPlugin());
        return fluentEvent;
    }

    public static <T extends Event> FluentEvent<T> onEventAsync(Class<T> tClass, Consumer<T> action)
    {

        var fluentEvent = new FluentEvent<T>(action);
        Bukkit.getPluginManager().registerEvent(tClass, getInstnace(), EventPriority.NORMAL,
                (listener, event) ->
                {
                    Bukkit.getScheduler().runTask(FluentPlugin.getPlugin(),()->
                    {
                        fluentEvent.invoke((T)event);
                    });
                }, FluentPlugin.getPlugin());
        return fluentEvent;
    }
}
