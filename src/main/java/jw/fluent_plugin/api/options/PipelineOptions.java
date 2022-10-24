package jw.fluent_plugin.api.options;

import jw.fluent_plugin.implementation.FluentPlugin;
import jw.fluent_plugin.implementation.config.ConfigFile;
import jw.fluent_plugin.api.data.CommandOptions;
import jw.fluent_plugin.api.data.PluginOptionsImpl;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PipelineOptions
{
    private FluentPlugin plugin;

    private CommandOptions defaultCommand;

    private PluginOptionsImpl pluginOptions;

    private ConfigFile configFile;


}
