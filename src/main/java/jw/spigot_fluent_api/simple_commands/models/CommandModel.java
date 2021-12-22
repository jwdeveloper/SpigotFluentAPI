package jw.spigot_fluent_api.simple_commands.models;


import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.utilites.ObjectUtility;
import jw.spigot_fluent_api.utilites.PermissionsUtility;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.List;

@Getter
@Setter
public class CommandModel {

    private String name;

    private String fullName;

    private String desciption;

    private String fullDesciption;

    private boolean requireAllParams;

    private int paramsCount;

    private Object commandClass;

    private Class<?>[] methodParameters;

    private List<String> permissions;

    private List<CommandArgument> arguments;

    private Method method;

    private CommandAccessType commandAccess;

    private boolean logs;

    private boolean requireCommandSender;

    private boolean isActive;

    public boolean isNameEmpty()
    {
        return name.length() == 0;
    }

    public int getParamsCount() {
        return arguments.size();
    }


    public boolean validate(CommandSender commandSender, String[] args) {
        if(!isActive)
            return false;

        if (!hasAccess(commandSender)) {
            return false;
        }
        if (!checkPermissions(commandSender)) {
            return false;
        }
        if (!ValidateArguments(args)) {
            return false;
        }
        return true;
    }

    public boolean runCommand(CommandSender commandSender, String[] args) throws Exception {
        Object[] params = new Object[getParamsCount()];
        for (int i = 0; i < arguments.size(); i++) {
            var paramIndex = arguments.get(i).index;
            if (paramIndex < args.length - 1) {
                if (requireAllParams) {
                    throw new Exception("All parameters are required");
                } else {
                    params[i] = null;
                    continue;
                }
            }
            var value = args[paramIndex];
            var paramType = arguments.get(i).type;
            params[i] = ObjectUtility.castStringToPrimitiveType(value, paramType);
        }
        if (requireCommandSender)
        {
            switch (commandAccess) {
                case COMMAND_SENDER -> params[params.length - 1] = commandSender;
                case PLAYER -> params[params.length - 1] = (Player) commandSender;
                case CONSOLE_SENDER -> params[params.length - 1] = (ConsoleCommandSender) commandSender;
            }
        }

        method.invoke(commandClass, params);
        return true;
    }

    private boolean ValidateArguments(String[] args) {
        if (requireAllParams && args.length != arguments.size()) {
            return false;
        }
        return true;
    }

    private boolean hasAccess(CommandSender commandSender) {

        switch (commandAccess) {
            case COMMAND_SENDER -> {
                return true;
            }
            case PLAYER -> {
                return commandSender instanceof Player;
            }
            case CONSOLE_SENDER -> {
                return commandSender instanceof ConsoleCommandSender;
            }
        }
        return false;
    }

    private boolean checkPermissions(CommandSender commandSender) {
        if (commandSender instanceof Player player) {
            return PermissionsUtility.playerHasPermissions(player, permissions);
        }
        return true;
    }
}
