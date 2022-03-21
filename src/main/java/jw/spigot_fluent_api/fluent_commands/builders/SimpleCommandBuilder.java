package jw.spigot_fluent_api.fluent_commands.builders;

import jw.spigot_fluent_api.desing_patterns.builder.NextStep;
import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.SimpleCommandManger;
import jw.spigot_fluent_api.fluent_commands.builders.sub_builders.DetailBuilder;
import jw.spigot_fluent_api.fluent_commands.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.events.CommandEvent;
import jw.spigot_fluent_api.fluent_commands.events.ConsoleCommandEvent;
import jw.spigot_fluent_api.fluent_commands.events.PlayerCommandEvent;
import jw.spigot_fluent_api.fluent_commands.models.CommandArgument;
import jw.spigot_fluent_api.fluent_commands.models.CommandModel;

import java.util.Arrays;
import java.util.function.Consumer;

public class SimpleCommandBuilder
{
    private final CommandModel commandModel;


    public SimpleCommandBuilder()
    {
        commandModel = new CommandModel();
    }

    public DetailBuilder setName(String name)
    {
        return new DetailBuilder(commandModel);
    }
}
