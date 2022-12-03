package jw.fluent_api.spigot.commands.api.builder;

import jw.fluent_api.desing_patterns.builder.FinishBuilder;
import jw.fluent_api.spigot.commands.api.builder.config.ArgumentConfig;
import jw.fluent_api.spigot.commands.api.builder.config.EventConfig;
import jw.fluent_api.spigot.commands.api.builder.config.PropertiesConfig;
import jw.fluent_api.spigot.commands.api.builder.config.SubCommandConfig;
import jw.fluent_api.spigot.commands.implementation.SimpleCommand;

import java.util.function.Consumer;

public interface CommandBuilder extends FinishBuilder<SimpleCommand>
{
    CommandBuilder propertiesConfig(Consumer<PropertiesConfig> config);
    CommandBuilder eventsConfig(Consumer<EventConfig> config);
    CommandBuilder argumentsConfig(Consumer<ArgumentConfig> config);
    CommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config);
    SimpleCommand buildSubCommand();
}
