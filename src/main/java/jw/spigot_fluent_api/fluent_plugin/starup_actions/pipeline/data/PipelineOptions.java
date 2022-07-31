package jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data;

import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_plugin.config.ConfigFile;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PipelineOptions
{
    private FluentPlugin plugin;

    private CommandOptions defaultCommand;

    private DefaultPermissionsDto defaultPermissions;

    private ConfigFile configFile;
}
