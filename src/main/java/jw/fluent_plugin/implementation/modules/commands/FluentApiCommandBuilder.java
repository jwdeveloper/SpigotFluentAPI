package jw.fluent_plugin.implementation.modules.commands;

import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;

public interface FluentApiCommandBuilder extends CommandBuilder
{
    public CommandBuilder setName(String  commandName);
    public String  getName();
}
