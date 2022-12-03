package jw.fluent_plugin.implementation.modules.commands;

import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.spigot.commands.implementation.builder.CommandBuilderImpl;

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
