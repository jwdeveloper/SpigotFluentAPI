package jw.fluent_api.minecraft.commands.api.builder.config;

import jw.fluent_api.minecraft.commands.api.builder.ArgumentBuilder;
import jw.fluent_api.minecraft.commands.api.builder.BuilderConfig;
import jw.fluent_api.minecraft.commands.api.enums.ArgumentType;
import jw.fluent_api.minecraft.commands.api.models.CommandArgument;

import java.util.function.Consumer;

public interface ArgumentConfig extends BuilderConfig
{
    ArgumentConfig addArgument(CommandArgument commandArgument);

    ArgumentConfig addArgument(String name, ArgumentType argumentType);

    ArgumentConfig addArgument(String name, Consumer<ArgumentBuilder> builderConsumer);

    ArgumentConfig addArgument(String name);
}
