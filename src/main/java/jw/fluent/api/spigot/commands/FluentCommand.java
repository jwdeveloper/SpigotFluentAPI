package jw.fluent.api.spigot.commands;

import jw.fluent.api.spigot.commands.api.builder.CommandBuilder;
import jw.fluent.api.spigot.commands.api.models.CommandModel;
import jw.fluent.api.spigot.commands.implementation.builder.CommandBuilderImpl;
import jw.fluent.api.spigot.commands.old.builders.sub_builders.DetailBuilder;


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
