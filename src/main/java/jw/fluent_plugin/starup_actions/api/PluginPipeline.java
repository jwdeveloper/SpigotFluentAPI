package jw.fluent_plugin.starup_actions.api;

import jw.fluent_plugin.starup_actions.data.PipelineOptions;
import jw.fluent_plugin.FluentPlugin;

public interface PluginPipeline
{
    public void pluginEnable(PipelineOptions options) throws Exception;

    public void pluginDisable(FluentPlugin fluentPlugin) throws Exception;
}
