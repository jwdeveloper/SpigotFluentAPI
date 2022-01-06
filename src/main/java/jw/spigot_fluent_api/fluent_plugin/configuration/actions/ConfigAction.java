package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

public interface ConfigAction
{
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception;

    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception;
}
