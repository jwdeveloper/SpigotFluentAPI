package jw.fluent.api.spigot.commands.implementation.builder.config;

import jw.fluent.api.spigot.commands.FluentCommand;
import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.api.builder.config.SubCommandConfig;
import jw.fluent.api.spigot.commands.implementation.SimpleCommand;

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
