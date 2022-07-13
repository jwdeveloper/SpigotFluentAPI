package jw.spigot_fluent_api.fluent_commands.api.builder;

import jw.spigot_fluent_api.desing_patterns.builder.FinishBuilder;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.ArgumentConfig;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.EventConfig;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.PropertiesConfig;
import jw.spigot_fluent_api.fluent_commands.api.builder.config.SubCommandConfig;
import jw.spigot_fluent_api.fluent_commands.implementation.SimpleCommand;

import java.util.function.Consumer;

public interface CommandBuilder extends FinishBuilder<SimpleCommand>
{
    CommandBuilder propertiesConfig(Consumer<PropertiesConfig> config);

    CommandBuilder eventsConfig(Consumer<EventConfig> config);

    CommandBuilder argumentsConfig(Consumer<ArgumentConfig> config);

    CommandBuilder subCommandsConfig(Consumer<SubCommandConfig> config);

    SimpleCommand buildSubCommand();
}
