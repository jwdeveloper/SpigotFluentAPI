package jw.fluent.plugin.implementation.modules.spigot.commands;

import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.implementation.builder.CommandBuilderImpl;

public class FluentApiDefaultCommandBuilder extends CommandBuilderImpl implements FluentApiCommandBuilder
{
    @Override
    public CommandBuilder setName(String  commandName) {
        model.setName(commandName);
        return this;
    }

    @Override
    public String  getName() {
        return model.getName();
    }
}
