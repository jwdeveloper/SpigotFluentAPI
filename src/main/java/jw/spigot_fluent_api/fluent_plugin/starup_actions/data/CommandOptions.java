package jw.spigot_fluent_api.fluent_plugin.starup_actions.data;

import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommandOptions
{
    private String name;

    public boolean hasDefaultCommand()
    {
        return builder != null;
    }

    private CommandBuilder builder;
}
