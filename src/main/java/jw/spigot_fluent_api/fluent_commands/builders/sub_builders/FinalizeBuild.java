package jw.spigot_fluent_api.fluent_commands.builders.sub_builders;

import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.SimpleCommandManger;
import jw.spigot_fluent_api.fluent_commands.models.CommandModel;
import jw.spigot_fluent_api.desing_patterns.builder.FinishBuilder;

public class FinalizeBuild implements FinishBuilder<SimpleCommand>
{
    private final CommandModel commandModel;
    private final EventsBuilder eventsBuilder;

    public FinalizeBuild(CommandModel commandModel, EventsBuilder eventsBuilder)
    {
        this.commandModel = commandModel;
        this.eventsBuilder = eventsBuilder;
    }

    public SimpleCommand build()
    {
        var result = new SimpleCommand(commandModel);
        result.getEventsService().onInvoke(eventsBuilder.onExecute);
        result.getEventsService().onPlayerInvoke(eventsBuilder.onPlayerExecute);
        result.getEventsService().onConsoleInvoke(eventsBuilder.onConsoleExecute);
        return result;
    }

    public SimpleCommand buildAndRegister()
    {
        var result = build();
        SimpleCommandManger.register(result);
        return result;
    }
}
