package jw.fluent.plugin.implementation.modules.spigot.commands;

import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;

public interface FluentApiCommandBuilder extends CommandBuilder
{
    public CommandBuilder setDefaultCommandName(String  commandName);
    public String  getName();
}
