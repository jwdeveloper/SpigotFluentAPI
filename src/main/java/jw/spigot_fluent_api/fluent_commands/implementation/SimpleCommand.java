package jw.spigot_fluent_api.fluent_commands.implementation;


import jw.spigot_fluent_api.fluent_commands.implementation.services.CommandServiceImpl;
import jw.spigot_fluent_api.fluent_commands.implementation.services.EventsServiceImpl;
import jw.spigot_fluent_api.fluent_commands.implementation.services.MessageServiceImpl;
import jw.spigot_fluent_api.fluent_commands.old.interfaces.SimpleCommandConfig;
import jw.spigot_fluent_api.fluent_logger.FluentLogger;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import jw.spigot_fluent_api.fluent_commands.api.enums.ArgumentType;
import jw.spigot_fluent_api.fluent_commands.api.models.CommandArgument;
import jw.spigot_fluent_api.fluent_commands.api.models.CommandModel;
import jw.spigot_fluent_api.fluent_commands.api.services.*;
import jw.spigot_fluent_api.fluent_message.message.MessageBuilder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.scoreboard.Objective;

import java.util.ArrayList;
import java.util.List;

@Getter
public class SimpleCommand extends Command {

    @Setter
    private List<SimpleCommand> subCommands;
    @Setter
    private SimpleCommand parent;

    private final CommandModel commandModel;

    @Setter
    private boolean logs;
    @Setter
    private boolean active = true;

    @Getter @Setter
    private EventsService eventsService;
    @Getter @Setter
    private CommandService commandService;
    @Getter @Setter
    private MessagesService messagesService;

    public SimpleCommand(CommandModel commandModel) {
        this(commandModel,
                new ArrayList<>(),
                new CommandServiceImpl(),
                new MessageServiceImpl(),
                new EventsServiceImpl());
    }
    public SimpleCommand(CommandModel commandModel, List<SimpleCommand> simpleCommands) {
        this(commandModel,
                simpleCommands,
                new CommandServiceImpl(),
                new MessageServiceImpl(),
                new EventsServiceImpl());
    }

    public SimpleCommand(CommandModel commandModel,
                         List<SimpleCommand> simpleCommands,
                         CommandService commandService,
                         MessagesService messagesService,
                         EventsService eventsService)
    {
        super(commandModel.getName());
        this.commandModel = commandModel;
        this.subCommands = simpleCommands;
        this.commandService = commandService;
        this.messagesService = messagesService;
        this.eventsService = eventsService;
        this.setPermissionMessage(commandModel.getPermissionMessage());
        this.setDescription(commandModel.getDescription());
        this.setUsage(commandModel.getUsageMessage());
        this.setLabel(commandModel.getLabel());
    }



    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
        displayLog("command triggered");
        var commandTarget = commandService.isSubcommandInvoked(this, args);
        return commandTarget.getSimpleCommand().invokeCommand(sender, args, commandTarget.getArgs());
    }

    public boolean invokeCommand(CommandSender sender, String[] args, String[] commandArgs) {
        if (!this.isActive()) {
            displayLog("inactive");
            sender.sendMessage(messagesService.inactiveCommand(this.getName()));
            return false;
        }

        if (!commandService.hasSenderAccess(sender, commandModel.getCommandAccesses())) {
            displayLog(sender.getName() + " has no access");
            sender.sendMessage(messagesService.noAccess(sender));
            return false;
        }

        var permissionResult = commandService.hasSenderPermissions(sender, commandModel.getPermissions());
        if (!permissionResult.isSuccess())
        {
            displayLog(sender.getName() + " has no permissions "+permissionResult.getMessage());
            sender.sendMessage(messagesService.noPermission(sender,permissionResult.getMessage()));
            return false;
        }

        var validationResult = commandService.validateArguments(commandArgs, commandModel.getArguments());
        if (!validationResult.isSuccess()) {
            displayLog("invalid arguments");
            sender.sendMessage(messagesService.invalidArgument(validationResult.getMessage()));
            return false;
        }

        try {
            var invokeStatus = eventsService.invokeEvent(sender,args,commandArgs);
            displayLog("command invoked with status " + invokeStatus);
            return invokeStatus;
        } catch (Exception exception) {
            FluentLogger.error("error while invoking command " + this.getName(), exception);
            return false;
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        var commandTarget = commandService.isSubcommandInvoked(this, args);
        return commandTarget.getSimpleCommand().displayTabComplete(sender, args, commandTarget.getArgs());
    }


    public List<String> displayTabComplete(CommandSender sender, String[] args, String[] commandArgs) {
        var arguments = this.getArguments();

        if (commandArgs.length > arguments.size()) {
            if (commandArgs.length == arguments.size() + 1) {
                return subCommands.stream().map(c -> c.getName()).toList();
            } else
                return List.of();
        }
        if (arguments.size() == 0) {
            return List.of();
        }

        var argIndex = commandArgs.length - 1;
        argIndex = argIndex < 0 ? 0 : argIndex;
        var argument = arguments.get(argIndex);
        switch (argument.getArgumentDisplayMode()) {
            case TAB_COMPLETE -> {
                if (argument.getType() == ArgumentType.PLAYERS) {
                    return Bukkit.getOnlinePlayers().stream().map(c -> c.getName()).toList();
                }
                return argument.getTabCompleter();
            }
            case NAME -> {
                return List.of(new MessageBuilder().text(argument.getType().name()).toString());
            }
            case TYPE -> {
                return List.of(new MessageBuilder().inBrackets(argument.getType().name().toLowerCase()).toString());
            }
        }
        return List.of();
    }



    public void addSubCommand(SimpleCommandConfig config) {
        this.addSubCommand(config.configureCommand());
    }

    public void addSubCommand(SimpleCommand command) {
        if(command == this)
            return;
        command.setParent(null);
        this.subCommands.add(command);
    }

    public void removeSubCommand(SimpleCommand command) {
        if (command.getParent() != this)
            return;
        command.setParent(null);
        this.subCommands.remove(command);
    }
    public String getName() {
        return commandModel.getName();
    }

    public List<CommandArgument> getArguments() {
        return commandModel.getArguments();
    }

    private void displayLog(String log) {
        if (logs) {
            FluentLogger.info("Command " + this.getName() + " " + log);
        }
    }

    public boolean hasParent() {
        return !(parent == null);
    }

    public void sendHelpMessage(CommandSender commandSender)
    {
        var messages =new MessageBuilder()
                .color(ChatColor.GRAY)
                .bar("_",30)
                .newLine()
                .color(ChatColor.GRAY)
                .text("Available commands")
                .newLine()
                .newLine();
        Objective s;

        for(var subCommand:subCommands)
        {
            messages.color(ChatColor.AQUA)
                    .text("/")
                    .text(subCommand.getName());
            for(var argument : subCommand.getArguments())
            {
                messages.space().inBrackets(argument.getName());
            }
            messages.newLine();
        }
       var result = messages.color(ChatColor.GRAY)
                .bar("_",30).toArray();

        commandSender.sendMessage(result);
    }


    public void unregister() {
        var status = SimpleCommandManger.unregister(this);
        displayLog("Unregistered status " + status);

    }

    public void register() {
        var status = SimpleCommandManger.register(this);
        displayLog("registered status " + status);
    }


}
