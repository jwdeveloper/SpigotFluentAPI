package jw.fluent_api.spigot.events;

import jw.fluent_plugin.implementation.FluentApi;
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

public class FluentEvent implements Listener {
    private static FluentEvent instnace;
    private static List<SimpleEvent<PluginDisableEvent>> onPluginDisableEvents;
    private static List<SimpleEvent<PluginEnableEvent>> onPluginEnableEvents;
    private static FluentEvent getInstnace()
    {
        if(instnace == null)
        {
            instnace = new FluentEvent();
            Bukkit.getPluginManager().registerEvents(instnace, FluentApi.plugin());
            onPluginDisableEvents = new ArrayList<>();
            onPluginEnableEvents = new ArrayList<>();
        }
        return  instnace;
    }

    @EventHandler
    public final void onPluginStart(PluginEnableEvent pluginEnableEvent)
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
    public final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent)
    {
        if(pluginDisableEvent.getPlugin() == FluentApi.plugin())
        {
            for(var fluentEvent :onPluginDisableEvents)
            {
                fluentEvent.invoke(pluginDisableEvent);
            }
        }
    }

    public static <T extends Event> SimpleEvent<T> onEvent(Class<T> eventType, Consumer<T> action)
    {
        var fluentEvent = new SimpleEvent<T>(action);

        if(eventType.equals(PluginDisableEvent.class))
        {
            getInstnace().onPluginDisableEvents.add((SimpleEvent<PluginDisableEvent>)fluentEvent);
            return fluentEvent;
        }
        if(eventType.equals(PluginEnableEvent.class))
        {
            getInstnace().onPluginEnableEvents.add((SimpleEvent<PluginEnableEvent>)fluentEvent);
            return fluentEvent;
        }

        Bukkit.getPluginManager().registerEvent(eventType, getInstnace(), EventPriority.NORMAL,
                (listener, event) ->
                {
                    if(!event.getClass().getSimpleName().equalsIgnoreCase(eventType.getSimpleName()))
                    {
                        return;
                    }
                    fluentEvent.invoke((T) event);
                }, FluentApi.plugin());
        return fluentEvent;
    }

    public static <T extends Event> SimpleEvent<T> onEventAsync(Class<T> tClass, Consumer<T> action)
    {

        var fluentEvent = new SimpleEvent<T>(action);
        Bukkit.getPluginManager().registerEvent(tClass, getInstnace(), EventPriority.NORMAL,
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
