package jw.fluent.api.spigot.commands.api.builder;
import jw.fluent.api.spigot.commands.implementation.validators.CommandArgumentValidator;
import jw.fluent.api.spigot.commands.api.enums.ArgumentDisplay;
import jw.fluent.api.spigot.commands.api.enums.ArgumentType;

import java.util.List;

public interface ArgumentBuilder
{
    ArgumentBuilder setType(ArgumentType type);

    ArgumentBuilder setTabComplete(List<String> tabComplete);

    ArgumentBuilder setTabComplete(String tabComplete);

    ArgumentBuilder setTabComplete(String tabComplete, int index);

    ArgumentBuilder setValidator(CommandArgumentValidator validator);

    ArgumentBuilder setArgumentDisplay(ArgumentDisplay argumentDisplayMode);

    ArgumentBuilder setDescription(String description);

}
