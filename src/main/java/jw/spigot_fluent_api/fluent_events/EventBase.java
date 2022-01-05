package jw.spigot_fluent_api.fluent_events;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public abstract class EventBase implements Listener
{
    public EventBase()
    {
        Bukkit.getPluginManager().registerEvents(this, FluentPlugin.getPlugin());
    }

    public void onPluginStart(PluginEnableEvent event)
    {

    }
    public void onPluginStop(PluginDisableEvent event)
    {

    }
    @EventHandler
    public final void onPluginStartEvent(PluginEnableEvent pluginEnableEvent)
    {
          if(pluginEnableEvent.getPlugin() == FluentPlugin.getPlugin())
          {
              onPluginStart(pluginEnableEvent);
          }
    }
    @EventHandler
    public final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent)
    {
        if(pluginDisableEvent.getPlugin() == FluentPlugin.getPlugin())
        {
            onPluginStop(pluginDisableEvent);
        }
    }

}
