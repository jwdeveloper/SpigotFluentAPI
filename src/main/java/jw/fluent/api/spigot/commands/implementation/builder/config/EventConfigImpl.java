package jw.fluent.api.spigot.commands.implementation.builder.config;

import jw.fluent.api.spigot.commands.api.builder.config.EventConfig;
import jw.fluent.api.spigot.commands.implementation.events.CommandEvent;
import jw.fluent.api.spigot.commands.implementation.events.ConsoleCommandEvent;
import jw.fluent.api.spigot.commands.implementation.events.PlayerCommandEvent;
import jw.fluent.api.spigot.commands.api.services.EventsService;

import java.util.function.Consumer;

public class EventConfigImpl implements EventConfig {

    private final EventsService eventsService;

    public EventConfigImpl(EventsService eventsService)
    {
        this.eventsService = eventsService;
    }

    @Override
    public EventConfig onExecute(Consumer<CommandEvent> event) {
        eventsService.onInvoke(event);
        return this;
    }

    @Override
    public EventConfig onPlayerExecute(Consumer<PlayerCommandEvent> event) {
        eventsService.onPlayerInvoke(event);
        return this;
    }

    @Override
    public EventConfig onConsoleExecute(Consumer<ConsoleCommandEvent> event) {
        eventsService.onConsoleInvoke(event);
        return this;
    }

    @Override
    public EventConfig onBlockExecute(Consumer<CommandEvent> event) {
       // eventsService.on(event);
        return this;
    }

    @Override
    public EventConfig onEntityExecute(Consumer<CommandEvent> event) {
       // eventsService.onInvoke(event);
        return this;
    }
}
