package jw.spigot_fluent_api.simple_commands.builders;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.SimpleCommandManger;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.simple_commands.events.SimpleCommandEvent;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class SimpleCommandBuilder
{
    private final CommandModel commandModel;

    private Consumer<SimpleCommandEvent> onExecute;
    private Consumer<SimpleCommandEvent> onPlayerExecute;
    private Consumer<SimpleCommandEvent> onConsoleExecute;

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

    public SimpleCommandBuilder setUsageMessage(String usageMessage)
    {
        commandModel.setUsageMessage(usageMessage);
        return this;
    }

    public SimpleCommandBuilder setPermissionMessage(String permissionMessage)
    {
        commandModel.setPermissionMessage(permissionMessage);
        return this;
    }
    public SimpleCommandBuilder setLabel(String label)
    {
        commandModel.setLabel(label);
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

    public SimpleCommandBuilder addAccess(CommandAccessType ... accessTypes)
    {
        var accesses = commandModel.getCommandAccesses();
        accesses.addAll(List.of(accessTypes));
        return this;
    }

    public CommandArgumentBuilder addArgument(String name)
    {
        return new CommandArgumentBuilder(this,commandModel.getArguments(),name);
    }

    public SimpleCommandBuilder onExecute(Consumer<SimpleCommandEvent> event)
    {
          onExecute = event;
        return this;
    }

    public SimpleCommandBuilder onPlayerExecute(Consumer<SimpleCommandEvent> event)
    {
       onPlayerExecute = event;
        return this;
    }

    public SimpleCommandBuilder onConsoleExecute(Consumer<SimpleCommandEvent> event)
    {
       onConsoleExecute = event;
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
        var result = new SimpleCommand(commandModel);
        result.onExecute(onExecute);
        result.onPlayerExecute(onPlayerExecute);
        result.onConsoleExecute(onConsoleExecute);
        return result;
    }

    public SimpleCommand register()
    {
        var result = build();
        SimpleCommandManger.register(result);
        return result;
    }
}
