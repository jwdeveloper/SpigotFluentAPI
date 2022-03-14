package jw.spigot_fluent_api.fluent_plugin.configuration.pipeline;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;

public interface PluginPipeline
{
    public void pluginEnable(FluentPlugin fluentPlugin) throws Exception;

    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception;
}
