package jw.fluent_plugin.api.data;

import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
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
