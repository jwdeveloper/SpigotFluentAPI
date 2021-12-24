package jw.spigot_fluent_api.simple_commands.builders;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.SimpleCommandManger;
import jw.spigot_fluent_api.simple_commands.events.SimpleCommandEvent;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;

import java.util.Arrays;
import java.util.function.Consumer;

public class SimpleCommandBuilder
{
    private final CommandModel commandModel;

    public SimpleCommandBuilder(String name)
    {
        commandModel = new CommandModel();
        commandModel.setName(name);
    }

    public SimpleCommandBuilder setName(String name)
    {
        commandModel.setName(name);
        return this;
    }

    public SimpleCommandBuilder setShortDescription(String shortDescription)
    {
        commandModel.setShortDescription(shortDescription);
        return this;
    }

    public SimpleCommandBuilder setDescription(String description)
    {
        commandModel.setDescription(description);
        return this;
    }

    public SimpleCommandBuilder addOpPermission()
    {
        commandModel.getPermissions().add("op");
        return this;
    }
    public SimpleCommandBuilder addPermission(String ... permisson)
    {
        commandModel.getPermissions().addAll(Arrays.asList(permisson));
        return this;
    }

    public CommandArgumentBuilder addArgument(String name)
    {
        return new CommandArgumentBuilder(this,commandModel.getArguments(),name);
    }

    public SimpleCommandBuilder onExecute(Consumer<SimpleCommandEvent> event)
    {

        return this;
    }

    public SimpleCommandBuilder onPlayerExecute(Consumer<SimpleCommandEvent> event)
    {

        return this;
    }

    public SimpleCommandBuilder onConsoleExecute(Consumer<SimpleCommandEvent> event)
    {

        return this;
    }

    public SimpleCommandBuilder onBlockExecute(Consumer<SimpleCommandEvent> event)
    {

        return this;
    }

    public SimpleCommandBuilder onEntityExecute(Consumer<SimpleCommandEvent> event)
    {

        return this;
    }

    public SimpleCommand build()
    {
        return new SimpleCommand(commandModel);
    }

    public SimpleCommand register()
    {
        var result = build();
        SimpleCommandManger.register(result);
        return result;
    }
}
