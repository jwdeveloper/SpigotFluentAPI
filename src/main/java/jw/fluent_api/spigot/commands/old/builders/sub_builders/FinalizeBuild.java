package jw.fluent_api.spigot.commands.old.builders.sub_builders;

import jw.fluent_api.spigot.commands.implementation.SimpleCommand;
import jw.fluent_api.spigot.commands.implementation.SimpleCommandManger;
import jw.fluent_api.spigot.commands.api.models.CommandModel;
import jw.fluent_api.desing_patterns.builder.FinishBuilder;

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
