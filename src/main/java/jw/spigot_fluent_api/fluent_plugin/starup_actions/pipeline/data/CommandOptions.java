package jw.spigot_fluent_api.fluent_plugin.starup_actions.pipeline.data;

import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandOptions
{
    private String name;
    private CommandBuilder builder;
}
