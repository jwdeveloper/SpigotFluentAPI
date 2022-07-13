package jw.spigot_fluent_api.fluent_commands;

import jw.spigot_fluent_api.fluent_commands.api.builder.CommandBuilder;
import jw.spigot_fluent_api.fluent_commands.implementation.builder.CommandBuilderImpl;
import jw.spigot_fluent_api.fluent_commands.old.builders.sub_builders.DetailBuilder;
import jw.spigot_fluent_api.fluent_commands.api.models.CommandModel;


public class FluentCommand {

    public static DetailBuilder create_OLDWAY(String commandName) {
        var commandModel = new CommandModel();
        commandModel.setName(commandName);
        return new DetailBuilder(commandModel);
    }
    public static CommandBuilder create(String commandName) {
        return new CommandBuilderImpl(commandName);
    }
}
