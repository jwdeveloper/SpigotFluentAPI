package jw.fluent.api.spigot.events;

import jw.fluent.plugin.implementation.FluentApi;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;

public abstract class EventBase implements Listener
{
    public EventBase()
    {
        //TODO handle plugin
        Bukkit.getPluginManager().registerEvents(this, FluentApi.plugin());
    }

    private boolean pluginDisabled = false;

    public boolean isPluginDisabled()
    {
        return pluginDisabled;
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
          if(pluginEnableEvent.getPlugin() == FluentApi.plugin())
          {
              onPluginStart(pluginEnableEvent);
          }
    }
    @EventHandler
    public final void onPluginStopEvent(PluginDisableEvent pluginDisableEvent)
    {
        pluginDisabled = true;
        if(pluginDisableEvent.getPlugin() == FluentApi.plugin())
        {
            onPluginStop(pluginDisableEvent);
        }
    }

}
