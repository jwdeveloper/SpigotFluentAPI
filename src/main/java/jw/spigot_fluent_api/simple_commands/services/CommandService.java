package jw.spigot_fluent_api.simple_commands.services;

import jw.spigot_fluent_api.simple_commands.SimpleCommand;
import jw.spigot_fluent_api.simple_commands.enums.CommandAccessType;
import jw.spigot_fluent_api.simple_commands.events.SimpleCommandEvent;
import jw.spigot_fluent_api.simple_commands.models.CommandArgument;
import jw.spigot_fluent_api.simple_commands.models.CommandTarget;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public interface CommandService {
    String connectArgs(String[] stringArray);

     Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments);

    boolean hasSenderAccess(CommandSender commandSender, List<CommandAccessType> commandAccessType);

    CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args);

    boolean hasSenderAccess(CommandSender commandSender, CommandAccessType commandAccessType);

    boolean hasSenderPermissions(CommandSender commandSender, List<String> permissons);

    boolean validateArguments(String[] args, List<CommandArgument> commandArguments);

   List<Consumer<SimpleCommandEvent>> getEventsToInvoke(CommandSender sender, HashMap<CommandAccessType, Consumer<SimpleCommandEvent>> events);
}
