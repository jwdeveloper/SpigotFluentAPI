package jw.fluent.api.spigot.commands.api.builder;

import jw.fluent.api.spigot.commands.api.builder.config.EventConfig;
import jw.fluent.api.spigot.commands.api.builder.config.SubCommandConfig;
import jw.fluent.api.spigot.commands.implementation.SimpleCommand;
import jw.fluent.api.desing_patterns.builder.FinishBuilder;
import jw.fluent.api.spigot.commands.api.builder.config.ArgumentConfig;
import jw.fluent.api.spigot.commands.api.builder.config.PropertiesConfig;

import java.util.function.Consumer;

public interface CommandBuilder extends FinishBuilder<SimpleCommand>
{
    CommandBuilder propertiesConfig(Consumer<PropertiesConfig> config);
    CommandBuilder eventsConfig(Consumer<EventConfig> config);
    CommandBuilder argumentsConfig(Consumer<ArgumentConfig> config);
    CommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config);
    SimpleCommand buildSubCommand();
}
