package jw.spigot_fluent_api.fluent_commands.api.builder.config;

import jw.spigot_fluent_api.fluent_commands.api.builder.BuilderConfig;
import jw.spigot_fluent_api.fluent_commands.implementation.events.CommandEvent;
import jw.spigot_fluent_api.fluent_commands.implementation.events.ConsoleCommandEvent;
import jw.spigot_fluent_api.fluent_commands.implementation.events.PlayerCommandEvent;

import java.util.function.Consumer;


public interface EventConfig extends BuilderConfig {
    EventConfig onExecute(Consumer<CommandEvent> event);

    EventConfig onPlayerExecute(Consumer<PlayerCommandEvent> event);

    EventConfig onConsoleExecute(Consumer<ConsoleCommandEvent> event);

    EventConfig onBlockExecute(Consumer<CommandEvent> event);

    EventConfig onEntityExecute(Consumer<CommandEvent> event);
}
