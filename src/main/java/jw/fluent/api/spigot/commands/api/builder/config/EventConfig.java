package jw.fluent.api.spigot.commands.api.builder.config;

import jw.fluent.api.spigot.commands.implementation.events.CommandEvent;
import jw.fluent.api.spigot.commands.implementation.events.ConsoleCommandEvent;
import jw.fluent.api.spigot.commands.implementation.events.PlayerCommandEvent;
import jw.fluent.api.spigot.commands.api.builder.BuilderConfig;

import java.util.function.Consumer;


public interface EventConfig extends BuilderConfig {
    EventConfig onExecute(Consumer<CommandEvent> event);

    EventConfig onPlayerExecute(Consumer<PlayerCommandEvent> event);

    EventConfig onConsoleExecute(Consumer<ConsoleCommandEvent> event);

    EventConfig onBlockExecute(Consumer<CommandEvent> event);

    EventConfig onEntityExecute(Consumer<CommandEvent> event);
}
