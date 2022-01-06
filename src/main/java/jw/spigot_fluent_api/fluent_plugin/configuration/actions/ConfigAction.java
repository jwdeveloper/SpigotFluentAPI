package jw.spigot_fluent_api.fluent_plugin.configuration.actions;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

public interface ConfigAction
{
    public void execute(FluentPlugin fluentPlugin) throws Exception;
}
