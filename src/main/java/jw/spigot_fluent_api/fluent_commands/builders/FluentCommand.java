package jw.spigot_fluent_api.fluent_commands.builders;

import jw.spigot_fluent_api.fluent_commands.builders.sub_builders.DetailBuilder;
import jw.spigot_fluent_api.fluent_commands.models.CommandModel;

public class FluentCommand
{

    public static DetailBuilder create(String commandName)
    {
        var commandModel = new CommandModel();
        commandModel.setName(commandName);
        return new DetailBuilder(commandModel);
    }
}
