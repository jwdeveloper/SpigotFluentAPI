package jw.spigot_fluent_api.fluent_commands.services;

import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.enums.AccessType;
import jw.spigot_fluent_api.fluent_commands.models.CommandArgument;
import jw.spigot_fluent_api.fluent_commands.models.CommandTarget;
import jw.spigot_fluent_api.fluent_commands.models.ValidationResult;
import jw.spigot_fluent_api.fluent_message.MessageBuilder;
import jw.spigot_fluent_api.fluent_plugin.FluentPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class SimpleCommandService implements CommandService {

    @Override
    public boolean hasSenderAccess(CommandSender commandSender, List<AccessType> commandAccessType) {

        if (commandSender instanceof ConsoleCommandSender) {
            return true;
        }
        for (var accessType : commandAccessType) {
            if (!hasSenderAccess(commandSender, accessType)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args) {

        if (args.length == 0 || command.getSubCommands().size() == 0) {
            return new CommandTarget(command, args);
        }
        var arguments = command.getArguments();
        var subCommandIndex = arguments.size() + 1;

        if (subCommandIndex > args.length) {
            return new CommandTarget(command, args);
        }

        var subCommandName = args[subCommandIndex - 1];
        var subCommandOptional = command.getSubCommands()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(subCommandName))
                .findFirst();

        if (subCommandOptional.isEmpty()) {
            return new CommandTarget(command, args);
        }
        String[] part = Arrays.copyOfRange(args, subCommandIndex, args.length);
        return isSubcommandInvoked(subCommandOptional.get(), part);
    }

    @Override
    public boolean hasSenderAccess(CommandSender commandSender, AccessType commandAccessType) {
        switch (commandAccessType) {
            case PLAYER -> {
                return commandSender instanceof Player;
            }
            case ENTITY -> {
                return commandSender instanceof Entity;
            }
            case CONSOLE_SENDER -> {
                return commandSender instanceof ConsoleCommandSender;
            }
            default -> {
                return false;
            }
        }
    }
    @Override
    public ValidationResult hasSenderPermissions(CommandSender commandSender, List<String> permissions) {
        if (commandSender instanceof Player player)
        {
            for (var permission : permissions) {
                if (!player.hasPermission(permission)) {
                    return new ValidationResult(false, permission);
                }
            }
        }
        return new ValidationResult(true, "");
    }

    public Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments) {
        Object[] result = new Object[args.length];
        for (int i = 0; i < args.length; i++) {
            try {
                var argument = commandArguments.get(i);
                var value = args[i];
                switch (argument.getType()) {
                    case INT -> result[i] = Integer.parseInt(value);
                    case BOOL -> result[i] = Boolean.parseBoolean(value);
                    case NUMBER -> result[i] = Float.parseFloat(value);
                    case COLOR -> result[i] = ChatColor.valueOf(value.toUpperCase());
                    default -> result[i] = value;
                }
            } catch (Exception e) {
                FluentPlugin.logException("Error while getting argument ",e);
            }
        }
        return result;
    }

    @Override
    public ValidationResult validateArguments(String[] args, List<CommandArgument> commandArguments) {

        if (commandArguments.size() == 0) {
            return new ValidationResult(true, "");
        }

        if (args.length != commandArguments.size()) {
            return new ValidationResult(false, "Incorrect number of arguments, should be " + commandArguments.size());
        }

        for (int i = 0; i < args.length; i++) {
            var argument = commandArguments.get(i);
            ValidationResult validationResult;
            for (var validator : argument.getValidators()) {
                validationResult = validator.validate(args[i]);
                if (!validationResult.result()) {
                    var message = new MessageBuilder()
                            .text("Argument")
                            .space()
                            .inBrackets((i + 1) + "")
                            .space()
                            .color(ChatColor.GREEN)
                            .color(ChatColor.BOLD)
                            .inBrackets(argument.getName())
                            .space()
                            .color(ChatColor.WHITE)
                            .text(validationResult.message())
                            .toString();
                    return new ValidationResult(false, message);
                }
            }
        }
        return new ValidationResult(true, "");
    }
}
