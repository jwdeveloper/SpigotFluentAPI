package jw.fluent_plugin.starup_actions.data;

import jw.fluent_plugin.FluentPlugin;
import jw.fluent_plugin.config.ConfigFile;
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
