package jw.spigot_fluent_api.simple_commands;


import jw.spigot_fluent_api.initialization.FluentPlugin;
import jw.spigot_fluent_api.simple_commands.builders.SimpleCommandBuilder;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.simple_commands.events.SimpleCommandEvent;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;
import jw.spigot_fluent_api.simple_commands.services.CommandService;
import jw.spigot_fluent_api.simple_commands.services.SimpleCommandService;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

@Getter
public class SimpleCommand extends Command {

    @Setter
    private List<SimpleCommand> subCommands;
    @Setter
    private SimpleCommand parent;

    private HashMap<CommandAccessType, Consumer<SimpleCommandEvent>> events;

    private final CommandModel commandModel;

    @Setter
    private boolean logs;
    @Setter
    private boolean active = true;

    private CommandService commandService;

    public SimpleCommand(CommandModel commandModel) {
        super(commandModel.getName());
        this.commandModel = commandModel;
        this.subCommands = new ArrayList<>();
        this.commandService = new SimpleCommandService();
        this.events = new HashMap<>();
        this.setPermissionMessage(commandModel.getPermissionMessage());
        this.setDescription(commandModel.getDescription());
        this.setUsage(commandModel.getUsageMessage());
        this.setLabel(commandModel.getLabel());
        for (var accessType : CommandAccessType.values()) {
            this.events.put(accessType, (a) -> {
            });
        }
    }

    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
        displayLog("command triggered");
        var commandTarget = commandService.isSubcommandInvoked(this, args);
        return commandTarget.getSimpleCommand().invokeCommand(sender, args,commandTarget.getArgs());
    }

    public boolean invokeCommand(CommandSender sender, String[] args, String[] commandArgs) {
        if (!this.isActive())
        {
            displayLog("inactive");
            return false;
        }


        if (!commandService.hasSenderAccess(sender, commandModel.getCommandAccesses()))
        {
            displayLog(sender.getName()+" has no access");
            return false;
        }


        if (!commandService.hasSenderPermissions(sender, commandModel.getPermissions()))
        {
            displayLog(sender.getName()+" has no permissions");
            return false;
        }


        if (!commandService.validateArguments(commandArgs, commandModel.getArguments()))
        {
            displayLog(" invalid arguments");
            return false;
        }


        try {
            var values = commandService.getArgumentValues(commandArgs, commandModel.getArguments());
            var eventDto = new SimpleCommandEvent(sender, commandArgs, args, values, true);
            var eventsToInvoke = commandService.getEventsToInvoke(sender, events);
            for (var event : eventsToInvoke) {
                event.accept(eventDto);
            }
            displayLog("command invoked with status "+eventDto.getResult());
            return eventDto.getResult();
        } catch (Exception exception) {
            FluentPlugin.logException("while invoking " + this.getName(), exception);
            return false;
        }
    }

    public void onExecute(Consumer<SimpleCommandEvent> event) {
        registerEvent(CommandAccessType.COMMAND_SENDER, event);
    }

    public void onPlayerExecute(Consumer<SimpleCommandEvent> event) {
        registerEvent(CommandAccessType.PLAYER, event);
    }

    public void onConsoleExecute(Consumer<SimpleCommandEvent> event) {
        registerEvent(CommandAccessType.CONSOLE_SENDER, event);
    }

    public void onBlockExecute(Consumer<SimpleCommandEvent> event) {


    }

    public void onEntityExecute(Consumer<SimpleCommandEvent> event) {

    }

    public void addSubCommand(SimpleCommand command) {
        command.setParent(null);
        this.subCommands.add(command);
    }

    public void removeSubCommand(SimpleCommand command) {
        if (command.getParent() != this)
            return;
        command.setParent(null);
        this.subCommands.remove(command);
    }

    public void setImplementation(CommandService commandService) {
        this.commandService = commandService;
    }

    public String getName() {
        return commandModel.getName();
    }

    public List<CommandArgument> getArguments() {
        return commandModel.getArguments();
    }

    public void displayLog(String log)
    {
        if(logs)
        {
            FluentPlugin.logInfo("Command "+this.getName()+" "+log);
        }
    }

    public boolean hasParent() {
        return !(parent == null);
    }

    private void registerEvent(CommandAccessType accessType, Consumer<SimpleCommandEvent> event) {
        if (event == null)
            return;
        events.replace(accessType, event);
    }

    public static SimpleCommandBuilder builder(String name) {
        return new SimpleCommandBuilder(name);
    }

    public void unregister() {
        SimpleCommandManger.unregister(this);
    }
}
