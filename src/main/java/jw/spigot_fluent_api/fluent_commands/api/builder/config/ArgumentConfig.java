package jw.spigot_fluent_api.fluent_commands.api.builder.config;

import jw.spigot_fluent_api.fluent_commands.api.builder.ArgumentBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.BuilderConfig;
import jw.spigot_fluent_api.fluent_commands.api.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.api.models.CommandArgument;

import java.util.function.Consumer;

public interface ArgumentConfig extends BuilderConfig
{
    ArgumentConfig addArgument(CommandArgument commandArgument);

    ArgumentConfig addArgument(String name, ArgumentType argumentType);

    ArgumentConfig addArgument(String name, Consumer<ArgumentBuilder> builderConsumer);

    ArgumentConfig addArgument(String name);
}
