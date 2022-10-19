package jw.fluent_api.minecraft.commands.api.services;

import jw.fluent_api.minecraft.commands.implementation.SimpleCommand;
import jw.fluent_api.minecraft.commands.api.enums.AccessType;
import jw.fluent_api.minecraft.commands.api.models.CommandArgument;
import jw.fluent_api.minecraft.commands.api.models.CommandTarget;
import jw.fluent_api.minecraft.commands.api.models.ValidationResult;
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
