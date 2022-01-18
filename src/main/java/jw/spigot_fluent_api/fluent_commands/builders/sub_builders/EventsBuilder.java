package jw.spigot_fluent_api.fluent_commands.builders.sub_builders;

import jw.spigot_fluent_api.fluent_commands.events.CommandEvent;
import jw.spigot_fluent_api.fluent_commands.events.ConsoleCommandEvent;
import jw.spigot_fluent_api.fluent_commands.events.PlayerCommandEvent;
import jw.spigot_fluent_api.fluent_commands.models.CommandModel;
import jw.spigot_fluent_api.desing_patterns.builder.NextStepable;

import java.util.function.Consumer;

public class EventsBuilder implements NextStepable<FinalizeBuild>
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
