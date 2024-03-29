package jw.fluent.api.spigot.commands.old.builders.sub_builders;

import jw.fluent.api.spigot.commands.old.builders.CommandArgumentBuilder;
import jw.fluent.api.spigot.commands.api.enums.ArgumentType;
import jw.fluent.api.spigot.commands.api.models.CommandArgument;
import jw.fluent.api.spigot.commands.api.models.CommandModel;
import jw.fluent.api.desing_patterns.builder.NextStep;

public class ArgumentBuilder implements NextStep<EventsBuilder>
{

    private final CommandModel commandModel;

    public ArgumentBuilder(CommandModel commandModel)
    {
        this.commandModel = commandModel;
    }

    public ArgumentBuilder withArgument(CommandArgument commandArgument)
    {
        commandModel.getArguments().add(commandArgument);
        return this;
    }

    public ArgumentBuilder withArgument(String name, ArgumentType argumentType)
    {
        return new CommandArgumentBuilder(this,
                commandModel.getArguments(),
                name,
                argumentType).build();
    }

    public CommandArgumentBuilder withArgument(String name)
    {
        return new CommandArgumentBuilder(this,commandModel.getArguments(),name);
    }

    @Override
    public EventsBuilder nextStep() {
        return new EventsBuilder(commandModel);
    }
}
