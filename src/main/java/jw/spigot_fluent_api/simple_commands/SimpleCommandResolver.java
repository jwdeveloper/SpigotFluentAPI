package jw.spigot_fluent_api.simple_commands;

import jw.spigot_fluent_api.simple_commands.annotations.Command;
import jw.spigot_fluent_api.simple_commands.annotations.CommandPermission;
import jw.spigot_fluent_api.simple_commands.annotations.CommandPermissionContainer;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandModel;
import jw.spigot_fluent_api.utilites.ObjectUtility;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class SimpleCommandResolver {

    public static List<CommandModel> resolveCommands(SimpleCommand betterCommand) throws Exception {
        var result = new ArrayList<CommandModel>();
        var type = betterCommand.getClass();
        var commands = getCommandMethods(type);
        CommandModel model = null;
        for (var command : commands) {

            model = new CommandModel();
            resolveCommandInformations(command,model);
            var permissions = resolvePermissions(command,model.getFullName());
            var parameters = resolveParameters(command, model.getFullName());
            model.setPermissions(permissions);
         //   model.setMethod(command);
            model.setArguments(parameters);
        }
        return result;
    }

    public static List<Method> getCommandMethods(Class<?> type)
    {
        for (var method:type.getDeclaredMethods())
        {
            method.setAccessible(true);
        }
        return ObjectUtility.getMethodsAnnotatedWith(type, Command.class);
    }

    public static CommandModel resolveCommandInformations(Method command, CommandModel commandModel) throws Exception {

        var commandInfo = command.getAnnotation(Command.class);
        commandModel.setName(commandInfo.name());
        commandModel.setFullName(commandInfo.name());
        commandModel.setCommandAccess(commandInfo.access());
        commandModel.setDescription(commandInfo.description());
        return commandModel;
    }

    public static List<String> resolvePermissions(Method command, String commandName) {
        var result = new ArrayList<String>();
        if (command.isAnnotationPresent(CommandPermission.class)) {
            return result;
        }
        var permissions = command.getAnnotation(CommandPermissionContainer.class);

        for(var permission: permissions.value())
        {
            if(permission.setGenericPermission())
            {
                result.add(commandName+"."+permission.permission());
            }
            else
            {
                result.add(permission.permission());
            }
        }
        return result;
    }

    public static List<CommandArgument> resolveParameters(Method command, String commandName) throws Exception {
        var result = new ArrayList<CommandArgument>();
        var paramsNames = getAllParametersFromName(commandName);
        var paramtersTypes = command.getParameterTypes();

        for(int i=0;i<paramsNames.size();i++)
        {
            if(ObjectUtility.isPrimitiveType(paramtersTypes[i]))
            {
                throw new Exception("Only primitive types can be use as parameters");
            }
            var commandParameter = new CommandArgument();
           // commandParameter.index = i;
            commandParameter.name = paramsNames.get(i);
            commandParameter.type = paramtersTypes[i];
            result.add(commandParameter);
        }
        return result;
    }


    public static List<String> getAllParametersFromName(String commandName) throws Exception {
        List<String> result = new ArrayList<>();
        if (!commandName.contains("{")) {
            return result;
        }
        var open = "{";
        var close = "}";

        var tempName = commandName;
        int p1 = 0;
        int p2 = 0;
        while (true) {
            p1 = tempName.indexOf(open);
            p2 = tempName.indexOf(close);

            if(p1 == -1 && p2 == -1)
            {
                break;
            }

            if ((p1 != -1 && p2 == -1) || (p1 == -1 && p2 != -1)) {
                throw new Exception("Wrong parameter format");
            }

            var paramName = tempName.substring(p1+1, p2);
            result.add(paramName);
            tempName = tempName.substring(p2+1, tempName.length());
        }
        return result;
    }


}
