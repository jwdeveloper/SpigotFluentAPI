package jw.spigot_fluent_api.simple_commands.services;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.simple_commands.enums.CommandArgumentType;
import jw.spigot_fluent_api.simple_commands.events.SimpleCommandEvent;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandTarget;
import jw.spigot_fluent_api.simple_commands.utilites.SimpleCommandUtility;
import jw.spigot_fluent_api.utilites.PermissionsUtility;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;

public class SimpleCommandService implements CommandService {
    @Override
    public String connectArgs(String[] stringArray) {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < stringArray.length; i++) {
            if (i < stringArray.length - 1)
                joiner.add(stringArray[i] + " ");
            else
                joiner.add(stringArray[i]);
        }
        return joiner.toString();
    }

    @Override
    public boolean hasSenderAccess(CommandSender commandSender, List<CommandAccessType> commandAccessType) {

        if(commandSender instanceof ConsoleCommandSender)
        {
            return true;
        }
        for(var accessType: commandAccessType)
        {
            if(!hasSenderAccess(commandSender,accessType))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    public CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args) {

        if(args.length == 0)
        {
            return new CommandTarget(command,args);
        }

        var arguments = command.getArguments();
        var subCommandIndex = 0;
        for(int i=0;i< arguments.size();i++)
        {
            if(arguments.get(i).getType() != CommandArgumentType.SUBCOMMAND)
            {
               continue;
            }
            subCommandIndex = i;
            break;
        }
        if(subCommandIndex == -1)
        {
            return new CommandTarget(command,args);
        }
        if(subCommandIndex > args.length-1)
        {
            return new CommandTarget(command,args);
        }
        var subCommandName = args[subCommandIndex];
        var subCommandOptional = command.getSubCommands()
                .stream()
                .filter(c -> c.getName().equalsIgnoreCase(subCommandName))
                .findFirst();

        if(subCommandOptional.isEmpty())
        {
            return new CommandTarget(command,args);
        }
        if(subCommandIndex == args.length -1)
        {
            return new CommandTarget(subCommandOptional.get(),args);
        }
        String[] part =  Arrays.copyOfRange(args, subCommandIndex+1, args.length);
        return isSubcommandInvoked(subCommandOptional.get(), part);
    }

    @Override
    public boolean hasSenderAccess(CommandSender commandSender, CommandAccessType commandAccessType) {
        switch (commandAccessType) {
            case PLAYER -> {
                return commandSender instanceof Player;
            }
            case ENTITY -> {
                return commandSender instanceof Entity;
            }
            default ->{
                return true;
            }
        }
    }

    public List<Consumer<SimpleCommandEvent>> getEventsToInvoke(CommandSender sender, HashMap<CommandAccessType, Consumer<SimpleCommandEvent>> events)
    {
        var result = new ArrayList<Consumer<SimpleCommandEvent>>();
        var senderType = SimpleCommandUtility.commandServerToCommandAccessType(sender);

        result.add(events.get(CommandAccessType.COMMAND_SENDER));
        if(senderType != CommandAccessType.COMMAND_SENDER)
        {
            result.add(events.get(senderType));
        }
        return result;
    }

    @Override
    public boolean hasSenderPermissions(CommandSender commandSender, List<String> permissons) {
        if (commandSender instanceof Player player) {
            return PermissionsUtility.playerHasPermissions(player, permissons);
        }
        return true;
    }

    public Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments)
    {
        if (args.length != commandArguments.size())
            return null;
        Object[] result = new Object[args.length];
        for(int i=0;i<args.length;i++)
        {
            try
            {
                var argument = commandArguments.get(i);
                var value = args[i];
                switch (argument.getType())
                {
                    case INT -> result[i] = Integer.parseInt(value);
                    case BOOL -> result[i] = Boolean.parseBoolean(value);
                    case NUMBER -> result[i] = Float.parseFloat(value);
                    case COLOR -> result[i] = ChatColor.valueOf(value.toUpperCase());
                    default -> result[i] = value;
                }
            }
            catch (Exception e)
            {

            }
        }
        return result;
    }

    @Override
    public boolean validateArguments(String[] args, List<CommandArgument> commandArguments)
    {
        if (args.length != commandArguments.size())
            return false;

        for(int i=0;i<args.length;i++)
        {
            var argument = commandArguments.get(i);
            var validationResult = true;
            for (var validator : argument.getValidators())
            {
                validationResult = validator.validate(args[i]);
                if(!validationResult)
                {
                    return false;
                }
            }
        }
        return true;
    }
}
