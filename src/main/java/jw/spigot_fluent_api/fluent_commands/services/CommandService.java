package jw.spigot_fluent_api.fluent_commands.services;

import jw.spigot_fluent_api.fluent_commands.SimpleCommand;
import jw.spigot_fluent_api.fluent_commands.enums.AccessType;
import jw.spigot_fluent_api.fluent_commands.models.CommandArgument;
import jw.spigot_fluent_api.fluent_commands.models.CommandTarget;
import jw.spigot_fluent_api.fluent_commands.models.ValidationResult;
import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandService {

    Object[] getArgumentValues(String[] args, List<CommandArgument> commandArguments);

    boolean hasSenderAccess(CommandSender commandSender, List<AccessType> commandAccessType);

    CommandTarget isSubcommandInvoked(SimpleCommand command, String[] args);

    boolean hasSenderAccess(CommandSender commandSender, AccessType commandAccessType);

    ValidationResult hasSenderPermissions(CommandSender commandSender, List<String> permissions);

    ValidationResult validateArguments(String[] args, List<CommandArgument> commandArguments);
}
