package jw.fluent.api.spigot.commands.implementation.builder.config;

import jw.fluent.api.spigot.commands.api.builder.ArgumentBuilder;
import jw.fluent.api.spigot.commands.api.builder.config.ArgumentConfig;
import jw.fluent.api.spigot.commands.implementation.builder.ArgumentBuilderImpl;
import jw.fluent.api.spigot.commands.api.enums.ArgumentType;
import jw.fluent.api.spigot.commands.api.models.CommandArgument;
import jw.fluent.api.spigot.commands.api.models.CommandModel;

import java.util.function.Consumer;

public class ArgumentConfigImpl implements ArgumentConfig
{
    private final CommandModel model;

    public ArgumentConfigImpl(CommandModel model)
    {
        this.model = model;
    }

    @Override
    public ArgumentConfig addArgument(CommandArgument commandArgument)
    {
        model.getArguments().add(commandArgument);
        return this;
    }

    @Override
    public ArgumentConfig addArgument(String name, ArgumentType argumentType) {

        var builder = new ArgumentBuilderImpl(name);
        builder.setType(argumentType);
        return this.addArgument(builder.build());

    }

    @Override
    public ArgumentConfig addArgument(String name) {
        return this.addArgument(new ArgumentBuilderImpl(name).build());
    }

    public ArgumentConfig addArgument(String name, Consumer<ArgumentBuilder> builder)
    {
        var argumentBuilder = new ArgumentBuilderImpl(name);
        builder.accept(argumentBuilder);
        return this.addArgument(argumentBuilder.build());
    }
}
