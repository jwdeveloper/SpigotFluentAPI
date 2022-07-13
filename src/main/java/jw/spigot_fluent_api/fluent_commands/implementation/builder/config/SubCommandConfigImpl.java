package jw.spigot_fluent_api.fluent_commands.implementation.builder.config;

import jw.spigot_fluent_api.fluent_commands.FluentCommand;
import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.SubCommandConfig;
import jw.spigot_fluent_api.fluent_commands.implementation.SimpleCommand;

import java.util.List;
import java.util.function.Consumer;

public class SubCommandConfigImpl implements SubCommandConfig {

    private final List<SimpleCommand>  commands;

    public SubCommandConfigImpl(List<SimpleCommand> commands)
    {
        this.commands = commands;
    }

    @Override
    public SubCommandConfig addSubCommand(CommandBuilder builder) {
        return addSubCommand(builder.buildSubCommand());
    }

    @Override
    public SubCommandConfig addSubCommand(SimpleCommand simpleCommand) {
        commands.add(simpleCommand);
        return this;
    }

    @Override
    public SubCommandConfig addSubCommand(List<SimpleCommand> simpleCommand) {
        commands.addAll(simpleCommand);
        return this;
    }

    @Override
    public SubCommandConfig addSubCommand(String name, Consumer<CommandBuilder> config) {
        var builder = FluentCommand.create(name);
        config.accept(builder);
        return addSubCommand(builder.buildSubCommand());
    }
}
