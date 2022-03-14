package jw.spigot_fluent_api.fluent_commands.builders;

import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.SimpleCommandManger;
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

    private Consumer<CommandEvent> onExecute;
    private Consumer<PlayerCommandEvent> onPlayerExecute;
    private Consumer<ConsoleCommandEvent> onConsoleExecute;

    public SimpleCommandBuilder(String name)
    {
        commandModel = new CommandModel();
        commandModel.setName(name);
    }



}
