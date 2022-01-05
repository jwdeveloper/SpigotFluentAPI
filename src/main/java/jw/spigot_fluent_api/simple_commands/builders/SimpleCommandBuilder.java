package jw.spigot_fluent_api.simple_commands.builders;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.SimpleCommandManger;
import jw.spigot_fluent_api.simple_commands.enums.ArgumentType;
import jw.spigot_fluent_api.simple_commands.events.CommandEvent;
import jw.spigot_fluent_api.simple_commands.events.ConsoleCommandEvent;
import jw.spigot_fluent_api.simple_commands.events.PlayerCommandEvent;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;

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
    public SimpleCommandBuilder addPermissions(String ... permisson)
    {
        commandModel.getPermissions().addAll(Arrays.asList(permisson));
        return this;
    }
    public SimpleCommandBuilder newArgument(CommandArgument commandArgument)
    {
        commandModel.getArguments().add(commandArgument);
        return this;
    }

    public CommandArgumentBuilder newArgument(String name, ArgumentType argumentType)
    {
        return new CommandArgumentBuilder(this,
                commandModel.getArguments(),
                name,
                argumentType);
    }

    public CommandArgumentBuilder newArgument(String name)
    {
        return new CommandArgumentBuilder(this,commandModel.getArguments(),name);
    }

    public SimpleCommandBuilder onExecute(Consumer<CommandEvent> event)
    {
          onExecute = event;
        return this;
    }

    public SimpleCommandBuilder onPlayerExecute(Consumer<PlayerCommandEvent> event)
    {
       onPlayerExecute = event;
        return this;
    }

    public SimpleCommandBuilder onConsoleExecute(Consumer<ConsoleCommandEvent> event)
    {
       onConsoleExecute = event;
        return this;
    }

    public SimpleCommandBuilder onBlockExecute(Consumer<CommandEvent> event)
    {
        return this;
    }

    public SimpleCommandBuilder onEntityExecute(Consumer<CommandEvent> event)
    {
        return this;
    }

    public SimpleCommand build()
    {
        var result = new SimpleCommand(commandModel);
        result.getEventsService().onInvoke(onExecute);
        result.getEventsService().onPlayerInvoke(onPlayerExecute);
        result.getEventsService().onConsoleInvoke(onConsoleExecute);
        return result;
    }

    public SimpleCommand register()
    {
        var result = build();
        SimpleCommandManger.register(result);
        return result;
    }
}
