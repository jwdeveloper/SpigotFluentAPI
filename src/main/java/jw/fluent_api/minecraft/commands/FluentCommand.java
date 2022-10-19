package jw.fluent_api.minecraft.commands;

import jw.fluent_api.minecraft.commands.api.builder.CommandBuilder;
import jw.fluent_api.minecraft.commands.implementation.builder.CommandBuilderImpl;
import jw.fluent_api.minecraft.commands.old.builders.sub_builders.DetailBuilder;
import jw.fluent_api.minecraft.commands.api.models.CommandModel;


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
