package jw.fluent_api.spigot.commands.api.builder.config;

import jw.fluent_api.spigot.commands.api.builder.ArgumentBuilder;
import jw.fluent_api.spigot.commands.api.builder.BuilderConfig;
import jw.fluent_api.spigot.commands.api.enums.ArgumentType;
import jw.fluent_api.spigot.commands.api.models.CommandArgument;

import java.util.function.Consumer;

public interface ArgumentConfig extends BuilderConfig
{
    ArgumentConfig addArgument(CommandArgument commandArgument);

    ArgumentConfig addArgument(String name, ArgumentType argumentType);

    ArgumentConfig addArgument(String name, Consumer<ArgumentBuilder> builderConsumer);

    ArgumentConfig addArgument(String name);
}
