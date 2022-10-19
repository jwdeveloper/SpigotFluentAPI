package jw.spigot_fluent_api.fluent_plugin.starup_actions.api;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.starup_actions.data.PipelineOptions;

public interface PluginPipeline
{
    public void pluginEnable(PipelineOptions options) throws Exception;

    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception;
}
