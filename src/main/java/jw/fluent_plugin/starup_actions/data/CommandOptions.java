package jw.fluent_plugin.starup_actions.data;

import jw.fluent_api.minecraft.commands.api.builder.CommandBuilder;
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
