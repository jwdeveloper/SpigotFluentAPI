package jw.spigot_fluent_api.simple_commands.utilites;

import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.utilites.PermissionsUtility;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.StringJoiner;

public class SimpleCommandUtility {

    public static String connectArgs(String[] stringArray) {
        StringJoiner joiner = new StringJoiner("");
        for (int i = 0; i < stringArray.length; i++) {
            if (i < stringArray.length - 1)
                joiner.add(stringArray[i] + " ");
            else
                joiner.add(stringArray[i]);
        }
        return joiner.toString();
    }

    public static boolean hasSenderAccess(CommandSender commandSender, List<CommandAccessType> commandAccessType) {
        for(var accessType: commandAccessType)
        {
            if(!hasSenderAccess(commandSender,accessType))
            {
                return false;
            }
        }
        return false;
    }


    public static boolean hasSenderAccess(CommandSender commandSender, CommandAccessType commandAccessType) {
        switch (commandAccessType) {
            case COMMAND_SENDER -> {
                return true;
            }
            case PLAYER -> {
                return commandSender instanceof Player;
            }
            case ENTITY -> {
                return commandSender instanceof Entity;
            }
            case CONSOLE_SENDER -> {
                return commandSender instanceof ConsoleCommandSender;
            }
        }
        return false;
    }

    public static boolean hasSenderPermissions(CommandSender commandSender, List<String> permissons) {
        if (commandSender instanceof Player player) {
            return PermissionsUtility.playerHasPermissions(player, permissons);
        }
        return true;
    }

    public static boolean validateArguments(String[] args, List<CommandArgument> commandArguments) {
        if (args.length != commandArguments.size())
            return false;

        for(int i=0;i<args.length;i++)
        {
            var arguemnt = commandArguments.get(i);
            var validaionResult = true;
            for (var validator : arguemnt.getValidators())
            {
                validaionResult = validator.validate(args[i]);
                if(!validaionResult)
                {
                    break;
                }
            }
        }
        return true;
    }
}
