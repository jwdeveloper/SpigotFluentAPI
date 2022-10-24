package jw.fluent_api.spigot.commands.api.builder.config;

import jw.fluent_api.spigot.commands.api.builder.BuilderConfig;
import jw.fluent_api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent_api.spigot.commands.implementation.SimpleCommand;

import java.util.List;
import java.util.function.Consumer;

public interface SubCommandConfig extends BuilderConfig
{
    SubCommandConfig addSubCommand(CommandBuilder builder);

    SubCommandConfig addSubCommand(SimpleCommand simpleCommand);

    SubCommandConfig addSubCommand(List<SimpleCommand> simpleCommand);

    SubCommandConfig addSubCommand(String name, Consumer<CommandBuilder> config);
}
