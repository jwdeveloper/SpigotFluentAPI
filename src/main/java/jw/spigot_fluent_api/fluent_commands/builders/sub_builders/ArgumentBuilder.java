package jw.spigot_fluent_api.fluent_commands.builders.sub_builders;

import jw.spigot_fluent_api.fluent_commands.builders.CommandArgumentBuilder;
import jw.spigot_fluent_api.fluent_commands.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.models.CommandArgument;
import jw.spigot_fluent_api.fluent_commands.models.CommandModel;
import jw.spigot_fluent_api.desing_patterns.builder.NextStepable;

public class ArgumentBuilder implements NextStepable<EventsBuilder>
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

    public CommandArgumentBuilder withArgument(String name, ArgumentType argumentType)
    {
        return new CommandArgumentBuilder(this,
                commandModel.getArguments(),
                name,
                argumentType);
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
