package jw.fluent_plugin.api;

import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.api.options.PipelineOptions;

public interface PluginAction
{
     void pluginEnable(PipelineOptions options) throws Exception;

     void pluginDisable(FluentPlugin fluentPlugin) throws Exception;
}
