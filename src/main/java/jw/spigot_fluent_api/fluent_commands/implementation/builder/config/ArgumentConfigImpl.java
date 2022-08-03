package jw.spigot_fluent_api.fluent_commands.implementation.builder.config;

import jw.spigot_fluent_api.fluent_commands.api.builder.ArgumentBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.ArgumentConfig;
import jw.spigot_fluent_api.fluent_commands.implementation.builder.ArgumentBuilderImpl;
import jw.spigot_fluent_api.fluent_commands.api.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.api.models.CommandArgument;
import jw.spigot_fluent_api.fluent_commands.api.models.CommandModel;

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
