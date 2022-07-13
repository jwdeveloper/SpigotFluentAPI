package jw.spigot_fluent_api.fluent_commands.api.builder.config;

import jw.spigot_fluent_api.fluent_commands.api.builder.BuilderConfig;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_commands.implementation.SimpleCommand;

import java.util.List;
import java.util.function.Consumer;

public interface SubCommandConfig extends BuilderConfig
{
    SubCommandConfig addSubCommand(CommandBuilder builder);

    SubCommandConfig addSubCommand(SimpleCommand simpleCommand);

    SubCommandConfig addSubCommand(List<SimpleCommand> simpleCommand);

    SubCommandConfig addSubCommand(String name, Consumer<CommandBuilder> config);
}
