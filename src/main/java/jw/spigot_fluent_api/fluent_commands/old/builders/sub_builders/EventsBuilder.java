package jw.spigot_fluent_api.fluent_commands.old.builders.sub_builders;

import jw.spigot_fluent_api.fluent_commands.implementation.events.CommandEvent;
import jw.spigot_fluent_api.fluent_commands.implementation.events.ConsoleCommandEvent;
import jw.spigot_fluent_api.fluent_commands.implementation.events.PlayerCommandEvent;
import jw.spigot_fluent_api.fluent_commands.api.models.CommandModel;
import jw.spigot_fluent_api.desing_patterns.builder.NextStep;

import java.util.function.Consumer;

public class EventsBuilder implements NextStep<FinalizeBuild>
{
    private final CommandModel commandModel;

     Consumer<CommandEvent> onExecute;
     Consumer<PlayerCommandEvent> onPlayerExecute;
     Consumer<ConsoleCommandEvent> onConsoleExecute;


    public EventsBuilder(CommandModel commandModel)
    {
        this.commandModel = commandModel;
    }

    public EventsBuilder onExecute(Consumer<CommandEvent> event)
    {
        onExecute = event;
        return this;
    }

    public EventsBuilder onPlayerExecute(Consumer<PlayerCommandEvent> event)
    {
        onPlayerExecute = event;
        return this;
    }

    public EventsBuilder onConsoleExecute(Consumer<ConsoleCommandEvent> event)
    {
        onConsoleExecute = event;
        return this;
    }

    public EventsBuilder onBlockExecute(Consumer<CommandEvent> event)
    {
        return this;
    }

    public EventsBuilder onEntityExecute(Consumer<CommandEvent> event)
    {
        return this;
    }

    @Override
    public FinalizeBuild nextStep()
    {
        return new FinalizeBuild(commandModel,this);
    }
}
